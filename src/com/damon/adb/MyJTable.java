package com.damon.adb;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.EventObject;

/**
 * 表格
 */
public class MyJTable{
    public static DefaultTableModel dtm;
    public JTable table;
    public MyJTable(){
        dtm = new DefaultTableModel();
        table = new JTable(dtm);
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
        column1.setPreferredWidth(400);
        column0.setCellEditor(this.cellEditor());
        column1.setCellEditor(this.cellEditor());
    }


    /**
     * 设置表格不可编辑
     * @return
     */
    TableCellEditor cellEditor() {
        TableCellEditor cellEditor = new TableCellEditor() {
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
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                return null;
            }
        };
        return cellEditor;
    }

}
