package com.codecool.shop.config.dbConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConfiguration {

    private String dbName;
    private static final String DB_USER = System.getenv("DB_USER");
    private static final String DB_PASSWORD = System.getenv("DB_PASSWORD");
    private static final String DATABASE = "jdbc:postgresql://localhost:5432/";

    public DataBaseConfiguration() {
        this.dbName = System.getenv("DB_NAME");
    }

    public DataBaseConfiguration(String dbName){
        this.dbName = System.getenv(dbName);
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DATABASE.concat(this.dbName),
                DB_USER,
                DB_PASSWORD
        );
    }
}
