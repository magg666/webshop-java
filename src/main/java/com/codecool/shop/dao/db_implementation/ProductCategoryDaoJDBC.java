package com.codecool.shop.dao.db_implementation;

import com.codecool.shop.config.dbConfig.DataBaseConfiguration;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;
import com.sun.org.apache.xerces.internal.xs.StringList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ProductCategoryDaoJDBC implements ProductCategoryDao {
    private DataBaseConfiguration dataBaseConfiguration;

    public ProductCategoryDaoJDBC(){

    }
    @Override
    public void add(ProductCategory category) {
        String query ="INSERT INTO categories(name, department_id) " +
                "VALUES (?, " +
                "       (SELECT id FROM departments " +
                "           WHERE name = ?))";
        try (Connection connection = dataBaseConfiguration.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1,category.getName());
            statement.setString(2, category.getDepartment());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public ProductCategory find(int id) {

        String query ="SELECT categories.id, " +
                "             categories.name as category_name, " +
                "             department_id, departments.name as department_name" +
                "             FROM categories " +
                "             INNER JOIN departments on categories.department_id = departments.id " +
                "             WHERE categories.id = ?";
        try (Connection connection = dataBaseConfiguration.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet= statement.executeQuery();
            if(resultSet.next()){
                ProductCategory productCategory = new ProductCategory(resultSet.getInt("id"), resultSet.getString("category_name"),resultSet.getString("department_name"));
                return productCategory;
            }else{
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public void remove(int id) {
        String query= "DELETE FROM categories WHERE id = ?";
        try (Connection connection = dataBaseConfiguration.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<ProductCategory> getAll() {

        List<ProductCategory>resultList = new ArrayList<>();

        String query = "SELECT categories.id, " +
                "             categories.name as category_name, " +
                "             categories.department_id, " +
                "             departments.name as department_name" +
                "             FROM categories " +
                "             INNER JOIN departments on categories.department_id = departments.id ";
        try (Connection connection = dataBaseConfiguration.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();


            while(resultSet.next()){
                ProductCategory productCategory = new ProductCategory(resultSet.getInt("id"),
                                                                        resultSet.getString("category_name"),
                                                                        resultSet.getString("department_name"));
                resultList.add(productCategory);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public List<ProductCategory> getByDepartments(String department) {
        List<ProductCategory>resultList = new ArrayList<>();
        String query = "SELECT categories.id," +
                "             categories.name as category_name, " +
                "             categories.department_id, " +
                "             departments.name as department_name" +
                "             FROM categories " +
                "             INNER JOIN departments on categories.department_id = departments.id " +
                "        WHERE departments.name = ?";
        try (Connection connection = dataBaseConfiguration.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, department);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                ProductCategory productCategory = new ProductCategory(resultSet.getInt("id"),
                                                                        resultSet.getString("category_name"),
                                                                        resultSet.getString("department_name"));
                resultList.add(productCategory);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public List<String> getAllDepartments() {
        List<String>resultList = new ArrayList<>();
        String query = "SELECT name FROM departments";
        try (Connection connection = dataBaseConfiguration.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                String department = new String(resultSet.getString("name"));
                resultList.add(department);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }
}
