package com.damon.adb;

import javax.swing.*;
import java.awt.*;

public class MyTextArea {
    public JTextArea textArea ;
    TextAreaListener listener = new TextAreaListener();
    public MyTextArea(){
        textArea = new JTextArea(" ===========================================使用说明==========================================\n" +
                "   1.检查连接：检查普通adb连接的设备状态\n" +
                "   2.定制系统连接：检查需要定制adb连接的设备状态\n" +
                "   3.断开连接：断开adb连接，即结束adb进程\n" +
                "   4.清屏：清除下方输出台文字信息\n" +
                "   5.抓取日志：抓取logcat日志，会在桌面自动创建logcat文件夹，日志文件以日期时间命名\n" +
                "   6.清除日志：清除设备logcat，等同于adb logcat -c\n" +
                "   7.当前运行包名：在输出台显示设备当前运行程序的包名和Activity\n" +
                "   8.recovery模式：设备重启进入recovery模式\n" +
                "   9.返回主界面：设备返回到安卓原生界面\n" +
                "   10.检查sideload：设备进入sideload模式后，检查sideload模式是否连接，防止刷机时连接中断，可能需要多次检查\n" +
                "   11.安装：将apk文件拖入输出台，点击安装即可安装到设备\n" +
                "   12.sideload:sideload模式连接成功后将刷机包拖进输出台，点击开始刷机\n" +
                "   13.发送：在左侧文本输入框输入文本信息（限制使用英文字母、符号、数字），点击可以发送到设备当前输入框\n" +
                "   14.清空：清空文本输入框内容\n" +
                " ============================================================================================\n");
        textArea.setEditable(false);
        textArea.setFont(new Font("Dialog",0,12));
        textArea.setLineWrap(true);        //激活自动换行功能
        textArea.setWrapStyleWord(true);   // 激活断行不断字功能
        listener.OutputLabelListener(textArea);
    }
}
