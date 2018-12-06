package com.damon.JFrame;

import javax.swing.*;

import java.awt.*;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.util.concurrent.TimeUnit;

/**

* Created by MyWorld on 2016/3/24.

*/

public class MyJProgressBar {

    public static JProgressBar progressBar;

   public MyJProgressBar() {

       progressBar = new JProgressBar();

       progressBar.setOrientation(JProgressBar.HORIZONTAL);
       progressBar.setStringPainted(true);
       progressBar.setSize(500, 100);
       progressBar.setMinimum(0);
       progressBar.setMaximum(100);

   }
}