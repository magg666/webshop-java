package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;

public class Order extends BaseModel {

    List <LineItem> orderedProductsList;
    double totalPrice;


    public Order() {
        super("Order", "Data");
        this.orderedProductsList = new ArrayList<>();
        this.totalPrice = 0;

    }

    public List<LineItem> getOrderedProductsList() {
        return orderedProductsList;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void remove(LineItem lineItem){
            orderedProductsList.remove(lineItem);
    }

    public void add(LineItem lineItem){
            orderedProductsList.add(lineItem);

    }
//
//    LineItem getLineItemById(int id){
//        LineItem item = null;
//        for(int i = 0; orderedProductsList.size() > i ; i++ )
//            if (orderedProductsList.get(i).product.id == id) {
//                item = orderedProductsList.get(i);
//            } else {
//                item = null;
//            }
//        return item;
//    }
}
