package com.damon.adb;

import javax.swing.*;
import java.util.List;

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
                for (int i=0;i<devicesResult.length;i++){
                    if (devicesResult[i].endsWith("device")||devicesResult[i].endsWith("sideload")){
                        deviceNmb = deviceNmb +1;
                        Application.addItem(deviceNmb+"、"+devicesResult[i].split("\t")[0]);
                    }
                }
                Application.setDevicesNmb(String.valueOf(deviceNmb));
            }
            if (deviceNmb==0){
                Application.setDevicesNmb(String.valueOf(deviceNmb));
                Application.rmItem();   //清除下拉框内容
                Application.setOutText("设备未连接！");
            }
        }else if (name==Keys.kill_server){
            cmd.CMDCommand(Util.getCommand(name.getName()));
            Application.setOutText("OOOOOOOOOOOOOOK!");
        }else if (name==Keys.logcat){
            if (cmd.isConnect()){
                Application.setOutText("正在抓取日志...");
                //日志文件路径
                String path = Util.getDesktopPath()+"\\logcat";
                cmd.CMDCommand("mkdir "+path);
                String filePath = path+"\\logcat"+Util.getDate()+".txt";

                cmd.CMDCommand("adb -s "+MyComboBox.choose+" "+Util.getCommand(name.getName())+filePath);
                Application.setOutText("抓取成功："+filePath);
            }


        }else if (name==Keys.cleanLog){
            if (cmd.isConnect()){
                cmd.CMDCommand("adb -s "+MyComboBox.choose+" "+Util.getCommand(name.getName()));
                Application.setOutText("清除成功！");
            }
        }else if (name==Keys.install){
            //获取到面板上的文件路径
            String thisText = Application.textArea.textArea.getText();
            if (cmd.isConnect()){
                //判断面板是否进行过其他操作
                if (thisText.endsWith("---------\n")){
                    String fileName;
                    List<String> filesPath = Util.getInstallPath(TextAreaListener.allpath);
                    if (filesPath.size()!=0)
                        for (String installFile : filesPath){
                            fileName = Util.getFileName(installFile);
                            if (installFile.endsWith(".apk")){
                                Application.setOutText("正在安装\""+fileName+"\"请稍后。。。");
                                cmd.CMDCommand("adb -s "+MyComboBox.choose+" "+Util.getCommand(name.getName())+"\""+installFile+"\"");
                                Application.setOutText("安装结果："+Util.getLastLine(cmd.getResult()));
                            }else{
                                JOptionPane.showMessageDialog(null,
                                        Util.getFileName(installFile)+"不是正确的安装包！",
                                        "提示",JOptionPane.WARNING_MESSAGE);
                            }
                        }
                }else{
                    JOptionPane.showMessageDialog(null,
                            "请将apk文件拖入输出台！",
                            "提示",JOptionPane.WARNING_MESSAGE);
                    Application.setOutText("请将apk文件拖入输出台！");
                }
            }
        }else if (name==Keys.getpackage){
            if (cmd.isConnect()){
                cmd.CMDCommand("adb -s "+MyComboBox.choose+" "+Util.getCommand(name.getName()));
                String ADBresult = cmd.getResult();
                String PAAresult = Util.getPackage(ADBresult);
                String[] split = PAAresult.split("/");
                if (split.length>1){
                    String thispackage = split[0];
                    String thisActivity = split[1];
                    Application.setOutText("=========================================================");
                    Application.setOutText("当前运行程序package/Activity：\n"+PAAresult);
                    Application.setOutText("package："+thispackage+"\n"+"Activity："+thisActivity);
                    Application.setOutText("=========================================================");
                }else
                    Application.setOutText("当前没有运行的程序！");
            }
        }else if (name==Keys.send){
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
                }else {
                    Application.setOutText("设备未连接！");
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
        }else if (name==Keys.recovery){
            if (cmd.isConnect()){
                //弹出提示框，返回的是按钮的index i=0或者1
                int n = JOptionPane.showConfirmDialog(null,
                        "确认进入recovery模式?",
                        "提示",JOptionPane.YES_NO_OPTION);
                if (n==0){
                    cmd.CMDCommand("adb -s "+MyComboBox.choose+" "+Util.getCommand(name.getName()));
                    Application.setOutText("OOOOOOOOOOOOOK!");
                }else {
                    Application.setOutText("干嘛取消！！");
                }
            }
        }else if (name==Keys.toHome){
            if (cmd.isConnect()){
                cmd.CMDCommand("adb -s "+MyComboBox.choose+" "+Util.getCommand(name.getName()));
                cmd.CMDCommand("adb -s "+MyComboBox.choose+" "+Util.getCommand("toHome2"));
                Application.setOutText("OOOOOOOOOOOOOOVER!");
            }
        }else if (name==Keys.isSideload){

            cmd.CMDCommand("adb devices");

            if (cmd.getResult().startsWith("*")||!cmd.getResult().startsWith("List")){
                Application.setOutText("再点一下~~");
            }

            String[] devicesResult = cmd.getResult().split("\n");
            int j = 0; //初始化一个j，如果选择的设备不为sideload模式，则j为0
            for (int i=0;i<devicesResult.length;i++){
                if (devicesResult[i].endsWith("sideload")&&devicesResult[i].split("\t")[0].equals(MyComboBox.choose)){
                    Application.setOutText("请将刷机包托到输出台，点击sideload开始刷机！");
                    j++;
                }
            }
            if (j==0){
                Application.setOutText("请确认设备是否在adb刷机界面！");
            }


        }else if (name==Keys.sideload){
            //当前面板显示内容
            String labelText = Application.textArea.textArea.getText();
            System.out.println(labelText);
            if (labelText.endsWith("--------\n")){
                //获取刷机包的路径
                String filePath = Util.getInstallPath(TextAreaListener.allpath).get(0);
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
        }else if (name==Keys.monitor){
            Application.setOutText("正在运行 Dalvik Debug Monitor Service（DDMS）...");
            cmd.CMDCommand("monitor");
            Application.setOutText("DDMS 已关闭！");
        }
    }
}
