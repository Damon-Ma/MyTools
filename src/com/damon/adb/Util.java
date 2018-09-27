package com.damon.adb;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    static ResourceBundle bundle;
    private static List<String> l;      //获取到的apk信息


    public static String getDate(){
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_hh_mm_ss");
        return df.format(System.currentTimeMillis());
        }
    //获取安装结果
    public static String getLastLine(String s){
        String[] strings = s.split("\n");
        return strings[strings.length-1];
    }
    //从配置文件中获取命令
    public static String getCommand(String key){
        bundle = ResourceBundle.getBundle("adb",Locale.CHINA);
        System.out.println();
        return bundle.getString(key);
    }
//    获取到桌面路径
    public static String getDesktopPath(){
        FileSystemView fsv = FileSystemView.getFileSystemView();
        File com=fsv.getHomeDirectory();    //这便是读取桌面路径的方法了
        return com.getAbsolutePath();
    }
//    获取所有安装包路径
    public static List<String> getInstallPath(String allPath){
        return Arrays.asList(allPath.split("\n"));
    }
//     获取包名和Activity
    public static String getPackage(String s){

        String [] s1  = s.split(" ");
        String s2 = s1[s1.length-1];
        return s2.substring(0,s2.length()-1);
    }
    //截取文件名
    public static String getFileName(String filePath){
        String[] s = filePath.split("\\\\");
        return s[s.length-1];
    }
    //将apk信息截取成List
    public static List<String> getAPKMsg(String filePath){
        CMD cmd = new CMD();
        cmd.CMDCommand("\""+Util.getThisPath()+"libs\\aapt.exe\" "+Util.getCommand("getapkmsg")+"\""+filePath+"\"");


        String APKMsgs = cmd.getResult();
        String[] s = APKMsgs.split("\n");
        l = Arrays.asList(s);
        return l;
    }
    //字符串通过空格截取成List
    public static List<String> getStrList(String str){
        String[] strings = str.split(" ");
        return Arrays.asList(strings);
    }
    //获取应用名、包名、MainActivity
    public static String getMyAPKMsg(String msgName){
        String apkName;
        String apkActivity;
//        List<String> l = Util.getAPKMsg(filePath);
        for (String msg : l){
//            System.out.println("msg:"+msg);
            //用冒号截取名称
            String Msgname = msg.split(":")[0];
            //获取应用名称
            if (Msgname.startsWith("application-label")&&msgName.equals("apkName")){
                apkName = msg.split("'")[1];
                return apkName;
            }   //获取Activity
            else if (Msgname.startsWith("launchable-activity")&&msgName.equals("activity")){
                apkActivity = msg.split("'")[1];
                return apkActivity;
            }   //获取包名、版本号
            else if (Msgname.startsWith("package")){
                String MsgValue = msg.split(":")[1];
                //截取package信息
                List<String> MsgList = Util.getStrList(MsgValue);
                for (String packageMsg:MsgList){
                    if (msgName.equals("package")&&packageMsg.startsWith("name")){
                        return packageMsg.split("=")[1];
                    }else if (msgName.equals("appVersion")&&packageMsg.startsWith("versionName")){
                        return packageMsg.split("=")[1];
                    }
                }

            }
        }
        return null;
    }
    //获取应用名称
    public static String getApkName(){
        String msgName = Keys.getAPKName.getName();
        String apkName = Util.getMyAPKMsg(msgName);
        return apkName;
    }
    //获取应用包名
    public static String getApkPackage(){
        String msgName = Keys.getAPKPackageName.getName();
        String packageName = Util.getMyAPKMsg(msgName);
        return packageName;
    }
    //获取应用MainActivity
    public static String getApkActivity(){
        String msgName = Keys.getAPKActivity.getName();
        String activityName = Util.getMyAPKMsg(msgName);
        return activityName;
    }
    //获取应用版本
    public static String getApkVersion(){
        String msgName = Keys.getAPKVersion.getName();
        String version = Util.getMyAPKMsg(msgName);
        return version;
    }
    //获取当前class路径
    public static String getThisPath(){
        String s =  Util.class.getClassLoader().getResource("1.jpg").getPath();
        //s=  file:/C:/Users/Malik/Desktop/MyTools.jar!/1.jpg
        s = s.split("file:/")[1].split("MyTools.jar!")[0];
        //将/换成\
        s = s.replace("/","\\");
        //路径中有空格时，因为获取到的是url，所以空格显示的是%20，把空格换成空格
        s = s.replace("%20"," ");

 //       Application.setOutText("获取到的路径是："+s);
        return s;
    }
    //获取当前OS版本
    public static String getOS(String getChoose) {
        CMD cmd = new CMD();
        cmd.CMDCommand("adb -s "+getChoose+" "+Util.getCommand("system"));
        if (cmd.getResult().equals("")) {
            cmd.CMDCommand("adb -s "+getChoose+" "+Util.getCommand("system6.0"));
        }
        return cmd.getResult();
    }
    //正则匹配OS信息
    public static String getOSMsg(String osName,String regex){
        // 创建 Pattern 对象
        Pattern r = Pattern.compile(regex);
        // 现在创建 matcher 对象
        Matcher m = r.matcher(osName);
        if (m.find()){
            return m.group();
        }
        return null;
    }
    //判断是否含中文
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        return m.find();
    }

    //获取一下表格中有数据的行数
    public static int getRowsNum(){
        int row=0;
        for (int i=0;i <20;i++){
            if (MyJTable.dtm.getValueAt(i,0)!=null){
                row++;
            }
            Config.rowsNum = row;
        }
        return row;
    }
    //获取表格所有数据
    public static List getRowsData(){
        List rowsData = new ArrayList(); //存放数据
        Object rowData; //存放每行数据

        if (Util.getRowsNum()==0){
            return  null;
        }else {
            for (int i =0;i<Util.getRowsNum();i++){
                rowData = MyJTable.dtm.getValueAt(i,0);
                rowsData.add(rowData);
            }
            return rowsData;
        }
    }


}
