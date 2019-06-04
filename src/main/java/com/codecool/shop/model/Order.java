package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;

public class Order extends BaseModel {

    List<LineItem> order;

    double totalPrice;


    public Order() {
        super("Order", "Data");
        this.order = new ArrayList<>();
        this.totalPrice = 0;

    }
    public void remove(int id){
        LineItem lineItem = getLineItemById(id);
        if(lineItem != null){
            order.remove(lineItem);
        }

    }

    LineItem getLineItemById(int id){
        LineItem item = null;
        for(int i = 0; order.size() > i ; i++ )
            if (order.get(i).product.id == id) {
                item = order.get(i);
            } else {
                item = null;
            }
        return item;
    }
}
