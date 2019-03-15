package com.SecureTrading.pageobjects;

import static util.helpers.IframeHandler.switchToDefaultIframe;
import static util.helpers.IframeHandler.switchToIframe;
import static util.helpers.actions.CustomClickImpl.click;
import static util.helpers.actions.CustomGetAttributeImpl.getAttribute;
import static util.helpers.actions.CustomGetTextImpl.getText;
import static util.helpers.actions.CustomSendKeysImpl.sendKeys;

import org.junit.Assert;
import org.openqa.selenium.By;
import util.PicoContainerHelper;
import util.SeleniumExecutor;
import util.enums.CardFieldType;
import util.enums.MerchantFieldType;
import util.enums.PaymentType;
import util.enums.StoredElement;

public class PaymentPage extends BasePage {

    //Credit card form
    private String cardNumberFrameName = "st-card-number";
    private String cvcFrameName = "st-security-code";
    private String expirationDateFrameName = "st-expiration-date";
    private String animatedCardFrameName = "";

    private By merchantName = By.id("example-form-name");
    private By merchantEmail = By.id("example-form-email");
    private By merchantPhone = By.id("example-form-phone");

    private By cardNumberInputField = By.id("st-card-number-input");
    private By cvcInputField = By.id("st-security-code-input");
    private By expirationDateInputField = By.id("st-expiration-date-input");
    private By payButton = By.xpath("//button[@type='submit']");

    private By creditCardFieldValidationMessage = By.id("st-card-number-message");
    private By cvcFieldValidationMessage = By.id("st-security-code-message");
    private By expirationDateFieldValidationMessage = By.id("st-expiration-date-message");

    //animated credit card
    private By creditCardNumberFromAnimatedCard = By.cssSelector("");
    private By cvcFromAnimatedCard = By.cssSelector("");
    private By expirationDateFromAnimatedCard = By.cssSelector("");
    private By cardTypeIconFromAnimatedCard = By.cssSelector("");

    //payment confirmation view
    private By paymentStatusMessage = By.cssSelector("");
    private By transactionReference = By.cssSelector("");
    private By authCode = By.cssSelector("");
    private By amount = By.cssSelector("");
    private By currency = By.cssSelector("");
    private By paymentType = By.cssSelector("");


    //paymentMethods
    private By creditCardPaymentMethod = By.cssSelector("");

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


    public String getPaymentStatusMessage() {
        return getText(SeleniumExecutor.getDriver().findElement(paymentStatusMessage));
    }

    //Get info from animated credit card
    public String getCardTypeIconFromAnimatedCardText() {
        switchToIframe(animatedCardFrameName);
        String iconName =  getAttribute(SeleniumExecutor.getDriver().findElement(cardTypeIconFromAnimatedCard), "a");
        switchToDefaultIframe();
        return iconName;
    }

    public void switchToFrameByFieldType(CardFieldType fieldType) {
        switch (fieldType) {
            case number:
                switchToIframe(cardNumberFrameName);
            case cvc:
                switchToIframe(cvcFrameName);
            case expiryDate:
                switchToIframe(expirationDateFrameName);
        }
    }

    public String getDataFromAnimatedCreditCard(CardFieldType fieldType) {
        String data = "";
        switchToIframe(animatedCardFrameName);
        switch (fieldType) {
            case number:
                data = getText(SeleniumExecutor.getDriver().findElement(creditCardNumberFromAnimatedCard));
                break;
            case cvc:
                data = getText(SeleniumExecutor.getDriver().findElement(cvcFromAnimatedCard));
                break;
            case expiryDate:
                data = getText(SeleniumExecutor.getDriver().findElement(expirationDateFromAnimatedCard));
                break;
        }
        switchToDefaultIframe();
        return data;
    }

    public void choosePaymentMethod(PaymentType paymentType) {
        switch (paymentType) {
            case creditCard:
                click(SeleniumExecutor.getDriver().findElement(creditCardPaymentMethod));
                break;
        }
    }

    public void fillAllCardData(String cardNumber, String cvc, String expiryDate){
        fillCreditCardInputField(CardFieldType.number, cardNumber);
        fillCreditCardInputField(CardFieldType.cvc, cvc);
        fillCreditCardInputField(CardFieldType.expiryDate, expiryDate);
    }

