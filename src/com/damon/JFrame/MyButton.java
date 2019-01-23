package com.damon.JFrame;

import com.damon.Listener.ButtonListener;

import javax.swing.*;

public class MyButton {
    public static JButton devicesBt,killBt,specialBt,logcatBt,recoveryBt,packageBt,
            installBt,sendBt,cleanLogBt,sideloadBt,cleanOutBt,cleanInputBt,toHome,
            isSideload,signToNosign,nosignToSign,screen,
            signInBt,uploadBt,getFileListBt,fastbootBt;
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
        cleanOutBt = new JButton("清屏");
        cleanInputBt = new JButton("清空");
        toHome = new JButton("返回主界面");
        isSideload = new JButton("检查sideload");
        signToNosign = new JButton("签名转非签");
        nosignToSign = new JButton("非签转签名");
        screen = new JButton("截图");
        signInBt = new JButton("登录");
        uploadBt = new JButton("上传");
        getFileListBt = new JButton("刷新文件列表");
        fastbootBt = new JButton("fastboot模式");

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
        listener.BtListener(cleanOutBt);
        listener.BtListener(cleanInputBt);
        listener.BtListener(toHome);
        listener.BtListener(isSideload);
        listener.BtListener(signToNosign);
        listener.BtListener(nosignToSign);
        listener.BtListener(screen);
        listener.BtListener(signInBt);
        listener.BtListener(uploadBt);
        listener.BtListener(getFileListBt);
        listener.BtListener(fastbootBt);
    }
}
