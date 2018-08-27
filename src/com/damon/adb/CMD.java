package com.damon.adb;

import java.io.BufferedReader;
import java.io.InputStreamReader;

class CMD {
    private String result;
    private String errorResult;
    void CMDCommand(String cmd){
        result = "";
        String cmd1 = "cmd /c\"  "+cmd;
        //Application.setOutText(cmd);
        // System.out.println(cmd);
        try {
            Process p = Runtime.getRuntime().exec(cmd1);
            BufferedReader	bufferedReader = new BufferedReader
                    (new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.equals("")) {
                    if (result == null || result == "") {
                        result = line;
                    } else {
                        //System.out.println(line);
                        result = String.format("%s\n%s", result, line);

                        //在这里获取刷机的实时进度
                        if (line.matches("(.*)%(.*)")){
                            Application.setOutText(line);
                        }else if(line.startsWith("Total")){
                            Application.setOutText("刷机包安装成功，请重启设备！");
                        }
                    }
                }
            }

            //异常信息
            BufferedReader	errorbufferedReader = new BufferedReader
                    (new InputStreamReader(p.getErrorStream()));
            String errorline;
            while ((errorline = errorbufferedReader.readLine()) != null) {
                errorResult = errorline;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    String getResult(){
        return result;
    }
    String getErrorResult(){
        return errorResult;
    }

    Boolean isConnect(){
        Application.setOutText("检查连接...");
        this.CMDCommand("adb devices");
        //System.out.println(this.getResult());
        String[] devicesResult = this.getResult().split("\n");
      //  System.out.println(devicesResult.length);
        if (devicesResult.length>1){
            String lastResult = devicesResult[devicesResult.length-1];
            String last2Result = devicesResult[devicesResult.length-2];
            if (last2Result.endsWith("device")||last2Result.endsWith("offline")){
                Application.setOutText("只支持连接一台设备，请检查...");
                return false;
            }else if (lastResult.endsWith("unauthorized")){
                Application.setOutText("unauthorized");
                return false;
            }else {
                return this.getResult().endsWith("device");
            }
        }else {
            Application.setOutText("设备未连接！");
            return false;
        }

    }
    Boolean isSideload(){
        Application.setOutText("检查连接...");
        this.CMDCommand("adb devices");
        return this.getResult().endsWith("sideload") && !this.getResult().startsWith("*");
    }
}
