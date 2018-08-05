package com.damon.adb;

import java.text.SimpleDateFormat;

public class Util {
    public String getDate(){
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_hh_mm_ss");
        String date = df.format(System.currentTimeMillis());
        return date;
        }

    public String getLastSt(String s){
        String[] strings = s.split("<br>");
        int i = strings.length-1;
        return strings[i];
    }
}
