package com.damon.adb;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener implements ActionListener {

    int i = 0;

    public void BtListener(JButton devicesBt){
        devicesBt.addActionListener(this::actionPerformed);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "检查连接":
                CommandThread devices = new CommandThread(Keys.DEVICES);
                devices.start();
                break;
            case "断开连接":
                CommandThread kill = new CommandThread(Keys.kill_server);
                kill.start();
                break;
            case "定制系统连接":
               CommandThread spAdb = new CommandThread(Keys.spdevices);
               spAdb.start();
               break;
            case "抓取日志":
                CommandThread logcat = new CommandThread(Keys.logcat);
                logcat.start();
                break;
            case "recovery模式":
                CommandThread recovery = new CommandThread(Keys.recovery);
                recovery.start();
                break;
            case "当前运行包名":
                CommandThread thispackage = new CommandThread(Keys.getpackage);
                thispackage.start();
                break;
            case "安装":
                CommandThread install = new CommandThread(Keys.install);
                install.start();
                break;
            case "发送":
                CommandThread send = new CommandThread(Keys.send);
                send.start();
                break;
            case "清除日志":
                CommandThread cleanLog = new CommandThread(Keys.cleanLog);
                cleanLog.start();
                break;
            case "sideload":
                CommandThread sideload = new CommandThread(Keys.sideload);
                sideload.start();
                break;
            case "清屏":
                Application.cleanOutText();
                break;
            case "清空":
                Application.cleanInputBox();
                break;
            case "返回主界面":
                CommandThread toHome = new CommandThread(Keys.toHome);
                toHome.start();
                break;
            case "检查sideload":
                CommandThread isSideload = new CommandThread(Keys.isSideload);
                isSideload.start();
                break;
            case "adb shell":
                CommandThread shell = new CommandThread(Keys.shell);
                shell.start();
                break;
            case "monitor":
                CommandThread monitor = new CommandThread(Keys.monitor);
                monitor.start();
                break;
        }
    }


    private void hello(){
        i++;
        if (i==8){
            while (true){
                int n = JOptionPane.showConfirmDialog(null,
                        "     你是猪么？",
                        "提示",JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        new ImageIcon(this.getClass().getClassLoader().getResource("2.jpg")));
                if (n==0){
                    JOptionPane.showMessageDialog(null,
                            "好的，我知道了^(*￣(oo)￣)^",
                            "提示",JOptionPane.WARNING_MESSAGE,
                            new ImageIcon(this.getClass().getClassLoader().getResource("3.jpg")));
                    break;
                }
            }
            i = 0;
        }
    }
}
