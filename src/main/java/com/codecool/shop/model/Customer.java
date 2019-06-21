package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Customer extends BaseModel {

    private String firstName;
    private String lastName;
    private String billingAddress;
    private String billingCity;
    private String billingCountry;
    private String billingZipCode;
    private String shippingAddress;
    private String shippingCity;
    private String shippingCountry;
    private String shippingZipCode;
    private String phone;
    private String email;
    private Map<String, String> customerData;

    // to refactor?

    private List<Order> listOfOrders;
    private boolean hasAccount;
    private String password;
    private String[] billingAddressList;
    private String[] shippingAddressList;

    public Customer(Map<String, String> customerData, String email, String password) {
        firstName = customerData.get("first_name");
        lastName = customerData.get("last_name");
        billingAddress = customerData.get("billing_address");
        billingCity = customerData.get("billing_city");
        billingCountry = customerData.get("billing_country");
        billingZipCode = customerData.get("billing_zip_code");
        phone = customerData.get("phone");
        shippingAddress = customerData.get("ship_address");
        shippingCity = customerData.get("ship_city");
        shippingCountry = customerData.get("ship_country");
        shippingZipCode = customerData.get("ship_zip_code");
        this.email = email;
        this.password = password;
    }

    public Customer(int id, String firstName, String lastName, String billingAddress, String billingCity, String billingCountry, String billingZipCode, String shippingAddress, String shippingCity, String shippingCountry, String shippingZipCode, String phone, String email) {
        super(id, firstName + " " + lastName);
        this.billingAddress = billingAddress;
        this.billingCity = billingCity;
        this.billingCountry = billingCountry;
        this.billingZipCode = billingZipCode;
        this.shippingAddress = shippingAddress;
        this.shippingCity = shippingCity;
        this.shippingCountry = shippingCountry;
        this.shippingZipCode = shippingZipCode;
        this.phone = phone;
        this.email = email;
    }

    public Customer(String firstName, String lastName, String[] billingAddressList, String[] shippingAddressList, String phone, String email) {
        super(firstName + " " + lastName);
        this.listOfOrders = new ArrayList<>();
        this.billingAddressList = billingAddressList;
        this.shippingAddressList = shippingAddressList;
        this.phone = phone;
        this.email = email;
        this.hasAccount = false;
        this.password = null;
    }

    // GETTERS
    public String getName(){
        return firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public String getBillingCity() {
        return billingCity;
    }

    public String getBillingCountry() {
        return billingCountry;
    }

    public String getBillingZipCode() {
        return billingZipCode;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public String getShippingCity() {
        return shippingCity;
    }

    public String getShippingCountry() {
        return shippingCountry;
    }

    public String getShippingZipCode() {
        return shippingZipCode;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
    public Map<String, String> getCustomerData(Customer customer){
        setCustomerData(customer);
        return this.customerData;
    }
    //

    public List<Order> getListOfOrders() {
        return listOfOrders;
    }

    public String[] getBillingAddressList() {
        return billingAddressList;
    }

    public String[] getShippingAddressList() {
        return shippingAddressList;
    }

    public boolean isHasAccount() {
        return hasAccount;
    }

    public String getPassword() {
        return password;
    }


    // SETTERS

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public void setBillingCity(String billingCity) {
        this.billingCity = billingCity;
    }

    public void setBillingCountry(String billingCountry) {
        this.billingCountry = billingCountry;
    }

    public void setBillingZipCode(String billingZipCode) {
        this.billingZipCode = billingZipCode;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public void setShippingCity(String shippingCity) {
        this.shippingCity = shippingCity;
    }

    public void setShippingCountry(String shippingCountry) {
        this.shippingCountry = shippingCountry;
    }

    public void setShippingZipCode(String shippingZipCode) {
        this.shippingZipCode = shippingZipCode;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCustomerData(Customer customer){
        this.customerData = new HashMap<>();
        customerData.put("first_name", customer.getFirstName());
        customerData.put("last_name", customer.getLastName());
        customerData.put("billing_address", customer.getBillingAddress());
        customerData.put("billing_city", customer.getBillingCity());
        customerData.put("billing_country", customer.getBillingCountry());
        customerData.put("billing_zip_code", customer.getBillingZipCode());
        customerData.put("phone", customer.getPhone());
        customerData.put("ship_address", customer.getShippingAddress());
        customerData.put("ship_city", customer.getShippingCity());
        customerData.put("ship_country", customer.getShippingCountry());
        customerData.put("ship_zip_code", customer.getShippingZipCode());
    }

    //
    public void setListOfOrders(List<Order> listOfOrders) {
        this.listOfOrders = listOfOrders;
    }

    public void setBillingAddressList(String[] billingAddressList) {
        this.billingAddressList = billingAddressList;
    }

    public void setShippingAddressList(String[] shippingAddressList) {
        this.shippingAddressList = shippingAddressList;
    }


    public void setHasAccount(boolean hasAccount) {
        this.hasAccount = hasAccount;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
