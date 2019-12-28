package com.codecool.shop.util;

import com.codecool.shop.service.SessionManager;

import javax.servlet.http.HttpServletRequest;

public class Util {
    public Util() {
    }

    public static boolean isCustomerLogged(HttpServletRequest req){
        return SessionManager.getCustomerFromSession(req) != null && SessionManager.getUserVerified(req) != null;
    }




    public boolean isNotNull(String[] checkoutData) {
        for (String parameter : checkoutData) {
            if (parameter.equals("")) {
                return false;
            }
        }
        return true;
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
