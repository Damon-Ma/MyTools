package com.damon.JFrame;
import java.awt.*;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * 选项卡
 */
public class MyTabbedPane  extends JPanel {

    public static JTabbedPane jTabbedpane = new JTabbedPane();// 存放选项卡的组件

    public MyTabbedPane(JPanel jpanelFirst,JPanel jpanelSecond) {

        // 第一个标签下的JPanel
        jTabbedpane.addTab("输出台",null, jpanelFirst, "输出台页面");// 加入第一个页面
        jTabbedpane.setMnemonicAt(0, KeyEvent.VK_0);// 设置第一个位置的快捷键为0

        // 第二个标签下的JPanel
        jTabbedpane.addTab("安装程序", null, jpanelSecond, "安装程序页面");// 加入第er个页面
        jTabbedpane.setMnemonicAt(1, KeyEvent.VK_1);// 设置快捷键为1


        setLayout(new GridLayout(1, 1));
        add(jTabbedpane);
    }
}
