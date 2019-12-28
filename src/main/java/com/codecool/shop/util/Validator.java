package com.codecool.shop.util;

import java.util.Arrays;
import java.util.List;

/**
 * This class contains static methods to validate all necessary
 * data. There should be overloaded methods for different kind of parameters
 */
public class Validator {

    public static boolean notNullAndEmpty(String string) {
        return string != null && !string.trim().equals("");
    }

    public static boolean notNullAndEmpty(String[] strings) {
        return Arrays.stream(strings).allMatch(Validator::notNullAndEmpty);
    }

    public static boolean isNull(String string){
        return string == null || string.trim().equals("");
    }

    public static boolean isNull(String [] strings){
        return Arrays.stream(strings).allMatch(Validator::isNull);
    }

    public static boolean hasCorrectLength(String string) {
        return string.trim().length() > 3 && string.trim().length() < 50;
    }

    public static boolean hasCorrectLength(String[] strings){
        return Arrays.stream(strings).allMatch(Validator::hasCorrectLength);
    }

    public static boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.trim().matches(regex);
    }

    public static boolean isPhoneValid(String phone) {
        String regex = "\\d{3}[\\-]\\d{3}[\\-]\\d{3}";
        return phone.trim().matches(regex);
    }

    public static boolean isZipCodeValid(String zipCode){
        String regex = "[0-9]{2}-[0-9]{3}";
        return zipCode.trim().matches(regex);
    }

    public static boolean isMatching(String password, String passwordRepeat){
        return password.equals(passwordRepeat);
    }

    static boolean isNotNumber(String number){
        String pattern = "^[1-9]\\d*$";
        return !number.matches(pattern);
    }

    public static boolean isMessage(List<String> messages){
        return messages.size() > 0;
    }
}




