package com.damon.adb;

import java.io.BufferedReader;
import java.io.InputStreamReader;

class CMD {
    private String result;
    void CMDCommand(String cmd){
        result = "";
        String cmd1 = "cmd /c\"  "+cmd;
//        Application.setOutText(cmd);
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
                        result = String.format("%s\n%s", result, line);
                        // System.out.println(line);
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
