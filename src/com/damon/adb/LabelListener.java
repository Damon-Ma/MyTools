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

public class LabelListener{
    String allpath;
    public void OutputLabelListener(JLabel label){

        //输出台拖拽监听
        DropTarget dt = new DropTarget(label,new DropTargetListener() {
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
                        while (it.hasNext()){
                            File f = (File) it.next();
                            String path = f.getAbsolutePath();
                            if(allpath==null||allpath==""||allpath==" "){
                                allpath = path;
                            }else {
                                allpath = allpath + "<br>" + path;
                            }
                            System.out.println("allpath:"+allpath);
                            System.out.println(path);
                            label.setText("<html><body>"+allpath+"<html><body>");
                            //  System.out.println(path);
                        }
                        dtde.dropComplete(true);
                        allpath = "";
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
