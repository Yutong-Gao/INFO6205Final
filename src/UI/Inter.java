package UI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import Menace.Menace;
import Menace.Train;
import Menace.TrainRandom;


class Inter extends JFrame implements ActionListener
{

    JButton pvc;//two choose, person vs computer and person vs person
    JButton pvp;
    JPanel area;//area
    JLabel title;
    JFrame frame;
    Menace menace;

    Inter() throws IOException {//Initial interface
        frame=new JFrame();
        frame.setTitle("Menace Game");
        frame.setBounds(500,500,150,120);//set size
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

        frame.setVisible(true);//set frame visible
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//make frame close
        //train the menace
        TrainRandom train = new TrainRandom();
        menace = train.train(1000000);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton buttonSource=(JButton)e.getSource();
        if(buttonSource==pvp){
            //when choose person vs person, call pvp interface
            frame.setVisible(false);
            MyOXGame myoxgame=new MyOXGame();
        }
        if(buttonSource==pvc){
            //when choose person vs computer, call pvc interface
            frame.setVisible(false);
            OXGamePVC myoxgame=new OXGamePVC(menace);
          
        }

    }
}


