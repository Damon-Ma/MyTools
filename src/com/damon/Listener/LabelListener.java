package com.damon.Listener;

import com.damon.JFrame.MyLabel;
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

/**
 * @ClassName LabelListener
 * @Description TODO
 * @Author Damon
 * @Date 2018/12/6
 * @Version 1.0
 **/
public class LabelListener {

    private String path;
    private String fileName;
    public void OutputLabelListener(JLabel label){

        //输出台拖拽监听
        new DropTarget(label,new DropTargetListener() {
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
                        List list = (List)tr.getTransferData(DataFlavor.javaFileListFlavor);
                        Iterator it = list.iterator();
                        //每一次拖拽都初始化一下allpath
                        while (it.hasNext()){
                            File f = (File) it.next();
                            path = f.getAbsolutePath();
                            fileName = Util.getFileName(path);

                            if (path.endsWith("apk")){
                                MyLabel.upLoadFileName.setText(fileName);
                            }else {

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
