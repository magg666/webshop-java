package com.codecool.shop.util;

/**
 * this is class with all possible messages for user
 * There should be no explicit messages in code, except this class
 */
public enum Message {
    WELCOME_REGISTER(" welcome, "),
    WELCOME_LOGIN(" welcome again, "),
    SENT_EMAIL(" we have sent you confirmation email. "),
    EMAIL_INCORRECT_FORMAT(" email is in wrong format "),
    EMAIL_NOT_EXISTS(" email not exists in our database "),
    PASSWORD_NOT_MATCH(" password incorrect "),
    PASSWORDS_NOT_MATCH(" passwords do not match "),
    EMAIL_EXISTS(" email already exists in our database "),
    WRONG_LENGTH_OR_NULL(" All fields must be filled. Minimum length of field content - 3 signs "),
    PHONE_INCORRECT_FORMAT(" incorrect phone number "),
    ZIP_CODE_INCORRECT_FORMAT(" incorrect zip code "),
    PASSWORD_EMPTY(" password must not be empty "),
    ALREADY_LOGGED(" you are already logged "),
    WRONG_TYPE(" the type of argument is wrong "),
    EMPTY_CART(" your cart is empty. Maybe put something nice into? "),
    CART_SAVED(" you successfully saved your cart! You can find it in your account page. "),
    NOT_LOGGED(" you are not logged. ");


    private String message;

    Message(String message) {
        this.message = message;
    }
    public String getMessage(){
        return this.message;
    }

}
