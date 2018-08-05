package com.damon.adb;

public class SplitResult {
    public String[] SplitResult(String result){
        return result.split("<br>");
    }
    public String resultSt(String result){
        int i ;
        String resultSt = "";
        String[] strings = result.split("<br>");
        for (i=0;i<strings.length;i++)
            resultSt = resultSt + strings[i];
            return resultSt;
    }
}
