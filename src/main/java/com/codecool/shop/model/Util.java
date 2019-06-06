package com.codecool.shop.model;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public int checkNumber(String number){
        String pattern = "^[1-9]\\d*$";
        int newNumber;
        if(number.matches(pattern)){
            newNumber = Integer.parseInt(number);
        } else {
            newNumber = -1;
        }return newNumber;

    }
}
