package com.codecool.shop.model;

public class LineItem extends BaseModel {
    private int quantity;
    private Product product;
    private transient float summaryPrice;

    public LineItem(Product product) {
        this.quantity = 1;
        this.product = product;
        this.summaryPrice = this.product.getDefaultPrice();
    }

    public LineItem(int id, Product product, int quantity){
        super(id);
        this.product = product;
        this.quantity = quantity;
        this.summaryPrice = this.product.getDefaultPrice();

    }

    public int getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }

    public float getSummaryPrice() {
        return summaryPrice;
    }

    public String getTotalPrice() {
        return String.format("%.2f", this.summaryPrice) + " " + this.product.getDefaultCurrency().toString();
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void increaseQuantityByOne() {
        this.quantity += 1;
    }

    public void decreaseQuantityByOne() {
        this.quantity -= 1;
    }

    public void setSummaryPrice() {
        int newQuantity = getQuantity();
        this.summaryPrice = newQuantity * this.product.getDefaultPrice();
    }

    public int getProductId(){
        return getProduct().getId();
    }

    // to remove
    public void increaseQuantity() {
        this.quantity = this.quantity + 1;
    }

    public void decreaseQuantity() {
        this.quantity = this.quantity - 1;
    }

    public void changePriceOfItem() {
        int newQuantity = getQuantity();
        this.summaryPrice = newQuantity * this.product.getDefaultPrice();
    }


}
