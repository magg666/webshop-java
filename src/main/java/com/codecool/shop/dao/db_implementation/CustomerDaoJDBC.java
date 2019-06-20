package com.codecool.shop.dao.db_implementation;

import com.codecool.shop.config.dbConfig.DataBaseConfiguration;
import com.codecool.shop.dao.CustomerDao;
import com.codecool.shop.model.Customer;
import com.codecool.shop.model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDaoJDBC implements CustomerDao {
    private DataBaseConfiguration dataBaseConfiguration = new DataBaseConfiguration();

    public CustomerDaoJDBC() {
    }

    CustomerDaoJDBC(DataBaseConfiguration dataBaseConfiguration) {
        this.dataBaseConfiguration = dataBaseConfiguration;
    }

    @Override
    public int addCustomer(Customer customer) {
        String query = "INSERT INTO customers(first_name, last_name, billing_address, " +
                "billing_city, billing_country, billing_zip_code, shipping_address, " +
                "shipping_city, shipping_country, shipping_zip_code, phone, email, password) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" +
                "RETURNING id;";
        try (Connection connection = dataBaseConfiguration.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            setStatementForCustomer(statement, customer);
            statement.setString(13, customer.getPassword());
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt("id");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    @Override
    public Customer findById(int id) {
        String query = "SELECT id, first_name, last_name, billing_address, " +
                "billing_city, billing_country, billing_zip_code, shipping_address, " +
                "shipping_city, shipping_country, shipping_zip_code, phone, email FROM customers " +
                "WHERE id = ?";

        try (Connection connection = dataBaseConfiguration.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return createNewCustomerFromDatabase(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void addOrderToCustomer(Customer customer, Order order) {

    }

    @Override
    public void addOrderToCustomerById(int id, Order order) {

    }

    @Override
    public void sendEmailToCustomer() {

    }

    @Override
    public boolean isExistsEmail(String email) {
        String query = "SELECT exists(SELECT email FROM customers WHERE email = ?);";
        try (Connection connection = dataBaseConfiguration.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBoolean("exists");
            }
        } catch (SQLException e) {
            System.out.println("query exists mail problem " + e.getMessage());
        }
        return false;
    }

    @Override
    public int getCustomerId(String email) {
        return 0;
    }

    private Customer createNewCustomerFromDatabase(ResultSet resultSet) throws SQLException {
        return new Customer(resultSet.getInt("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("billing_address"),
                resultSet.getString("billing_city"),
                resultSet.getString("billing_country"),
                resultSet.getString("billing_zip_code"),
                resultSet.getString("shipping_address"),
                resultSet.getString("shipping_city"),
                resultSet.getString("shipping_country"),
                resultSet.getString("shipping_zip_code"),
                resultSet.getString("phone"),
                resultSet.getString("email"));
    }

    private void setStatementForCustomer(PreparedStatement statement, Customer customer) throws SQLException {
        statement.setString(1, customer.getFirstName());
        statement.setString(2, customer.getLastName());
        statement.setString(3, customer.getBillingAddress());
        statement.setString(4, customer.getBillingCity());
        statement.setString(5, customer.getBillingCountry());
        statement.setString(6, customer.getBillingZipCode());
        statement.setString(7, customer.getShippingAddress());
        statement.setString(8, customer.getShippingCity());
        statement.setString(9, customer.getShippingCountry());
        statement.setString(10, customer.getShippingZipCode());
        statement.setString(11, customer.getPhone());
        statement.setString(12, customer.getEmail());
    }
}
