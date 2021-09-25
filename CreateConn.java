package com.medical;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CreateConn {

    static CreateConn objCreateConn;
    protected Connection conn;

    private CreateConn() {
        connect();
    }

    private void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:/home/kashifiqbalkhan/LoginDB.db";
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static CreateConn estConnection() {
        if (objCreateConn == null) {
            objCreateConn = new CreateConn();
        }
        return objCreateConn;
    }
}