package com.codecool.shop.dao.db_implementation;

import com.codecool.shop.config.dbConfig.DataBaseConfiguration;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class SupplierDaoJDBCTest {
    private DataBaseConfiguration dataBaseConfiguration = new DataBaseConfiguration("TEST_DATABASE");
    private SupplierDao suppliers = new SupplierDaoJDBC(dataBaseConfiguration);
    Supplier supplier1 = new Supplier("Hewlett-Packard");
    Supplier supplier2 = new Supplier("Asus");
    Supplier supplier3 = new Supplier("Dell");


    @BeforeEach
    void clearDatabase() {
        String query = "DELETE FROM suppliers;";
        try (Connection connection = dataBaseConfiguration.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    void setId() {
        String query = "SELECT setval('suppliers_id_seq', 1, FALSE);";
        try (Connection connection = dataBaseConfiguration.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @Test
    void addSuppliersAndCount() {
        suppliers.add(supplier1);
        Assertions.assertEquals(1, suppliers.getAll().size());
        suppliers.add(supplier2);
        suppliers.add(supplier3);
        Assertions.assertEquals(3, suppliers.getAll().size());
    }

    @Test
    void find() {
    }

    @Test
    void remove() {
    }

    @Test
    void getAll() {
    }
}