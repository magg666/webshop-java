package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;

import java.util.List;

public class OrderDaoMem implements OrderDao {

    private Order order = new Order();
    private static OrderDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private OrderDaoMem() {
    }

    public static OrderDaoMem getInstance() {
        if (instance == null) {
            instance = new OrderDaoMem();
        }
        return instance;
    }


    @Override
    public Order getOrder() {
        return this.order;
    }

    @Override
    public void setOrder(Order order){
        this.order = order;
    };

    @Override
    public void add(Product product) {
        LineItem lineItem = getOrder().findByProductId(product.getId());
        if (lineItem == null) {
            getOrder().addLineItem(new LineItem(product));
        } else {
            lineItem.increaseQuantityByOne();
            lineItem.setSummaryPrice();
        }
    }


    @Override
    public void removeProductById(int productId) {
        LineItem lineItemWithProduct = getOrder().findByProductId(productId);
        if (lineItemWithProduct != null && lineItemWithProduct.getQuantity() > 1) {
            lineItemWithProduct.decreaseQuantityByOne();
            lineItemWithProduct.setSummaryPrice();
        } else if (lineItemWithProduct != null) {
            getLineItems().remove(lineItemWithProduct);
        }
    }

    @Override
    public void changeQuantityOfProduct(int productId, int newQuantity) {
        LineItem lineItemWithProduct = getOrder().findByProductId(productId);
        lineItemWithProduct.setQuantity(newQuantity);
        lineItemWithProduct.setSummaryPrice();
    }

    @Override
    public void addCustomerId(int customerId) {
        order.setCustomerId(customerId);
    }

    @Override
    public void setPayment(String paymentMethod) {
        if (paymentMethod != null) {
            getOrder().setPaymentMethod(paymentMethod);
        }
    }

    @Override
    public void clearOrder() {
        order = new Order();
    }

    @Override
    public int countProducts() {
        int sum = 0;
        for (LineItem lineItem : getOrder().getLineItemList()) {
            sum += lineItem.getQuantity();
        }return sum;
    }

    @Override
    public List<LineItem> getLineItems() {
        return getOrder().getLineItemList();
    }

    @Override
    public String getFormattedPrice() {
        return getOrder().getFormattedPrice();
    }
}
