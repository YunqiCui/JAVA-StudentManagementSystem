package com.model;

import javax.swing.table.AbstractTableModel;
import java.sql.*;
import java.util.Vector;

public class MyStudentModel extends AbstractTableModel{

    Vector rowdata, colnames;
    Connection ct = null;
    PreparedStatement ps = null;
    ResultSet rs = null;


    public MyStudentModel(String sql){
        this.init(sql);
    }
    public MyStudentModel(){
        this.init("");
    }

    public void init(String sql){
        if(sql ==""||sql.equals(null)){
            sql = "select * from student";
        }

        colnames = new Vector();
        rowdata = new Vector();

        colnames.add("stuid");
        colnames.add("firstname");
        colnames.add("lastname");
        colnames.add("gender");
        colnames.add("age");
        colnames.add("city");
        colnames.add("faculty");


        try {
            Class.forName("com.mysql.jdbc.Driver");
            ct = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentmanagement", "root", "");
            ps = ct.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Vector row = new Vector();
                row.add(rs.getString(1));
                row.add(rs.getString(2));
                row.add(rs.getString(3));
                row.add(rs.getString(4));
                row.add(rs.getInt(5));
                row.add(rs.getString(6));
                row.add(rs.getString(7));
                rowdata.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (ct != null) {
                    ct.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public int getRowCount() {
        return this.rowdata.size();
    }

    public int getColumnCount() {
        return this.colnames.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return ((Vector)this.rowdata.get(rowIndex)).get(columnIndex);
    }

    @Override
    public String getColumnName(int col){
        return (String)this.colnames.get(col);
    }
}
