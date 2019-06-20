package com.codecool.shop.service.form;

import com.codecool.shop.util.Message;
import com.codecool.shop.util.Validator;

import javax.servlet.http.HttpServletRequest;

/**
 * Class for handling getting user's data such as name, mail, address etc.
 * It can be used for registration, checkout, updating info about customer
 */
public class UserDataForm extends Form {


    public UserDataForm() {
    }

    public String getWelcomeMessage() {
        return (Message.WELCOME_REGISTER.getMessage() + getCustomerData().get("first_name") + Message.SENT_EMAIL.getMessage());
    }


    public void setCustomerData(HttpServletRequest req) {
        String firstName = req.getParameter("first-name");
        String lastName = req.getParameter("last-name");
        String address = req.getParameter("address");
        String city = req.getParameter("city");
        String country = req.getParameter("country");
        String zipCode = req.getParameter("zip-code");
        String phoneNumber = req.getParameter("tel");

        String[] partialCustomerData = new String[]{firstName, lastName, address, city, country, zipCode, phoneNumber};
        if (!Validator.notNullAndEmpty(partialCustomerData) && !Validator.hasCorrectLength(partialCustomerData)) {
            getMessages().add(Message.WRONG_LENGTH_OR_NULL.getMessage());
        }
        if (!Validator.isPhoneValid(phoneNumber)) {
            getMessages().add(Message.PHONE_INCORRECT_FORMAT.getMessage());
        }
        if (!Validator.isZipCodeValid(zipCode)) {
            getMessages().add(Message.ZIP_CODE_INCORRECT_FORMAT.getMessage());
        }

        setCustomerData("first_name", firstName);
        setCustomerData("last_name", lastName);
        setCustomerData("billing_address", address);
        setCustomerData("billing_city", city);
        setCustomerData("billing_country", country);
        setCustomerData("billing_zip_code", zipCode);
        setCustomerData("phone", phoneNumber);

        setShippingAddress(req, address, city, country, zipCode);

    }

    public void setEmail(HttpServletRequest req) {
        String email = req.getParameter("email");
        if (!Validator.isValidEmail(email)) {
            getMessages().add(Message.EMAIL_INCORRECT_FORMAT.getMessage());
        }
        setEmail(email);
    }

    public String getValidPassword(HttpServletRequest req) {
        String password = req.getParameter("password");
        String passwordRepeat = req.getParameter("password-repeat");

        if (!Validator.isMatching(password, passwordRepeat)) {
            getMessages().add(Message.PASSWORDS_NOT_MATCH.getMessage());
            return null;
        } else if (Validator.isNull(password) || Validator.isNull(passwordRepeat)) {
            getMessages().add(Message.PASSWORD_EMPTY.getMessage());
            return null;
        } else {
            return password;
        }
    }

    private void setShippingAddress(HttpServletRequest req, String address, String city, String country, String zipCode) {
        String shipElsewhere = req.getParameter("shipping-address");
        String shipAddress = req.getParameter("ship-address");
        String shipCity = req.getParameter("ship-city");
        String shipCountry = req.getParameter("ship-country");
        String shipZipCode = req.getParameter("ship-zip-code");

        String[] checkoutShipping = new String[]{shipAddress, shipCity, shipCountry, shipZipCode};

        if (shipElsewhere != null && Validator.notNullAndEmpty(checkoutShipping)) {
            if (!Validator.isZipCodeValid(shipZipCode)) {
                getMessages().add(Message.ZIP_CODE_INCORRECT_FORMAT.getMessage());
            } else {
                shippingAddress(shipAddress, shipCity, shipCountry, shipZipCode);
            }
        } else {
            shippingAddress(address, city, country, zipCode);
        }
    }

    private void shippingAddress(String address, String city, String country, String zipCode) {
        setCustomerData("ship_address", address);
        setCustomerData("ship_city", city);
        setCustomerData("ship_country", country);
        setCustomerData("ship_zip_code", zipCode);
    }
}
