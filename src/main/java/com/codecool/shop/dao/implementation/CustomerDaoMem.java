package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.CustomerDao;
import com.codecool.shop.model.Customer;
import com.codecool.shop.model.Order;

import java.util.ArrayList;
import java.util.List;

public class CustomerDaoMem implements CustomerDao {
    private List<Customer> allCustomers = new ArrayList<>();
    private static CustomerDaoMem instance = null;

    private CustomerDaoMem() {
    }

    public static CustomerDaoMem getInstance() {
        if (instance == null) {
            instance = new CustomerDaoMem();
        }
        return instance;
    }

    @Override
    public int addCustomer(Customer customer) {
        customer.setId(allCustomers.size() + 1);
        allCustomers.add(customer);
        return customer.getId();
    }

    @Override
    public Customer findById(int id) {
        for (Customer customer : allCustomers) {
            if (customer.getId() == id) {
                return customer;
            }
        }
        return null;
    }

    @Override
    public void addOrderToCustomer(Customer customer, Order order) {
        customer.getListOfOrders().add(order);
    }

    @Override
    public void addOrderToCustomerById(int id, Order order) {
        Customer customer = findById(id);
        addOrderToCustomer(customer, order);
    }

    @Override
    public void sendEmailToCustomer() {
    }

    @Override
    public boolean isExistsEmail(String email) {
        for (Customer customer : allCustomers) {
            if (customer.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getCustomerId(String email) {
        for (Customer customer : allCustomers) {
            if (customer.getEmail().equals(email)) {
                return customer.getId();
            }
        }
        return -1;
    }
}
