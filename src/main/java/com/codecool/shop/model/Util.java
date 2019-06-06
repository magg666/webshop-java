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
    public boolean checkNumber(String number){
        String pattern = "^[1-9]\\d*$";
        return number.matches(pattern);

    }

}
