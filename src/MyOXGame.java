import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MyOXGame extends JFrame implements ActionListener {
    //主体部分，绘制GUI，编辑有关响应
    JButton[] button;//对象数组，表示棋盘上的九个格子
    JButton restart;//重置键
    JButton quit;//返回上一级
    JLabel judgement;//裁判，用来显示游戏的结果
    JPanel chessboard,basicPanel;//棋盘，棋盘下部区域
    int player=0;//显示当前玩家编号
    boolean gameOverFlag=false;//记录游戏是否结束，如果结束值为true，停止对事件的响应

    MyOXGame(){//生成函数，生成游戏
        setTitle("Menace Game");
        setBounds(500,500,300,300);//窗体基本设置

        button=new JButton[10];
        restart=new JButton("Restart");
        quit=new JButton("Quit");
        chessboard=new JPanel();
        basicPanel=new JPanel();
        judgement=new JLabel("");
        chessboard.setLayout(new GridLayout(3,3));
        basicPanel.setLayout(new FlowLayout());//组件初始化设置


        add(chessboard,BorderLayout.CENTER);
        add(basicPanel,BorderLayout.SOUTH);
        basicPanel.add(restart);
        basicPanel.add(judgement);//布局设置
        basicPanel.add(quit);

        initChessboard();//初始化棋盘，使九个按钮按顺序加上1-9的标签，
        addChessToChessboard(chessboard);//将九个按钮添加至chessboard
        restart.addActionListener(this);//添加监听器
        quit.addActionListener(this);

        buttonListener();//为按钮添加监听器

        setVisible(true);//设置可视化
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//使关闭键有用
    }

    private void addChessToChessboard(JPanel chessboard){
        //添加按钮至棋盘
        for(int i=1;i<=9;i++) {
            chessboard.add(button[i]);
        }
    }

    private void initChessboard(){
        //使棋子按钮初始化为1-9
        for(int i=1;i<=9;i++){
            button[i]=new JButton(""+i);
            button[i].setFont(new Font("Arial",2,0));
        }
    }

    private void buttonListener(){
        //为棋子按钮添加监听器
        for(int i=1;i<=9;i++)
        {
            button[i].addActionListener(this);
        }
    }

    private void restartTheGame(){
        //重置游戏
        chessboard.removeAll();//必须于repaint和updateUI一起用不然要不啥都没有，要不没有改动
        //必须注意removll会清空布局
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
        //检查是否被填满，填满即为平局
        for(int i=1;i<chess.length;i++){
            if (chess[i]>='1'&&chess[i]<='9')
                return false;
        }
        return true;
    }

    private int checkWinner(int playerNow) {
        //检查当前游戏状态，-2表示没有决出胜负，-1表示平局。0表示O玩家胜利，1表示X玩家胜利
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
            //判断当前玩家是否为赢家
            return playerNow;
        }
        else if(checkDogfall(chess))
            return -1;
        return -2;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        //对监听的事件进行响应
        int statueCode;
        JButton buttonSource=(JButton)actionEvent.getSource();
        if(buttonSource==restart){
            //判断是否使restart键，如果是，重置游戏
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
            if(player==0) {
                buttonSource.setLabel("O");
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
