package com.codecool.shop.model;

public class LineItem {
    int quantity;
    Product product;
    double totalPrice;

    public LineItem(Product product) {
        this.quantity = 1;
        this.product = product;
        this.totalPrice = 0;
    }
}
