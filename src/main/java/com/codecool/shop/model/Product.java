package com.codecool.shop.model;

import java.util.Currency;

public class Product extends BaseModel {

    private transient float defaultPrice;
    private transient Currency defaultCurrency;
    private transient ProductCategory productCategory;
    private transient Supplier supplier;


    public Product(int id, String name, String description, float defaultPrice, String  currencyString, ProductCategory productCategory, Supplier supplier) {
        super(id, name, description);
        this.setPrice(defaultPrice, currencyString);
        this.productCategory = productCategory;
        this.supplier = supplier;
    }


    public Product(String name, float defaultPrice, String currencyString, String description, ProductCategory productCategory, Supplier supplier) {
        this(0, name,description, defaultPrice, currencyString, productCategory, supplier);
//        super(name, description);
//        this.setPrice(defaultPrice, currencyString);
//        this.supplier = supplier;
//        this.productCategory = productCategory;
    }

    // TODO placeholders - empty constructors
    public Product(int id, String name, float defaultPrice, String currencyString){
        this(id, name, "",defaultPrice, currencyString, new ProductCategory("laptop", ""), new Supplier(""));
    }
// TODO validation in setters
    public float getDefaultPrice() {
        return defaultPrice;
    }

    public void setDefaultPrice(float defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public Currency getDefaultCurrency() {
        return defaultCurrency;
    }

    public void setDefaultCurrency(Currency defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    public String getPrice() {
        return String.valueOf(this.defaultPrice) + " " + this.defaultCurrency.toString();
    }

    private void setPrice(float price, String currency) {
        this.defaultPrice = price;
        this.defaultCurrency = Currency.getInstance(currency);
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
        this.productCategory.addProduct(this);
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
        this.supplier.addProduct(this);
    }

    @Override
    public String toString() {
        return String.format("id: %1$d, " +
                        "name: %2$s, " +
                        "defaultPrice: %3$f, " +
                        "defaultCurrency: %4$s, " +
                        "productCategory: %5$s, " +
                        "supplier: %6$s",
                this.id,
                this.name,
                this.defaultPrice,
                this.defaultCurrency.toString(),
                this.productCategory.getName(),
                this.supplier.getName());
    }
}
