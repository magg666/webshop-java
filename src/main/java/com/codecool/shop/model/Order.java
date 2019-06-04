package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;

public class Order extends BaseModel {

    private List <LineItem> lineItemList;
    private double totalPrice;


    public Order() {
        super("Order", "Data");
        this.lineItemList = new ArrayList<>();
        this.totalPrice = 0;

    }

    public List<LineItem> getLineItemList() {
        return lineItemList;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void remove(LineItem lineItem){
            lineItemList.remove(lineItem);
    }

    public void add(LineItem lineItem){
            lineItemList.add(lineItem);

    }
//
//    LineItem getLineItemById(int id){
//        LineItem item = null;
//        for(int i = 0; lineItemList.size() > i ; i++ )
//            if (lineItemList.get(i).product.id == id) {
//                item = lineItemList.get(i);
//            } else {
//                item = null;
//            }
//        return item;
//    }
}
