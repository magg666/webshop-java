package com.codecool.shop.dao.db_implementation;

import com.codecool.shop.config.dbConfig.DataBaseConfiguration;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDaoJDBC implements SupplierDao {
    private DataBaseConfiguration dataBaseConfiguration;

    public SupplierDaoJDBC() {
    }

    @Override
    public void add(Supplier supplier) {
        String query = "INSERT INTO suppliers (name) VALUES (?)";
        try (Connection connection = dataBaseConfiguration.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, supplier.getName());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Supplier find(int id) {
        String query = "SELECT * FROM suppliers WHERE id = ?";
        try (Connection connection = dataBaseConfiguration.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Supplier(resultSet.getInt("id"),
                        resultSet.getString("name"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void remove(int id) {
        String query = "DELETE FROM suppliers WHERE id = ?";
        try (Connection connection = dataBaseConfiguration.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Supplier> getAll() {
        String query = "SELECT * FROM suppliers";
        List<Supplier> resultList = new ArrayList<>();
        try (Connection connection = dataBaseConfiguration.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Supplier supplier = new Supplier(resultSet.getInt("id"),
                        resultSet.getString("name"));
                resultList.add(supplier);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }
}
