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
import util.enums.StoredElement;

public class PaymentPage extends BasePage {

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

    private By successfulPaymentMessage = By.cssSelector("");
    private By errorPaymentMessage = By.cssSelector("");

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

    public String getSuccessfulPaymentMessage() {
        return getText(SeleniumExecutor.getDriver().findElement(successfulPaymentMessage));
    }

    public String getErrorPaymentMessage() {
        return getText(SeleniumExecutor.getDriver().findElement(errorPaymentMessage));
    }

    public void validateIfFieldValidationMessageWasAsExpected(CardFieldType fieldType, String expectedMessage) {
        PicoContainerHelper.addToContainer(StoredElement.errorMessage, fieldType.toString() + " error message is not correct, should be " + expectedMessage + " but was: " + getCreditCardFieldValidationMessage(fieldType));
        Assert.assertEquals(PicoContainerHelper.getFromContainer(StoredElement.errorMessage, String.class), getCreditCardFieldValidationMessage(fieldType), expectedMessage);
    }
}