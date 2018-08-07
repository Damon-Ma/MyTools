package com.damon.adb;

import javax.swing.*;

public class MyButton {
    public JButton devicesBt,killBt,specialBt,logcatBt,recoveryBt,packageBt,installBt,sendBt,cleanLogBt,sideloadBt;
    public MyButton(){
        ButtonListener listener = new ButtonListener();

        devicesBt = new JButton("检查连接");
        killBt = new JButton("断开连接");
        specialBt = new JButton("定制系统连接");
        logcatBt = new JButton("抓取日志");
        recoveryBt = new JButton("recovery模式");
        packageBt = new JButton("当前运行包名");
        installBt = new JButton("安装");
        sendBt = new JButton("发送");
        cleanLogBt = new JButton("清除日志");
        sideloadBt = new JButton("sideload");

        listener.BtListener(devicesBt);
        listener.BtListener(killBt);
        listener.BtListener(specialBt);
        listener.BtListener(logcatBt);
        listener.BtListener(recoveryBt);
        listener.BtListener(packageBt);
        listener.BtListener(installBt);
        listener.BtListener(sendBt);
        listener.BtListener(cleanLogBt);
        listener.BtListener(sideloadBt);
    }
}
