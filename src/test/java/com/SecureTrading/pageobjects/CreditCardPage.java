package com.SecureTrading.pageobjects;

import org.openqa.selenium.By;

public class CreditCardPage {
    private By creditCardInput = By.cssSelector("");
    private By cvvInput = By.cssSelector("");
    private By expirationDateInput = By.cssSelector("");
    private By submitButton = By.cssSelector("");

    private By creditCardValidation = By.cssSelector("");
    private By cvvValidation = By.cssSelector("");
    private By expirationDateValidation = By.cssSelector("");

    private By successfulPaymentMessage = By.cssSelector("");
    private By declinedPaymentMessage = By.cssSelector("");


    public void fillCreditCardField(String creditCard) {

    }

    public void fillCvvFieldField(String cvv) {

    }

    public void fillExpirationDateField(String date) {

    }

    public void clickSubmitButton() {

    }

    public void getCreditCardValidation() {

    }

    public void getCvvValidation() {

    }

    public void getExpirationDateValidation() {

    }

    public void getSuccessfulPaymentMessage() {

    }

    public void getDeclinedPaymentMessage() {

    }
}
