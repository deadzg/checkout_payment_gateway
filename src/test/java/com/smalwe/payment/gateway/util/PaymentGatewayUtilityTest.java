package com.smalwe.payment.gateway.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PaymentGatewayUtilityTest {

    @Test
    public void testIsValidCreditCardNumber_Valid_13Digit() {
        assertTrue(PaymentGatewayUtility.isValidCreditCardNumber("4567123456789"));
    }

    @Test
    public void testIsValidCreditCardNumber_Valid_16Digit() {
        assertTrue(PaymentGatewayUtility.isValidCreditCardNumber("4567123456789333"));
    }

    @Test
    public void testIsValidCreditCardNumber_Valid_16_Digit() {
        assertTrue(PaymentGatewayUtility.isValidCreditCardNumber("4999999999999109"));
    }



    @Test
    public void testIsValidCreditCardNumber_Invalid_Start() {
        assertFalse(PaymentGatewayUtility.isValidCreditCardNumber("1567123456789"));
    }

    @Test
    public void testIsValidCreditCardNumber_Invalid_Digits() {
        assertFalse(PaymentGatewayUtility.isValidCreditCardNumber("4567123456789333333"));
    }

    @Test
    public void testIsValidCreditCardNumber_Invalid_Character() {
        assertFalse(PaymentGatewayUtility.isValidCreditCardNumber("45671238933333asd"));
    }

    @Test
    public void testIsValidCVVNumber_Valid_3_digits() {
        assertTrue(PaymentGatewayUtility.isValidCVVNumber("023"));
    }

    @Test
    public void testIsValidCVVNumber_Valid_4_digits() {
        assertTrue(PaymentGatewayUtility.isValidCVVNumber("1233"));
    }

    @Test
    public void testIsValidCVVNumber_Invalid_3_digits() {
        assertFalse(PaymentGatewayUtility.isValidCVVNumber("12a"));
    }

    @Test
    public void testIsValidCVVNumber_Invalid_5_digits() {
        assertFalse(PaymentGatewayUtility.isValidCVVNumber("12334"));
    }

    @Test
    public void testIsValidCVVNumber_Invalid_Empty() {
        assertFalse(PaymentGatewayUtility.isValidCVVNumber(""));
    }

    @Test
    public void testIsCardDateValid_Valid_FutureDate() {
        assertTrue(PaymentGatewayUtility.isCardDateValid(12, 2023));
    }

    @Test
    public void testIsCardDateValid_Valid_CurrentYear_FutureMonth() {
        assertTrue(PaymentGatewayUtility.isCardDateValid(12, 2022));
    }

    @Test
    public void testIsCardDateValid_InValid_PastDate() {
        assertFalse(PaymentGatewayUtility.isCardDateValid(12, 2021));
    }

    @Test
    public void testIsCardDateValid_InValid_CurrentYear_PastMonth() {
        assertFalse(PaymentGatewayUtility.isCardDateValid(05, 2022));
    }
}
