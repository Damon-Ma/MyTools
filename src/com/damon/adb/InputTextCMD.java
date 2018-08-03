package com.damon.adb;

public class InputTextCMD {
    public InputTextCMD(String text){
        MyPanel panel = new MyPanel();
        String cmd = "cmd /c\" adb shell input text "+text;
        System.out.println(cmd);
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
