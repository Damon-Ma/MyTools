package com.damon.adb;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class Util {

    static ResourceBundle bundle;


    public String getDate(){
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_hh_mm_ss");
        String date = df.format(System.currentTimeMillis());
        return date;
        }

    public String getLastLine(String s){
        return "";
    }

    public static String getCommand(String key){
        bundle = ResourceBundle.getBundle("adb",Locale.CHINA);
        return bundle.getString(key);
    }

    public String getDesktopPath(){
        FileSystemView fsv = FileSystemView.getFileSystemView();
        File com=fsv.getHomeDirectory();    //这便是读取桌面路径的方法了
        return com.getAbsolutePath();
    }

    public static List<String> getInstallPath(){
        String allPath = TextAreaListener.getAllpath();
        List<String> filepathList = Arrays.asList(allPath.split("\n"));
        return filepathList;
    }

    public String getPackage(String s){

        String [] s1  = s.split(" ");
        String s2 = s1[s1.length-1];
        String s3 = s2.substring(0,s2.length()-1);
        return s3;
    }
    //截取文件名
    public static String getFileName(String filePath){
        String[] s = filePath.split("\\\\");
        String fileName = s[s.length-1];
        return fileName;
    }
    public static void main(String[] args){
    }

    //获取apk信息
    public static String getAPKMsg(String filePath){
        CMD cmd = new CMD();
        cmd.CMDCommand(Util.getCommand("getapkmsg")+filePath);
        String APKMsg = cmd.getResult();
        return APKMsg;
    }
    //截取apk信息
    public static String getApkName(String filePath){
        String allMsg = Util.getAPKMsg(filePath);
        String apkName;
        System.out.println("allMsg:\n"+allMsg);
        String[] s = allMsg.split("\n");
        List<String> l = Arrays.asList(s);
        for (String msg : l){
            System.out.println("msg:"+msg);
            if (msg.startsWith("application-label")){
                apkName = msg.split("'")[1];
                return apkName;
            }
        }
        return null;
    }
}
