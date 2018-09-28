package com.damon.adb;

import javax.swing.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandThread extends Thread{

    Keys name;
    CMD cmd = new CMD();
    public CommandThread(Keys name){
        this.name = name;
    }
    static Object ob = "aa";
    @Override
    public void run(){
        if (name==Keys.DEVICES||name==Keys.spdevices){
            this.devices();
        }else if (name==Keys.kill_server){
            this.killServer();
        }else if (name==Keys.logcat){
            this.logcat();
        }else if (name==Keys.cleanLog){
            this.cleanLog();
        }else if (name==Keys.install){
            this.tableInstall();
        }else if (name==Keys.getpackage){
            this.getpackage();
        }else if (name==Keys.send){
            this.sendText();
        }else if (name==Keys.recovery){
            this.recovery();
        }else if (name==Keys.toHome){
            this.toHome();
        }else if (name==Keys.isSideload){
            this.isSideload();
        }else if (name==Keys.sideload){
            this.sideload();
        }else if (name==Keys.monitor){
            this.monitor();
        }else if (name==Keys.shell){
            this.shell();
        }else if (name==Keys.screen){
            this.screen();
        }else if (name==Keys.installCellAKP){
            this.installCellAKP();
        }
    }
    //截图
    private void screen() {
        if (cmd.isConnect()){
            Application.setOutText("正在截图...");
            //截图文件路径
            String path = Util.getDesktopPath()+"\\screen";
            cmd.CMDCommand("mkdir "+path);
            //日期
            String date = Util.getDate();
            //文件名
            String screenName = "screen"+date+".png";
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
                Application.setOutText("截图成功，文件保存在："+screenPath);
            }else {
                Application.setOutText("截图失败！！");
            }
        }
    }
    //检查连接
    private void devices(){
        Application.setOutText("正在连接...");
        if (name==Keys.DEVICES){
            cmd.CMDCommand(Util.getCommand(name.getName()));
        }else if (name==Keys.spdevices){
            cmd.CMDCommand("\""+Util.getThisPath()+"libs\\adb.exe\"\" devices");
        }
        String[] devicesResult = cmd.getResult().split("\n");
        int deviceNmb = 0;   //连接数量
        if (devicesResult.length>1){
            Application.rmItem();   //清除下拉框内容
            for (String result : devicesResult) {
                if (result.endsWith("device") || result.endsWith("sideload")) {
                    deviceNmb = deviceNmb + 1;
                    Application.addItem(deviceNmb + "、" + result.split("\t")[0]);
                }
            }
            Application.setDevicesNmb(String.valueOf(deviceNmb));
        }
        if (deviceNmb==0){
            Application.setDevicesNmb(String.valueOf(deviceNmb));
            Application.rmItem();   //清除下拉框内容
            Application.setOutText("设备未连接！");
        }
    }
    //抓取日志
    private void logcat(){
        if (cmd.isConnect()){
            Application.setOutText("正在抓取日志...");
            //日志文件路径
            String path = Util.getDesktopPath()+"\\logcat";
            cmd.CMDCommand("mkdir "+path);
            String filePath = path+"\\logcat"+Util.getDate()+".txt";

            cmd.CMDCommand("adb -s "+MyComboBox.choose+" "+Util.getCommand(name.getName())+filePath);
            Application.setOutText("抓取成功："+filePath);
        }

    }
    //控制台安装
    private void install(){

        int sum = 0; //计数
        int successNu = 0; //安装成功数量

        //获取到面板上的文件路径
        String thisText = Application.textArea.textArea.getText();
        if (cmd.isConnect()){
            //判断面板是否进行过其他操作
            if (thisText.endsWith("---------\n")){
                String fileName;
                List<String> filesPath = Util.getInstallPath(Config.allpath);

                sum = filesPath.size();

                if (filesPath.size()!=0) {
                    for (String installFile : filesPath) {
                        fileName = Util.getFileName(installFile);
                        if (installFile.endsWith(".apk")) {
                            Application.setOutText("正在安装\"" + fileName + "\"请稍后。。。");
                            cmd.CMDCommand("adb -s " + MyComboBox.choose + " " + Util.getCommand(name.getName()) + "\"" + installFile + "\"");
                            Application.setOutText("安装结果：" + Util.getLastLine(cmd.getResult()));

                            if (Util.getLastLine(cmd.getResult()).equals("Success")) {
                                successNu++;
                            }
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    Util.getFileName(installFile) + "不是正确的安装包！",
                                    "提示", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                    Application.setOutText("-----------------------------------------\n" +
                            "共"+sum+"个安装包，安装成功"+successNu+"个，失败"+(sum-successNu)+"个\n" +
                            "-----------------------------------------");
                }
            }else{
                JOptionPane.showMessageDialog(null,
                        "请将apk文件拖入输出台！",
                        "提示",JOptionPane.WARNING_MESSAGE);
                Application.setOutText("请将apk文件拖入输出台！");
            }
        }
    }
    //表格安装
    private void tableInstall(){
        //清空一下状态栏
        for (int i =0;i<20;i++){
            MyJTable.dtm.setValueAt(null,i,1);
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
                    MyJTable.dtm.setValueAt("正在安装...",count,1);
                    cmd.CMDCommand("adb -s " + MyComboBox.choose + " " + Util.getCommand(name.getName()) + "\"" + installFile + "\"");
                    if (cmd.getResult().endsWith("Success1")){
                        MyJTable.dtm.setValueAt("安装成功",count++,1);
                    }else {

                        Pattern r = Pattern.compile("\\[.*]");
                        Matcher m = r.matcher(cmd.getResult());
                        if (m.find()){
                            MyJTable.dtm.setValueAt(m.group(),count++,1);
                        }else {
                            //MyJTable.dtm.setValueAt(cmd.getResult(),count++,1);
                            MyJTable.dtm.setValueAt(cmd.getErrorResult(),count++,1);
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
                Application.setOutText("=========================================================");
                Application.setOutText("当前运行程序:\n"+
                        "package："+thispackage+
                        "\nActivity："+thisActivity+
                        "\npackage/Activity："+PAAresult
                );
                Application.setOutText("=========================================================");
            }else
                Application.setOutText("当前没有运行的程序！");
        }
    }
    //send text
    private void sendText(){
        String text = Application.getInputText();
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
                Application.setOutText("输入成功："+text);
            }
        }else {
            if (s.equals(" ")){
                JOptionPane.showMessageDialog(null,
                        "输入字符有误！不能发送空格",
                        "提示",JOptionPane.WARNING_MESSAGE);
                Application.setOutText("输入包含空格，请检查："+text);
            }
            else{
                //弹窗提示
                JOptionPane.showMessageDialog(null,
                        "输入字符有误！请检查：\n"+s,
                        "提示",JOptionPane.WARNING_MESSAGE);
                //输入框显示
                Application.setOutText("输入有误，请检查："+text);
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
                Application.setOutText("OOOOOOOOOOOOOK!");
            }else {
                Application.setOutText("取消！！");
            }
        }
    }
    //返回桌面
    private void toHome(){
        if (cmd.isConnect()){
            cmd.CMDCommand("adb -s "+MyComboBox.choose+" "+Util.getCommand(name.getName()));
            cmd.CMDCommand("adb -s "+MyComboBox.choose+" "+Util.getCommand("toHome2"));
            Application.setOutText("OOOOOOOOOOOOOOVER!");
        }
    }
    //检查sideload模式
    private void isSideload(){
        cmd.CMDCommand("adb start-server");
        cmd.CMDCommand("adb start-server");
        cmd.CMDCommand("adb start-server");
        cmd.CMDCommand("adb start-server");
        cmd.CMDCommand("adb devices");

        if (cmd.getResult().startsWith("*")||!cmd.getResult().startsWith("List")){
            Application.setOutText("再点一下~~");
        }else {
            String[] devicesResult = cmd.getResult().split("\n");
            int j = 0; //初始化一个j，如果选择的设备不为sideload模式，则j为0
            for (String aDevicesResult : devicesResult) {
                if (aDevicesResult.endsWith("sideload") && aDevicesResult.split("\t")[0].equals(MyComboBox.choose)) {
                    Application.setOutText("请将刷机包托到输出台，点击sideload开始刷机！");
                    j++;
                }
            }
            if (j==0){
                Application.setOutText("请确认设备是否在adb刷机界面！");
                Application.setOutText("先检查连接，选择设备，再检查sideload");
            }
        }
    }
    //刷机 sideload
    private void sideload(){
        //当前面板显示内容
        String labelText = Application.textArea.textArea.getText();
        System.out.println(labelText);
        if (labelText.endsWith("--------\n")){
            //获取刷机包的路径
            String filePath = Util.getInstallPath(Config.allpath).get(0);
            if (cmd.isSideload()){
                cmd.CMDCommand("adb -s "+MyComboBox.choose+" "+Util.getCommand(name.getName())+filePath);
                if (cmd.getErrorResult() != null){
                    Application.setOutText(cmd.getErrorResult());
                }
            }else {
                Application.setOutText("设备未连接，请先检查sideload！");
            }
        }else {
            JOptionPane.showMessageDialog(null,
                    "请将刷机包拖进输出台！",
                    "提示",JOptionPane.WARNING_MESSAGE);
        }
    }
    //打开DDMS
    private void monitor(){
        Application.setOutText("正在运行 Dalvik Debug Monitor Service（DDMS）...");
        cmd.CMDCommand("monitor");
        Application.setOutText("DDMS 已关闭！");
    }
    //清除日志
    private void cleanLog(){
        if (cmd.isConnect()){
            cmd.CMDCommand("adb -s "+MyComboBox.choose+" "+Util.getCommand(name.getName()));
            Application.setOutText("清除成功！");
        }
    }
    //结束adb
    private void killServer(){
        cmd.CMDCommand(Util.getCommand(name.getName()));
        Application.setOutText("OOOOOOOOOOOOOOK!");
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
            MyJTable.dtm.setValueAt("正在安装...",row,1);
            cmd.CMDCommand("adb -s " + MyComboBox.choose + " install -r \"" + Config.cellVal + "\"");
            if (cmd.getResult().endsWith("Success")){
                MyJTable.dtm.setValueAt("安装成功",row,1);
            }else {
                Pattern r = Pattern.compile("\\[.*]");
                Matcher m = r.matcher(cmd.getResult());
                if (m.find()){
                    MyJTable.dtm.setValueAt(m.group(),row,1);
                }else {
                    MyJTable.dtm.setValueAt(cmd.getErrorResult(),row,1);
                }
            }
        }
    }
}
