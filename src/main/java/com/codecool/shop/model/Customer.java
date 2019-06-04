package com.codecool.shop.model;

import java.util.List;

public class Customer extends BaseModel {
    private List<Order> listOfOrders;
    private String firstName;
    private String lastName;
    private String billingAddress;
    private String shippingAddress;
    private String phone;
    private String email;
    private boolean hasAccount;
    private String password;


    public Customer(List<Order> listOfOrders, String firstName, String lastName, String billingAddress, String shippingAddress, String phone, String email, boolean hasAccount, String password) {
        super(firstName + " " + lastName);
        this.listOfOrders = listOfOrders;
        this.billingAddress = billingAddress;
        this.shippingAddress = shippingAddress;
        this.phone = phone;
        this.email = email;
        this.hasAccount = hasAccount;
        this.password = password;
    }

    public String getFirstName() {return firstName; }

    public String getLastName() {return lastName; }

    public List<Order> getListOfOrders() {
        return listOfOrders;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public boolean isHasAccount() {
        return hasAccount;
    }

    public String getPassword() {
        return password;
    }

    public void setListOfOrders(List<Order> listOfOrders) {
        this.listOfOrders = listOfOrders;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHasAccount(boolean hasAccount) {
        this.hasAccount = hasAccount;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
