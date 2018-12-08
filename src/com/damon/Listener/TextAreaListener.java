package com.damon.Listener;

import com.damon.JFrame.MyTextArea;
import com.damon.Util.Config;
import com.damon.Util.Util;

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

    private String path;
    private String fileName;
    public void OutputLabelListener(JTextArea textArea){

        //输出台拖拽监听
        new DropTarget(textArea,new DropTargetListener() {
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
                        //每一次拖拽都初始化一下allpath
                        Config.allpath = "";
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

                                String apkName = Util.getApkName(path);
                                String apkVersion = Util.getApkVersion(path);
                                String apkPackage = Util.getApkPackage(path);
                                String apkActivity = Util.getApkActivity(path);


                                MyTextArea.setOutText("------------------------------安装包“"+fileName+"”信息------------------------------"+
                                    "\n应用名称："+apkName+
                                    "\n应用版本："+apkVersion+
                                    "\n应用包名："+apkPackage+
                                    "\nMainActivity："+apkActivity+
                                    "\n------------------------------------------------------------------");
                                if((Config.allpath == null) || Config.allpath.equals("")) {
                                    Config.allpath = path;
                                    Config.filesName = fileName;
                                } else {
                                    Config.allpath = Config.allpath + "\n" + path;
                                    Config.filesName = Config.filesName +"\n" + fileName;
                                }
                            }else if (path.endsWith("zip")){
                                //给allpath赋值
                                if (Config.allpath==null|| Config.allpath.equals("") || Config.allpath.equals(" ")){
                                    Config.allpath = path;
                                    Config.filesName = fileName;
                                }else if (!path.equals(Config.allpath)){        // 如果不止一个值allpath会不等于path，这里做一个拦截
                                    JOptionPane.showMessageDialog(null,
                                            "只能拖入一个刷机包！",
                                            "提示",
                                            JOptionPane.WARNING_MESSAGE);
                                }
                                //在这里截取一下刷机包的名称
                                String[] names = Config.filesName.split("_");
                                if ((names.length < 7) && !Config.filesName.matches(".*[Recovery].*") && !Config.filesName.matches(".*SE.*")){
                                    JOptionPane.showMessageDialog(null, fileName+"不是正确的刷机包！", "提示",JOptionPane.WARNING_MESSAGE);
                                }else if (Config.filesName.matches(".*[OS].*-2.N.*")){
                                    JOptionPane.showMessageDialog(null,
                                            "添加的是签名转非签包！",
                                            "提示",JOptionPane.WARNING_MESSAGE);
                                    MyTextArea.setOutText("----------------------------------\n" +
                                            "点击开始刷入转换包：\n" +
                                            "\n----------------------------------");
                                }else if (Config.filesName.matches(".*[ON].*-2.S.*")){
                                    JOptionPane.showMessageDialog(null,
                                            "添加的是非签转签名包！",
                                            "提示",JOptionPane.WARNING_MESSAGE);
                                    MyTextArea.setOutText("----------------------------------\n" +
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
                                    String sim = Util.match(Config.filesName,"(M2)?[SD]S");
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
                                    String name = Util.match(fileName,"SQ\\d+[A-Z]*");
                                    //定制版本
                                    String whos = Util.match(fileName,"[A-Z]{3,}|XX|WD");
                                    //发布日期
                                    String date = Util.match(fileName,"\\d{6}");
                                    //是否签名
                                    String Sign = Util.match(fileName,"_[SN]_");
                                    String isSign;
                                    try {
                                        assert Sign != null;
                                        if (Sign.equals("_S_")){
                                            isSign = "签名";
                                        }else {
                                            isSign = "非签名";
                                        }
                                    }catch (NullPointerException e){
                                        isSign = "";
                                    }
                                    MyTextArea.setOutText("----------------------------------\n" +
                                            "适用机型：" +name+
                                            "\n项目名称：" +whos+
                                            "\n发布日期：" +date+
                                            "\n是否签名版本：" +isSign+
                                            "\n----------------------------------" +
                                            "\n点击sideload开始刷刷机包：\n"+Config.filesName);
                                    MyTextArea.setOutText("----------------------------------");
                                }
                            }else{
                                JOptionPane.showMessageDialog(null, fileName+"不是正确的安装包！", "提示",JOptionPane.WARNING_MESSAGE);
                            }

                         //   System.out.println("allpath:"+allpath);

                        }
                        dtde.dropComplete(true);


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
