package com.damon.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class testCMD {
    private static String line;
    public static void main(String[] args){
//        String cmd = "cmd /c\" adb devices";
        String cmd = "cmd /c\" ping www.baidu.com";

 //      String cmd = "cmd /c\" adb sideload E:\\FileRecv\\SQ29_P1_M2SS_T28.V1.0.1_UMSPOS_S_0_180730_01.zip";
        //System.out.println(cmd);
        try {
           Process process = Runtime.getRuntime().exec(cmd);
//           BufferedReader	bufferedReader = new BufferedReader
//                    (new InputStreamReader(process.getInputStream()));

            BufferedReader	bufferedReader = new BufferedReader
                    (new InputStreamReader(process.getInputStream()));


            while ((line = bufferedReader.readLine()) != null) {

                    System.out.println(line);
                }
            } catch (Exception e) {
            e.printStackTrace();
        }
    }
   }
