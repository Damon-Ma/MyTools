package com.damon.adb;

import com.damon.JFrame.MyComboBox;
import com.damon.JFrame.MyTextArea;
import com.damon.Util.Log;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class CMD {


    public Process Cmd(String command){
        String cmd = "cmd /c\"  "+command;
        Log.logger.info("执行："+command);
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            return process;
        } catch (IOException e) {
            Log.logger.error(e);
            return null;
        }
    }
    //获取结果
    public String getResult(Process p) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader
                    (new InputStreamReader(p.getInputStream(),"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String line;
        String result = null;
        try {
            while ((line = bufferedReader.readLine()) != null){
                if (result == null){
                    result = line;
                }else{
                    result = String.format("%s\n%s",result,line).trim();
                }
            }
        }catch (IOException e){
            Log.logger.error(e);
            return null;
        }
        Log.logger.info("执行结果："+result);
        return result;
    }
    //打印实时结果
    public void printRealResult(Process p) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader
                    (new InputStreamReader(p.getInputStream(),"GBK"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String line;
        //对于4.3版本，重复显示修复
        String tmp = "";
        try {
            while ((line = bufferedReader.readLine()) != null){
                if (!line.trim().equals(tmp)){
                    MyTextArea.setOutText(line.trim());
                    Log.logger.info(line.trim());
                    tmp = line.trim();
                }
            }
        }catch (IOException e){
            Log.logger.error(e);
        }

    }
    //获取异常结果
    public String getErrorResult(Process p){
        String errorLine;
        String errorResult = null;
        BufferedReader	errorBufferedReader = null;
        try {
            errorBufferedReader = new BufferedReader
                    (new InputStreamReader(p.getErrorStream(),"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            while ((errorLine = errorBufferedReader.readLine()) != null) {
                if (errorResult != null){
                    errorResult = errorResult +"\n\t"+ errorLine.trim();
                }else {
                    errorResult = "\n\t"+errorLine.trim();
                }
            }
        }catch (IOException e){
            Log.logger.error(e);
        }
        Log.logger.error("异常结果："+errorResult);
        return errorResult;
    }


//
//    public void CMDCommand(String cmd){
//        result = "";
//        String cmd1 = "cmd /c\"  "+cmd;
//        //Application.setOutText(cmd);
//        System.out.println(cmd);
//        try {
//            Process p = Runtime.getRuntime().exec(cmd1);
//            BufferedReader	bufferedReader = new BufferedReader
//                    (new InputStreamReader(p.getInputStream()));
//            String line;
//            while ((line = bufferedReader.readLine()) != null) {
//                //Application.setOutText(line);
//                System.out.println(line);
//                if (!line.equals("")) {
//                    if (result == null || result.equals("")) {
//                        result = line;
//                    } else {
//                        result = String.format("%s\n%s", result, line);
//
//                        //在这里获取刷机的实时进度
//                        if (line.matches("(.*)%(.*)")){
//                            MyTextArea.setOutText(line);
//                        }else if(line.startsWith("Total")){
//                            MyTextArea.setOutText("刷机包安装结束，请重启设备！");
//                        }
//                    }
//                }
//            }
//
//            //异常信息
//            BufferedReader	errorBufferedReader = new BufferedReader
//                    (new InputStreamReader(p.getErrorStream()));
//            String errorLine;
//            while ((errorLine = errorBufferedReader.readLine()) != null) {
//                errorResult = errorLine;
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    public String getResult(){
//        return result;
//    }
//    public String getErrorResult(){
//        return errorResult;
//    }

    Boolean isConnect(){
        if (MyComboBox.choose==null){
            JOptionPane.showMessageDialog(null,
                    "请先检查连接，选择需要连接的设备！",
                    "提示",
                    JOptionPane.WARNING_MESSAGE);

            //Application.setOutText("请先检查连接，选择需要连接的设备！");
            return false;
        }else {
            Process p = this.Cmd("adb -s "+MyComboBox.choose+" root");
            if (this.getErrorResult(p)==null){
                return true;
            }else{
                MyTextArea.setOutText("设备未连接！");
                return false;
            }
        }
    }
    Boolean isSideload(){
        MyTextArea.setOutText("检查连接...");
        Process p = this.Cmd("adb devices");
        String[] devicesResult = this.getResult(p).split("\n");
        for (String result : devicesResult) {
            if (result.endsWith("sideload") && result.split("\t")[0].equals(MyComboBox.choose)) {
                return true;
            }
        }
        return false;
    }
}
