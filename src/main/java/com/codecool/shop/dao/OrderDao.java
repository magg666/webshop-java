package com.codecool.shop.dao;

import com.codecool.shop.model.Order;

public interface OrderDao {

    Order getOrder();

    void removeById(int id);

    void addById(int id);
}
