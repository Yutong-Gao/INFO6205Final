package GameServer;


import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * I designed a server that can link with two clients on the same computer
 * and provide services for a tic-tac-toe game.
 *
 * The GameServer class can tell each client the state of the game
 * and what to do next.
 *
 * @author Chuntao Cai
 */
public class GameServer extends JFrame {
    private final String[] gameBoard = new String[9];//the board of the game, 9 locations to place chess
    private final Player[] players;//players of the game

    private ServerSocket myListener;//waiting for requests from clients (i.e. players) to come in over the network

    private int currentPlayer;//can be changed between player0 & playerX (i.e. 0 or 1)
    private final int PlayerO = 0;//Player O = 0, Player X = 1

    private final String[] chessPiece = {"O", "X"}; //two kinds of chess Piece
    private final ExecutorService executorService;//execute a thread pool where players can run

    private final Lock lock;//a game lock controlling access to the game by multiple threads

    //different conditions to control the lock
    private final Condition bothPlayersConnected;
    private final Condition opponentTurn;

    private JTextArea jTextArea;//show the process of the game in Server's frame

    public GameServer() {
        super("Tic-tac-toe Game Server");//the title of the server's window
        for (int i = 0; i < 9; i++) {
            gameBoard[i] = "";//initialize the game board
        }
        players = new Player[2];

        try {
            myListener = new ServerSocket(6000, 2);//limit the number of threads to 2
        } catch (IOException e) {
            e.printStackTrace();
        }

        currentPlayer = PlayerO;//set Player O as the first player

        executorService = Executors.newFixedThreadPool(2);//2 players, 2 threads

        lock = new ReentrantLock();//game lock

        bothPlayersConnected = lock.newCondition();
        opponentTurn = lock.newCondition();

        jTextArea = new JTextArea();
        add(jTextArea, BorderLayout.CENTER);//show texts in the center area of the frame
        jTextArea.setText("Server starts.\nWaiting for players' connection.\n");
        jTextArea.setFont(new Font("TimesRoman", Font.BOLD, 20));//modify font format

        setSize(450, 450);//size of the server's window
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    class Player implements Runnable {
        private final Socket LinkSocket;//the connection to client
        private Scanner sc;//input from client
        private PrintWriter pw;//output to client
        private final int playerNumber;//0 or 1, stands for O or X

        public Player(Socket socket, int playerNumber) {
            LinkSocket = socket;
            this.playerNumber = playerNumber;

            try {
                sc = new Scanner(LinkSocket.getInputStream());
                pw = new PrintWriter(LinkSocket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * Override Player's run
         */

        @Override
        public void run() {
            jTextArea.append("Player " + chessPiece[playerNumber] + " joined the game.\n");//show in the server that someone joined the game
            pw.println(chessPiece[playerNumber]);//tell the player his mark
            pw.flush();

            //if this player is the first player(O), waiting for another one to join
            if (playerNumber == 0) {
                pw.println("You are connected to the game.\n" + "Waiting for another player...");
                pw.flush();

                lock.lock();
                try {

                    bothPlayersConnected.await();//waiting for another player to join; lock the game

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }

                pw.println("Your opponent joined the game. Your turn.");
            } else {
                pw.println("You are the second player. \nPlease wait for Player O's move.");
            }
            pw.flush();
            /*
             * Both players joined the game. Game starts.
             */
            while (!isGameOver()) {
                int location = sc.nextInt();//get the location of the chess piece from client
                /*
                 * if it's not the player's turn, wait.
                 */
                if (currentPlayer != playerNumber) {
                    lock.lock();
                    try {
                        opponentTurn.await();//waiting for player's own turn
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
                /*
                 * This player's turn now.
                 */
                if (!isOccupied(location)) {
                    gameBoard[location] = chessPiece[playerNumber];//place chessPiece

                    jTextArea.append("Player " + chessPiece[playerNumber] + " put chess at: " + location + "\n");
                    pw.println("Valid move.");
                    pw.flush();

                    currentPlayer = (currentPlayer + 1) % 2; //change to another player
                    /*
                     * examine whether the game is over. if not, go ahead.
                     */
                    if (hasWinner()) {
                        pw.println("You win!");
                        pw.flush();
                        players[currentPlayer].pw.println("You lose.");
                        players[currentPlayer].pw.flush();
                        players[currentPlayer].pw.println(location);
                        players[currentPlayer].pw.flush();
                        jTextArea.append("Player " + chessPiece[playerNumber] + " wins the game.");
                    } else if (isFull()) {
                        pw.println("The game is a draw.");
                        pw.flush();
                        players[currentPlayer].pw.println("The game is a draw.");
                        players[currentPlayer].pw.flush();
                        players[currentPlayer].pw.println(location);
                        players[currentPlayer].pw.flush();
                        jTextArea.append("The game is a draw.");
                    } else {
                        /*
                         * go ahead
                         */
                        pw.println("Please wait.");
                        pw.flush();
                        players[currentPlayer].pw.println("Your turn.");//tell another player to go
                        players[currentPlayer].pw.flush();
                        players[currentPlayer].pw.println(location);
                        players[currentPlayer].pw.flush();

                        lock.lock();
                        opponentTurn.signal();//signal another player to go
                        lock.unlock();
                    }
                } else {
                    pw.println("Wrong move. Click another location.");//tell the client to choose another location
                    pw.flush();
                }
            }
            /*
             * Game Over.
             * Close the socket.
             */
            try {
                LinkSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //determine whether the game is over
    public boolean isGameOver() {
        return hasWinner() || isFull();
    }

    //judge if someone has won the game
    public boolean hasWinner() {
        return (!gameBoard[0].isEmpty() && gameBoard[0].equals(gameBoard[1]) && gameBoard[0].equals(gameBoard[2]))
                || (!gameBoard[3].isEmpty() && gameBoard[3].equals(gameBoard[4]) && gameBoard[3].equals(gameBoard[5]))
                || (!gameBoard[6].isEmpty() && gameBoard[6].equals(gameBoard[7]) && gameBoard[6].equals(gameBoard[8]))
                || (!gameBoard[0].isEmpty() && gameBoard[0].equals(gameBoard[3]) && gameBoard[0].equals(gameBoard[6]))
                || (!gameBoard[1].isEmpty() && gameBoard[1].equals(gameBoard[4]) && gameBoard[1].equals(gameBoard[7]))
                || (!gameBoard[2].isEmpty() && gameBoard[2].equals(gameBoard[5]) && gameBoard[2].equals(gameBoard[8]))
                || (!gameBoard[0].isEmpty() && gameBoard[0].equals(gameBoard[4]) && gameBoard[0].equals(gameBoard[8]))
                || (!gameBoard[2].isEmpty() && gameBoard[2].equals(gameBoard[4]) && gameBoard[2].equals(gameBoard[6]));
    }

    //judge if the board is full
    public boolean isFull() {
        for (String s : gameBoard) {
            if (s.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    //judge if the move is valid
    public boolean isOccupied(int location) {
        return !gameBoard[location].equals("");
    }

    public void execute() {
        //add both players to the thread pool
        try {
            players[0] = new Player(myListener.accept(), 0);
            executorService.execute(players[0]);
            players[1] = new Player(myListener.accept(), 1);
            executorService.execute(players[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        lock.lock();

        bothPlayersConnected.signal();//signal Player O to start
        lock.unlock();
    }
}
