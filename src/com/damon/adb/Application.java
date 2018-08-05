package com.damon.adb;

import javax.swing.*;
import java.awt.*;

public class Application extends JFrame {
    //定义面板，并设置为网格布局，4行2列，组件水平、垂直间距均为3
    JPanel l = new JPanel();
    JPanel c = new JPanel();
    JPanel r = new JPanel();



    //定义文本
    JLabel inputText = new JLabel("adb shell input text ");
    JLabel outputText = new JLabel("result");
    JTextField text = new JTextField(12);
    JButton inputBt = new JButton("发送");

    public Application(String s)
    {
        super(s);

       // cText.setPreferredSize(new Dimension(10,10));
        this.setLayout(new GridLayout(1, 3,3,3));  //网格式布局

        l.add(inputText);
        c.add(text);
        r.add(inputBt);
        r.add(outputText);

        getContentPane().add(l,BorderLayout.WEST);
        getContentPane().add(c,BorderLayout.CENTER);
        getContentPane().add(r,BorderLayout.EAST);

        setVisible(true);
        setSize(450,200);
       // this.setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);               //让窗体居中显示
    }

    public static void main(String[] args)
    {
        Application application=new Application("工具");
    }

}
