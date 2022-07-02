package com.smalwe.payment.gateway.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PaymentGatewayUtility {

    public static boolean isValidCreditCardNumber(String cardNumber) {
        /*
        A credit card number must have between 13 and 16 digits. It must start with:
        4 for Visa cards
        5 for Master cards
        3 for American Express cards
        6 for Discover cards
         */

        String regex = "^[3,4,5,6][0-9]{12,15}"; // Regex to check valid cardNumber

        Pattern p = Pattern.compile(regex);

        if (cardNumber == null) return false;

        Matcher m = p.matcher(cardNumber);

        return m.matches();
    }

    /*
    Method to validate the CVV
    CVV Should have 3 or 4 digits
    It should have only digits between 0-9
     */
    public static boolean isValidCVVNumber(String cvv) {

        String regex = "^[0-9]{3,4}$"; // Regex to check valid CVV number.

        Pattern p = Pattern.compile(regex);

        if (cvv == null) return false;

        Matcher m = p.matcher(cvv);

        return m.matches();
    }

    // Masks 75% of the cardNumber
    public static String maskCardNumber(String cardNumber) {
        int length = cardNumber.length() - cardNumber.length()/4;
        String s = cardNumber.substring(0, length);
        return s.replaceAll("[0-9]", "X") + cardNumber.substring(length);
    }
}
