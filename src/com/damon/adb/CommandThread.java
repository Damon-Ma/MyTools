package com.damon.adb;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public class CommandThread extends Thread{
    Util util = new Util();

    String name;
    CMD cmd = new CMD();
    public CommandThread(String name){
        this.name = name;
    }

    static Object ob = "aa";

    @Override
    public void run(){
        switch (name){
            case "devices"  :
            case "spdevices":

                TestPanel.setOutText("正在连接...");
                String devResult ;

                cmd.CMDCommand(util.getCommand(name));
                devResult = cmd.getResult();
                System.out.println("devResult:"+devResult);

                if (cmd.getResult().endsWith("device")){
                    cmd.CMDCommand(util.getCommand("system"));
                    TestPanel.setOutText("连接成功："+cmd.getResult());
                }
                else
                    TestPanel.setOutText("设备未连接！");
                break;
            case "kill_server":
                cmd.CMDCommand(util.getCommand(name));
                TestPanel.setOutText("OOOOOOOOOOOOOOK!");
                break;
            case "logcat":

                if (cmd.isConnect()){
                    TestPanel.setOutText("正在抓取日志...");
                    //日志文件路径
                    String path = util.getDesktopPath()+"\\logcat";
                    cmd.CMDCommand("mkdir "+path);
                    String filePath = path+"\\logcat"+util.getDate()+".txt";

                    cmd.CMDCommand(util.getCommand(name)+filePath);
                    cmd.getResult();
                    TestPanel.setOutText("抓取成功："+filePath);
                }else
                    TestPanel.setOutText("设备未连接！");
                break;
            case "cleanLog":
                if (cmd.isConnect()){
                    cmd.CMDCommand(util.getCommand(name));
                    TestPanel.setOutText("清除成功！");
                }else
                    TestPanel.setOutText("设备未连接！");
                break;
            case "install":
                //获取到面板上的文件路径
               if (cmd.isConnect()){


                   List<String> filesPath = Util.getInstallPath();
                   if (filesPath.size()!=0)
                       for (String installFile : filesPath){
                            System.out.println("获取到的installFile："+installFile);
                            if (installFile.endsWith(".apk")){
                                TestPanel.setOutText("正在安装,请稍后。。。");
                                cmd.CMDCommand(util.getCommand(name)+installFile);
                                TestPanel.setOutText(cmd.getResult());
                            }else{
                                TestPanel.setOutText(installFile+"不是正确的安装包！");
                                }
                   }
               }else
                   TestPanel.setOutText("设备未连接！");
                break;
            case "getpackage":
                if (cmd.isConnect()){
                    cmd.CMDCommand(util.getCommand(name));
                    String ADBresult = cmd.getResult();
                    String PAAresult = util.getPackage(ADBresult);
//                    System.out.println("ADBresult: "+ADBresult);
//                    System.out.println("result : "+PAAresult);
                    String[] split = PAAresult.split("/");
                    if (split.length>1){
                        String thispackage = split[0];
                        String thisActivity = split[1];
                        TestPanel.setOutText("当前运行程序package/Activity：\n"+PAAresult);
                        TestPanel.setOutText("package："+thispackage+"\n"+"Activity："+thisActivity);
                    }else
                        TestPanel.setOutText("当前没有运行的程序！");
                }else
                    TestPanel.setOutText("设备未连接！");
                break;
            case "send":
                if (cmd.isConnect()){
                    String text = TestPanel.getInputText();
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
                        cmd.CMDCommand(util.getCommand("send")+text);
                        TestPanel.setOutText("输入成功："+text);
                    }else {
                        if (s.equals(" ")){
                            JOptionPane.showMessageDialog(null, "输入字符有误！不能发送空格", "提示",JOptionPane.WARNING_MESSAGE);
                            TestPanel.setOutText("输入包含空格，请检查："+text);
                        }
                        else{
                            //弹窗提示
                            JOptionPane.showMessageDialog(null, "输入字符有误！请检查：\n"+s, "提示",JOptionPane.WARNING_MESSAGE);
                            //输入框显示
                            TestPanel.setOutText("输入有误，请检查："+text);
                        }
                    }
                }else
                    TestPanel.setOutText("设备未连接！");
                break;
            case "recovery":
                if (cmd.isConnect()){
                    //弹出提示框，返回的是按钮的index i=0或者1
                    int n = JOptionPane.showConfirmDialog(null, "确认进入recovery模式?", "提示",JOptionPane.YES_NO_OPTION);
                    if (n==0){
                        cmd.CMDCommand(util.getCommand("recovery"));
                    }
                }else
                    TestPanel.setOutText("设备未连接！");
        }

    }
}
