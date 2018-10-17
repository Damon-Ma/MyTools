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
    public MyTable(){
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
