import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Inter extends JFrame implements ActionListener
{
    //开局选择界面
    JButton pvc;//两种模式选择
    JButton pvp;
    JPanel area;//选择区域
    JLabel title;
    JFrame frame;

    Inter()
    {//界面生成
        frame=new JFrame();
        frame.setTitle("Menace Game");
        frame.setBounds(500,500,150,120);//窗体基本设置
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER));
        title=new JLabel("Menace Game");
        frame.add(title);
        pvc=new JButton("pvc");
        pvp=new JButton("pvp");
        area=new JPanel();
        area.setLayout(new FlowLayout());

        add(area,BorderLayout.SOUTH);
        area.add(pvc);
        area.add(pvp);
        pvc.addActionListener(this);
        pvp.addActionListener(this);
        frame.add(area);

        frame.setVisible(true);//设置可视化
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//使关闭键有用

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton buttonSource=(JButton)e.getSource();
        if(buttonSource==pvp){
            //判断是否使pvp键，如果是，进入pvp模式
            frame.setVisible(false);
            MyOXGame myoxgame=new MyOXGame();
        }
        if(buttonSource==pvc){
            //判断是否使pvc键，如果是，进入pvc模式
            frame.setVisible(false);
            MyOXGame myoxgame=new MyOXGame();
        }

    }
}


