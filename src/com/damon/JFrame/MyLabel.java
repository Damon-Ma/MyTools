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

        showFile = new JLabel("<html>" +
                "&nbsp&nbsp&nbsp&nbsp请<br/>" +
                "&nbsp&nbsp&nbsp&nbsp拖<br/>" +
                "&nbsp&nbsp&nbsp&nbsp入<br/>" +
                "&nbsp&nbsp&nbsp&nbsp A<br/>" +
                "&nbsp&nbsp&nbsp&nbsp P<br/>" +
                "&nbsp&nbsp&nbsp&nbsp K<br/>" +
                "&nbsp&nbsp&nbsp&nbsp文<br/>" +
                "&nbsp&nbsp&nbsp&nbsp件</html>");
        showFile.setPreferredSize(new Dimension(100,270));
        showFile.setFont(new Font("Dialog",Font.BOLD ,26));
        showFile.setForeground(Color.RED);
        LabelListener labelListener = new LabelListener();
        labelListener.OutputLabelListener(showFile);

        fileNameLabel = new JLabel("待上传文件名：");

        //显示待上传文件名
        upLoadFileName = new JLabel();
        upLoadFileName.setPreferredSize(new Dimension(200,20));
        upLoadFileName.setFont(new Font("Dialog",Font.BOLD,15));
        upLoadFileName.setForeground(Color.BLUE);

        tempo = new JLabel("上传进度：");

        uploadResultLabel = new JLabel("状态：");

        //上传状态
        uploadResult = new JLabel();
        uploadResult.setPreferredSize(new Dimension(200,20));
        uploadResult.setForeground(Color.RED);
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
}
