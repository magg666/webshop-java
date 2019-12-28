package com.codecool.shop.config.dbConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConfiguration {

    private String databaseName;
    private static final String DB_USER = System.getenv("DB_USER");
    private static final String DB_PASSWORD = System.getenv("DB_PASSWORD");
    private static final String DATABASE = System.getenv("DB_HOST");

    public DataBaseConfiguration() {
        this.databaseName = System.getenv("DB_NAME");
    }

    public DataBaseConfiguration(String databaseName){
        this.databaseName = System.getenv(databaseName);
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DATABASE.concat(this.databaseName),
                DB_USER,
                DB_PASSWORD
        );
    }
}
