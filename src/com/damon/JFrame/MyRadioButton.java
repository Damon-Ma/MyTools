package com.damon.JFrame;

import com.damon.Util.Config;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class MyRadioButton implements ItemListener {
    ButtonGroup netRadioButtonGroup = new ButtonGroup();
    public static JRadioButton netRadioButton1,netRadioButton2;
    public MyRadioButton(){
        netRadioButton1 = new JRadioButton("内网");
        netRadioButton2 = new JRadioButton("外网");
        netRadioButtonGroup.add(netRadioButton1);
        netRadioButtonGroup.add(netRadioButton2);

        netRadioButton1.addItemListener(this);
        netRadioButton2.addItemListener(this);
    }


    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == netRadioButton1){
            Config.signURL = "http://192.168.8.173:8082";
        }else if (e.getSource() == netRadioButton2){
            Config.signURL = "";
        }
    }
}
