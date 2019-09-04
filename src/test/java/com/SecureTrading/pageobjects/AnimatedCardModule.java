package com.SecureTrading.pageobjects;

import org.junit.Assert;
import org.openqa.selenium.By;
import util.PicoContainerHelper;
import util.SeleniumExecutor;
import util.enums.FieldType;
import util.enums.StoredElement;

import static util.helpers.TestConditionHandler.checkIfBrowserNameStartWith;
import static util.helpers.actions.CustomGetAttributeImpl.getAttribute;
import static util.helpers.actions.CustomGetTextImpl.getText;
import static util.helpers.actions.CustomSendKeysImpl.sendKeys;

public class AnimatedCardModule {

    // animated card
    private By animatedCard = By.id("st-animated-card");
    private By creditCardNumberFromAnimatedCard = By.id("st-animated-card-number");
    private By cvcBackSideAnimatedCard = By.id("st-animated-card-security-code");
    private By cvcFrontSideAnimatedCard = By.id("st-animated-card-security-code-front-field");
    private By expirationDateFromAnimatedCard = By.id("st-animated-card-expiration-date");
    private By cardTypeLogoFromAnimatedCard = By.id("st-payment-logo");

    //labels
    public By animatedCardNumberLabel = By.id("st-animated-card-card-number-label");
    public By animatedExpirationDateLabel = By.id("st-animated-card-expiration-date-label");
    public By animatedSecurityCodeLabel = By.id("st-animated-card-security-code-label");

    //Inputs without iframes
    private By cardNumberInputField = By.id("st-card-number-input");
    private By cvcInputField = By.id("st-security-code-input");
    private By expirationDateInputField = By.id("st-expiration-date-input");

    // No-iFrames-fields validation messages
    private By creditCardFieldValidationMessage = By.id("st-card-number-message");
    private By cvcFieldValidationMessage = By.id("st-security-code-message");
    private By expirationDateFieldValidationMessage = By.id("st-expiration-date-message");


    public boolean checkIfAnimatedCardIsFlippedWithoutIframe() throws InterruptedException {
        if (checkIfBrowserNameStartWith("IE"))
            Thread.sleep(2000);
        boolean isFlipped = false;
        String cardSide = getAttribute(SeleniumExecutor.getDriver().findElement(animatedCard), "class");
        if (cardSide.contains("flip_card")) {
            isFlipped = true;
        }
        return isFlipped;
    }

    public String getDataFromAnimatedCreditCardWithoutIframe(FieldType fieldType) {
        String data = "";
        switch (fieldType) {
            case CARD_NUMBER:
                data = getText(SeleniumExecutor.getDriver().findElement(creditCardNumberFromAnimatedCard));
                break;
            case CVC:
                if (PicoContainerHelper.getFromContainer(StoredElement.cardType).toString().contains("AMEX")) {
                    data = getText(SeleniumExecutor.getDriver().findElement(cvcFrontSideAnimatedCard));
                } else {
                    data = getText(SeleniumExecutor.getDriver().findElement(cvcBackSideAnimatedCard));
                }
                break;
            case EXPIRY_DATE:
                data = getText(SeleniumExecutor.getDriver().findElement(expirationDateFromAnimatedCard));
                break;
        }
        return data;
    }

    public void validateIfProvidedDataOnAnimatedCardWasAsExpectedWithoutIframe(FieldType fieldType, String expectedData) {
        PicoContainerHelper.updateInContainer(StoredElement.errorMessage,
                fieldType.toString() + " data from animated credit card is not correct, should be " + expectedData
                        + " but was: " + getDataFromAnimatedCreditCardWithoutIframe(fieldType));
        Assert.assertEquals(PicoContainerHelper.getFromContainer(StoredElement.errorMessage, String.class),
                expectedData, getDataFromAnimatedCreditCardWithoutIframe(fieldType));
    }

    public void validateIfAnimatedCardIsFlippedWithoutIframe(boolean amexCard) throws InterruptedException {
        if (amexCard) {
            PicoContainerHelper.updateInContainer(StoredElement.errorMessage,
                    "Animated card is flipped for AMEX but shouldn't be");
            Assert.assertFalse(PicoContainerHelper.getFromContainer(StoredElement.errorMessage, String.class),
                    checkIfAnimatedCardIsFlippedWithoutIframe());
        } else {
            PicoContainerHelper.updateInContainer(StoredElement.errorMessage,
                    "Animated card is not flipped but should be");
            Assert.assertTrue(PicoContainerHelper.getFromContainer(StoredElement.errorMessage, String.class),
                    checkIfAnimatedCardIsFlippedWithoutIframe());
        }
    }

