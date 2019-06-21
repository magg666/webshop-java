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
import java.util.List;

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
    void addProduct() {
//        Product product1 = new Product(null, 0, "USD", null, null, null);
        Assertions.assertEquals(0, products.getAll().size());
        products.add(product);
        Assertions.assertEquals(1, products.getAll().size());
    }

    @Test
    void find() {
        Assertions.assertEquals(0, products.getAll().size());
        products.add(product);
        Assertions.assertNotNull(products.find(1));
    }

    @Test
    void remove() {
        Assertions.assertEquals(0, products.getAll().size());
        products.add(product);
        products.remove(1);
        Assertions.assertEquals(0, products.getAll().size());
    }

    @Test
    void getAll() {
        Assertions.assertEquals(0, products.getAll().size());
        products.add(product);
        Assertions.assertNotNull(products.getAll());
    }

    @Test
    void getBySupplier() {
        products.add(product);
        List<Product> productsBySupplier = products.getBy(suppliers.find(1));
        Supplier addedProductSupplier = products.find(1).getSupplier();
        Supplier testedProductSupplier = productsBySupplier.get(0).getSupplier();
        Assertions.assertEquals(addedProductSupplier.getName(), testedProductSupplier.getName());

    }

    @Test
    void getByProductCategory() {
        products.add(product);
        List<Product> productsByCategory = products.getBy(categories.find(1));
        ProductCategory addedProductCategory = products.find(1).getProductCategory();
        ProductCategory testedProductCategory = productsByCategory.get(0).getProductCategory();
        Assertions.assertEquals(addedProductCategory.getName(), testedProductCategory.getName());
    }

    @Test
    void getByDepartments() {
        products.add(product);
        List<Product> productsByDepartments = products.getByDepartments("Electronics");
        ProductCategory addedProductCategory = products.find(1).getProductCategory();
        ProductCategory testedProductCategory = productsByDepartments.get(0).getProductCategory();
        Assertions.assertEquals(addedProductCategory.getDepartment(), testedProductCategory.getDepartment());
    }
}