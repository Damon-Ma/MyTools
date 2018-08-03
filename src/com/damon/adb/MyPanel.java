package com.damon.adb;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyPanel extends JPanel implements ActionListener {
    private JLabel inputText;            //标签      adb
    private JButton inputBt;         //按钮      输入
    public JTextField text;             //文本框  text

    private JPanel panelInputText;
    private JPanel panelInputBt;
    private JPanel panelText;

    public MyPanel() {
        this.inputText = new JLabel("请输入文本");
        this.inputBt = new JButton("输入");
        this.text = new JTextField(10);

        this.panelInputText = new JPanel();
        this.panelText = new JPanel();
        this.panelInputBt = new JPanel();

        this.setLayout(new GridLayout(2, 1));  //网格式布局

        this.panelInputText.add(this.inputText);
        this.panelText.add(this.text);
        this.panelInputBt.add(inputBt);
        this.add(this.panelInputText);
        this.add(this.panelText);
        this.add(this.panelInputBt);

        //注册监听
        inputBt.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("输入")) // 多态的思想
        {
            new InputTextCMD(this.text.getText());
            System.out.println("输出完成");
        }

    }

}
