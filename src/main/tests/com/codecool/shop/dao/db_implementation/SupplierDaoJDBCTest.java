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
    private Supplier supplier1 = new Supplier("Hewlett-Packard");
    private Supplier supplier2 = new Supplier("Asus");
    private Supplier supplier3 = new Supplier("Dell");


    @BeforeEach
    void clearDatabase() {
        String query = "ALTER SEQUENCE suppliers_id_seq RESTART WITH 1; DELETE FROM suppliers;";
        try (Connection connection = dataBaseConfiguration.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @Test
    void addSuppliersAndCountGoodData() {
        Assertions.assertEquals(0, suppliers.getAll().size());
        suppliers.add(supplier1);
        Assertions.assertEquals(1, suppliers.getAll().size());
        suppliers.add(supplier2);
        suppliers.add(supplier3);
        Assertions.assertEquals(3, suppliers.getAll().size());
    }


    @Test
    void findSupplierById_NotNull() {
        Assertions.assertEquals(0, suppliers.getAll().size());
        suppliers.add(supplier1);
        Assertions.assertNotNull(suppliers.find(1));

    }

    @Test
    void findSupplierById_CorrectValue(){
        Assertions.assertEquals(0, suppliers.getAll().size());
        suppliers.add(supplier1);
        suppliers.add(supplier2);
        suppliers.add(supplier3);
        Assertions.assertEquals(suppliers.find(3).getName(), "Dell");
    }

    @Test
    void removeAndCount() {
        Assertions.assertEquals(0, suppliers.getAll().size());
        suppliers.add(supplier1);
        suppliers.add(supplier2);
        Assertions.assertEquals(2, suppliers.getAll().size());
        suppliers.remove(suppliers.getAll().get(0).getId());
        Assertions.assertEquals(1, suppliers.getAll().size());

    }

    @Test
    void removeIncorrect(){
        Assertions.assertEquals(0, suppliers.getAll().size());
        suppliers.add(supplier1);
        suppliers.add(supplier2);
        suppliers.remove(7);
        Assertions.assertEquals(2, suppliers.getAll().size());
    }

    @Test
    void getAllCheckSize() {
        Assertions.assertEquals(0, suppliers.getAll().size());
        suppliers.add(supplier1);
        suppliers.add(supplier2);
        suppliers.add(supplier3);
        Assertions.assertEquals(3, suppliers.getAll().size());
    }

    @Test
    void getAllCheckNotNull(){
        Assertions.assertEquals(0, suppliers.getAll().size());
        suppliers.add(supplier1);
        suppliers.add(supplier2);
        suppliers.add(supplier3);
        suppliers.getAll().forEach(Assertions::assertNotNull);
    }
}