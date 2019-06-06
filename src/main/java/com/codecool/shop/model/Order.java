package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;

public class Order extends BaseModel {

    private List<LineItem> lineItemList;
    private transient float totalPrice;
    private int customerId;
    private String paymentMethod;

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Order() {
        super("Order", "Data");
        this.lineItemList = new ArrayList<>();
        this.totalPrice = 0;
        this.customerId = -1;
    }

    public List<LineItem> getLineItemList() {
        return lineItemList;
    }

    public String getTotalPrice() {
        defineTotalPrice();
        return totalPrice + " " + "USD";
    }

    public LineItem find(int productId) {
        for (LineItem item : lineItemList) {
            if (item.getProduct().getId() == productId) {
                return item;
            }
        }
        return null;
    }

    public LineItem findById(int id) {
        for (LineItem item : lineItemList) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public void add(LineItem lineItem) {
        lineItem.setId(this.lineItemList.size() + 1);
        this.lineItemList.add(lineItem);
    }

    public void remove(LineItem lineItem) {
        lineItemList.remove(lineItem);
    }

    public int getOrderedItemsQuantity() {
        int counter = 0;
        for (LineItem lineItem : lineItemList) {
            counter += lineItem.getQuantity();
        }
        return counter;
    }

    private void defineTotalPrice() {
        float sumOfItemsPrices = 0;
        for (LineItem item : lineItemList) {
            sumOfItemsPrices += item.getPriceOfItems();
        }
        this.totalPrice = sumOfItemsPrices;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
