package com.codecool.shop.dao;

import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;

import java.util.List;

public interface OrderDao {

    Order getOrder();

    void add(Product product);

    void removeProductById(int productId);

    void changeQuantityOfProduct(int productId, int newQuantity);


    void addCustomerId(int customerId); // to remove

    void setPayment(String paymentMethod); // to remove

    void clearOrder();

    int countProducts();

    List<LineItem> getLineItems();

    String getFormattedPrice();
}
