package com.damon.adb;


import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ButtonListener implements ActionListener {
    Util util = new Util();
    ADBCommand command = new ADBCommand();
    String cmdResult;
    public void BtListener(JButton devicesBt){
        devicesBt.addActionListener(this::actionPerformed);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "adb devices":
                cmdResult = command.devices();
                //cmd = new CMD("adb devices");
                //cmdResult = cmd.getResult();
                TestPanel.setOutText("<html><body>"+cmdResult+"<body><html>");
                System.out.println(cmdResult);
                break;
            case "kill-server":
                //cmd = new CMD("adb kill-server");
                cmdResult = command.kill_server();
                if (cmdResult=="")
                    TestPanel.setOutText("<html><body>"+"OK"+"<body><html>");
                break;
            case "adb 5.1":
                //cmd = new CMD(".\\libs\\adb\\adb.exe devices");
                cmdResult = command.specialAdb();
                TestPanel.setOutText("<html><body>"+cmdResult+"<body><html>");
                System.out.println("点击了adb 5.1"+cmdResult);
                break;
            case "logcat":
//                FileSystemView fsv = FileSystemView.getFileSystemView();
//                File com=fsv.getHomeDirectory();    //这便是读取桌面路径的方法了
//
//                cmd = new CMD("mkdir "+com.getAbsolutePath()+"\\logcat");
//                String path = com.getAbsolutePath()+"\\logcat\\logcat"+util.getDate()+".txt";
//                cmd = new CMD("adb logcat -d > "+path);
                cmdResult = command.logcat();
                TestPanel.setOutText("<html><body>抓取日志结果："+cmdResult+"<body><html>");
                break;
        }
    }
}
