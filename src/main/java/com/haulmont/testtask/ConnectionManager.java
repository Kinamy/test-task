package com.haulmont.testtask;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    public static final String USER = "SA";
    public static final String PASSWORD = "";
    public static String URL = "jdbc:hsqldb:file:db/testDB";

    public static java.sql.Connection getConnection() {
        java.sql.Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static String readFile(String fileName) {
        String content = "";
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(fileName));
            content = new String(encoded, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public static void createDB() {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (java.sql.Connection connection = ConnectionManager.getConnection()) {
            String dbScript = readFile("src/main/resources/db.sql");
            connection.createStatement().executeUpdate(dbScript);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void fillingData(){
        String createData = readFile("src/main/resources/data.sql");
        try (Connection connection = ConnectionManager.getConnection()) {
            connection.createStatement().executeUpdate(createData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
