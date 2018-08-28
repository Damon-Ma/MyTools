package com.damon.adb;

import javax.swing.*;
import java.awt.*;

public class MyLabel {
    public JLabel input,devices,deviceNmb,choose;
    public MyLabel() {
        input = new JLabel("inputText");

        devices = new JLabel("当前连接设备数：");

        deviceNmb = new JLabel("0",SwingConstants.CENTER); //默认为0，显示位置中心
        deviceNmb.setFont(new Font("Dialog",3,18));
        deviceNmb.setForeground(Color.RED);

        choose = new JLabel("请选择要连接的设备：");
    }

}
