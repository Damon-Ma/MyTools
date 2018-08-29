package com.damon.adb;

import javax.swing.*;
import java.awt.event.*;

public class MJTextArea extends JTextArea implements MouseListener {

    private JPopupMenu pop = null; // 弹出菜单
    private JMenuItem copy = null; // 三个功能菜单

    public MJTextArea() {
        super();
        init();
    }

    public MJTextArea(String text){
        super(text);
        init();
    }

    private void init() {
        this.addMouseListener(this);
        pop = new JPopupMenu();
        pop.add(copy = new JMenuItem("复制"));

        copy.setAccelerator(KeyStroke.getKeyStroke('C', InputEvent.CTRL_MASK));

        copy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                action(e);
            }
        });

        this.add(pop);
    }

    public void action(ActionEvent e) {
        String str = e.getActionCommand();
        if (str.equals(copy.getText())) { // 复制
            this.copy();
        }
    }

    public JPopupMenu getPop() {
        return pop;
    }

    public void setPop(JPopupMenu pop) {
        this.pop = pop;
    }


    /**
     * 文本组件中是否具备复制的条件
     *
     * @return true为具备
     */
    public boolean copyable() {
        boolean b = false;
        int start = this.getSelectionStart();
        int end = this.getSelectionEnd();
        if (start != end)
            b = true;
        return b;
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            copy.setEnabled(copyable());
            pop.show(this, e.getX(), e.getY());
        }
    }

    public void mouseReleased(MouseEvent e) {
    }

}
