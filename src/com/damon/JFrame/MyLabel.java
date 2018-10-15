package com.damon.JFrame;

import javax.swing.*;
import java.awt.*;

public class MyLabel {
    public static JLabel input,devices,deviceNmb,choose,installResult;
    public MyLabel() {
        input = new JLabel("inputText");

        devices = new JLabel("当前连接设备数：");

        deviceNmb = new JLabel("0",SwingConstants.CENTER); //默认为0，显示位置中心
        deviceNmb.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC,18));
        deviceNmb.setForeground(Color.RED);

        choose = new JLabel("请选择要连接的设备：");
        installResult = new JLabel();
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
