package com.damon.adb;

import javax.swing.*;

public class MyButton {
    public JButton devicesBt,killBt,specialBt,logcatBt,recoveryBt,packageBt,installBt,sendBt;
    public MyButton(){
        ButtonListener listener = new ButtonListener();

        devicesBt = new JButton("adb devices");
        killBt = new JButton("kill-server");
        specialBt = new JButton("adb 5.1");
        logcatBt = new JButton("logcat");
        recoveryBt = new JButton("recovery");
        packageBt = new JButton("package/activity");
        installBt = new JButton("Install/Sideload");
        sendBt = new JButton("send");

        listener.BtListener(devicesBt);
        listener.BtListener(killBt);
        listener.BtListener(specialBt);
        listener.BtListener(logcatBt);
        listener.BtListener(recoveryBt);
        listener.BtListener(packageBt);
        listener.BtListener(installBt);
        listener.BtListener(sendBt);
    }
}
