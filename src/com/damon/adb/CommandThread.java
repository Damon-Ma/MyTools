package com.damon.adb;

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
                if(name.equals("devices"))
                    cmd.CMDCommand(util.getCommand("devices"));
                else if (name.equals("spdevices"))
                    cmd.CMDCommand(util.getCommand("spdevices"));

                if (cmd.isConnect()){
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
                    System.out.println("ADBresult: "+ADBresult);
                    System.out.println("result : "+PAAresult);
                    String[] split = PAAresult.split("/");
                    if (split.length>1){
                        String thispackage = split[0];
                        String thisActivity = split[1];
                        TestPanel.setInputText("package/Activity："+PAAresult);
                        TestPanel.setOutText("package："+thispackage+"<br>"+"Activity："+thisActivity);
                    }else
                        TestPanel.setOutText("当前没有运行的程序！");
                }else
                    TestPanel.setOutText("设备未连接！");
            case "send":
                if (cmd.isConnect()){
                    cmd.CMDCommand(util.getCommand("send")+TestPanel.getInputText());
                    TestPanel.setOutText("输入完成！");
                }
        }

    }
}
