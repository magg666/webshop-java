package com.codecool.shop.dao.db_implementation;

import com.codecool.shop.config.dbConfig.DataBaseConfiguration;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.BaseModel;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoJDBC implements ProductDao {
    private DataBaseConfiguration dataBaseConfiguration = new DataBaseConfiguration();

    public ProductDaoJDBC() {
    }

    ProductDaoJDBC(DataBaseConfiguration dataBaseConfiguration) {
        this.dataBaseConfiguration = dataBaseConfiguration;
    }
// TODO convert string with column names - parameters into string builder
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
        String query = "SELECT p.id, p.name, p.price, p.currency, p.description, " +
                "c.id as cat_id, c.name as cat_name, d.name as dep_name, " +
                "s.id sup_id, s.name sup_name " +
                "FROM products p " +
                "LEFT JOIN categories c on p.category_id = c.id " +
                "LEFT JOIN suppliers s on p.supplier_id = s.id " +
                "LEFT JOIN departments d on c.department_id = d.id " +
                "WHERE p.id = ?;";
        try (Connection connection = dataBaseConfiguration.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return createProductFromDatabase(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        String query = "SELECT p.id, p.name, p.price, p.currency, p.description, " +
                "c.id as cat_id, c.name as cat_name, d.name as dep_name, " +
                "s.id sup_id, s.name sup_name " +
                "FROM products p " +
                "LEFT JOIN categories c on p.category_id = c.id " +
                "LEFT JOIN suppliers s on p.supplier_id = s.id " +
                "LEFT JOIN departments d on c.department_id = d.id ";
        List<Product> productsList = new ArrayList<>();
        try (Connection connection = dataBaseConfiguration.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = createProductFromDatabase(resultSet);
                productsList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productsList;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        String query = "SELECT p.id, p.name, p.price, p.currency, p.description, " +
                "c.id as cat_id, c.name as cat_name, d.name as dep_name, " +
                "s.id sup_id, s.name sup_name " +
                "FROM products p " +
                "LEFT JOIN categories c on p.category_id = c.id " +
                "LEFT JOIN suppliers s on p.supplier_id = s.id " +
                "LEFT JOIN departments d on c.department_id = d.id " +
                "WHERE p.supplier_id = ?";
        return getProducts(supplier, query);
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        String query = "SELECT p.id, p.name, p.price, p.currency, p.description, " +
                "c.id as cat_id, c.name as cat_name, d.name as dep_name, " +
                "s.id sup_id, s.name sup_name " +
                "FROM products p " +
                "LEFT JOIN categories c on p.category_id = c.id " +
                "LEFT JOIN suppliers s on p.supplier_id = s.id " +
                "LEFT JOIN departments d on c.department_id = d.id " +
                "WHERE c.id = ?";
        return getProducts(productCategory, query);
    }

    @Override
    public List<Product> getByDepartments(String department) {
        String query = "SELECT p.id, p.name, p.price, p.currency, p.description, " +
                "c.id as cat_id, c.name as cat_name, d.name as dep_name, " +
                "s.id sup_id, s.name sup_name " +
                "FROM products p " +
                "LEFT JOIN categories c on p.category_id = c.id " +
                "LEFT JOIN suppliers s on p.supplier_id = s.id " +
                "LEFT JOIN departments d on c.department_id = d.id " +
                "WHERE d.name = ?";
        List<Product> productsList = new ArrayList<>();
        try (Connection connection = dataBaseConfiguration.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, department);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = createProductFromDatabase(resultSet);
                productsList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productsList;
    }

    private Product createProductFromDatabase(ResultSet resultSet) throws SQLException {
        return new Product(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getFloat("price"),
                resultSet.getString("currency"),
                new ProductCategory(
                        resultSet.getInt("cat_id"),
                        resultSet.getString("cat_name"),
                        resultSet.getString("dep_name")),
                new Supplier(
                        resultSet.getInt("sup_id"),
                        resultSet.getString("sup_name")));
    }

    private List<Product> getProducts(BaseModel baseModel, String query) {
        int id = baseModel.getId();
        List<Product> productsList = new ArrayList<>();
        try (Connection connection = dataBaseConfiguration.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = createProductFromDatabase(resultSet);
                productsList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productsList;
    }

}
