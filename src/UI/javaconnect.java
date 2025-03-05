/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

/**
 *
 * @author NQHuy
 */
import java.sql.*;
import javax.swing.JOptionPane;
public class javaconnect {
    Connection conn;

public static Connection ConnectDB(){
    try{
        Class.forName("org.sqlite.JDBC");
        Connection conn=DriverManager.getConnection("jdbc:sqlite:D:\\FinalProject\\Advanced_Software_Engineering_2020\\Project_Demo_1\\AdvencedSoftwareEngineering\\KaraokeManagementDatabase.db");
        return conn;
    }catch(Exception e){
        JOptionPane.showMessageDialog(null,e);
        return null;
    }

}


}