package com.codecool.shop.dao;

import com.codecool.shop.model.Customer;
import com.codecool.shop.model.Order;

public interface CustomerDao {
    //CRUD
    void addCustomer(Customer customer);
    Customer findById(int id);

    void addOrderToCustomer(Customer customer, Order order);
    void addOrderToCustomerById(int id, Order order);
    void sendEmailToCustomer();

}
