package com.codecool.shop.dao.db_implementation;

import com.codecool.shop.config.dbConfig.DataBaseConfiguration;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// TODO sprawdź warunki początkowe
class ProductCategoryDaoJDBCTest {
    private DataBaseConfiguration dataBaseConfiguration = new DataBaseConfiguration("TEST_DATABASE");
    private ProductCategoryDao categories = new ProductCategoryDaoJDBC(dataBaseConfiguration);
    private ProductCategory productCategory1 = new ProductCategory("Laptop","Electronics");
    private ProductCategory productCategory2 = new ProductCategory("Romance","Books");
    private ProductCategory productCategory3 = new ProductCategory("Tablet","Electronics");


    @BeforeEach
    void clearDatabase() {
        String query = "ALTER SEQUENCE categories_id_seq RESTART WITH 1; DELETE FROM categories;";
        try (Connection connection = dataBaseConfiguration.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    void addProductCategoriesAndCountGoodData() {
        categories.add(productCategory1);
        Assertions.assertEquals(1, categories.getAll().size());
        categories.add(productCategory2);
        categories.add(productCategory3);
        Assertions.assertEquals(3, categories.getAll().size());
    }

    @Test
    void findProductCategoryById() {
        categories.add(productCategory1);
        Assertions.assertNotNull(categories.find(1));
    }

    @Test
    void removeProductCategoryById() {
        categories.add(productCategory1);
        categories.remove(1);
        Assertions.assertEquals(0,categories.getAll().size());
    }

    @Test
    void getAllProductCategoriesFromDB() {
        categories.add(productCategory1);
        categories.add(productCategory2);
        categories.add(productCategory3);
        Assertions.assertEquals(3,categories.getAll().size());
    }

    @Test
    void getAllProductCategoriesWithRightDepartment() {
        categories.add(productCategory1);
        categories.add(productCategory2);
        categories.add(productCategory3);
        Assertions.assertEquals(2,categories.getByDepartments("Electronics").size());
    }


}