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
    String fileName;
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

                            if (path.endsWith("apk")){
                                apkMsgs = Util.getAPKMsg(path);
//                                String apkMsg = Util.getAPKMsg(path);
//                                System.out.println("---------\n"+apkMsg+"\n-------");
                               // Application.setOutText("-----------------------------------------------------");
                                Application.setOutText("------------------------------安装包“"+fileName+"”信息------------------------------");
                                Application.setOutText("应用名称："+Util.getApkName(path));
                                Application.setOutText("应用版本："+Util.getApkVersion(path));
                                Application.setOutText("应用包名："+Util.getApkPackage(path));
                                Application.setOutText("MainActivity：'"+Util.getApkActivity(path)+"'");
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
                                    JOptionPane.showMessageDialog(null, "只能拖入一个刷机包！", "提示",JOptionPane.WARNING_MESSAGE);
                                }
                                //在这里截取一下刷机包的名称
                                String[] names = filesName.split("_");
                                if (names.length<9){
                                    JOptionPane.showMessageDialog(null, fileName+"不是正确的刷机包！", "提示",JOptionPane.WARNING_MESSAGE);
                                }else {
                                    //机型
                                    String name = names[0];
                                    //定制版本
                                    String whos = names[1];
                                    //发布日期
                                    String date = names[2];
                                    //是否签名
                                    String Sign = names[4];
                                    String isSign;
                                    if (Sign.equals("S")){
                                        isSign = "签名";
                                    }else {
                                        isSign = "非签名";
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
                                //Application.setOutText(path+"不是正确的安装包");
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
