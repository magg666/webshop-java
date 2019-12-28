package com.codecool.shop.util;
import org.mindrot.jbcrypt.BCrypt;

/**
 * This class contains methods for hashing password and compare
 * un-hashed and hashed strings
 */

public class HashPassword {
    /**
     * Method to hash plain string     *
     * @param password - password to hash
     * @return String - hashed password
     */
    public static String getHashed(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**Check that an unencrypted password matches one that has
     * previously been hashed
     * @param candidate - String to check
     * @param hashed - hashed String to compare
     * @return boolean
     */
    public static boolean isMatching(String candidate, String hashed) {
        return (BCrypt.checkpw(candidate, hashed));
    }
}