    public void validateIfAllProvidedDataOnAnimatedCardWasAsExpectedWithoutIframe(String cardNumber, String expirationDate,
                                                                                  String cvc) {
        validateIfProvidedDataOnAnimatedCardWasAsExpectedWithoutIframe(FieldType.CARD_NUMBER, cardNumber);
        validateIfProvidedDataOnAnimatedCardWasAsExpectedWithoutIframe(FieldType.EXPIRY_DATE, expirationDate);
        validateIfProvidedDataOnAnimatedCardWasAsExpectedWithoutIframe(FieldType.CVC, cvc);
    }

    public void fillPaymentFormWithoutIFrames(String cardNumber, String expiryDate, String cvv){
        sendKeys(SeleniumExecutor.getDriver().findElement(cardNumberInputField), cardNumber);
        sendKeys(SeleniumExecutor.getDriver().findElement(expirationDateInputField), expiryDate);
        sendKeys(SeleniumExecutor.getDriver().findElement(cvcInputField), cvv);
    }

    public String getCreditCardNoIFrameFieldValidationMessage(FieldType fieldType) {
        String message = "";
        switch (fieldType) {
            case CARD_NUMBER:
                message = getText(SeleniumExecutor.getDriver().findElement(creditCardFieldValidationMessage));
                break;
            case CVC:
                message = getText(SeleniumExecutor.getDriver().findElement(cvcFieldValidationMessage));
                break;
            case EXPIRY_DATE:
                message = getText(SeleniumExecutor.getDriver().findElement(expirationDateFieldValidationMessage));
                break;
        }
        return message;
    }

    public boolean checkIfNoIFrameFieldIsHighlighted(FieldType fieldType) {
        boolean highlight = false;
        String className = "";
        switch (fieldType) {
            case CARD_NUMBER:
                className = getAttribute(SeleniumExecutor.getDriver().findElement(cardNumberInputField), "class");
                break;
            case CVC:
                className = getAttribute(SeleniumExecutor.getDriver().findElement(cvcInputField), "class");
                break;
            case EXPIRY_DATE:
                className = getAttribute(SeleniumExecutor.getDriver().findElement(expirationDateInputField), "class");
                break;
        }

        if (className.contains("error-field")) {
            highlight = true;
        }
        return highlight;
    }

    public void validateIfNoIFrameFieldIsHighlighted(FieldType fieldType) {
        PicoContainerHelper.updateInContainer(StoredElement.errorMessage,
                " Field is not highlighted but should be");
        Assert.assertTrue(PicoContainerHelper.getFromContainer(StoredElement.errorMessage, String.class),
                checkIfNoIFrameFieldIsHighlighted(fieldType));
    }

    public void validateIfNoIFrameFieldValidationMessageWasAsExpected(FieldType fieldType, String expectedMessage) {
        PicoContainerHelper.updateInContainer(StoredElement.errorMessage,
                fieldType.toString() + " error message is not correct, should be " + expectedMessage + " but was: "
                        + getCreditCardNoIFrameFieldValidationMessage(fieldType));
        Assert.assertEquals(PicoContainerHelper.getFromContainer(StoredElement.errorMessage, String.class),
                expectedMessage, getCreditCardNoIFrameFieldValidationMessage(fieldType));
    }

    public void validateIfCardTypeIconWasAsExpectedForWithoutIframe(String expectedCardIcon) {
        PicoContainerHelper.updateInContainer(StoredElement.errorMessage, " Card type icon is not correct, should be "
                + expectedCardIcon + " but was: " + getCardTypeIconFromAnimatedCardTextWithoutIframe());
        Assert.assertEquals(PicoContainerHelper.getFromContainer(StoredElement.errorMessage, String.class),
                expectedCardIcon, getCardTypeIconFromAnimatedCardTextWithoutIframe());
    }

    public String getCardTypeIconFromAnimatedCardTextWithoutIframe() {
        String cardLogo = getAttribute(SeleniumExecutor.getDriver().findElement(cardTypeLogoFromAnimatedCard), "alt");
        return cardLogo;
    }
}
