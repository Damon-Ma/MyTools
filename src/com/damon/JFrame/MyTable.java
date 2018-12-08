package com.damon.JFrame;

import com.damon.Listener.TableListener;
import com.damon.adb.CommandThread;
import com.damon.Util.Config;
import com.damon.Util.Keys;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EventObject;

/**
 * 表格
 */
public class MyTable{
    //安装应用表格
    public static DefaultTableModel dtm;
    public static JTable table;
    //apk列表表格
    public static DefaultTableModel signDTM;
    public static JTable signFileTable;

    //按钮框
    public static JPanel buttonPanel;
    public static JButton srcDownloadBt;
    public static JButton signedDownloadBt;

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


        column0.setPreferredWidth(700);
        column1.setPreferredWidth(400);
        column2.setPreferredWidth(300);
        column3.setPreferredWidth(400);
        column4.setPreferredWidth(600);

        column3.setCellRenderer(new ButtonRenderer());
        //column4.setCellRenderer(this.cellRenderer());

        column0.setCellEditor(this.cellEditor());
        column1.setCellEditor(this.cellEditor());
        column2.setCellEditor(this.cellEditor());

        column3.setCellEditor(new ButtonEditor(new JTextField()));
        //column4.setCellEditor(this.cellEditor());


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


                    if (table.getValueAt(Config.row,0)!=null){
                        Config.cellVal=(table.getValueAt(Config.row,0)).toString(); //获得点击单元格数据
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
//重写CellEditor
class ButtonEditor extends DefaultCellEditor {

    protected JButton button;
    private String label;
    private boolean isPushed;

    public ButtonEditor(JTextField checkBox) {
        super(checkBox);
        this.setClickCountToStart(1);
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });

    }

    public Component getTableCellEditorComponent(final JTable table, Object value,
                                                 boolean isSelected,int row, int column) {
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Config.downloadRowNum = table.getSelectedRow();
            }
        });
        isPushed = true;
        return button;
    }

    public Object getCellEditorValue() {
        if (isPushed) {
            //
            //
            // JOptionPane.showMessageDialog(button, label + ": Ouch!");
            // System.out.println(label + ": Ouch!");
        }
        isPushed = false;
        return new String(label);
    }

    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        //System.out.println(1);
        return super.shouldSelectCell(anEvent);
    }
}

//重写TableCellRenderer实现
class ButtonRenderer extends JButton implements TableCellRenderer {

    public ButtonRenderer() {
        setOpaque(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(UIManager.getColor("UIManager"));
        }
        setText((value == null) ? "" : value.toString());
        return this;
    }
}
