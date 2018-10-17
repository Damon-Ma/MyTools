package com.damon.adb;

import com.damon.JFrame.*;
import com.damon.Util.Config;
import com.damon.Util.Keys;
import com.damon.Util.Util;

import javax.swing.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandThread extends Thread{

    private Keys name;
    private CMD cmd;
    public CommandThread(Keys name){
        this.name = name;
        cmd = new CMD();
    }
    @Override
    public void run(){
        if (name==Keys.DEVICES||name==Keys.SP_DEVICES){
            this.devices();
        }else if (name==Keys.KILL_SERVER){
            this.killServer();
        }else if (name==Keys.LOGCAT){
            this.logcat();
        }else if (name==Keys.CLEAN_LOG){
            this.cleanLog();
        }else if (name==Keys.INSTALL){
            this.tableInstall();
        }else if (name==Keys.PACKAGE){
            this.getpackage();
        }else if (name==Keys.SEND){
            this.sendText();
        }else if (name==Keys.RECOVERY){
            this.recovery();
        }else if (name==Keys.MAIN_ACTIVITY){
            this.toHome();
        }else if (name==Keys.IS_SIDELOAD){
            this.isSideload();
        }else if (name==Keys.SIDELOAD){
            this.sideload();
        }else if (name==Keys.DDMS){
            this.monitor();
        }else if (name==Keys.SHELL){
            this.shell();
        }else if (name==Keys.SCREEN){
            this.screen();
        }else if (name==Keys.INSTALL_CELL_APK){
            this.installCellAKP();
        }
    }
    //截图
    private void screen() {
        if (cmd.isConnect()){
            MyTextArea.setOutText("正在截图...");
            //截图文件路径
            String path = Util.getDesktopPath()+"\\Screenshots";
            cmd.CMDCommand("mkdir "+path);
            //日期
            String date = Util.getDate();
            //文件名
            String screenName = "Screenshot_"+date+".png";
            //sdcard中路径
            String sdPath = "/sdcard/"+screenName;
            //文件保存路径
            String screenPath = path+"\\"+screenName;

            cmd.CMDCommand("adb -s "+MyComboBox.choose+" "+Util.getCommand(name.getName())+sdPath);
            cmd.CMDCommand("adb -s "+MyComboBox.choose+" pull "+sdPath+" "+path);
            //删除终端中的文件
            cmd.CMDCommand("adb -s "+MyComboBox.choose+" shell rm -rf "+sdPath);
            cmd.CMDCommand(screenPath);
            System.out.println(cmd.getErrorResult());
            if (cmd.getErrorResult().matches("(.*[bytes].*)")){
                MyTextArea.setOutText("截图成功，文件保存在："+screenPath);
            }else {
                MyTextArea.setOutText("截图失败！！");
            }
        }
    }
    //检查连接
    private void devices(){
        MyTextArea.setOutText("正在连接...");
        if (name==Keys.DEVICES){
            cmd.CMDCommand(Util.getCommand(name.getName()));
        }else if (name==Keys.SP_DEVICES){
            cmd.CMDCommand("\""+Util.getThisPath()+"libs\\adb.exe\"\" devices");
        }
        String[] devicesResult = cmd.getResult().split("\n");
        int deviceNmb = 0;   //连接数量
        if (devicesResult.length>1){
            MyComboBox.rmItem();   //清除下拉框内容
            for (String result : devicesResult) {
                if (result.endsWith("device") || result.endsWith("sideload")) {
                    deviceNmb = deviceNmb + 1;
                    MyComboBox.addItem(deviceNmb + "、" + result.split("\t")[0]);
                }
            }
            MyLabel.setDevicesNmb(String.valueOf(deviceNmb));
        }
        if (deviceNmb==0){
            MyLabel.setDevicesNmb(String.valueOf(deviceNmb));
            MyComboBox.rmItem();   //清除下拉框内容
            MyTextArea.setOutText("设备未连接！");
        }
    }
    //抓取日志
    private void logcat(){
        if (cmd.isConnect()){
            MyTextArea.setOutText("正在抓取日志...");
            //日志文件路径
            String path = Util.getDesktopPath()+"\\logcat";
            cmd.CMDCommand("mkdir "+path);
            String filePath = path+"\\logcat"+Util.getDate()+".txt";

            cmd.CMDCommand("adb -s "+MyComboBox.choose+" "+Util.getCommand(name.getName())+filePath);
            MyTextArea.setOutText("抓取成功："+filePath);
        }

    }
    //表格安装
    private void tableInstall(){
        //清空一下状态栏
        for (int i =0;i<20;i++){
            MyTable.dtm.setValueAt(null,i,1);
        }
        //获取到表格的文件路径
        List allFilesPath = Util.getRowsData();
        //初始化行数
        Util.getRowsNum();

        if (cmd.isConnect()){
            System.out.println(allFilesPath);
            if (allFilesPath!=null) {
                int count = 0;
                for (Object installFile : allFilesPath) {
                    MyTable.dtm.setValueAt("正在安装...",count,1);
                    cmd.CMDCommand("adb -s " + MyComboBox.choose + " " + Util.getCommand(name.getName()) + "\"" + installFile + "\"");
                    if (cmd.getResult().endsWith("Success")){
                        MyTable.dtm.setValueAt("安装成功",count++,1);
                    }else {

                        Pattern r = Pattern.compile("\\[.*]");
                        Matcher m = r.matcher(cmd.getResult());
                        if (m.find()){
                            MyTable.dtm.setValueAt(m.group(),count++,1);
                        }else {
                            //MyJTable.dtm.setValueAt(cmd.getResult(),count++,1);
                            MyTable.dtm.setValueAt(cmd.getErrorResult(),count++,1);
                        }
                    }
                }
            }else {
                JOptionPane.showMessageDialog(null,
                        "请先将apk文件拖入安装程序显示框！",
                        "提示", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    //获取当前应用
    private void getpackage(){
        if (cmd.isConnect()){
            cmd.CMDCommand("adb -s "+MyComboBox.choose+" "+Util.getCommand(name.getName()));
            String ADBresult = cmd.getResult();
            String PAAresult = Util.getPackage(ADBresult);
            String[] split = PAAresult.split("/");
            if (split.length>1){
                String thispackage = split[0];
                String thisActivity = split[1];
                MyTextArea.setOutText("=========================================================");
                MyTextArea.setOutText("当前运行程序:\n"+
                        "package："+thispackage+
                        "\nActivity："+thisActivity+
                        "\npackage/Activity："+PAAresult
                );
                MyTextArea.setOutText("=========================================================");
            }else
                MyTextArea.setOutText("当前没有运行的程序！");
        }
    }
    //send text
    private void sendText(){
        String text = MyTextField.getInputText();
        String s = "";

        boolean isBlank = false;
        boolean isASCII = false;
        boolean isOther = false;
        //判断输入的文字中是否有非法字符
        for (int i = 0;i<text.length();i++){
            s = text.substring(i,i+1);
            //判断空格
            isBlank = s.matches("[\\u0020]");
            //判断其他字符
            isOther = s.matches("[\\u007C,\\u0060]");
            //判断ASCII
            isASCII = s.matches("[\\u0020-\\u007E]");

            if (!isASCII||isBlank||isOther){
                break;
            }
        }
        if (isASCII&&!isBlank&&!isOther){
            if (cmd.isConnect()){
                cmd.CMDCommand("adb -s "+MyComboBox.choose+" "+Util.getCommand(name.getName())+text);
                MyTextArea.setOutText("输入成功："+text);
            }
        }else {
            if (s.equals(" ")){
                JOptionPane.showMessageDialog(null,
                        "输入字符有误！不能发送空格",
                        "提示",JOptionPane.WARNING_MESSAGE);
                MyTextArea.setOutText("输入包含空格，请检查："+text);
            }
            else{
                //弹窗提示
                JOptionPane.showMessageDialog(null,
                        "输入字符有误！请检查：\n"+s,
                        "提示",JOptionPane.WARNING_MESSAGE);
                //输入框显示
                MyTextArea.setOutText("输入有误，请检查："+text);
            }
        }
    }
    //recovery
    private void recovery(){
        if (cmd.isConnect()){
            //弹出提示框，返回的是按钮的index i=0或者1
            int n = JOptionPane.showConfirmDialog(null,
                    "确认进入recovery模式?",
                    "提示",JOptionPane.YES_NO_OPTION);
            if (n==0){
                cmd.CMDCommand("adb -s "+MyComboBox.choose+" "+Util.getCommand(name.getName()));
                MyTextArea.setOutText("OOOOOOOOOOOOOK!");
            }else {
                MyTextArea.setOutText("取消！！");
            }
        }
    }
    //返回桌面
    private void toHome(){
        if (cmd.isConnect()){
            cmd.CMDCommand("adb -s "+MyComboBox.choose+" "+Util.getCommand(name.getName()));
            cmd.CMDCommand("adb -s "+MyComboBox.choose+" "+Util.getCommand("toHome2"));
            MyTextArea.setOutText("OOOOOOOOOOOOOOVER!");
        }
    }
    //检查sideload模式
    private void isSideload(){
        MyTextArea.setOutText("正在连接...");
        cmd.CMDCommand("adb kill-server");
        cmd.CMDCommand("adb kill-server");
        cmd.CMDCommand("adb kill-server");
        cmd.CMDCommand("adb start-server");
        cmd.CMDCommand("adb start-server");
        cmd.CMDCommand("adb start-server");
        cmd.CMDCommand("adb start-server");
        cmd.CMDCommand("adb start-server");
        cmd.CMDCommand("adb devices");

        if (cmd.getResult().startsWith("*")||!cmd.getResult().startsWith("List")){
            MyTextArea.setOutText("再点一下~~");
        }else {
            String[] devicesResult = cmd.getResult().split("\n");
            boolean isSideload = false; //初始化一个isSideload，如果选择的设备不为sideload模式，则isSideload=false
            for (String aDevicesResult : devicesResult) {
                if (aDevicesResult.endsWith("sideload") && aDevicesResult.split("\t")[0].equals(MyComboBox.choose)) {
                    MyTextArea.setOutText("请将刷机包托到输出台，点击sideload开始刷机！");
                    isSideload = true;
                }
            }
            if (!isSideload){
                MyTextArea.setOutText("请确认设备是否在adb刷机界面！");
                MyTextArea.setOutText("先检查连接，选择设备，再检查sideload");
            }
        }
    }
    //刷机 sideload
    private void sideload(){

        if (cmd.isSideload()){
            //获取刷机包的路径
            try {
                Config.OSPath = Util.getInstallPath(Config.allpath).get(0);
            }catch (NullPointerException e){
                JOptionPane.showMessageDialog(null,
                        "请将刷机包拖进输出台！",
                        "提示",JOptionPane.WARNING_MESSAGE);
            }
            cmd.CMDCommand("adb -s "+MyComboBox.choose+" "+Util.getCommand(name.getName())+Config.OSPath);
            Config.OSPath = null;
            if (cmd.getErrorResult() != null){
                MyTextArea.setOutText(cmd.getErrorResult());
            }
        }else {
            MyTextArea.setOutText("设备未连接，请先检查sideload！");
        }
    }
    //打开DDMS
    private void monitor(){
        MyTextArea.setOutText("正在运行 Dalvik Debug Monitor Service（DDMS）...");
        cmd.CMDCommand("monitor");
        MyTextArea.setOutText("DDMS 已关闭！");
    }
    //清除日志
    private void cleanLog(){
        if (cmd.isConnect()){
            cmd.CMDCommand("adb -s "+MyComboBox.choose+" "+Util.getCommand(name.getName()));
            MyTextArea.setOutText("清除成功！");
        }
    }
    //结束adb
    private void killServer(){
        cmd.CMDCommand(Util.getCommand(name.getName()));
        cmd.CMDCommand(Util.getCommand(name.getName()));
        cmd.CMDCommand(Util.getCommand(name.getName()));
        MyTextArea.setOutText("OOOOOOOOOOOOOOK!");
    }
    //adb shell
    private void shell(){
        if (cmd.isConnect()){
            cmd.CMDCommand("start adb -s "+MyComboBox.choose+" shell");
        }
    }
    //安装单个apk
    private void installCellAKP(){
        if (cmd.isConnect()){
            //把行号存起来，不然不同线程调用的时候会乱
            int row = Config.row;
            MyTable.dtm.setValueAt("正在安装...",row,1);
            cmd.CMDCommand("adb -s " + MyComboBox.choose + " install -r \"" + Config.cellVal + "\"");
            if (cmd.getResult().endsWith("Success")){
                MyTable.dtm.setValueAt("安装成功",row,1);
            }else {
                Pattern r = Pattern.compile("\\[.*]");
                Matcher m = r.matcher(cmd.getResult());
                if (m.find()){
                    MyTable.dtm.setValueAt(m.group(),row,1);
                }else {
                    MyTable.dtm.setValueAt(cmd.getErrorResult(),row,1);
                }
            }
        }
    }
}
