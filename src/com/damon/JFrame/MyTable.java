package com.damon.JFrame;

import com.damon.Listener.TableListener;
import com.damon.adb.CommandThread;
import com.damon.Util.Config;
import com.damon.Util.Keys;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EventObject;

/**
 * 表格
 */
public class MyTable{
    public static DefaultTableModel dtm;
    public static JTable table;

    public static DefaultTableModel signDTM;
    public static JTable signFileTable;

    public MyTable(){
        //安装应用表格
        dtm = new DefaultTableModel();
        table = new JTable(dtm);
        this.installTable();

        //签名文件表格
        signDTM = new DefaultTableModel();
        signFileTable = new JTable(signDTM);
        this.signFileTable();

    }

    private void signFileTable() {
        //设置列名
        signDTM.addColumn("文件名");
        signDTM.addColumn("上传时间");
        signDTM.addColumn("签名证书");
        signDTM.addColumn("操作");
        signDTM.addColumn("下载进度");


        //设置DefaultTableModel的列数和行数
        signDTM.setColumnCount(5);

        signFileTable.setRowHeight(20);

        //设置行宽
        TableColumn column0 = signFileTable.getColumnModel().getColumn(0);
        TableColumn column1 = signFileTable.getColumnModel().getColumn(1);
        TableColumn column2 = signFileTable.getColumnModel().getColumn(2);
        TableColumn column3 = signFileTable.getColumnModel().getColumn(3);
        TableColumn column4 = signFileTable.getColumnModel().getColumn(4);


        column0.setPreferredWidth(600);
        column1.setPreferredWidth(300);
        column2.setPreferredWidth(300);
        column3.setPreferredWidth(600);
        column4.setPreferredWidth(600);

        column0.setCellEditor(this.cellEditor());
        column1.setCellEditor(this.cellEditor());
        column2.setCellEditor(this.cellEditor());
        column3.setCellEditor(this.cellEditor());
        column4.setCellEditor(this.cellEditor());
    }

    //安装文件表格
    private void installTable(){

        TableListener listener = new TableListener();
        listener.OutputLabelListener(table);

        //设置列名
        dtm.addColumn("文件名");
        dtm.addColumn("安装状态");

        //设置DefaultTableModel的列数和行数
        dtm.setColumnCount(2);
        dtm.setRowCount(30);

        table.setRowHeight(20);

        //设置行宽
        TableColumn column0 = table.getColumnModel().getColumn(0);
        TableColumn column1 = table.getColumnModel().getColumn(1);
        column0.setPreferredWidth(800);
        column1.setPreferredWidth(200);
        column0.setCellEditor(this.cellEditor());
        column1.setCellEditor(this.cellEditor());

        //添加双击监听
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if(e.getClickCount() == 2){

                    Config.row =((JTable)e.getSource()).rowAtPoint(e.getPoint()); //获得行位置
                    Config.cellVal=(table.getValueAt(Config.row,0)).toString(); //获得点击单元格数据

                    if (Config.cellVal!=null){
                        CommandThread thread = new CommandThread(Keys.INSTALL_CELL_APK);
                        thread.start();
                    }
                }
            }
        });
    }


    /**
     * 设置表格不可编辑
     * @return cellEditor
     */
    private TableCellEditor cellEditor() {
        return new TableCellEditor() {
            @Override
            public Object getCellEditorValue() {
                return null;
            }

            @Override
            public boolean isCellEditable(EventObject anEvent) {
                return false;
            }

            @Override
            public boolean shouldSelectCell(EventObject anEvent) {
                return false;
            }

            @Override
            public boolean stopCellEditing() {
                return false;
            }

            @Override
            public void cancelCellEditing() {

            }

            @Override
            public void addCellEditorListener(CellEditorListener l) {

            }

            @Override
            public void removeCellEditorListener(CellEditorListener l) {

            }

            @Override
            public Component getTableCellEditorComponent(JTable table1, Object value, boolean isSelected, int row, int column) {
                return null;
            }
        };
    }


}
