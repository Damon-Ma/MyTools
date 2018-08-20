package com.damon.adb;

import java.io.BufferedReader;
import java.io.InputStreamReader;

class CMD {
    private String result;
    private String errorResult;
    void CMDCommand(String cmd){
        result = "";
        String cmd1 = "cmd /c\"  "+cmd;
    //    Application.setOutText(cmd);
        System.out.println(cmd);
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
            //            System.out.println(line);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    String getResult(){
        return result;
    }

    Boolean isConnect(){
        Application.setOutText("检查连接...");
        this.CMDCommand("adb devices");
        //System.out.println(this.getResult());
        return this.getResult().endsWith("device");
    }
    Boolean isSideload(){
        Application.setOutText("检查连接...");
        this.CMDCommand("adb devices");
        return this.getResult().endsWith("sideload") && !this.getResult().startsWith("*");
    }
}
