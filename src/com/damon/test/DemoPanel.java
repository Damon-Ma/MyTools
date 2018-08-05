package com.damon.test;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class DemoPanel extends JPanel{

    private JLabel labelUser, labelPassWd;            //标签      用户名，密码
    private JButton buttonLogin, buttonReset;         //按钮      登录，重置
    private JTextField textFieldUserName;             //文本框  用户名输入
    private JPasswordField passWdField;               //密码框  密码输入
    private JPanel panelUserName;
    private JPanel panelPassWd;
    private JPanel panelLoginButton;

    public DemoPanel(){
        this.labelUser = new JLabel("用户名");
        this.labelPassWd = new JLabel("密    码");
        this.buttonLogin = new JButton("登录");
        this.buttonReset = new JButton("重置");
        this.textFieldUserName = new JTextField(20);
        this.passWdField = new JPasswordField(20);

        this.panelPassWd = new JPanel();
        this.panelUserName = new JPanel();
        this.panelLoginButton = new JPanel();

        this.setLayout(new GridLayout(3, 1));  //网格式布局

        this.panelUserName.add(this.labelUser);
        this.panelUserName.add(this.textFieldUserName);
        this.panelPassWd.add(this.labelPassWd);
        this.panelPassWd.add(this.passWdField);
        this.panelLoginButton.add(buttonLogin);
        this.panelLoginButton.add(buttonReset);

        this.add(this.panelUserName);
        this.add(this.panelPassWd);
        this.add(this.panelLoginButton);
    }
}

