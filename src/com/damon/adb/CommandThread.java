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
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandThread extends Thread {

    private Keys name;
    private CMD cmd;
    private Process publicP;


    public CommandThread(Keys name) {
        this.name = name;
        cmd = new CMD();
    }

    @Override
    public void run() {
        switch (name){
            case DEVICES:
            case SP_DEVICES:
                this.devices();
                break;
            case KILL_SERVER:
                this.killServer();
                break;
            case LOGCAT:
                this.logcat();
                break;
            case CLEAN_LOG:
                this.cleanLog();
                break;
            case INSTALL:
                this.tableInstall();
                break;
            case PACKAGE:
                this.getpackage();
                break;
            case SEND:
                this.sendText();
                break;
            case RECOVERY:
                this.recovery();
                break;
            case MAIN_ACTIVITY:
                this.toHome();
                break;
            case IS_SIDELOAD:
                this.isSideload();
                break;
            case SIDELOAD:
                this.sideload();
                break;
            case SIGN_TO_NOSIGN:
                this.signToNosign();
                break;
            case NOSIGN_TO_SIGN:
                this.nosignToSign();
                break;
            case SCREEN:
                this.screen();
                break;
            case INSTALL_CELL_APK:
                this.installCellAKP();
                break;
            case SIGN_LOGIN:
                this.loginSignSys();
                break;
            case GET_APK_LIST:
                this.getAPKList();
                break;
            case UPLOAD_APK:
                this.upload();
                break;
            case DOWNLOAD_SIGNED_APK:
                this.downloadSignedFile();
                break;
            case FASTBOOT:
                this.fastboot();
                break;
        }
    }

    //截图
    private void screen() {
        if (cmd.isConnect()) {

            MyTextArea.setOutText("正在截图...");
            //截图文件路径
            String path = Util.getDesktopPath() + "\\Screenshots";
            //创建截图保存路径
            File file = new File(path);
            if (!file.exists()){
                file.mkdir();
            }

            //日期
            String date = Util.getDate();
            //文件名
            String screenName = "Screenshot_" + date + ".png";
            //sdcard中路径
            String sdPath = "/sdcard/" + screenName;
            //文件保存路径
            String screenPath = path + "\\" + screenName;
            publicP = cmd.Cmd("adb -s " + MyComboBox.choose + " " + Util.getCommand(name.getName()) + sdPath);
            cmd.getResult(publicP);
            publicP = cmd.Cmd("adb -s " + MyComboBox.choose + " pull " + sdPath + " " + path);
            cmd.getResult(publicP);
            //删除终端中的文件
            publicP = cmd.Cmd("adb -s " + MyComboBox.choose + " shell rm -rf " + sdPath);
            cmd.getResult(publicP);
            //打开截图文件
            publicP = cmd.Cmd(screenPath);
            if (cmd.getErrorResult(publicP) == null) {
                MyTextArea.setOutText("截图成功，文件保存在：" + screenPath);
            } else {
                MyTextArea.setOutText("截图失败！！");
            }
        }
    }

    //检查连接
    private void devices() {
        MyTextArea.setOutText("正在连接...");
        Process p = null;
        if (name == Keys.DEVICES) {
            p = cmd.Cmd(Util.getCommand(name.getName()));
        } else if (name == Keys.SP_DEVICES) {
            p = cmd.Cmd("\"" + Util.getThisPath() + "libs\\adb.exe\"\" devices");
        }

        String result = cmd.getResult(p);
        if (result==null){
            MyTextArea.setOutText("获取设备信息异常！");
            return;
        }
        Object[] deviceResult = Util.getConnectDevice(result);
        MyLabel.setDevicesNmb(String.valueOf(deviceResult.length));
        int deviceNmb = 0;   //连接序号
        if (deviceResult.length > 0) {
            MyComboBox.rmItem();
            for (Object ob : deviceResult) {
                deviceNmb++;
                MyComboBox.addItem(deviceNmb + "、" + ob);
            }
        } else {
            MyLabel.setDevicesNmb(String.valueOf(deviceNmb));
            MyComboBox.rmItem();   //清除下拉框内容
            MyTextArea.setOutText("设备未连接！");
        }
    }

    //抓取日志
    private void logcat() {
        if (cmd.isConnect()) {
            MyTextArea.setOutText("正在抓取日志...");
            //日志文件路径
            String path = Util.getDesktopPath() + "\\logcat";
            File file = new File(path);
            if (!file.exists()){
                file.mkdir();
            }
            String filePath = path + "\\logcat" + Util.getDate() + ".txt";
            publicP = cmd.Cmd("adb -s " + MyComboBox.choose + " " + Util.getCommand(name.getName()) + filePath);
            cmd.getResult(publicP);
            MyTextArea.setOutText("抓取成功：" + filePath);
            //打开保存文件夹
            publicP = cmd.Cmd("start " + path);

        }

    }

    //表格安装
    private void tableInstall() {
        //清空一下状态栏
        for (int i = 0; i < 20; i++) {
            MyTable.dtm.setValueAt(null, i, 1);
        }
        //获取到表格的文件路径
        List allFilesPath = Util.getRowsData();
        //初始化行数
        //Util.getRowsNum();

        if (cmd.isConnect()) {
            System.out.println(allFilesPath);
            if (allFilesPath != null) {
                int count = 0;
                for (Object installFile : allFilesPath) {
                    MyTable.dtm.setValueAt("正在安装...", count, 1);
                    Process p = cmd.Cmd("adb -s " + MyComboBox.choose + " " + Util.getCommand(name.getName()) + "\"" + installFile + "\"");
                    String installResult = cmd.getResult(p);
                    if (installResult.endsWith("Success")) {
                        MyTable.dtm.setValueAt("安装成功", count++, 1);
                    } else {
                        Pattern r = Pattern.compile("\\[.*");
                        Matcher m = r.matcher(installResult);
                        if (m.find()) {
                            MyTable.dtm.setValueAt(m.group(), count++, 1);
                        } else {
                            //MyJTable.dtm.setValueAt(cmd.getResult(),count++,1);
                            MyTable.dtm.setValueAt(cmd.getErrorResult(p), count++, 1);
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "请先将apk文件拖入安装程序显示框！",
                        "提示", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    //获取当前应用
    private void getpackage() {
        if (cmd.isConnect()) {
            Process p = cmd.Cmd("adb -s " + MyComboBox.choose + " " + Util.getCommand(name.getName()));
            String ADBresult = cmd.getResult(p);

            String PAAresult = Util.getPackage(ADBresult);
            String[] split = PAAresult.split("/");
            if (split.length > 1) {
                String thispackage = split[0];
                String thisActivity = split[1];
                MyTextArea.setOutText("=========================================================");
                MyTextArea.setOutText("当前运行程序:\n" +
                        "package：" + thispackage +
                        "\nActivity：" + thisActivity +
                        "\npackage/Activity：" + PAAresult
                );
                MyTextArea.setOutText("=========================================================");
            } else
                MyTextArea.setOutText("当前没有运行的程序！");
        }
    }

    //send text
    private void sendText() {
        String text = MyTextField.getInputText();
        String s = "";

        boolean isBlank = false;
        boolean isASCII = false;
        boolean isOther = false;
        //判断输入的文字中是否有非法字符
        for (int i = 0; i < text.length(); i++) {
            s = text.substring(i, i + 1);
            //判断空格
            isBlank = s.matches("[\\u0020]");
            //判断其他字符
            isOther = s.matches("[\\u007C,\\u0060]");
            //判断ASCII
            isASCII = s.matches("[\\u0020-\\u007E]");

            if (!isASCII || isBlank || isOther) {
                break;
            }
        }
        if (isASCII && !isBlank && !isOther) {
            if (cmd.isConnect()) {
                cmd.Cmd("adb -s " + MyComboBox.choose + " " + Util.getCommand(name.getName()) + text);
                MyTextArea.setOutText("输入成功：" + text);
            }
        } else {
            if (s.equals(" ")) {
                JOptionPane.showMessageDialog(null,
                        "输入字符有误！不能发送空格",
                        "提示", JOptionPane.WARNING_MESSAGE);
                MyTextArea.setOutText("输入包含空格，请检查：" + text);
            } else {
                //弹窗提示
                JOptionPane.showMessageDialog(null,
                        "输入字符有误！请检查：\n" + s,
                        "提示", JOptionPane.WARNING_MESSAGE);
                //输入框显示
                MyTextArea.setOutText("输入有误，请检查：" + text);
            }
        }
    }

    //recovery
    private void recovery() {
        if (cmd.isConnect()) {
            //弹出提示框，返回的是按钮的index i=0或者1
            int n = JOptionPane.showConfirmDialog(null,
                    "确认进入recovery模式?",
                    "提示", JOptionPane.YES_NO_OPTION);
            if (n == 0) {
                cmd.Cmd("adb -s " + MyComboBox.choose + " " + Util.getCommand(name.getName()));
                MyTextArea.setOutText("OOOOOOOOOOOOOK!");
            } else {
                MyTextArea.setOutText("取消！！");
            }
        }
    }

    //返回桌面
    private void toHome() {
        if (cmd.isConnect()) {
            cmd.Cmd("adb -s " + MyComboBox.choose + " " + Util.getCommand(name.getName()));
            cmd.Cmd("adb -s " + MyComboBox.choose + " " + Util.getCommand("toHome2"));
            MyTextArea.setOutText("返回主界面!");
        }
    }

    //检查sideload模式
    private void isSideload() {
        MyTextArea.setOutText("正在连接...");
        //adb刷机时偶尔会因为adb意外停止而断开，所以在这里尝试启动adb，检查是否会自动停止
        Process p;
        do {
            p = cmd.Cmd("adb start-server");
        } while (cmd.getResult(p) != null);

        String[] devicesResult = cmd.getResult(cmd.Cmd("adb devices")).split("\n");
        boolean isSideload = false; //初始化一个isSideload，如果选择的设备不为sideload模式，则isSideload=false
        for (String aDevicesResult : devicesResult) {
            if (aDevicesResult.endsWith("sideload") && aDevicesResult.split("\t")[0].equals(MyComboBox.choose)) {
                MyTextArea.setOutText("请将刷机包托到输出台，点击sideload开始刷机！");
                isSideload = true;
            }
        }
        if (!isSideload) {
            MyTextArea.setOutText("请确认设备是否在adb刷机界面！");
            MyTextArea.setOutText("先检查连接，选择设备，再检查sideload");
        }
    }

    //刷机 sideload
    private void sideload() {

        if (cmd.isSideload()) {
            //获取刷机包的路径
            try {
                Config.OSPath = Util.getInstallPath(Config.allpath).get(0);
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(null,
                        "请将刷机包拖进输出台！",
                        "提示", JOptionPane.WARNING_MESSAGE);
            }
            Process p = cmd.Cmd("adb -s " + MyComboBox.choose +" "+ Util.getCommand(name.getName()) + "\""+Config.OSPath+"\"");
            cmd.printRealResult(p);
            Config.OSPath = null;
            if (cmd.getErrorResult(p) != null) {
                MyTextArea.setOutText(cmd.getErrorResult(p));
            }
        } else {
            MyTextArea.setOutText("设备未连接，请先检查sideload！");
        }
    }

    //打开DDMS
    private void monitor() {
        MyTextArea.setOutText("正在运行 Dalvik Debug Monitor Service（DDMS）...");
        Process ddms = cmd.Cmd("monitor");
        cmd.getResult(ddms);
        MyTextArea.setOutText("DDMS 已关闭！");
    }

    //清除日志
    private void cleanLog() {
        if (cmd.isConnect()) {
            cmd.Cmd("adb -s " + MyComboBox.choose + " " + Util.getCommand(name.getName()));
            MyTextArea.setOutText("清除成功！");
        }
    }

    //结束adb
    private void killServer() {
        cmd.Cmd(Util.getCommand(name.getName()));
        cmd.Cmd(Util.getCommand(name.getName()));
        cmd.Cmd(Util.getCommand(name.getName()));
        MyTextArea.setOutText("OOOOOOOOOOOOOOK!");
    }

    //adb shell
    private void shell() {
        if (cmd.isConnect()) {
            cmd.Cmd("start adb -s " + MyComboBox.choose + " shell");
        }
    }

    //安装单个apk
    private void installCellAKP() {
        if (cmd.isConnect()) {
            //把行号存起来，不然不同线程调用的时候会乱
            int row = Config.row;
            MyTable.dtm.setValueAt("正在安装...", row, 1);
            Process p = cmd.Cmd("adb -s " + MyComboBox.choose + " install -r \"" + Config.cellVal + "\"");
            String installResult = cmd.getResult(p);
            if (installResult.endsWith("Success")) {
                MyTable.dtm.setValueAt("安装成功", row, 1);
            } else {
                Pattern r = Pattern.compile("\\[.*]");
                Matcher m = r.matcher(installResult);
                if (m.find()) {
                    MyTable.dtm.setValueAt(m.group(), row, 1);
                } else {
                    MyTable.dtm.setValueAt(cmd.getErrorResult(p), row, 1);
                }
            }
        }
    }

    //登陆签名系统
    private void loginSignSys() {
        if (Config.signURL == null){
            MyLabel.uploadResult.setText("请先选择内网/外网！");
            return;
        }
        Config.loginTime = Util.getLongTime();
        MyLabel.uploadResult.setText("正在登陆...");
        Config.sign = new Sign();
        Boolean signResult = Config.sign.login();
        if (signResult) {
            MyLabel.uploadResult.setText("登陆成功!");
            Log.logger.info("登陆成功！");
        } else {
            MyLabel.uploadResult.setText("登陆失败！！");
        }
    }

    //获取应用列表
    private void getAPKList() {
        //fileIdList
        Config.fileIdList = new ArrayList<Integer>();
        int count = MyTable.signDTM.getRowCount();
        for (int i = 0; i < count; i++) {
            MyTable.signDTM.removeRow(0);
        }

        if (Config.sign == null) {
            MyLabel.uploadResult.setText("请先登陆！");
        } else if (Util.getLongTime() - Config.loginTime > 1200000) {
            JOptionPane.showMessageDialog(null,
                    "登陆过期，请重新登陆！",
                    "提示",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            MyLabel.uploadResult.setText("获取列表...");
            JSONObject resultJson = Config.sign.getFileList();

            if (resultJson.size() == 1) {
                MyLabel.uploadResult.setText(resultJson.getString("result"));
            } else if (resultJson.size() == 2) {
                //获取apk的jsonarray
                JSONArray apkList = resultJson.getJSONArray("rows");
                if (apkList.size() == 0){
                    MyLabel.uploadResult.setText("获取成功，列表为空！");
                }else {
                    for (int i = 0; i < apkList.size(); i++) {
                        //获取单个apk的json对象
                        JSONObject apkJson = apkList.getJSONObject(i);
                        //id
                        int id = (int) apkJson.get("id");
                        //证书类型
                        String signType = apkJson.getString("certificate");
                        //文件名
                        String apkName = apkJson.getString("fileName");
                        //上传时间
                        String uploadTime = apkJson.getString("uploadTimeStr");

                        Log.logger.info("id:" + id);
                        Log.logger.info("证书类型：" + signType);
                        Log.logger.info("文件名：" + apkName);
                        Log.logger.info("上传时间：" + uploadTime);
                        Log.logger.info("================================");

                        Config.fileIdList.add(id);

                        Object[] apkMsg = {apkName, uploadTime, signType, "下载签名文件"};
                        MyTable.signDTM.addRow(apkMsg);
                        MyLabel.uploadResult.setText("获取成功！");
                    }

                }

            } else {
                MyLabel.uploadResult.setText("获取成功，列表为空！");
            }

        }
    }

    //上传
    private void upload() {
        if (Config.sign == null) {
            MyLabel.uploadResult.setText("请先登陆！");
        } else if (Config.scrFilePath == null) {
            JOptionPane.showMessageDialog(null,
                    "请拖入apk文件！",
                    "提示",
                    JOptionPane.WARNING_MESSAGE);
        } else if (Config.signType == null || Config.signType.equals("---请选择---")) {
            JOptionPane.showMessageDialog(null,
                    "请选择证书类型！",
                    "提示",
                    JOptionPane.WARNING_MESSAGE);
        } else if (Util.getLongTime() - Config.loginTime > 1200000) {
            JOptionPane.showMessageDialog(null,
                    "登陆过期，请重新登陆！",
                    "提示",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            MyLabel.uploadResult.setText("正在上传...");
            Log.logger.info("证书类型：" + Config.signType);
            Log.logger.info("文件路径：" + Config.scrFilePath);

            String uploadResult = Config.sign.upload(Config.signType, Config.scrFilePath);

            String matchResult = Util.match(uploadResult, "(?<=<font color=\"red\">).*(?= </font>)");
            if (matchResult == null) {
                Log.logger.error("上传结果：" + uploadResult);
            } else {
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
    private void downloadSignedFile() {
        if (Util.getLongTime() - Config.loginTime > 1200000) {
            JOptionPane.showMessageDialog(null,
                    "登陆过期，请重新登陆！",
                    "提示",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            int rowNum = Config.downloadRowNum;
            System.out.println("下载第：" + rowNum + "行");

            int id = Config.fileIdList.get(rowNum);
            Config.sign.signDownload(id);
            cmd.Cmd("start " + Util.getDesktopPath() + "\\签名文件\\");
        }

    }

    //签名转非签
    private void signToNosign() {
        this.unlock(1);
    }

    //非签转签名
    private void nosignToSign() {
        this.unlock(2);

    }

    //解锁
    private void unlock(int i){
        Process p = cmd.Cmd("fastboot devices");
        MyTextArea.setOutText("执行：fastboot devices");
        String devicesResult = cmd.getResult(p);
        MyTextArea.setOutText("结果：" + devicesResult);
        if (devicesResult != null){

            System.out.printf(devicesResult);
            String devicesName = devicesResult.split("fastboot")[0];
            //设置下拉框
            MyComboBox.rmItem();
            MyComboBox.addItem("解锁模式...");


            //i=1  签名转非签   i=2 非签转签名
            String unlock = "fastboot oem unlock "+ Util.getCommand("unlockPwd");
            MyTextArea.setOutText("执行："+unlock);
            Process p1 = cmd.Cmd(unlock);

            String errorResult = cmd.getErrorResult(p1);
            MyTextArea.setOutText("结果："+errorResult);

            if (errorResult.contains("OKAY")&&!errorResult.contains("FAILED")) {
                Process p2 = null;
                String flashComm = "";
                if (i == 1) {
                    flashComm = "fastboot flash recovery " + "\"" + Util.getThisPath() + "libs\\signtonosign\\recovery.img\"";
                } else if (i == 2) {
                    flashComm = "fastboot flash recovery " + "\"" + Util.getThisPath() + "libs\\nosigntosign\\recovery.img\"";
                }

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                MyTextArea.setOutText("执行："+flashComm);
                p2 = cmd.Cmd(flashComm);
                String flashResult = cmd.getErrorResult(p2);
                MyTextArea.setOutText("结果："+flashResult);

                if (flashResult.contains("OKAY")&&!flashResult.contains("FAILED")) {
                    MyTextArea.setOutText("解锁完成，请重启...");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                    cmd.Cmd("fastboot reboot");
//                    MyTextArea.setOutText("解锁完成，自动重启。。\n请按住“电源键+音量+”进入recovery模式");

//                    MyTextArea.setOutText("解锁完成，重启自动进入recovery模式，请稍等...");
//                    Process p3 = cmd.Cmd("fastboot reboot");
//                    String rebootResult = cmd.getResult(p3);
//                    String recoveryResult = "";
//                    do {
//                        Process p4 = cmd.Cmd(" adb devices");
//                        recoveryResult = cmd.getResult(p4);
//                        if (recoveryResult == null){
//                            recoveryResult = "";
//                        }
//                    }while (!recoveryResult.contains(devicesName));
//
//                    Process p5 = cmd.Cmd("adb -s "+devicesName+" reboot recovery");
//                    String re = cmd.getResult(p5);
                } else {
                    MyTextArea.setOutText("解锁失败，请检查！");
                }
            } else {
                MyTextArea.setOutText("解锁失败，请检查！");
            }
        }else {
            MyTextArea.setOutText("请检查是否连接或设备是否进入fastboot模式");
        }
    }

    //fastboot
    private void fastboot(){

        if (cmd.isConnect()) {
            //弹出提示框，返回的是按钮的index i=0或者1
            int n = JOptionPane.showConfirmDialog(null,
                    "确认进入fastboot模式?",
                    "提示", JOptionPane.YES_NO_OPTION);
            if (n == 0) {
                Process p = cmd.Cmd("adb -s " + MyComboBox.choose+ " reboot bootloader");
                String bootResult = cmd.getResult(p);
                MyTextArea.setOutText("OOOOOOOOOOOOOK!");
            } else {
                MyTextArea.setOutText("取消！！");
            }
        }
    }
}
