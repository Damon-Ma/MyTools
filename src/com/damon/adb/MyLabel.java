package com.damon.adb;

import javax.swing.*;
import java.awt.*;

public class MyLabel {
    public JLabel input, output;
    LabelListener listener = new LabelListener();
    public MyLabel() {
        input = new JLabel();
        output = new JLabel();
        output.setFont(new Font("Dialog",0,18));
        listener.OutputLabelListener(output);
    }
}
