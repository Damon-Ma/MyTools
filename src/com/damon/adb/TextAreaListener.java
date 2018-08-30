package com.damon.adb;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.*;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class TextAreaListener {
    public static String allpath;
    String path;
    public static String fileName;
    String filesName;
    List<String> apkMsgs;
    public void OutputLabelListener(JTextArea textArea){

        //输出台拖拽监听
        DropTarget dt = new DropTarget(textArea,new DropTargetListener() {
            @Override
            public void dragEnter(DropTargetDragEvent dtde) { }
            @Override
            public void dragOver(DropTargetDragEvent dtde) { }
            @Override
            public void dropActionChanged(DropTargetDragEvent dtde) { }
            @Override
            public void dragExit(DropTargetEvent dte) { }
            @Override
            public void drop(DropTargetDropEvent dtde) {
                Transferable tr = dtde.getTransferable();
                try {
                    if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)){
                        dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                        java.util.List list = (List)tr.getTransferData(DataFlavor.javaFileListFlavor);
                        Iterator it = list.iterator();
                        allpath = "";
                        while (it.hasNext()){
                            File f = (File) it.next();
                            path = f.getAbsolutePath();
                            fileName = Util.getFileName(path);

//                            if (Util.isContainChinese(path)){
//                                JOptionPane.showMessageDialog(null,
//                                        "路径中不能包含中文！",
//                                        "提示",
//                                        JOptionPane.WARNING_MESSAGE);
//                                Application.setOutText("路径中包含中文，请检查！\n" +
//                                        "路径：" +
//                                        path);
//                                break;
//                            }


                            if (path.endsWith("apk")){
                                apkMsgs = Util.getAPKMsg(path);
                                String apkName = Util.getApkName(path);
                                String apkVersion = Util.getApkVersion(path);
                                String apkPackage = Util.getApkPackage(path);
                                String apkActivity = Util.getApkActivity(path);


//                                System.out.println("---------\n"+apkMsg+"\n-------");
                                Application.setOutText("------------------------------安装包“"+fileName+"”信息------------------------------");
                                Application.setOutText("应用名称："+apkName);
                                Application.setOutText("应用版本："+apkVersion);
                                Application.setOutText("应用包名："+apkPackage);
                                Application.setOutText("MainActivity："+apkActivity);
                                Application.setOutText(" ");
                                if(allpath==null||allpath==""||allpath==" ") {
                                    allpath = path;
                                    filesName = fileName;
                                } else {
                                    allpath = allpath + "\n" + path;
                                    filesName = filesName +"\n" + fileName;
                                }
                            }else if (path.endsWith("zip")){
                                //给allpath赋值
                                if (allpath==null||allpath==""||allpath==" "){
                                    allpath = path;
                                    filesName = fileName;
                                }else if (!path.equals(allpath)){        // 如果不止一个值allpath会不等于path，这里做一个拦截
                                    JOptionPane.showMessageDialog(null,
                                            "只能拖入一个刷机包！",
                                            "提示",
                                            JOptionPane.WARNING_MESSAGE);
                                }
                                //在这里截取一下刷机包的名称
                                String[] names = filesName.split("_");
                                if (names.length<7&&!filesName.matches(".*[Recovery].*")&&!filesName.matches(".*SE.*")){
                                    JOptionPane.showMessageDialog(null, fileName+"不是正确的刷机包！", "提示",JOptionPane.WARNING_MESSAGE);
                                }else if (filesName.matches(".*[OS].*-2.N.*")){
                                    JOptionPane.showMessageDialog(null,
                                            "添加的是签名转非签包！",
                                            "提示",JOptionPane.WARNING_MESSAGE);
                                    Application.setOutText("----------------------------------\n" +
                                            "点击开始刷入转换包：\n" +
                                            fileName +
                                            "\n----------------------------------");
                                }else if (filesName.matches(".*[ON].*-2.S.*")){
                                    JOptionPane.showMessageDialog(null,
                                            "添加的是非签转签名包！",
                                            "提示",JOptionPane.WARNING_MESSAGE);
                                    Application.setOutText("----------------------------------\n" +
                                            "点击开始刷入转换包：\n" +
                                            fileName +
                                            "\n----------------------------------");
                                }else {

                                    //获取一下当前OS版本
                                    //进入recovery模式之后获取不到os版本信息
//                                    String osVersion = Util.getOS();
//                                    if (!osVersion.startsWith(names[0])){
//                                        JOptionPane.showMessageDialog(null,
//                                                "请确定OS是否与机型匹配！\n当前机型："+osVersion.split("_")[0]+"\n刷机包："+names[0],
//                                                "提示",JOptionPane.WARNING_MESSAGE);
//                                    }

                                    //判断一下单卡双卡
                                    String simType = "";
                                    String sim = Util.getOSMsg(filesName,"(M2)?[SD]S");
                                    if (sim!=null){
                                        if (sim.equals("M2SS")||sim.equals("SS")){
                                            simType = " 单卡设备";
                                        }else if (sim.equals("M2DS")||sim.equals("DS")){
                                            simType = " 双卡设备";
                                        }
                                    }
                                    JOptionPane.showMessageDialog(null,
                                            "当前OS适用机型："+names[0]+simType,
                                            "提示",JOptionPane.WARNING_MESSAGE);

                                    //机型
                                    String name = Util.getOSMsg(fileName,"SQ\\d+[A-Z]*");
                                    //定制版本
                                    String whos = Util.getOSMsg(fileName,"[A-Z]{3,}|XX|WD");
                                    //发布日期
                                    String date = Util.getOSMsg(fileName,"\\d{6}");
                                    //是否签名
                                    String Sign = Util.getOSMsg(fileName,"_[SN]_");
                                    String isSign;
                                    try {
                                        if (Sign.equals("_S_")){
                                            isSign = "签名";
                                        }else {
                                            isSign = "非签名";
                                        }
                                    }catch (NullPointerException e){
                                        isSign = "";
                                    }
                                    Application.setOutText("----------------------------------\n" +
                                            "适用机型：" +name+
                                            "\n项目名称：" +whos+
                                            "\n发布日期：" +date+
                                            "\n是否签名版本：" +isSign+
                                            "\n----------------------------------" +
                                            "\n点击sideload开始刷刷机包：\n"+filesName);
                                    Application.setOutText("----------------------------------");
                                }
                            }else{
                                JOptionPane.showMessageDialog(null, fileName+"不是正确的安装包！", "提示",JOptionPane.WARNING_MESSAGE);
                            }

                         //   System.out.println("allpath:"+allpath);

                        }
                        dtde.dropComplete(true);
                            if (!allpath.equals("")&&!allpath.endsWith("zip")){

                                Application.setOutText(
                                        "----------------------------------\n"
                                        +"点击开始安装：\n"
                                        +filesName
                                        +"\n----------------------------------"
                                );
                            }

                    }else {
                        dtde.rejectDrop();
                    }
                } catch (UnsupportedFlavorException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
