package GameClient;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The GameClient class tells the server this player's next move.
 *
 * @author Chuntao Cai
 */
public class GameClient extends JFrame implements Runnable {
    private final String host;//server's host, used to connect the server

    private final JTextField jTextField;//show the player his mark (O or X)
    private JTextArea jTextArea;//show the instructions to the client
    private final JPanel panel;//the panel of the client
    private final JPanel boardPanel;//the panel of the game

    private Socket socket;//send connection requests to the server
    private Scanner sc;//the input from the server
    private PrintWriter pw;//the output to the server

    private String myMark;//O or X
    private boolean myTurn;//control whether the mouse click is valid

    private final Square[][] board;// 9 squares form up the board
    private Square currentSquare;

    private final ExecutorService executorService;

    public GameClient(String host) {
        super("Tic-tac-toe Game Client");//the title of the client's window
        this.host = host;

        jTextField = new JTextField();
        add(jTextField, BorderLayout.NORTH);//show his marks in the client
        jTextField.setFont(new Font("TimesRoman", Font.BOLD, 30));
        jTextArea = new JTextArea(1, 25);
        jTextArea.setFont(new Font("TimesRoman", Font.BOLD, 20));

        add(jTextArea, BorderLayout.EAST);//show the instructions and the process of the game

        panel = new JPanel();//where put the boardPanel
        add(panel);

        boardPanel = new JPanel();//where put the game board
        boardPanel.setLayout(new GridLayout(3, 3, 0, 0));
        panel.add(boardPanel);

        //initialize the game board
        board = new Square[3][3];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new Square(" ", 3 * i + j);
                boardPanel.add(board[i][j]);
            }
        }

        setSize(800, 450);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            socket = new Socket(InetAddress.getByName(this.host), 6000);//connect the server
            sc = new Scanner(socket.getInputStream());
            pw = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        executorService = Executors.newFixedThreadPool(1);

    }

    @Override
    public void run() {
        myMark = sc.nextLine();//get the mark (O or X) from the server
        jTextField.setText("You are Player " + myMark);
        myTurn = (myMark.equals("O"));
        while (true) {
            actionsToInput(sc.nextLine());//process the information sent by the server
        }
    }

    //what the client should do when receiving information from the server
    private void actionsToInput(String input) {
        switch (input) {
            case "Valid move.":
                jTextArea.append("Valid move. ");
                setMark(currentSquare, myMark);
                break;
            case "Please wait.":
                jTextArea.append("Please wait.\n");
                break;
            case "Wrong move. Click another location.":
                jTextArea.append("Wrong move. Click another location.\n");
                myTurn = true;
                break;
            case "Your turn.":
                //modify the state of the game according to the server
                int location = sc.nextInt();
                int i = location / 3;
                int j = location % 3;
                setMark(board[i][j], myMark.equals("O") ? "X" : "O");
                jTextArea.append("Your turn.");
                myTurn = true;
                break;
            case "You win!":
            case "You lose.":
            case "The game is a draw.":
                jTextArea.append(input + "\n");
                location = sc.nextInt();
                i = location / 3;
                j = location % 3;
                setMark(board[i][j], myMark.equals("O") ? "X" : "O");
                myTurn = false;
                break;
            default:
                jTextArea.append(input + "\n");
                break;
        }
    }

    public void setMark(Square square, String mark) {
        square.mark = mark;
        repaint();//repaint the square with new mark (change from " " to "X" or "O")
    }

    public class Square extends JPanel {
        private String mark;
        private final int location; // location of square

        public Square(String squareMark, int squareLocation) {
            mark = squareMark;
            location = squareLocation;

            //get each move by mouse click
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    currentSquare = Square.this;
                    // send location of this square to server
                    if (myTurn) {
                        pw.println(location); // send location to the game server
                        pw.flush();
                        myTurn = false;
                    }
                }
            });
        }

        // set size of each square
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(80, 80);
        }

        // draw square
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawRect(0, 0, 78, 78); // draw the shape of a square
            g.setFont(new Font("Arial", Font.BOLD, 80));
            g.drawString(mark, 10, 70); // draw mark
        }
    }

    /**
     * execute the client
     */
    public void execute() {
        executorService.execute(this);
    }
}
