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

    public boolean isPaymentAndTermsChecked(String paymentMethod, String terms){
        return paymentMethod != null && terms != null;
    }

    public boolean isChosenPaymentFilled(String payment, String[] cardData, String[] paypalData){
        final boolean b = true;
        if(payment.equals("direct")){
            return b;
        } else if (payment.equals("card") && isNotNull(cardData)){
            return b;
        }else if (payment.equals("paypal") && isNotNull(paypalData)){
            return b;
        }return false;
    }
}
