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
 * @author Chuntao Cai, NUID: 002960552
 * To be modified
 */
public class GameServer extends JFrame {
    private final String[] gameBoard = new String[9];
    private final Player[] players;

    private ServerSocket myListener;

    private int currentPlayer;
    private final int PlayerO = 0;
    private final int PlayerX = 1;
    private final String chessPiece[] = {"O", "X"}; //two kinds of chess Piece
    private final ExecutorService executorService;//execute a thread pool where players can run

    private final Lock lock;
    private final Condition bothPlayersConnected;
    private final Condition opponentTurn;

    private final JTextArea jTextArea;//show the process of the game in the server's frame

    public GameServer() {
        super("Tic-tac-toe Game Server");
        players = new Player[2];

        try {
            myListener = new ServerSocket(6000, 2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        currentPlayer = PlayerO;//set PlayerO (O) as the first player;

        executorService = Executors.newFixedThreadPool(2);//2 players, 2 threads

        lock = new ReentrantLock();//game lock

        bothPlayersConnected = lock.newCondition();
        opponentTurn = lock.newCondition();

        jTextArea = new JTextArea();
        add(jTextArea, BorderLayout.CENTER);//show texts in the center area of the frame
        jTextArea.setText("Server start\n");

        setSize(450, 450);//size of the server's window
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    class Player implements Runnable {
        private final Socket LinkSocket;//the connection to client
        private Scanner sc;//input from client
        private PrintWriter pw;//output to client
        private final int playerNumber;//0 or 1, stands for O or X
        private boolean suspended = true;

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


        @Override
        public void run() {
            jTextArea.append("Player " + chessPiece[playerNumber] + " joined the game.\n");
            pw.println("Your Mark is " + chessPiece[playerNumber]);//tell player his chessPiece is O or X
            pw.flush();
            //if this player is the first player(O), waiting for another one to join
            if (playerNumber == 0) {
                pw.println("You are connected to the game.\n" + "Waiting for another player...");
                pw.flush();

                lock.lock();
                try {
                    while (suspended) {
                        bothPlayersConnected.await();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
                pw.println("Your opponent joined the game. Your turn.");
                pw.flush();
            } else {
                pw.println("You are the second player. Please wait for Player X's move.");
            }

            /**
             * Both players has joined the game. Game starts.
             */
            while (!isGameOver()) {
                int location = sc.nextInt();

                if (currentPlayer != playerNumber) {
                    lock.lock();
                    try {
                        opponentTurn.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }

                if (!isOccupied(location)) {//put chessPiece
                    gameBoard[location] = chessPiece[playerNumber];

                    jTextArea.append("Player " + chessPiece[playerNumber] + " put chess at: " + location + "\n");
                    pw.println("Valid move.");
                    pw.flush();

                    currentPlayer = (currentPlayer + 1) % 2; //change to another player

                    lock.lock();
                    opponentTurn.signal();
                    lock.unlock();

                    if (hasWinner()) {
                        pw.println("You win!");
                        pw.flush();
                        players[currentPlayer].pw.println("You lose.");
                    } else if (isFull()) {
                        pw.println("The game is draw.");
                        players[currentPlayer].pw.println("The game is draw");
                    } else {
                        players[currentPlayer].pw.println("Your turn.");//tell another player to go
                        players[currentPlayer].pw.flush();
                    }
                } else {
                    pw.println("Invalid move, click another location.");
                    pw.flush();
                }
            }

            /**
             * Game Over.
             * Close socket.
             */

            try {
                LinkSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    public boolean isGameOver() {
        return hasWinner() || isFull();
    }

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

    public boolean isFull() {
        for (int i = 0; i < gameBoard.length; i++) {
            if (gameBoard[i].isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public boolean isOccupied(int location) {
        return !gameBoard[location].equals(null);
    }


    public void execute() {
        //add each player to the thread pool
        try {
            players[0] = new Player(myListener.accept(), 0);
            players[1] = new Player(myListener.accept(), 1);
            executorService.execute(players[0]);
            executorService.execute(players[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        lock.lock();
        players[0].suspended = false;
        bothPlayersConnected.signal();
        lock.unlock();
    }
}
