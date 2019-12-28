package com.codecool.shop.service.form;

import com.codecool.shop.model.Customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Base abstract class for all forms.
 * It has variables and methods common for all forms.
 */
abstract class Form {
    private List<String> messages;
    private Map<String, String> customerData;
    private String email;

    Form() {
        this.messages = new ArrayList<>();
        this.customerData = new HashMap<>();
        this.email = null;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public Map<String, String> getCustomerData() {
        return customerData;
    }

    public void setCustomerData(Map<String, String> customerData) {
        this.customerData = customerData;
    }

    void setCustomerData(String key, String value) {
        this.customerData.put(key, value);
    }

    public void setCustomerData(Customer customer) {
        setCustomerData("first_name", customer.getFirstName());
        setCustomerData("last_name", customer.getLastName());
        setCustomerData("billing_address", customer.getBillingAddress());
        setCustomerData("billing_city", customer.getBillingCity());
        setCustomerData("billing_country", customer.getBillingCountry());
        setCustomerData("billing_zip_code", customer.getBillingZipCode());
        setCustomerData("phone", customer.getPhone());
        setCustomerData("ship_address", customer.getShippingAddress());
        setCustomerData("ship_city", customer.getShippingCity());
        setCustomerData("ship_country", customer.getShippingCountry());
        setCustomerData("ship_zip_code", customer.getShippingZipCode());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addMessage(String message){
        this.messages.add(message);
    }
}
