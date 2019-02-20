package com.SecureTrading.pageobjects;

import static util.helpers.IframeHandler.switchToDefaultIframe;
import static util.helpers.IframeHandler.switchToIframe;
import static util.helpers.actions.CustomClickImpl.click;
import static util.helpers.actions.CustomGetTextImpl.getText;
import static util.helpers.actions.CustomSendKeysImpl.sendKeys;

import org.junit.Assert;
import org.openqa.selenium.By;
import util.PicoContainerHelper;
import util.SeleniumExecutor;
import util.enums.CardFieldType;
import util.enums.PaymentType;
import util.enums.StoredElement;

public class PaymentPage extends BasePage {

    //Credit card form
    private String creditCardNumberFrameName = "";
    private String cvcFrameName = "";
    private String expiryDateFrameName = "";

    private By creditCardNumberInputField = By.cssSelector("");
    private By cvcInputField = By.cssSelector("");
    private By expirationDateInputField = By.cssSelector("");
    private By submitButtonField = By.cssSelector("");

    private By creditCardFieldValidationMessage = By.cssSelector("");
    private By cvcFieldValidationMessage = By.cssSelector("");
    private By expirationDateFieldValidationMessage = By.cssSelector("");

    private By cvvTooltipIcon = By.cssSelector("");
    private By cvvTooltipText = By.cssSelector("");

    //animated credit card
    private By creditCardNumberFromAnimatedCard = By.cssSelector("");
    private By cvcInputFromAnimatedCard = By.cssSelector("");
    private By expirationDateFromAnimatedCard = By.cssSelector("");
    private By cardTypeIconFromAnimatedCard = By.cssSelector("");

    //paymentMethods
    private By visaPaymentMethod = By.cssSelector("");
    private By masterCardPaymentMethod = By.cssSelector("");
    private By applePayPaymentMethod = By.cssSelector("");
    private By visaCheckoutPaymentMethod = By.cssSelector("");
    private By payPal = By.cssSelector("");

    //payment confirmation view
    private By successfulPaymentMessage = By.cssSelector("");
    private By errorPaymentMessage = By.cssSelector("");
    private By transactionReference = By.cssSelector("");
    private By authCode = By.cssSelector("");
    private By amount = By.cssSelector("");
    private By currency = By.cssSelector("");
    private By paymentType = By.cssSelector("");
    private By merchantName = By.cssSelector("");

    //Other payment method - ToDo
    private String applePayPopup = "";
    private String visaCheckoutPopup = "";
    private String psyPalPopup = "";


    //Get info from payment confirmation view
    public String getTransactionReferenceText() {
        return getText(SeleniumExecutor.getDriver().findElement(transactionReference));
    }

    public String getAuthCodeText() {
        return getText(SeleniumExecutor.getDriver().findElement(authCode));
    }

    public String getAmountText() {
        return getText(SeleniumExecutor.getDriver().findElement(amount));
    }

    public String getCurrencyText() {
        return getText(SeleniumExecutor.getDriver().findElement(currency));
    }

    public String getPaymentTypeText() {
        return getText(SeleniumExecutor.getDriver().findElement(paymentType));
    }

    public String getSuccessfulPaymentMessage() {
        return getText(SeleniumExecutor.getDriver().findElement(successfulPaymentMessage));
    }

    public String getErrorPaymentMessage() {
        return getText(SeleniumExecutor.getDriver().findElement(errorPaymentMessage));
    }

    public String getMerchantNameText() {
        return getText(SeleniumExecutor.getDriver().findElement(merchantName));
    }

    //Get info from animated credit card
    public String getCreditCardNumberFromAnimatedCardText() {
        return getText(SeleniumExecutor.getDriver().findElement(creditCardNumberFromAnimatedCard));
    }

    public String getCvcFromAnimatedCardText() {
        return getText(SeleniumExecutor.getDriver().findElement(cvcInputFromAnimatedCard));
    }

    public String getExpirationDateFromAnimatedCard() {
        return getText(SeleniumExecutor.getDriver().findElement(expirationDateFromAnimatedCard));
    }

    public String getCardTypeIconFromAnimatedCardText() {
        return getText(SeleniumExecutor.getDriver().findElement(cardTypeIconFromAnimatedCard));
    }


    public void choosePaymentMethod(PaymentType paymentType) {
        switch (paymentType) {
            case visa:
                click(SeleniumExecutor.getDriver().findElement(visaPaymentMethod));
                break;
            case masterCard:
                click(SeleniumExecutor.getDriver().findElement(masterCardPaymentMethod));
                break;
            case applePay:
                click(SeleniumExecutor.getDriver().findElement(applePayPaymentMethod));
                break;
            case visaCheckout:
                click(SeleniumExecutor.getDriver().findElement(visaCheckoutPaymentMethod));
                break;
            case payPal:
                click(SeleniumExecutor.getDriver().findElement(payPal));
                break;
        }
    }

    public void fillCreditCardInputField(CardFieldType fieldType, String value) {
        switch (fieldType) {
            case number:
                switchToIframe(creditCardNumberFrameName);
                sendKeys(SeleniumExecutor.getDriver().findElement(creditCardNumberInputField), value);
                break;
            case cvc:
                switchToIframe(cvcFrameName);
                sendKeys(SeleniumExecutor.getDriver().findElement(cvcInputField), value);
                break;
            case expiryDate:
                switchToIframe(expiryDateFrameName);
                sendKeys(SeleniumExecutor.getDriver().findElement(expirationDateInputField), value);
                break;
        }

        switchToDefaultIframe();
    }

    public void clickSubmitButton() {
        click(SeleniumExecutor.getDriver().findElement(submitButtonField));
    }

    public String getCreditCardFieldValidationMessage(CardFieldType fieldType) {
        String message = "";

        switch (fieldType) {
            case number:
                switchToIframe(creditCardNumberFrameName);
                message = getText(SeleniumExecutor.getDriver().findElement(creditCardFieldValidationMessage));
                break;
            case cvc:
                switchToIframe(cvcFrameName);
                message = getText(SeleniumExecutor.getDriver().findElement(cvcFieldValidationMessage));
                break;
            case expiryDate:
                switchToIframe(expiryDateFrameName);
                message = getText(SeleniumExecutor.getDriver().findElement(expirationDateFieldValidationMessage));
                break;
        }

        switchToDefaultIframe();
        return message;
    }



    public void validateIfFieldValidationMessageWasAsExpected(CardFieldType fieldType, String expectedMessage) {
        PicoContainerHelper.addToContainer(StoredElement.errorMessage, fieldType.toString() + " error message is not correct, should be " + expectedMessage + " but was: " + getCreditCardFieldValidationMessage(fieldType));
        Assert.assertEquals(PicoContainerHelper.getFromContainer(StoredElement.errorMessage, String.class), getCreditCardFieldValidationMessage(fieldType), expectedMessage);
    }

    public void clickCvvTooltipIcon() {
        click(SeleniumExecutor.getDriver().findElement(cvvTooltipIcon));
    }

    public String getCvvTooltipText(){
        return getText(SeleniumExecutor.getDriver().findElement(cvvTooltipText));
    }
}