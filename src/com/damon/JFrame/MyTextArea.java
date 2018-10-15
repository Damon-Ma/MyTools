package com.damon.JFrame;

import com.damon.Listener.TextAreaListener;

import javax.swing.*;
import java.awt.*;

public class MyTextArea {
    public static JTextArea textArea ;

    public MyTextArea(){
        textArea = new MJTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Dialog", Font.PLAIN,12));
        textArea.setLineWrap(true);        //激活自动换行功能
        textArea.setWrapStyleWord(true);   // 激活断行不断字功能
        TextAreaListener listener = new TextAreaListener();
        listener.OutputLabelListener(textArea);
    }
    //设置输出台文字
    public static void setOutText(String text){
        MyTextArea.textArea.append(text+"\n");
        //设置光标移到最后一行
        MyTextArea.textArea.setCaretPosition(MyTextArea.textArea.getDocument().getLength());
    }
    //清空
    public static void cleanOutText(){
        MyTextArea.textArea.setText("");
    }
}
