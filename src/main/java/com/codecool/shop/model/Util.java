package com.codecool.shop.model;

public class Util {
    public Util() {
    }

    public boolean isNotNull(String[] checkoutData) {
        for (String parameter : checkoutData) {
            if (parameter.equals("")) {
                return false;
            }
        }
        return true;
    }
}
