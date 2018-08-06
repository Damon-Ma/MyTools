package com.damon.adb;

import javax.swing.*;
import java.awt.*;

public class MyTextArea {
    public JTextArea textArea ;
    TextAreaListener listener = new TextAreaListener();
    public MyTextArea(){
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Dialog",0,18));
        textArea.setLineWrap(true);        //激活自动换行功能
        textArea.setWrapStyleWord(true);   // 激活断行不断字功能
        listener.OutputLabelListener(textArea);

    }
}
