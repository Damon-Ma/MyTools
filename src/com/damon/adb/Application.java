package com.damon.adb;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class Application extends JFrame{
    static MyButton button = new MyButton();
    static MyLabel label = new MyLabel();
    static MyTextArea textArea = new MyTextArea();
    static MyTextField textField = new MyTextField();
    static JScrollPane text2;


    public Application(){
        //按钮 网格
        JPanel BtJpanel = new JPanel();
        BtJpanel.setBorder(BorderFactory.createEtchedBorder()); //添加蚀刻边框
        BtJpanel.setLayout(new GridLayout(1,4,5,5));
        //定义四个按钮面板
        JPanel Bt1,Bt2,Bt3,Bt4;
        Bt1 = new JPanel();
        Bt1.setLayout(new GridLayout(4,1,5,5));
        //将按钮加到面板上
        Bt1.add(button.devicesBt);
        Bt1.add(button.specialBt);
        Bt1.add(button.killBt);
        Bt1.add(button.cleanOutBt);

        Bt2 = new JPanel();
        Bt2.setLayout(new GridLayout(4,1,5,5));

        Bt2.add(button.logcatBt);
        Bt2.add(button.cleanLogBt);
        Bt2.add(button.packageBt);
        Bt2.add(button.recoveryBt);

        Bt3 = new JPanel();
        Bt3.setLayout(new GridLayout(4,1,5,5));

        Bt3.add(button.toHome);
        Bt3.add(button.isSideload);
        Bt3.add(button.monitor);
        Bt3.add(button.other);

        Bt4 = new JPanel();
        Bt4.setLayout(new GridLayout(2,1,5,5));

        Bt4.add(button.installBt);
        Bt4.add(button.sideloadBt);

        BtJpanel.add(Bt1);
        BtJpanel.add(Bt2);
        BtJpanel.add(Bt3);
        BtJpanel.add(Bt4);


        //输入框行
        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(BorderFactory.createEtchedBorder()); //添加蚀刻边框
        inputPanel.setLayout(new BorderLayout(5,5));

        //输入框按钮行
        JPanel inputBt = new JPanel();
        inputBt.setLayout(new GridLayout(1,2,5,10));

        inputBt.add(button.sendBt);
        inputBt.add(button.cleanInputBt);


        inputPanel.add(label.input,BorderLayout.WEST);
        inputPanel.add(textField.inputText,BorderLayout.CENTER);
        inputPanel.add(inputBt,BorderLayout.EAST);

        //分割线+输出框p2
        JPanel p2 = new JPanel();

        //设置边框
        TitledBorder tb = BorderFactory.createTitledBorder("输出台");
        tb.setTitleJustification(TitledBorder.CENTER);
        p2.setBorder(tb);

        p2.setLayout(new BorderLayout(3,3));

        //设置输出台可滚动
        text2 =new JScrollPane(textArea.textArea);

        text2.doLayout();
        p2.add(text2,BorderLayout.CENTER);

        //输入框+p2
        JPanel p1 = new JPanel();
        p1.setLayout(new BorderLayout(5,5));

        p1.add(inputPanel,BorderLayout.NORTH);
        p1.add(p2,BorderLayout.CENTER);

        //
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout(5,5));
        p.setBorder(BorderFactory.createEtchedBorder()); //添加蚀刻边框
        p.add(BtJpanel,BorderLayout.NORTH);
        p.add(p1,BorderLayout.CENTER);
    //    p.add(new JLabel());

        add(p,BorderLayout.CENTER);


        setTitle("Tools");
        setSize(700,550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }



    //设置文本框文字
    public static void setInputText(String text){
        textField.inputText.setText(text);
    }
    public static String getInputText(){
        return textField.inputText.getText();
    }

    //设置输出台文字
    public static void setOutText(String text){
        textArea.textArea.append(text+"\n");
        //设置光标移到最后一行
        textArea.textArea.setCaretPosition(textArea.textArea.getDocument().getLength());
    }

    //设置输出台字体、、textArea不能单独设置字体颜色，可以使用html样式的TextPane
//    public static void setOutTextFont(int size){
//        //textArea.textArea.setFont(new Font("Dialog",0,size));
//        //textArea.textArea.setForeground(Color.BLUE);
//    }

    public static void cleanOutText(){
        textArea.textArea.setText("");
    }

    public static void cleanInputBox(){
        textField.inputText.setText("");
    }



    public static void main(String[] args) throws InterruptedException {
        Application panel = new Application();

//        frame.setTitle("Tools");
//        frame.setSize(700,500);
//        frame.setLocationRelativeTo(null);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setVisible(true);
    }

}
