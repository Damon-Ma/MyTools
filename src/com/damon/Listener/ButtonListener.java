package com.damon.Listener;


import com.alibaba.fastjson.JSONObject;
import com.damon.JFrame.*;
import com.damon.Util.Keys;
import com.damon.Util.Log;
import com.damon.adb.*;
import com.damon.sign.Sign;

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
                CommandThread kill = new CommandThread(Keys.KILL_SERVER);
                kill.start();
                break;
            case "定制系统连接":
                MyTabbedPane.jTabbedpane.setSelectedIndex(0);
                CommandThread spAdb = new CommandThread(Keys.SP_DEVICES);
               spAdb.start();
               break;
            case "抓取日志":
                MyTabbedPane.jTabbedpane.setSelectedIndex(0);
                CommandThread logcat = new CommandThread(Keys.LOGCAT);
                logcat.start();
                break;
            case "recovery模式":
                MyTabbedPane.jTabbedpane.setSelectedIndex(0);
                CommandThread recovery = new CommandThread(Keys.RECOVERY);
                recovery.start();
                break;
            case "当前运行包名":
                MyTabbedPane.jTabbedpane.setSelectedIndex(0);
                CommandThread thispackage = new CommandThread(Keys.PACKAGE);
                thispackage.start();
                break;
            case "安装":
                MyTabbedPane.jTabbedpane.setSelectedIndex(1);
                CommandThread install = new CommandThread(Keys.INSTALL);
                install.start();
                break;
            case "发送":
                CommandThread send = new CommandThread(Keys.SEND);
                send.start();
                break;
            case "清除日志":
                MyTabbedPane.jTabbedpane.setSelectedIndex(0);

                CommandThread cleanLog = new CommandThread(Keys.CLEAN_LOG);
                cleanLog.start();
                break;
            case "sideload":
                MyTabbedPane.jTabbedpane.setSelectedIndex(0);
                CommandThread sideload = new CommandThread(Keys.SIDELOAD);
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
                CommandThread toHome = new CommandThread(Keys.MAIN_ACTIVITY);
                toHome.start();
                break;
            case "检查sideload":
                MyTabbedPane.jTabbedpane.setSelectedIndex(0);
                CommandThread isSideload = new CommandThread(Keys.IS_SIDELOAD);
                isSideload.start();
                break;
            case "签名转非签":
                CommandThread signToNosign = new CommandThread(Keys.SIGN_TO_NOSIGN);
                signToNosign.start();
                //shell.start();
                break;
            case "非签转签名":
                CommandThread nosignToSign = new CommandThread(Keys.NOSIGN_TO_SIGN);
                nosignToSign.start();
                //monitor.start();
                break;
            case "截图":
                MyTabbedPane.jTabbedpane.setSelectedIndex(0);
                CommandThread screen = new CommandThread(Keys.SCREEN);
                screen.start();
                break;
            case "登录":
                CommandThread signLogin =  new CommandThread(Keys.SIGN_LOGIN);
                signLogin.start();
                break;
            case "上传":
                CommandThread upload = new CommandThread(Keys.UPLOAD_APK);
                upload.start();
                break;
            case "原文件下载" :
                CommandThread downloadRes = new CommandThread(Keys.DOWNLOAD_SRC_APK);
                downloadRes.start();
                break;
            case "签名文件下载":
                CommandThread downloadSigned = new CommandThread(Keys.DOWNLOAD_SIGNED_APK);
                downloadSigned.start();
                break;
            case "刷新文件列表":
                CommandThread getAPKList = new CommandThread(Keys.GET_APK_LIST);
                getAPKList.start();
                break;
            case "fastboot模式":
                CommandThread fastboot = new CommandThread(Keys.FASTBOOT);
                fastboot.start();
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
