package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;

public class Order extends BaseModel {

    private List<LineItem> lineItemList;
    private int customerId;
    private String paymentMethod;
    private transient float totalPrice;
    private String status;

    public Order() {
        super("Order");
        this.lineItemList = new ArrayList<>();
        this.customerId = -1;
        this.totalPrice = 0;

    }

    public Order(int id, String date, int customerId, String paymentMethod, float totalPrice, String status) {
        super(id, date);
        this.customerId = customerId;
        this.paymentMethod = paymentMethod;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    // Getters
    public List<LineItem> getLineItemList() {
        return lineItemList;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public float getTotalPrice() {
        return defineTotalPrice();
    }

    public String getStatus() {
        return status;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    private float defineTotalPrice() {
        float sum = 0;
        for (LineItem item : lineItemList) {
            sum += item.getSummaryPrice();
        }
        return sum;
    }

    public String getFormattedPrice() {
        this.totalPrice = defineTotalPrice();
        return String.format("%.2f", this.totalPrice) + " " + "USD";
    }

    public LineItem findByProductId(int productId) {
        for (LineItem item : lineItemList) {
            if (item.getProduct().getId() == productId) {
                return item;
            }
        }
        return null;
    }

    public void addLineItem(LineItem lineItem) {
        lineItem.setId(this.lineItemList.size() + 1);
        this.lineItemList.add(lineItem);
    }

    public void remove(LineItem lineItem) {
        lineItemList.remove(lineItem);
    }


    // to delete
    public LineItem findById(int id) {
        for (LineItem item : lineItemList) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public int getOrderedItemsQuantity() {
        int counter = 0;
        for (LineItem lineItem : lineItemList) {
            counter += lineItem.getQuantity();
        }
        return counter;
    }


}
