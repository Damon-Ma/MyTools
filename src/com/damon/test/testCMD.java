package com.damon.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class testCMD {
    private static String line;
    public static void main(String[] args){
        String cmd = "cmd /c\" adb devices";
        //System.out.println(cmd);
        try {
           Process process = Runtime.getRuntime().exec(cmd);
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
