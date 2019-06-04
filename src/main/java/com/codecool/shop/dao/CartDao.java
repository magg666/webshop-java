package com.codecool.shop.dao;

import com.codecool.shop.model.Order;

public interface CartDao {

    Order getOrder();
    void removeById(int id);
    void addById(int id, ProductDao productDao);
}
