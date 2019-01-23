package com.damon.JFrame;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import static java.awt.event.InputEvent.*;

//实现JTextfield 的复制、剪切、粘贴功能。

public class MJTextField extends JTextField implements MouseListener {

    private static final long serialVersionUID = -2308615404205560110L;

    private JPopupMenu pop = null; // 弹出菜单

    private JMenuItem copy = null, paste = null, cut = null; // 三个功能菜单

    MJTextField() {
        super();
        init();
    }

    private void init() {
        this.addMouseListener(this);
        pop = new JPopupMenu();
        pop.add(copy = new JMenuItem("复制"));
        pop.add(paste = new JMenuItem("粘贴"));
        pop.add(cut = new JMenuItem("剪切"));
        copy.setAccelerator(KeyStroke.getKeyStroke('C', CTRL_MASK));
        paste.setAccelerator(KeyStroke.getKeyStroke('V', CTRL_MASK));
        cut.setAccelerator(KeyStroke.getKeyStroke('X', CTRL_MASK));
        copy.addActionListener(this::action);
        paste.addActionListener(this::action);
        cut.addActionListener(this::action);
        this.add(pop);
    }

    /**
     * 菜单动作
     *
     */
    private void action(ActionEvent e) {
        String str = e.getActionCommand();
        if (str.equals(copy.getText())) { // 复制
            this.copy();
        } else if (str.equals(paste.getText())) { // 粘贴
            this.paste();
        } else if (str.equals(cut.getText())) { // 剪切
            this.cut();
        }
    }

    /**
     * 剪切板中是否有文本数据可供粘贴
     *
     * @return true为有文本数据
     */
    private boolean isClipboardString() {
        boolean b = false;
        Clipboard clipboard = this.getToolkit().getSystemClipboard();
        Transferable content = clipboard.getContents(this);
        try {
            if (content.getTransferData(DataFlavor.stringFlavor) instanceof String) {
                b = true;
            }
        } catch (Exception ignored) {
        }
        return b;
    }

    /**
     * 文本组件中是否具备复制的条件
     *
     * @return true为具备
     */
    private boolean isCanCopy() {
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
            copy.setEnabled(isCanCopy());
            paste.setEnabled(isClipboardString());
            cut.setEnabled(isCanCopy());
            pop.show(this, e.getX(), e.getY());
        }
    }

    public void mouseReleased(MouseEvent e) {
    }
}