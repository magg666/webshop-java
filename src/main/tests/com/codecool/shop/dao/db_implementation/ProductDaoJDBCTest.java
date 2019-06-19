package com.codecool.shop.dao.db_implementation;

import com.codecool.shop.config.dbConfig.DataBaseConfiguration;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.fail;

class ProductDaoJDBCTest {
    private DataBaseConfiguration dataBaseConfiguration = new DataBaseConfiguration("TEST_DATABASE");
    private ProductDao products = new ProductDaoJDBC(dataBaseConfiguration);
    private ProductCategoryDao categories = new ProductCategoryDaoJDBC(dataBaseConfiguration);
    private SupplierDao suppliers = new SupplierDaoJDBC(dataBaseConfiguration);
    private Product product;

    @BeforeEach
    void prepareDatabase() {
        ProductCategory laptopCategory = new ProductCategory("laptop", "Electronics");
        Supplier supplier1 = new Supplier("Hewlett-Packard");
        categories.add(laptopCategory);
        suppliers.add(supplier1);
        Supplier realSupplier = suppliers.getAll().get(0);
        ProductCategory realCategory = categories.getAll().get(0);
        product = new Product("Acer", 130.00f, "USD", "Great laptop", realCategory, realSupplier);
    }

    @AfterEach
    void clearDatabase() {
        String query = "ALTER SEQUENCE products_id_seq RESTART WITH 1; " +
                "DELETE FROM products;" +
                "ALTER SEQUENCE suppliers_id_seq RESTART WITH 1; " +
                "DELETE FROM suppliers;" +
                "ALTER SEQUENCE categories_id_seq RESTART WITH 1; " +
                "DELETE FROM categories;";
        try (Connection connection = dataBaseConfiguration.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void add() {
        Assertions.assertEquals(0, products.getAll().size());
        products.add(product);
        Assertions.assertEquals(1, products.getAll().size());
    }

    @Test
    void find() {
        fail("this test has to be implemented");
    }

    @Test
    void remove() {
        fail("this test has to be implemented");
    }

    @Test
    void getAll() {
        fail("this test has to be implemented");
    }

    @Test
    void getBy() {
        fail("this test has to be implemented");
    }

    @Test
    void getBy1() {
        fail("this test has to be implemented");
    }

    @Test
    void getByDepartments() {
        fail("this test has to be implemented");
    }
}