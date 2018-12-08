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

    public static JProgressBar uploadProgressBar;

   public MyJProgressBar() {

       uploadProgressBar = new JProgressBar();
       this.setUploadProgressBar();

   }

    private void setUploadProgressBar() {
        uploadProgressBar.setOrientation(JProgressBar.HORIZONTAL);
        uploadProgressBar.setStringPainted(true);
        uploadProgressBar.setSize(500, 100);
        uploadProgressBar.setMinimum(0);
        uploadProgressBar.setMaximum(100);
    }

    public static void setUploadProgress(int n){
       uploadProgressBar.setValue(n);
   }
}