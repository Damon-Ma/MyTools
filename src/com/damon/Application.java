package com.damon;

import com.damon.JFrame.*;
import com.damon.Util.Util;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Application extends JFrame{

    private Application(){
        //初始化页面控件
        new MyButton();
        new MyTextArea();
        new MyTextField();
        new MyComboBox();
        new MyLabel();
        new MyTable();
        new MyJProgressBar();
        new MyRadioButton();

        //设置log4j日志输出路径
        String logPath = Util.getThisPath()+"logs";
        System.setProperty("log_home",logPath);

        //所有按钮面板，一行四列
        JPanel BtJpanel = new JPanel();
        BtJpanel.setBorder(BorderFactory.createEtchedBorder()); //添加蚀刻边框
        BtJpanel.setLayout(new GridLayout(1,4,5,5));

        //定义五个面板
        JPanel Bt1,Bt2,Bt3,Bt4,Bt5;

        //第一列按钮
        Bt1 = new JPanel();
        Bt1.setLayout(new GridLayout(4,1,5,5));
        Bt1.add(MyButton.devicesBt);
        Bt1.add(MyButton.specialBt);
        Bt1.add(MyButton.killBt);
        Bt1.add(MyButton.cleanOutBt);

        //第二列按钮
        Bt2 = new JPanel();
        Bt2.setLayout(new GridLayout(4,1,5,5));
        Bt2.add(MyButton.logcatBt);
        Bt2.add(MyButton.cleanLogBt);
        Bt2.add(MyButton.packageBt);
        Bt2.add(MyButton.recoveryBt);

        //第三列按钮
        Bt3 = new JPanel();
        Bt3.setLayout(new GridLayout(4,1,5,5));
        Bt3.add(MyButton.toHome);
        Bt3.add(MyButton.isSideload);
        Bt3.add(MyButton.signToNosign);
        Bt3.add(MyButton.nosignToSign);

        //第四列按钮
        Bt4 = new JPanel();
        Bt4.setLayout(new GridLayout(4,1,5,5));
        Bt4.add(MyButton.installBt);
        Bt4.add(MyButton.screen);
        Bt4.add(MyButton.sideloadBt);
        Bt4.add(MyButton.fastbootBt);

        //设备显示列
        Bt5 = new JPanel();
        Bt5.setLayout(new GridLayout(4,1,5,5));
        Bt5.add(MyLabel.devices);
        Bt5.add(MyLabel.deviceNmb);
        Bt5.add(MyLabel.choose);
        Bt5.add(MyComboBox.comboBox);

        //将五个面板添加到按钮面板上
        BtJpanel.add(Bt5);
        BtJpanel.add(Bt1);
        BtJpanel.add(Bt2);
        BtJpanel.add(Bt3);
        BtJpanel.add(Bt4);


        //第二行，输入框和两个按钮面板
        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(BorderFactory.createEtchedBorder()); //添加蚀刻边框
        inputPanel.setLayout(new BorderLayout(5,5));

        //两个按钮面板
        JPanel inputBt = new JPanel();
        inputBt.setLayout(new GridLayout(1,2,5,10));
        inputBt.add(MyButton.sendBt);
        inputBt.add(MyButton.cleanInputBt);

        //添加文字标签、输入框和两个按钮面板
        inputPanel.add(MyLabel.input,BorderLayout.WEST);
        inputPanel.add(MyTextField.inputText,BorderLayout.CENTER);
        inputPanel.add(inputBt,BorderLayout.EAST);

        //输出框面板
        JPanel p2 = new JPanel();
        p2.setLayout(new BorderLayout(3,3));

        //设置边框
//        TitledBorder tb = BorderFactory.createTitledBorder("输出台");
//        tb.setTitleJustification(TitledBorder.CENTER);
//        p2.setBorder(tb);

        //设置输出台可滚动
        JScrollPane text2 = new JScrollPane(MyTextArea.textArea);
        text2.doLayout();
        p2.add(text2,BorderLayout.CENTER);


        //安装标签页
        //安装标签页面板
        JPanel p3 = new JPanel();
        p3.setLayout(new BorderLayout(3,3));

        //设置表格可滚动
        JScrollPane table2 = new JScrollPane(MyTable.table);
        p3.add(table2,BorderLayout.CENTER);
        p3.add(MyLabel.installResult,BorderLayout.SOUTH);


        //签名页
        //签名页面板
        JPanel sign_p = new JPanel();
        sign_p.setLayout(new BorderLayout(3,3));
        sign_p.setBorder(BorderFactory.createEtchedBorder()); //添加蚀刻边框

        //第一行，按钮面板
        JPanel sign_p_1 = new JPanel(); //第一行

        //接收文件面板
        JPanel sign_p_2 = new JPanel(); //中左
        sign_p_2.setMinimumSize(new Dimension(800,500));

        //显示文件列表面板
        JPanel sign_p_3 = new JPanel(); //中右
        sign_p_3.setLayout(new BorderLayout(0,0));

        //底部状态面板
        JPanel sign_p_4 = new JPanel(); //底部

        //
        sign_p_1.add(MyRadioButton.netRadioButton1);
        sign_p_1.add(MyRadioButton.netRadioButton2);
        sign_p_1.add(MyButton.signInBt);
        sign_p_1.add(MyLabel.chooseMessage);
        sign_p_1.add(MyComboBox.signType);
        sign_p_1.add(MyButton.uploadBt);
        sign_p_1.add(MyButton.getFileListBt);

        sign_p_2.add(MyLabel.showFile);
        sign_p_2.setBorder(BorderFactory.createEtchedBorder());

        JScrollPane fileTable = new JScrollPane(MyTable.signFileTable);
        sign_p_3.add(fileTable);
        sign_p_3.setBorder(BorderFactory.createEtchedBorder());

        sign_p_4.add(MyLabel.fileNameLabel);
        sign_p_4.add(MyLabel.upLoadFileName);
        sign_p_4.add(MyLabel.tempo);
        sign_p_4.add(MyJProgressBar.uploadProgressBar);
        sign_p_4.add(MyLabel.uploadResultLabel);
        sign_p_4.add(MyLabel.uploadResult);

        sign_p.add(sign_p_1,BorderLayout.NORTH);
        sign_p.add(sign_p_2,BorderLayout.WEST);
        sign_p.add(sign_p_3,BorderLayout.CENTER);
        sign_p.add(sign_p_4,BorderLayout.SOUTH);

        //添加三个标签页
        MyTabbedPane p4 = new MyTabbedPane(p2,p3,sign_p);

        //下半部分面板
        JPanel p1 = new JPanel();
        p1.setLayout(new BorderLayout(5,5));

        //添加第二行输入框和标签面板
        p1.add(inputPanel,BorderLayout.NORTH);
        p1.add(p4,BorderLayout.CENTER);

        //定义最大面板
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout(5,5));
        p.setBorder(BorderFactory.createEtchedBorder()); //添加蚀刻边框

        //添加按钮面板和下半部分面板
        p.add(BtJpanel,BorderLayout.NORTH);
        p.add(p1,BorderLayout.CENTER);


        //将面板添加到显示框
        add(p,BorderLayout.CENTER);
        //获取图片设置图标
        //Image img = Toolkit.getDefaultToolkit ().getImage ("1.jpg");
        //setIconImage(img);
        setIconImage(new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("1.jpg"))).getImage());
        setTitle("Tools");
        setSize(850,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Application();
    }
}
