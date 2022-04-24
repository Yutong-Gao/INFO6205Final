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
 * @author Chuntao Cai
 */
public class GameClient extends JFrame implements Runnable {
    private final String host;//server's host

    private final JTextField jTextField;//which shows player his mark (O or X)
    private JTextArea jTextArea;
    private final JPanel panel;
    private final JPanel boardPanel;

    private Socket socket;
    private Scanner sc;
    private PrintWriter pw;

    private String myMark;
    private boolean myTurn;//in fact, control the loop of the mouse

    private final Square[][] board;
    private Square currentSquare;

    private final ExecutorService executorService;

    public GameClient(String host) {
        this.host = host;
        jTextField = new JTextField();
        add(jTextField, BorderLayout.NORTH);

        jTextArea = new JTextArea(4, 30);
        jTextArea.setFont(new Font("TimesRoman", Font.BOLD, 20));

        add(jTextArea, BorderLayout.EAST);

        panel = new JPanel();
        add(panel, BorderLayout.CENTER);
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3, 3, 0, 0));

        panel.add(boardPanel, BorderLayout.CENTER);

        board = new Square[3][3];

        for (int row = 0; row < board.length; row++) {
            // loop over the columns in the board
            for (int column = 0; column < board[row].length; column++) {
                // create square
                board[row][column] = new Square(" ", row * 3 + column);
                boardPanel.add(board[row][column]); // add square
            }
        }


        setSize(800, 500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //start the Client
        try {
            socket = new Socket(InetAddress.getByName(host), 6000);
            sc = new Scanner(socket.getInputStream());
            pw = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        executorService = Executors.newFixedThreadPool(1);
        executorService.execute(this);
    }

    @Override
    public void run() {
        myMark = sc.nextLine();
        jTextField.setText("You are player " + myMark);
        myTurn = (myMark.equals("O"));
        while (true) {
            actionToInput(sc.nextLine());
        }
    }

    private void actionToInput(String input) {
        switch (input) {
            case "Valid move.":
                jTextArea.append("Valid move. Please wait.\n");
                setMark(currentSquare, myMark);
                break;
            case "Wrong move, click another location.":
                jTextArea.append("Wrong move, click another location.\n");
                myTurn = true;
                break;
            case "Your turn.":
                int location = sc.nextInt();
                int i = location / 3;
                int j = location % 3;
                setMark(board[i][j], myMark.equals("O") ? "X" : "O");
                jTextArea.append("Your turn.");
                myTurn = true;
                break;

            case "You win!":
            case "You lose.":
            case "The game is draw.":
                jTextArea.append(input + "\n");
                myTurn = false;
                break;
            default:
                jTextArea.append(input + "\n");
                break;
        }
    }

    public void setMark(Square square, String mark) {
        square.mark = mark;
        repaint();
    }

    private class Square extends JPanel {
        private String mark; // mark to be drawn in this square
        private final int location; // location of square

        public Square(String squareMark, int squareLocation) {
            mark = squareMark; // set mark for this square
            location = squareLocation; // set location of this square

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    // set current square
                    currentSquare = Square.this;
                    // send location of this square to server
                    if (myTurn) {
                        pw.println(location); // send location to server
                        pw.flush();
                        myTurn = false; // not my turn any more
                    }
                }
            });
        }

        // return preferred size of Square
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(30, 30); // return preferred size
        }

        // return minimum size of Square
        @Override
        public Dimension getMinimumSize() {
            return getPreferredSize(); // return preferred size
        }

        // draw Square
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawRect(0, 0, 28, 28); // draw square
            g.drawString(mark, 11, 20); // draw mark
        }
    }

}
