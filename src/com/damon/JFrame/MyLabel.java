package com.damon.JFrame;

import com.damon.Listener.LabelListener;

import javax.swing.*;
import java.awt.*;

public class MyLabel {
    public static JLabel input,devices,deviceNmb,choose,installResult,chooseMessage,showFile,
            tempo,upLoadFileName,fileNameLabel,uploadResultLabel,uploadResult;
    public MyLabel() {
        input = new JLabel("inputText");

        devices = new JLabel("当前连接设备数：");

        deviceNmb = new JLabel("0",SwingConstants.CENTER); //默认为0，显示位置中心
        deviceNmb.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC,18));
        deviceNmb.setForeground(Color.RED);

        choose = new JLabel("请选择要连接的设备：");

        installResult = new JLabel();

        chooseMessage = new JLabel("请选择证书类型：",SwingConstants.CENTER);

        showFile = new JLabel("<html>&nbsp&nbsp请<br/>&nbsp&nbsp拖<br/>&nbsp&nbsp入<br/>&nbsp&nbsp A<br/>&nbsp&nbsp P<br/>&nbsp&nbsp K<br/>&nbsp&nbsp文<br/>&nbsp&nbsp件</html>");
        showFile.setPreferredSize(new Dimension(60,230));
        showFile.setFont(new Font("Dialog",Font.BOLD ,22));
        LabelListener labelListener = new LabelListener();
        labelListener.OutputLabelListener(showFile);

        fileNameLabel = new JLabel("文件名：");

        upLoadFileName = new JLabel();
        upLoadFileName.setPreferredSize(new Dimension(150,10));

        tempo = new JLabel("上传进度：");

        uploadResultLabel = new JLabel("上传结果：");

        uploadResult = new JLabel();
        uploadResult.setPreferredSize(new Dimension(150,10));
    }

    //设置连接数量
    public static void setDevicesNmb(String nmb){
        MyLabel.deviceNmb.setText(nmb);
        if (!nmb.equals("0")){
            MyLabel.deviceNmb.setForeground(Color.GREEN);
        }else {
            MyLabel.deviceNmb.setForeground(Color.RED);
        }
    }

    //设置showFile的文字
    public static void setShowFileWord(String s){
        showFile.setText(s);
    }

}
