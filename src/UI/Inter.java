package UI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import Menace.Menace;
import Menace.Train;


class Inter extends JFrame implements ActionListener
{
    //å¼€å±€é€‰æ‹©ç•Œé�¢
    JButton pvc;//ä¸¤ç§�æ¨¡å¼�é€‰æ‹©
    JButton pvp;
    JPanel area;//é€‰æ‹©åŒºåŸŸ
    JLabel title;
    JFrame frame;
    Menace menace;

    Inter()
    {//ç•Œé�¢ç”Ÿæˆ�
        frame=new JFrame();
        frame.setTitle("Menace Game");
        frame.setBounds(500,500,150,120);//çª—ä½“åŸºæœ¬è®¾ç½®
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

        frame.setVisible(true);//è®¾ç½®å�¯è§†åŒ–
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//ä½¿å…³é—­é”®æœ‰ç”¨
        //train the menace
        Train train = new Train();
        menace = train.train(100);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton buttonSource=(JButton)e.getSource();
        if(buttonSource==pvp){
            //åˆ¤æ–­æ˜¯å�¦ä½¿pvpé”®ï¼Œå¦‚æžœæ˜¯ï¼Œè¿›å…¥pvpæ¨¡å¼�
            frame.setVisible(false);
            MyOXGame myoxgame=new MyOXGame();
        }
        if(buttonSource==pvc){
            //åˆ¤æ–­æ˜¯å�¦ä½¿pvcé”®ï¼Œå¦‚æžœæ˜¯ï¼Œè¿›å…¥pvcæ¨¡å¼�
            frame.setVisible(false);
            OXGamePVC myoxgame=new OXGamePVC(menace);
          
        }

    }
}


