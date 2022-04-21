package UI;

import javax.swing.*;

import Model.CheeseState;
import Model.Move;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

class OXGamePVC extends JFrame implements ActionListener {
    JButton[] button;
    JButton restart;
    JButton quit;
    JLabel judgement;
    JPanel chessboard,basicPanel;
    int player=0;
    boolean gameOverFlag=false;
    CheeseState cs = new CheeseState();
    HashMap<String,Move> movelist = new HashMap<>();

    OXGamePVC(){
        setTitle("Menace Game");
        setBounds(500,500,300,300);

        button=new JButton[10];
        restart=new JButton("Restart");
        quit=new JButton("Quit");
        chessboard=new JPanel();
        basicPanel=new JPanel();
        judgement=new JLabel("");
        chessboard.setLayout(new GridLayout(3,3));
        basicPanel.setLayout(new FlowLayout());


        add(chessboard,BorderLayout.CENTER);
        add(basicPanel,BorderLayout.SOUTH);
        basicPanel.add(restart);
        basicPanel.add(judgement);
        basicPanel.add(quit);

        initChessboard();
        addChessToChessboard(chessboard);
        restart.addActionListener(this);
        quit.addActionListener(this);

        buttonListener();

        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void addChessToChessboard(JPanel chessboard){
        
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
        
        for(int i=1;i<chess.length;i++){
            if (chess[i]>='1'&&chess[i]<='9')
                return false;
        }
        return true;
    }

    private int checkWinner(int playerNow) {
        
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
        
        int statueCode;
        JButton buttonSource=(JButton)actionEvent.getSource();
        if(buttonSource==restart){
           
            restartTheGame();
        }
        if(buttonSource==quit)
        {
            setVisible(false);
            Inter inter=new Inter();
        }
        if (gameOverFlag==true) return;
        String buttonMark=buttonSource.getLabel();
        if(buttonMark.matches("[0-9]")){
           // if(player==0) {
                buttonSource.setLabel("O");
                buttonSource.setFont(new Font("Arial",2,10));
                
                statueCode=checkWinner(player);
                if(statueCode==0){
                    judgement.setText("Player O wins the game! ");
                    trainLose();
                    gameOverFlag=true;
                }
                else if(statueCode==1){
                    judgement.setText("Player X wins the game!");
                    trainWin();
                    gameOverFlag=true;
                }
                else if(statueCode==-1){
                    judgement.setText("The game a draw!");
                    trainDraw();
                }
                
                player=(player+1)%2;
                if(statueCode==-2) {
            	String state = getCheeseState();//get current state.
            	Move move = cs.nextMove(state);//get next move
            	movelist.put(state,move);
            	int i = move.getName();
                button[i].setLabel("X");
                button[i].setFont(new Font("Arial",2,10));
                
          //  }
            statueCode=checkWinner(player);
            if(statueCode==0){
                judgement.setText("Player O wins the game! ");
                trainLose();
                gameOverFlag=true;
            }
            else if(statueCode==1){
                judgement.setText("Player X wins the game!");
                trainWin();
                gameOverFlag=true;
            }
            else if(statueCode==-1){
                judgement.setText("The game a draw!");
                trainDraw();
            }
                }
            player=(player+1)%2;
        }
    }
    
    public String getCheeseState() {
    	//get current cheese state
    	String state = "";
        for(int i=1;i<=9;i++){
            char c =button[i].getLabel().charAt(0);
            if(c=='O') state +="1";
            else if(c=='X') state +="2";
            else state +="0";
        }
        return state;
    }
    
    public void trainWin() {
    	//train the machine
    	for(String key:movelist.keySet()) {
    		cs.win(key, movelist.get(key));
    	}
    }
    
    public void trainLose() {
    	//train the machine
    	for(String key:movelist.keySet()) {
    		cs.lose(key, movelist.get(key));
    	}
    }
    
    public void trainDraw() {
    	//train the machine
    	for(String key:movelist.keySet()) {
    		cs.draw(key, movelist.get(key));
    	}
    }

}
