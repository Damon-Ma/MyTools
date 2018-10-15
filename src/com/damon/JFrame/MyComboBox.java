package com.damon.JFrame;

import com.damon.adb.CMD;
import com.damon.Util.Util;

import javax.swing.*;
import java.awt.event.ItemEvent;

/**
 * 下拉选项框，添加监听，并获取到所选选项
 * */

public class MyComboBox {
    public static JComboBox comboBox;
    public static String choose;



    public MyComboBox(){
        comboBox = new JComboBox();

        comboBox.addItemListener(e -> {
            MyTabbedPane.jTabbedpane.setSelectedIndex(0);
            switch (e.getStateChange()) {
                case ItemEvent.SELECTED:
                    choose = String.valueOf(e.getItem());
                    if (choose!=null) {
                        choose = choose.split("、")[1];
                    }
                    getDeviceMsg();
                    break;
                case ItemEvent.DESELECTED:
                    break;
            }
        });

    }

    private String getChoose(){
        return choose;
    }

    //清除下拉框
    public static void rmItem(){
        MyComboBox.comboBox.removeAllItems();
    }
    //设置下拉框
    public static void addItem(String msg){
        MyComboBox.comboBox.addItem(msg);
    }

    private void getDeviceMsg(){
        //获取设备型号
        CMD cmd = new CMD();
        cmd.CMDCommand("adb -s "+this.getChoose()+" shell getprop ro.product.model");
        MyTextArea.setOutText("=========================================================");
        if (cmd.getResult()==null||cmd.getResult().equals("")){
            MyTextArea.setOutText("获取设备型号错误："+cmd.getErrorResult());
        }else {
            MyTextArea.setOutText("设备型号："+cmd.getResult());
        }

        //获取OS版本
        String osVersion = Util.getOS(this.getChoose());
        if (osVersion.equals("")){
            MyTextArea.setOutText("获取系统版本错误："+cmd.getErrorResult());
        }else {
            MyTextArea.setOutText("系统版本："+osVersion);
        }

        //获取SN号
        String snNum;
        cmd.CMDCommand("adb -s "+this.getChoose()+" shell getprop persist.sys.product.serialno");
        if (cmd.getResult().equals("")){
            snNum=cmd.getErrorResult();
            MyTextArea.setOutText("获取设备SN号错误："+snNum);
        }else {
            snNum=cmd.getResult();
            MyTextArea.setOutText("SN："+snNum);
            cmd.CMDCommand("adb -s "+this.getChoose()+" shell getprop persist.sys.product.tusn");
            MyTextArea.setOutText("TUSN："+cmd.getResult());
        }

        MyTextArea.setOutText("=========================================================");
    }

}
