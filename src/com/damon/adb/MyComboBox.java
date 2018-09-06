package com.damon.adb;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * 下拉选项框，添加监听，并获取到所选选项
 * */

public class MyComboBox {
    JComboBox comboBox;
    public static String choose;



    public MyComboBox(){
        comboBox = new JComboBox();

        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
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
            }
        });

    }

    public String getChoose(){
        return choose;
    }



    private void getDeviceMsg(){
        //获取设备型号
        CMD cmd = new CMD();
        cmd.CMDCommand("adb -s "+this.getChoose()+" shell getprop ro.product.model");
        Application.setOutText("=========================================================");
        if (cmd.getResult()==null||cmd.getResult().equals("")){
            Application.setOutText("获取设备型号错误："+cmd.getErrorResult());
        }else {
            Application.setOutText("设备型号："+cmd.getResult());
        }

        //获取OS版本
        String osVersion = Util.getOS(this.getChoose());
        if (osVersion.equals("")){
            Application.setOutText("获取系统版本错误："+cmd.getErrorResult());
        }else {
            Application.setOutText("系统版本："+osVersion);
        }

        //获取SN号
        cmd.CMDCommand("adb -s "+this.getChoose()+" shell getprop persist.sys.product.serialno");
        if (cmd.getResult().equals("")){
            Application.setOutText("获取设备SN号错误："+cmd.getErrorResult());
        }else {
            Application.setOutText("SN："+cmd.getResult());
            cmd.CMDCommand("adb -s "+this.getChoose()+" shell getprop persist.sys.product.tusn");
            Application.setOutText("TUSN："+cmd.getResult());
        }

        Application.setOutText("=========================================================");
    }

}
