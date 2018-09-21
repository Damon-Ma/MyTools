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

public class TableListener {

    String path;
    String fileName;
    List<String> apkMsgs;
    static int count = 0;

    public void OutputLabelListener(JTable table){


        //输出台拖拽监听
        DropTarget dt = new DropTarget(table,new DropTargetListener() {
            //int count=0; //计数,行数
            @Override
            public void dragEnter(DropTargetDragEvent dtde) {

            }

            @Override
            public void dragOver(DropTargetDragEvent dtde) {

            }

            @Override
            public void dropActionChanged(DropTargetDragEvent dtde) {

            }

            @Override
            public void dragExit(DropTargetEvent dte) {

            }

            @Override
            public void drop(DropTargetDropEvent dtde) {
                Transferable tr = dtde.getTransferable();
                try {
                    if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)){
                        dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                        java.util.List list = (List)tr.getTransferData(DataFlavor.javaFileListFlavor);
                        Iterator it = list.iterator();

                        while (it.hasNext()){
                            File f = (File) it.next();
                            path = f.getAbsolutePath();
                            fileName = Util.getFileName(path);

                            if (path.endsWith("apk")){
                                if (Config.allpath==null){
                                    Config.allpath=path;
                                    Config.filesName = fileName;
                                }else {
                                        Config.allpath = Config.allpath + "\n" + path;
                                        Config.filesName = Config.filesName +"\n" +fileName;
                                }


                                //判断是否存在相同的数据
                                boolean isEqualData = false; //存储结果，true为存在相同数据

                                if (Util.getRowsData()==null){
                                    MyJTable.dtm.setValueAt(Util.getFileName(path),0,0);
                                }else {
                                    for (Object rowData : Util.getRowsData()){
                                        String rowName = Util.getFileName(rowData.toString());
                                        if (fileName.equals(rowName)){
                                            isEqualData=true;
                                        }
                                    }
                                    //写数据
                                    if (!isEqualData){
                                        MyJTable.dtm.setValueAt(Util.getFileName(path),Config.rowsNum,0);
                                    }else {
                                        JOptionPane.showConfirmDialog(null,
                                                "已存在同名apk文件，请检查：\n\""+Util.getFileName(path)+"\"",
                                                "提示",JOptionPane.WARNING_MESSAGE);
                                    }
                                }
                            }else {
                                JOptionPane.showConfirmDialog(null,
                                        "请拖入正确的apk文件",
                                        "提示", JOptionPane.WARNING_MESSAGE);
                            }
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
