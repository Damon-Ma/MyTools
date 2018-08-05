package com.damon.adb;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CMD {
    private static String line;
    private String result;
    public void CMDCommand(String cmd){
        result = "";
        String cmd1 = "cmd /c\"  "+cmd;
        System.out.println(cmd);
        try {
            Process p = Runtime.getRuntime().exec(cmd1);
            BufferedReader	bufferedReader = new BufferedReader
                    (new InputStreamReader(p.getInputStream()));
            while ((line = bufferedReader.readLine()) != null) {
                if (result == null || result == "") {
                    result = line;
                } else {
                    result = result
                            + "<br>"
                            + line;
                    //  System.out.println(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String getResult(){
        return result;
    }



}
