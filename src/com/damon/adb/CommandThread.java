package com.damon.adb;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.damon.JFrame.*;
import com.damon.Util.Config;
import com.damon.Util.Keys;
import com.damon.Util.Log;
import com.damon.Util.Util;
import com.damon.sign.Sign;
import org.apache.commons.text.StringEscapeUtils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandThread extends Thread{

    private Keys name;
    private CMD cmd;
    private Process publicP;


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
        }else if (name==Keys.SIGN_LOGIN){
            this.loginSignSys();
        }else if (name==Keys.GET_APK_LIST){
            this.getAPKList();
        }else if (name==Keys.UPLOAD_APK){
            this.upload();
        }else if (name==Keys.DOWNLOAD_SIGNED_APK){
            this.downloadSignedFile();
        }
    }
    //截图
    private void screen() {
        if (cmd.isConnect()){

            MyTextArea.setOutText("正在截图...");
            //截图文件路径
            String path = Util.getDesktopPath()+"\\Screenshots";
            //日期
            String date = Util.getDate();
            //文件名
            String screenName = "Screenshot_"+date+".png";
            //sdcard中路径
            String sdPath = "/sdcard/"+screenName;
            //文件保存路径
            String screenPath = path+"\\"+screenName;
            publicP = cmd.Cmd("mkdir "+path);
            cmd.getResult(publicP);
            publicP = cmd.Cmd("adb -s "+MyComboBox.choose+" "+Util.getCommand(name.getName())+sdPath);
            cmd.getResult(publicP);
            publicP = cmd.Cmd("adb -s "+MyComboBox.choose+" pull "+sdPath+" "+path);
            cmd.getResult(publicP);
            //删除终端中的文件
            publicP = cmd.Cmd("adb -s "+MyComboBox.choose+" shell rm -rf "+sdPath);
            cmd.getResult(publicP);
            //打开截图文件
            publicP = cmd.Cmd(screenPath);
            if (cmd.getErrorResult(publicP)==null){
                MyTextArea.setOutText("截图成功，文件保存在："+screenPath);
            }else {
                MyTextArea.setOutText("截图失败！！");
            }
        }
    }
    //检查连接
    private void devices(){
        MyTextArea.setOutText("正在连接...");
        Process p = null;
        if (name==Keys.DEVICES){
            p = cmd.Cmd(Util.getCommand(name.getName()));
        }else if (name==Keys.SP_DEVICES){
            p = cmd.Cmd("\""+Util.getThisPath()+"libs\\adb.exe\"\" devices");
        }

        Object[] deviceResult = Util.getConnectDevice(cmd.getResult(p));
        MyLabel.setDevicesNmb(String.valueOf(deviceResult.length));
        int deviceNmb = 0;   //连接序号
        if (deviceResult.length>0){
            MyComboBox.rmItem();
            for (Object ob : deviceResult){
                deviceNmb++;
                MyComboBox.addItem(deviceNmb + "、" + ob);
            }
        }else{
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
            String filePath = path+"\\logcat"+Util.getDate()+".txt";
            publicP = cmd.Cmd("mkdir "+path);
            cmd.getResult(publicP);
            publicP = cmd.Cmd("adb -s "+MyComboBox.choose+" "+Util.getCommand(name.getName())+filePath);
            cmd.getResult(publicP);
            MyTextArea.setOutText("抓取成功："+filePath);
            //打开保存文件夹
            publicP = cmd.Cmd("start "+path);

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
        //Util.getRowsNum();

        if (cmd.isConnect()){
            System.out.println(allFilesPath);
            if (allFilesPath!=null) {
                int count = 0;
                for (Object installFile : allFilesPath) {
                    MyTable.dtm.setValueAt("正在安装...",count,1);
                    Process p = cmd.Cmd("adb -s " + MyComboBox.choose + " " + Util.getCommand(name.getName()) + "\"" + installFile + "\"");
                    String installResult = cmd.getResult(p);
                    if (installResult.endsWith("Success")){
                        MyTable.dtm.setValueAt("安装成功",count++,1);
                    }else {
                        Pattern r = Pattern.compile("\\[.*");
                        Matcher m = r.matcher(installResult);
                        if (m.find()){
                            MyTable.dtm.setValueAt(m.group(),count++,1);
                        }else {
                            //MyJTable.dtm.setValueAt(cmd.getResult(),count++,1);
                            MyTable.dtm.setValueAt(cmd.getErrorResult(p),count++,1);
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
            Process p = cmd.Cmd("adb -s "+MyComboBox.choose+" "+Util.getCommand(name.getName()));
            String ADBresult = cmd.getResult(p);
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
                cmd.Cmd("adb -s "+MyComboBox.choose+" "+Util.getCommand(name.getName())+text);
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
                cmd.Cmd("adb -s "+MyComboBox.choose+" "+Util.getCommand(name.getName()));
                MyTextArea.setOutText("OOOOOOOOOOOOOK!");
            }else {
                MyTextArea.setOutText("取消！！");
            }
        }
    }
    //返回桌面
    private void toHome(){
        if (cmd.isConnect()){
            cmd.Cmd("adb -s "+MyComboBox.choose+" "+Util.getCommand(name.getName()));
            cmd.Cmd("adb -s "+MyComboBox.choose+" "+Util.getCommand("toHome2"));
            MyTextArea.setOutText("返回主界面!");
        }
    }
    //检查sideload模式
    private void isSideload(){
        MyTextArea.setOutText("正在连接...");
        //adb刷机时偶尔会因为adb意外停止而断开，所以在这里尝试启动adb，检查是否会自动停止
        Process p;
        do {
            p = cmd.Cmd("adb start-server");
        }while (cmd.getResult(p)!=null);

        String[] devicesResult = cmd.getResult(cmd.Cmd("adb devices")).split("\n");
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
            Process p = cmd.Cmd("adb -s "+MyComboBox.choose+" "+Util.getCommand(name.getName())+Config.OSPath);
            cmd.printRealResult(p);
            Config.OSPath = null;
            if (cmd.getErrorResult(p) != null){
                MyTextArea.setOutText(cmd.getErrorResult(p));
            }
        }else {
            MyTextArea.setOutText("设备未连接，请先检查sideload！");
        }
    }
    //打开DDMS
    private void monitor(){
        MyTextArea.setOutText("正在运行 Dalvik Debug Monitor Service（DDMS）...");
        Process ddms = cmd.Cmd("monitor");
        cmd.getResult(ddms);
        MyTextArea.setOutText("DDMS 已关闭！");
    }
    //清除日志
    private void cleanLog(){
        if (cmd.isConnect()){
            cmd.Cmd("adb -s "+MyComboBox.choose+" "+Util.getCommand(name.getName()));
            MyTextArea.setOutText("清除成功！");
        }
    }
    //结束adb
    private void killServer(){
        cmd.Cmd(Util.getCommand(name.getName()));
        cmd.Cmd(Util.getCommand(name.getName()));
        cmd.Cmd(Util.getCommand(name.getName()));
        MyTextArea.setOutText("OOOOOOOOOOOOOOK!");
    }
    //adb shell
    private void shell(){
        if (cmd.isConnect()){
            cmd.Cmd("start adb -s "+MyComboBox.choose+" shell");
        }
    }
    //安装单个apk
    private void installCellAKP(){
        if (cmd.isConnect()){
            //把行号存起来，不然不同线程调用的时候会乱
            int row = Config.row;
            MyTable.dtm.setValueAt("正在安装...",row,1);
            Process p = cmd.Cmd("adb -s " + MyComboBox.choose + " install -r \"" + Config.cellVal + "\"");
            String installResult = cmd.getResult(p);
            if (installResult.endsWith("Success")){
                MyTable.dtm.setValueAt("安装成功",row,1);
            }else {
                Pattern r = Pattern.compile("\\[.*]");
                Matcher m = r.matcher(installResult);
                if (m.find()){
                    MyTable.dtm.setValueAt(m.group(),row,1);
                }else {
                    MyTable.dtm.setValueAt(cmd.getErrorResult(p),row,1);
                }
            }
        }
    }
    //登陆签名系统
    private void loginSignSys(){
        MyLabel.uploadResult.setText("正在登陆...");
        Config.sign = new Sign();
        Boolean signResult = Config.sign.login();
        if (signResult){
            MyLabel.uploadResult.setText("登陆成功!");
            Log.logger.info("登陆成功！");
        }else {
            MyLabel.uploadResult.setText("登陆失败！！");
        }
    }
    //获取应用列表
    private void getAPKList(){
        //fileIdList
        Config.fileIdList = new ArrayList<Integer>();
        int count = MyTable.signDTM.getRowCount();
        Log.logger.info("共有："+count+"行");
        for (int i=0; i<count;i++){
            MyTable.signDTM.removeRow(0);
        }

        if (Config.sign==null){
            MyLabel.uploadResult.setText("请先登陆！");
        }else {
            MyLabel.uploadResult.setText("获取列表...");

            JSONObject resultJson = Config.sign.getFileList();

            //tisohiyong
//            JSONObject resultJson = HelloWorld.resultJson();

            if (resultJson.size()==1){
                MyLabel.uploadResult.setText(resultJson.getString("result"));
            }else if (resultJson.size()==2){
                //获取apk的jsonarray
                JSONArray apkList = resultJson.getJSONArray("rows");
                for (int i=0;i<apkList.size();i++){
                    //获取单个apk的json对象
                    JSONObject apkJson = apkList.getJSONObject(i);

                    //id
                    int id = (int)apkJson.get("id");
                    //证书类型
                    String signType = apkJson.getString("certificate");
                    //文件名
                    String apkName = apkJson.getString("fileName");
                    //上传时间
                    String uploadTime = apkJson.getString("uploadTimeStr");
//                    Log.logger.info("id:"+id);
//                    Log.logger.info("证书类型："+signType);
//                    Log.logger.info("文件名："+apkName);
//                    Log.logger.info("上传时间："+uploadTime);
//                    Log.logger.info("================================");

                    Config.fileIdList.add(id);

                    Object[] apkMsg = {apkName,uploadTime,signType,"下载签名文件"};
                    MyTable.signDTM.addRow(apkMsg);
                    MyLabel.uploadResult.setText("获取成功！");
                }


            }else {
                MyLabel.uploadResult.setText("获取成功，列表为空！");
            }

        }
    }
    //上传
    private void upload(){
        if (Config.scrFilePath==null){
            JOptionPane.showMessageDialog(null,
                    "请拖入apk文件！",
                    "提示",
                    JOptionPane.WARNING_MESSAGE);
        }else if (Config.signType==null || Config.signType.equals("---请选择---")){
            JOptionPane.showMessageDialog(null,
                    "请选择证书类型！",
                    "提示",
                    JOptionPane.WARNING_MESSAGE);
        }else {
            MyLabel.uploadResult.setText("正在上传...");

            Log.logger.info("证书类型："+Config.signType);
            Log.logger.info("文件路径："+Config.scrFilePath);

            String uploadResult = Config.sign.upload(Config.signType,Config.scrFilePath);

            String matchResult = Util.match(uploadResult,"(?<=<font color=\"red\">).*(?= </font>)");
            if (matchResult ==null){
                if (uploadResult.contains("用户名不能为空")){
                    MyLabel.uploadResult.setText("登录过期！");
                }else {
                    MyLabel.uploadResult.setText("未知异常！");
                    Log.logger.error(uploadResult);
                }
            }else {
                matchResult = StringEscapeUtils.unescapeHtml4(matchResult);
                MyLabel.uploadResult.setText(matchResult);
                if (matchResult.equals("上传成功")) {
                    getAPKList();
                    MyLabel.upLoadFileName.setText("");
                    MyLabel.showFile.setForeground(Color.RED);
                }

            }
        }

    }
    //下载签名文件
    private void downloadSignedFile(){

        int rowNum = Config.downloadRowNum;
        System.out.println("下载第："+rowNum+"行");

        int id = Config.fileIdList.get(rowNum);
        Config.sign.signDownload(id);
        cmd.Cmd("start "+Util.getDesktopPath()+"\\签名文件\\");

    }
}
