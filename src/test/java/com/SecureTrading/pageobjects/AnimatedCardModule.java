package com.SecureTrading.pageobjects;

import org.junit.Assert;
import org.openqa.selenium.By;
import util.PicoContainerHelper;
import util.SeleniumExecutor;
import util.enums.FieldType;
import util.enums.StoredElement;

import static util.helpers.IframeHandler.switchToDefaultIframe;
import static util.helpers.IframeHandler.switchToIframe;
import static util.helpers.TestConditionHandler.checkIfBrowserNameStartWith;
import static util.helpers.actions.CustomGetAttributeImpl.getAttribute;
import static util.helpers.actions.CustomGetTextImpl.getText;

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


    public String getCardTypeIconFromAnimatedCardText() {
        switchToIframe(FieldType.ANIMATED_CARD.getIframeName());
        String cardLogo = getAttribute(SeleniumExecutor.getDriver().findElement(cardTypeLogoFromAnimatedCard), "alt");
        switchToDefaultIframe();
        return cardLogo;
    }

    public boolean checkIfAnimatedCardIsFlipped() throws InterruptedException {
        if (checkIfBrowserNameStartWith("IE"))
            Thread.sleep(2000);
        switchToIframe(FieldType.ANIMATED_CARD.getIframeName());
        boolean isFlipped = false;
        String cardSide = getAttribute(SeleniumExecutor.getDriver().findElement(animatedCard), "class");
        if (cardSide.contains("flip_card")) {
            isFlipped = true;
        }
        switchToDefaultIframe();
        return isFlipped;
    }

    public String getDataFromAnimatedCreditCard(FieldType fieldType) {
        String data = "";
        switchToIframe(FieldType.ANIMATED_CARD.getIframeName());
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
        switchToDefaultIframe();
        return data;
    }

    public void validateIfCardTypeIconWasAsExpected(String expectedCardIcon) {
        PicoContainerHelper.updateInContainer(StoredElement.errorMessage, " Card type icon is not correct, should be "
                + expectedCardIcon + " but was: " + getCardTypeIconFromAnimatedCardText());
        Assert.assertEquals(PicoContainerHelper.getFromContainer(StoredElement.errorMessage, String.class),
                expectedCardIcon, getCardTypeIconFromAnimatedCardText());
    }

    public void validateIfProvidedDataOnAnimatedCardWasAsExpected(FieldType fieldType, String expectedData) {
        PicoContainerHelper.updateInContainer(StoredElement.errorMessage,
                fieldType.toString() + " data from animated credit card is not correct, should be " + expectedData
                        + " but was: " + getDataFromAnimatedCreditCard(fieldType));
        Assert.assertEquals(PicoContainerHelper.getFromContainer(StoredElement.errorMessage, String.class),
                expectedData, getDataFromAnimatedCreditCard(fieldType));
    }

    public void validateIfAnimatedCardIsFlipped(boolean amexCard) throws InterruptedException {
        if (amexCard) {
            PicoContainerHelper.updateInContainer(StoredElement.errorMessage,
                    "Animated card is flipped for AMEX but shouldn't be");
            Assert.assertFalse(PicoContainerHelper.getFromContainer(StoredElement.errorMessage, String.class),
                    checkIfAnimatedCardIsFlipped());
        } else {
            PicoContainerHelper.updateInContainer(StoredElement.errorMessage,
                    "Animated card is not flipped but should be");
            Assert.assertTrue(PicoContainerHelper.getFromContainer(StoredElement.errorMessage, String.class),
                    checkIfAnimatedCardIsFlipped());
        }
    }

    public void validateIfAllProvidedDataOnAnimatedCardWasAsExpected(String cardNumber, String expirationDate,
                                                                     String cvc) {
        validateIfProvidedDataOnAnimatedCardWasAsExpected(FieldType.CARD_NUMBER, cardNumber);
        validateIfProvidedDataOnAnimatedCardWasAsExpected(FieldType.EXPIRY_DATE, expirationDate);
        validateIfProvidedDataOnAnimatedCardWasAsExpected(FieldType.CVC, cvc);
    }
}
