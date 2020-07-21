package com.example.practicemav.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBOrder {
    private final String HOST ="jdbc:mysql://localhost/mydb?useUnicode=" +
            "true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode" +
            "=false&serverTimezone=Europe/Moscow";
    private final String USERNAME = "root";
    private final String PASSWORD = "root";

    private Connection connection;

    public DBOrder(){
        try{
            connection = DriverManager.getConnection(HOST,USERNAME,PASSWORD);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
