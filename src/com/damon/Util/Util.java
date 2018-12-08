package com.damon.Util;

import com.damon.JFrame.MyTable;
import com.damon.adb.CMD;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    static CMD cmd = new CMD();

    //获取时间
    public static String getDate(){
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        return df.format(System.currentTimeMillis());
        }
    //获取安装结果
    public static String getLastLine(String s){
        String[] strings = s.split("\n");
        return strings[strings.length-1];
    }
    //从配置文件中获取命令
    public static String getCommand(String key){
        ResourceBundle bundle = ResourceBundle.getBundle("adb",Locale.CHINA);
        return bundle.getString(key);
    }
    //获取接口配置
    public static String getInterFace(String key){
        ResourceBundle bundle = ResourceBundle.getBundle("interfaceMsg",Locale.CHINA);
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
        Process p = cmd.Cmd("\""+Util.getThisPath()+"libs\\aapt.exe\" "+Util.getCommand("getapkmsg")+"\""+filePath+"\"");

        String APKMsgs = cmd.getResult(p);
        String[] s = APKMsgs.split("\n");
        return Arrays.asList(s);
    }
    //字符串通过空格截取成List
    public static List<String> getStrList(String str){
        String[] strings = str.split(" ");
        return Arrays.asList(strings);
    }
    //获取应用名、包名、MainActivity
    public static String getMyAPKMsg(String filePath,String msgName){
        String apkName;
        String apkActivity;
        List<String> l = Util.getAPKMsg(filePath);
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
    public static String getApkName(String filePath){
        String msgName = Keys.APK_NAME.getName();
        return Util.getMyAPKMsg(filePath,msgName);
    }
    //获取应用包名
    public static String getApkPackage(String filePath){
        String msgName = Keys.APN_PACKAGE_NAME.getName();
        return Util.getMyAPKMsg(filePath,msgName);
    }
    //获取应用MainActivity
    public static String getApkActivity(String filePath){
        String msgName = Keys.APK_ACTIVITY_NAME.getName();
        return Util.getMyAPKMsg(filePath,msgName);
    }
    //获取应用版本
    public static String getApkVersion(String filePath){
        String msgName = Keys.APK_VERSION.getName();
        return Util.getMyAPKMsg(filePath,msgName);
    }
    //获取当前class路径
    public static String getThisPath(){
        String s =  Objects.requireNonNull(Util.class.getClassLoader().getResource("1.jpg")).getPath();
        //s=  file:/C:/Users/Malik/Desktop/MyTools.jar!/1.jpg
        s = s.split("file:/")[1].split("MyTools.jar!")[0];
        //将/换成\
        s = s.replace("/","\\");
        //路径中有空格时，因为获取到的是url，所以空格显示的是%20，把空格换成空格
        s = s.replace("%20"," ");

        return s;
    }
    //获取当前OS版本
    public static String getOS(String getChoose) {
        Process p;
        String result;
        p = cmd.Cmd("adb -s "+getChoose+" "+Util.getCommand("system"));
        result = cmd.getResult(p);
        //System.out.println("错误结果："+cmd.getResult(p)+"--");
        if (result==null) {
            p = cmd.Cmd("adb -s "+getChoose+" "+Util.getCommand("system6.0"));
            result = cmd.getResult(p);
        }
        return result;
    }
    //正则匹配OS信息
    public static String match(String matcher,String regex){
        // 创建 Pattern 对象
        Pattern r = Pattern.compile(regex);
        // 现在创建 matcher 对象
        Matcher m = r.matcher(matcher);
        return m.find() ? m.group():null;
    }
    //判断是否含中文
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        return m.find();
    }
    //获取设备型号
    public static String getMode(String getChoose){
        Process p;
        p = cmd.Cmd("adb -s "+getChoose+" shell getprop ro.product.model");
        return cmd.getResult(p);
    }
    //获取SN号
    public static String getSnNum(String getChoose){
        Process p = cmd.Cmd("adb -s "+getChoose+" shell getprop persist.sys.product.serialno");
        return cmd.getResult(p);
    }
    //获取TUSN
    public static String getTusnNum(String getChoose){
        Process p = cmd.Cmd("adb -s "+getChoose+" shell getprop persist.sys.product.tusn");
        return cmd.getResult(p);
    }
    //获取一下表格中有数据的行数
    public static int getRowsNum(){
        int row=0;
        for (int i=0;i <20;i++){
            if (MyTable.dtm.getValueAt(i,0)!=null){
                row++;
            }
            //Config.rowsNum = row;
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
                rowData = MyTable.dtm.getValueAt(i,0);
                rowsData.add(rowData);
            }
            return rowsData;
        }
    }
    //获取当前连接设备
    public static Object[] getConnectDevice(String cmdResult){
        List connectDevice = new ArrayList();
        String[] cmdResults = cmdResult.split("\n");
        for (String device : cmdResults){
            if (device.endsWith("device") || device.endsWith("sideload")){
                connectDevice.add(device.split("\t")[0]);
            }
        }
        return connectDevice.toArray();
    }


}
