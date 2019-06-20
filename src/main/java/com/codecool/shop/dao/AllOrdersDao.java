package com.codecool.shop.dao;

import com.codecool.shop.model.Customer;
import com.codecool.shop.model.Order;

import java.util.List;

public interface AllOrdersDao {

    Order addFullOrderAndReturn(Order orderFromSession) ;

    Order find(int orderId);

    void updatePayment(String paymentName, int orderId);

    void updateTotalPrice(float totalPrice, int orderId);

    void updateStatus(String status, int orderId);

    List<Order> getForCustomer(Customer customer);



}
