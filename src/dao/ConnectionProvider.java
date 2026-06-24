/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ConnectionProvider {

    private static final String DB_NAME = "attendanceJframedb";
    private static final String DB_URL = "jdbc:mysql://localhost:3306";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "password";

    public static Connection getCon() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // First connect to MySQL server (without DB)
            Connection con = DriverManager.getConnection(DB_URL + "?useSSL=false", DB_USERNAME, DB_PASSWORD);

            // Check if database exists, otherwise create it
            if (!databaseExists(con, DB_NAME)) {
                createDatabase(con, DB_NAME);
            }

            // Now connect to the specific database (fixed: added "/")
            con = DriverManager.getConnection(DB_URL + "/" + DB_NAME + "?useSSL=false", DB_USERNAME, DB_PASSWORD);

            return con;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static boolean databaseExists(Connection con, String dbName) throws Exception {
        Statement stmt = con.createStatement();
        return stmt.executeQuery("SHOW DATABASES LIKE '" + dbName + "'").next();
    }

    private static void createDatabase(Connection con, String dbName) throws Exception {
        Statement stmt = con.createStatement();
        stmt.executeUpdate("CREATE DATABASE " + dbName);
        System.out.println("Database '" + dbName + "' created successfully.");
    }
}
