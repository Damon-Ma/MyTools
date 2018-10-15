package com.damon.Listener;


import com.damon.JFrame.MyTabbedPane;
import com.damon.JFrame.MyTable;
import com.damon.JFrame.MyTextArea;
import com.damon.JFrame.MyTextField;
import com.damon.Util.Keys;
import com.damon.adb.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener implements ActionListener {

    public void BtListener(JButton devicesBt){
        devicesBt.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()){
            case "检查连接":
                MyTabbedPane.jTabbedpane.setSelectedIndex(0);
                CommandThread devices = new CommandThread(Keys.DEVICES);
                devices.start();
                break;
            case "断开连接":
                MyTabbedPane.jTabbedpane.setSelectedIndex(0);
                CommandThread kill = new CommandThread(Keys.kill_server);
                kill.start();
                break;
            case "定制系统连接":
                MyTabbedPane.jTabbedpane.setSelectedIndex(0);
                CommandThread spAdb = new CommandThread(Keys.spdevices);
               spAdb.start();
               break;
            case "抓取日志":
                MyTabbedPane.jTabbedpane.setSelectedIndex(0);
                CommandThread logcat = new CommandThread(Keys.logcat);
                logcat.start();
                break;
            case "recovery模式":
                MyTabbedPane.jTabbedpane.setSelectedIndex(0);
                CommandThread recovery = new CommandThread(Keys.recovery);
                recovery.start();
                break;
            case "当前运行包名":
                MyTabbedPane.jTabbedpane.setSelectedIndex(0);
                CommandThread thispackage = new CommandThread(Keys.getpackage);
                thispackage.start();
                break;
            case "安装":
                MyTabbedPane.jTabbedpane.setSelectedIndex(1);
                CommandThread install = new CommandThread(Keys.install);
                install.start();
                break;
            case "发送":
                CommandThread send = new CommandThread(Keys.send);
                send.start();
                break;
            case "清除日志":
                MyTabbedPane.jTabbedpane.setSelectedIndex(0);
                CommandThread cleanLog = new CommandThread(Keys.cleanLog);
                cleanLog.start();
                break;
            case "sideload":
                MyTabbedPane.jTabbedpane.setSelectedIndex(0);
                CommandThread sideload = new CommandThread(Keys.sideload);
                sideload.start();
                break;
            case "清屏":
                MyTabbedPane.jTabbedpane.setSelectedIndex(0);
                MyTextArea.cleanOutText();
                break;
            case "清空":
                MyTabbedPane.jTabbedpane.setSelectedIndex(1);
                MyTextField.cleanInputBox();
                //清空表格
                for (int i=0;i<20;i++){
                    MyTable.dtm.setValueAt(null,i,0);
                    MyTable.dtm.setValueAt(null,i,1);
                }
                break;
            case "返回主界面":
                MyTabbedPane.jTabbedpane.setSelectedIndex(0);
                CommandThread toHome = new CommandThread(Keys.toHome);
                toHome.start();
                break;
            case "检查sideload":
                MyTabbedPane.jTabbedpane.setSelectedIndex(0);
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
            case "截图":
                MyTabbedPane.jTabbedpane.setSelectedIndex(0);
                CommandThread screen = new CommandThread(Keys.screen);
                screen.start();
                break;
        }
    }


//    private void hello(){
//        i++;
//        if (i==8){
//            while (true){
//                int n = JOptionPane.showConfirmDialog(null,
//                        "     你是猪么？",
//                        "提示",JOptionPane.YES_NO_OPTION,
//                        JOptionPane.QUESTION_MESSAGE,
//                        new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("2.jpg"))));
//                if (n==0){
//                    JOptionPane.showMessageDialog(null,
//                            "好的，我知道了^(*￣(oo)￣)^",
//                            "提示",JOptionPane.WARNING_MESSAGE,
//                            new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("3.jpg"))));
//                    break;
//                }
//            }
//            i = 0;
//        }
//    }
}
