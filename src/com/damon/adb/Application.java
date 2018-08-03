package com.damon.adb;

import javax.swing.*;

public class Application extends JFrame {

    public Application(MyPanel panel)
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 200);
        this.setTitle("adb命令");
        this.add(panel);
        this.setResizable(false);
        this.setVisible(true);
    }

    public static void main(String[] args)
    {
        MyPanel panel = new MyPanel();
        Application Frame = new Application(panel);
    }

}
