package com.damon.adb;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener implements ActionListener {

    public void BtListener(JButton devicesBt){
        devicesBt.addActionListener(this::actionPerformed);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "检查连接":
                CommandThread devices = new CommandThread(Keys.DEVICES.getName());
                devices.start();
                break;
            case "结束adb":
                CommandThread kill = new CommandThread(Keys.kill_server.getName());
                kill.start();
                break;
            case "检查连接S":
               CommandThread spAdb = new CommandThread(Keys.spdevices.getName());
               spAdb.start();
               break;
            case "抓取日志":
                CommandThread logcat = new CommandThread(Keys.logcat.getName());
                logcat.start();
                break;
            case "recovery模式":
                CommandThread recovery = new CommandThread(Keys.recovery.getName());
                recovery.start();
                break;
            case "当前运行包名":
                CommandThread thispackage = new CommandThread(Keys.getpackage.getName());
                thispackage.start();
                break;
            case "安装":
                CommandThread install = new CommandThread(Keys.install.getName());
                install.start();
                break;
            case "发送":
                CommandThread send = new CommandThread(Keys.send.getName());
                send.start();
                break;
            case "清除日志":
                CommandThread cleanLog = new CommandThread(Keys.cleanLog.getName());
                cleanLog.start();
                break;
            case "sideload":
                CommandThread sideload = new CommandThread(Keys.sideload.getName());
                sideload.start();
                break;
        }
    }
}
