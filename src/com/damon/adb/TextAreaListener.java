package com.damon.adb;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
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
                            if (path.endsWith("apk")||path.endsWith("zip")){

//                                String apkMsg = Util.getAPKMsg(path);
//                                System.out.println("---------\n"+apkMsg+"\n-------");
                               // TestPanel.setOutText("-----------------------------------------------------");
                                TestPanel.setOutText("------------------------------安装包“"+fileName+"”信息------------------------------");
                                TestPanel.setOutText("应用名称："+Util.getApkName(path));
                                TestPanel.setOutText("应用版本："+Util.getApkVersion(path));
                                TestPanel.setOutText("应用包名："+Util.getApkPackage(path));
                                TestPanel.setOutText("MainActivity：'"+Util.getApkActivity(path)+"'");
                                TestPanel.setOutText(" ");
                                if(allpath==null||allpath==""||allpath==" ") {
                                    allpath = path;
                                    filesName = fileName;
                                }
                                else {
                                    allpath = allpath + "\n" + path;
                                    filesName = filesName +"\n" + fileName;
                                }
                            }else{
                                JOptionPane.showMessageDialog(null, fileName+"不是正确的安装包！", "提示",JOptionPane.WARNING_MESSAGE);
                                //TestPanel.setOutText(path+"不是正确的安装包");
                            }

                            System.out.println("allpath:"+allpath);

                        }
                        dtde.dropComplete(true);
                            if (!allpath.equals("")){

                                TestPanel.setOutText(
                                        "----------------------------------\n"
                                        +"点击开始安装：\n"
                                        +filesName
                                        +"\n----------------------------------"
                                );
                            }

                    }else {
                        dtde.rejectDrop();
                    }
                } catch (UnsupportedFlavorException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
