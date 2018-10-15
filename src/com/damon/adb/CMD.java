package com.damon.adb;

import com.damon.JFrame.MyComboBox;
import com.damon.JFrame.MyTextArea;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CMD {
    private String result;
    private String errorResult;
    public void CMDCommand(String cmd){
        result = "";
        String cmd1 = "cmd /c\"  "+cmd;
        //Application.setOutText(cmd);
        System.out.println(cmd);
        try {
            Process p = Runtime.getRuntime().exec(cmd1);
            BufferedReader	bufferedReader = new BufferedReader
                    (new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                //Application.setOutText(line);
                System.out.println(line);
                if (!line.equals("")) {
                    if (result == null || result.equals("")) {
                        result = line;
                    } else {
                        result = String.format("%s\n%s", result, line);

                        //在这里获取刷机的实时进度
                        if (line.matches("(.*)%(.*)")){
                            MyTextArea.setOutText(line);
                        }else if(line.startsWith("Total")){
                            MyTextArea.setOutText("刷机包安装结束，请重启设备！");
                        }
                    }
                }
            }

            //异常信息
            BufferedReader	errorBufferedReader = new BufferedReader
                    (new InputStreamReader(p.getErrorStream()));
            String errorLine;
            while ((errorLine = errorBufferedReader.readLine()) != null) {
                errorResult = errorLine;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String getResult(){
        return result;
    }
    public String getErrorResult(){
        return errorResult;
    }

    Boolean isConnect(){
        if (MyComboBox.choose==null){
            JOptionPane.showMessageDialog(null,
                    "请先检查连接，选择需要连接的设备！",
                    "提示",
                    JOptionPane.WARNING_MESSAGE);

            //Application.setOutText("请先检查连接，选择需要连接的设备！");
            return false;
        }else {
            this.CMDCommand("adb -s "+MyComboBox.choose+" root");
            if (this.getErrorResult()==null){
                return true;
            }else{
                MyTextArea.setOutText("设备未连接！");
                return false;
            }
        }
    }
    Boolean isSideload(){
        MyTextArea.setOutText("检查连接...");
        this.CMDCommand("adb devices");
        String[] devicesResult = this.getResult().split("\n");
        for (String result : devicesResult) {
            if (result.endsWith("sideload") && result.split("\t")[0].equals(MyComboBox.choose)) {
                return true;
            }
        }
        return false;
    }
}
