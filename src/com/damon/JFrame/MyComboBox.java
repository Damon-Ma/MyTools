package com.damon.JFrame;

import com.damon.Util.Config;
import com.damon.Util.Log;
import com.damon.Util.Util;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * 下拉选项框，添加监听，并获取到所选选项
 * */

public class MyComboBox {
    public static JComboBox comboBox;
    public static JComboBox signType;
    public static String choose;
    public static String typeName;



    public MyComboBox() {
        comboBox = new JComboBox();
        comboBox.addItemListener(e -> {
            MyTabbedPane.jTabbedpane.setSelectedIndex(0);
            switch (e.getStateChange()) {
                case ItemEvent.SELECTED:
                    choose = String.valueOf(e.getItem());
                    if (choose != null) {
                        try {
                            choose = choose.split("、")[1];
                            getDeviceMsg();
                            break;
                        }catch (IndexOutOfBoundsException expect){
                            break;
                        }
                    }
                case ItemEvent.DESELECTED:
                    break;
            }
        });


        signType = new JComboBox();
        //证书类型
        signType.addItem("---请选择---");
        signType.addItem("I9000");
        signType.addItem("NEW_V2_POS");
        signType.addItemListener(e -> {
            switch (e.getStateChange()) {
                case ItemEvent.SELECTED:
                    typeName = String.valueOf(e.getItem());
                    Config.signType = typeName;
                    Log.logger.info("选择："+typeName);
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

        String modle = Util.getMode(this.getChoose());

        MyTextArea.setOutText("=========================================================");
        if (modle==null||modle.equals("")){
            MyTextArea.setOutText("获取设备型号错误："+modle);
        }else {
            MyTextArea.setOutText("设备型号："+modle);
        }

        //获取OS版本
        String osVersion = Util.getOS(this.getChoose());
        if (osVersion.equals("")){
            MyTextArea.setOutText("获取系统版本错误："+osVersion);
        }else {
            MyTextArea.setOutText("系统版本："+osVersion);
        }

        //获取SN号
        String snNum = Util.getSnNum(this.getChoose());
        if (snNum.equals("")){
            MyTextArea.setOutText("获取设备SN号错误："+snNum);
        }else {
            MyTextArea.setOutText("SN："+snNum);
            String tusn = Util.getTusnNum(this.getChoose());
            MyTextArea.setOutText("TUSN："+tusn);
        }
        MyTextArea.setOutText("=========================================================");
    }

}
