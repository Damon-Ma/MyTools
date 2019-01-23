package com.damon.JFrame;

import javax.swing.*;

public class MyTextField {
    public static JTextField inputText;
    public MyTextField(){
        inputText = new MJTextField();
    }
    //获取输入框文字
    public static String getInputText(){
        return MyTextField.inputText.getText();
    }

    //清空输入框
    public static void cleanInputBox(){
        MyTextField.inputText.setText("");
    }
}