    public void fillAllMerchantData(String name, String email, String phone){
        fillMerchantInputField(MerchantFieldType.name, name);
        fillMerchantInputField(MerchantFieldType.email, email);
        fillMerchantInputField(MerchantFieldType.phone, phone);
    }

    public void fillCreditCardInputField(CardFieldType fieldType, String value) {
        switchToFrameByFieldType(fieldType);
        switch (fieldType) {
            case number:
                sendKeys(SeleniumExecutor.getDriver().findElement(cardNumberInputField), value);
                break;
            case cvc:
                sendKeys(SeleniumExecutor.getDriver().findElement(cvcInputField), value);
                break;
            case expiryDate:
                sendKeys(SeleniumExecutor.getDriver().findElement(expirationDateInputField), value);
                break;
        }
        switchToDefaultIframe();
    }

    public void fillMerchantInputField(MerchantFieldType fieldType, String value) {
        switch (fieldType) {
            case name:
                sendKeys(SeleniumExecutor.getDriver().findElement(cardNumberInputField), value);
                break;
            case email:
                sendKeys(SeleniumExecutor.getDriver().findElement(cvcInputField), value);
                break;
            case phone:
                sendKeys(SeleniumExecutor.getDriver().findElement(expirationDateInputField), value);
                break;
        }
    }

    public String getCreditCardFieldValidationMessage(CardFieldType fieldType) {
        String message = "";
        switchToFrameByFieldType(fieldType);
        switch (fieldType) {
            case number:
                message = getText(SeleniumExecutor.getDriver().findElement(creditCardFieldValidationMessage));
                break;
            case cvc:
                message = getText(SeleniumExecutor.getDriver().findElement(cvcFieldValidationMessage));
                break;
            case expiryDate:
                message = getText(SeleniumExecutor.getDriver().findElement(expirationDateFieldValidationMessage));
                break;
        }
        switchToDefaultIframe();
        return message;
    }

    public void clickPayButton() {
        click(SeleniumExecutor.getDriver().findElement(payButton));
    }

    public void validateIfFieldValidationMessageWasAsExpected(CardFieldType fieldType, String expectedMessage) {
        PicoContainerHelper.updateInContainer(StoredElement.errorMessage, fieldType.toString() + " error message is not correct, should be " + expectedMessage + " but was: " + getCreditCardFieldValidationMessage(fieldType));
        Assert.assertEquals(PicoContainerHelper.getFromContainer(StoredElement.errorMessage, String.class), expectedMessage, getCreditCardFieldValidationMessage(fieldType));
    }

    public void validateIfPaymentStatusMessageWasAsExpected(String expectedMessage) {
        PicoContainerHelper.updateInContainer(StoredElement.errorMessage, " payment status message is not correct, should be " + expectedMessage + " but was: " + getPaymentStatusMessage());
        Assert.assertEquals(PicoContainerHelper.getFromContainer(StoredElement.errorMessage, String.class), expectedMessage, getPaymentStatusMessage());
    }

    public void validateIfCardTypeIconWasAsExpected(String expectedCardIcon) {
        PicoContainerHelper.updateInContainer(StoredElement.errorMessage, " Card type icon is not correct, should be " + expectedCardIcon + " but was: " + getCardTypeIconFromAnimatedCardText());
        Assert.assertEquals(PicoContainerHelper.getFromContainer(StoredElement.errorMessage, String.class), expectedCardIcon, getCardTypeIconFromAnimatedCardText());
    }

    public void validateIfProvidedDataOnAnimatedCardWasAsExpected(CardFieldType fieldType, String expectedData) {
        PicoContainerHelper.updateInContainer(StoredElement.errorMessage, fieldType.toString() + " data from animated credit card is not correct, should be " + expectedData + " but was: " + getDataFromAnimatedCreditCard(fieldType));
        Assert.assertEquals(PicoContainerHelper.getFromContainer(StoredElement.errorMessage, String.class), expectedData, getDataFromAnimatedCreditCard(fieldType));
    }

    public void validateIfAllProvidedDataOnAnimatedCardWasAsExpected(String cardNumber, String cvc, String expirationDate) {
        validateIfProvidedDataOnAnimatedCardWasAsExpected(CardFieldType.number, cardNumber);
        validateIfProvidedDataOnAnimatedCardWasAsExpected(CardFieldType.cvc, cvc);
        validateIfProvidedDataOnAnimatedCardWasAsExpected(CardFieldType.expiryDate, expirationDate);
    }
}