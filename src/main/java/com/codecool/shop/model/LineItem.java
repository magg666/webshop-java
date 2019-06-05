package com.codecool.shop.model;

public class LineItem extends BaseModel {
    private int quantity;
    private Product product;
    private float priceOfItems;

    public LineItem(Product product) {
        this.quantity = 1;
        this.product = product;
        this.priceOfItems = this.product.getDefaultPrice();
    }

    public int getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }

    public float getPriceOfItems() {
        return priceOfItems;
    }

    public String getTotalPrice() {
        return String.valueOf(this.priceOfItems) + " " + this.product.getDefaultCurrency().toString();
    }

    public void increaseQuantity() {
        this.quantity = this.quantity + 1;
    }

    public void decreaseQuantity() {
        this.quantity = this.quantity - 1;
    }

    public void changePriceOfItem() {
        int newQuantity = getQuantity();
        this.priceOfItems = newQuantity * this.product.getDefaultPrice();
    }
}
