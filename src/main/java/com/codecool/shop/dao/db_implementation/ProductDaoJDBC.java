package com.codecool.shop.dao.db_implementation;

import com.codecool.shop.config.dbConfig.DataBaseConfiguration;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ProductDaoJDBC implements ProductDao {
    private DataBaseConfiguration dataBaseConfiguration;

    public ProductDaoJDBC() {
    }

    @Override
    public void add(Product product) {
        String query = "INSERT INTO products (name, description, price, currency, category_id, supplier_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataBaseConfiguration.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, product.getDefaultPrice());
            statement.setString(4, String.valueOf(product.getDefaultCurrency()));
            statement.setInt(5, product.getProductCategory().getId());
            statement.setInt(6, product.getSupplier().getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Product find(int id) {
//        String query = "SELECT * FROM products WHERE id = ?";
//        Product product = null;
//        try (Connection connection = dataBaseConfiguration.getConnection();
//             PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.setInt(1, id);
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                product = new Product(resultSet.getString("name"), resultSet.getFloat("price"), resultSet.getString("currency"), resultSet.getString("description"));
//            } else {
//                return null;
//            }
//
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        return null;
    }

    @Override
    public void remove(int id) {
        String query = "DELETE FROM products WHERE id = ?";
        try (Connection connection = dataBaseConfiguration.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Product> getAll() {
        return null;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        return null;
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        return null;
    }

    @Override
    public List<Product> getByDepartments(String department) {
        return null;
    }
}
