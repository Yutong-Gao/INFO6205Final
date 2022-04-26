package UI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

class MyOXGame extends JFrame implements ActionListener {//pvp
    JButton[] button;//represent the grids on the checkerboard
    JButton restart;//restart the game
    JButton quit;//return to former interface
    JLabel judgement;//judge the result
    JPanel chessboard,basicPanel;//checkerboard
    int player=0;//number of player
    boolean gameOverFlag=false;//judge whether the game is over

    MyOXGame(){//begin game
        setTitle("Menace Game");
        setBounds(500,500,300,300);//set the window

        button=new JButton[10];
        restart=new JButton("Restart");
        quit=new JButton("Quit");
        chessboard=new JPanel();
        basicPanel=new JPanel();
        judgement=new JLabel("");
        chessboard.setLayout(new GridLayout(3,3));
        basicPanel.setLayout(new FlowLayout());//initialize the module


        add(chessboard,BorderLayout.CENTER);
        add(basicPanel,BorderLayout.SOUTH);
        basicPanel.add(restart);
        basicPanel.add(judgement);//
        basicPanel.add(quit);

        initChessboard();//initialize the checkerboard
        addChessToChessboard(chessboard);//put button on checkerboear
        restart.addActionListener(this);//add monitor
        quit.addActionListener(this);

        buttonListener();//add monitor to button

        setVisible(true);//set visible
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void addChessToChessboard(JPanel chessboard){
        //add buttons to checkerboard
        for(int i=1;i<=9;i++) {
            chessboard.add(button[i]);
        }
    }

    private void initChessboard(){

        for(int i=1;i<=9;i++){
            button[i]=new JButton(""+i);
            button[i].setFont(new Font("Arial",2,0));
        }
    }

    private void buttonListener(){

        for(int i=1;i<=9;i++)
        {
            button[i].addActionListener(this);
        }
    }

    private void restartTheGame(){
        //restart game
        chessboard.removeAll();
        chessboard.setLayout(new GridLayout(3,3));
        initChessboard();
        addChessToChessboard(chessboard);
        buttonListener();
        chessboard.updateUI();
        chessboard.repaint();

        judgement.setText("");
        gameOverFlag=false;
    }
    private boolean checkDogfall(char[] chess){
        //check if draw
        for(int i=1;i<chess.length;i++){
            if (chess[i]>='1'&&chess[i]<='9')
                return false;
        }
        return true;
    }

    private int checkWinner(int playerNow) {
        //check game, -2 means game in not over, -1 means draw, 0 means player 0 win, 1 means player x win
        char[] chess=new char[10];
        for(int i=1;i<=9;i++){
            chess[i]=button[i].getLabel().charAt(0);
        }
        if(     (chess[1]==chess[2]&&chess[2]==chess[3])||
                (chess[4]==chess[5]&&chess[5]==chess[6])||
                (chess[7]==chess[8]&&chess[8]==chess[9])||
                (chess[1]==chess[5]&&chess[5]==chess[9])||
                (chess[7]==chess[5]&&chess[5]==chess[3])||
                (chess[1]==chess[4]&&chess[4]==chess[7])||
                (chess[2]==chess[5]&&chess[5]==chess[8])||
                (chess[3]==chess[6]&&chess[6]==chess[9])){

            return playerNow;
        }
        else if(checkDogfall(chess))
            return -1;
        return -2;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        //response to action
        int statueCode;
        JButton buttonSource=(JButton)actionEvent.getSource();
        if(buttonSource==restart){
            //judge if restart
            restartTheGame();
        }
        if(buttonSource==quit)
        {//judge if quit
            setVisible(false);
            try {
                Inter inter=new Inter();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (gameOverFlag==true) return;
        String buttonMark=buttonSource.getLabel();
        if(buttonMark.matches("[0-9]")){
            if(player==0) {
                buttonSource.setLabel("O");//fill the button
                buttonSource.setFont(new Font("Arial",2,10));
            }
            else {
                buttonSource.setLabel("X");
                buttonSource.setFont(new Font("Arial",2,10));
            }
            statueCode=checkWinner(player);
            if(statueCode==0){
                judgement.setText("Player O wins the game! ");
                gameOverFlag=true;
            }
            else if(statueCode==1){
                judgement.setText("Player X wins the game!");
                gameOverFlag=true;
            }
            else if(statueCode==-1){
                judgement.setText("The game a draw!");
            }
            player=(player+1)%2;
        }
    }

}
