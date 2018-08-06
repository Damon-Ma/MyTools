package com.damon.adb;

import javax.swing.*;
import java.awt.*;

public class MyTextArea {
    public JTextArea textArea ;
    LabelListener listener = new LabelListener();
    public MyTextArea(){
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Dialog",0,18));
      // listener.OutputLabelListener(textArea);

    }
}
