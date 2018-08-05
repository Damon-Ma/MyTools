package com.damon.adb;

import javax.swing.filechooser.FileSystemView;
import java.io.File;

public class ADBCommand extends CMD {
    Util util = new Util();
    public String devices(){
        super.CMDCommand("adb devices");
        return getResult();
    }
    public String kill_server(){
        super.CMDCommand("adb kill-server");
        return getResult();
    }
    public String specialAdb(){
        super.CMDCommand(".\\libs\\adb\\adb.exe devices");
        return getResult();
    }
    public String logcat(){
        if (util.getLastSt(this.devices()).endsWith("device")||util.getLastSt(this.specialAdb()).endsWith("device")){
            FileSystemView fsv = FileSystemView.getFileSystemView();
            File com=fsv.getHomeDirectory();    //这便是读取桌面路径的方法了
            super.CMDCommand("mkdir "+com.getAbsolutePath()+"\\logcat");
            String path = com.getAbsolutePath()+"\\logcat\\logcat"+util.getDate()+".txt";
            super.CMDCommand("adb logcat -d > "+path);
            return path;
        }
        return "设备未连接";
    }
}
