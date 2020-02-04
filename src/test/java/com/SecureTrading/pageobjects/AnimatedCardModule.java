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
import static util.helpers.actions.CustomClickImpl.click;
import static util.helpers.actions.CustomGetAttributeImpl.getAttribute;
import static util.helpers.actions.CustomGetTextImpl.getText;
import static util.helpers.actions.CustomSendKeysImpl.sendKeys;
import static util.helpers.actions.CustomWaitImpl.waitUntilElementIsDisplayed;

public class AnimatedCardModule extends BasePage {

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

    public void validateIfAnimatedCardIsFlipped(boolean amexCard, boolean fieldInIframe) throws InterruptedException {
        if (amexCard) {
            PicoContainerHelper.updateInContainer(StoredElement.errorMessage,
                    "Animated card is flipped for AMEX but shouldn't be");
            Assert.assertFalse(PicoContainerHelper.getFromContainer(StoredElement.errorMessage, String.class),
                    checkIfAnimatedCardIsFlipped(fieldInIframe));
        } else {
            PicoContainerHelper.updateInContainer(StoredElement.errorMessage,
                    "Animated card is not flipped but should be");
            Assert.assertTrue(PicoContainerHelper.getFromContainer(StoredElement.errorMessage, String.class),
                    checkIfAnimatedCardIsFlipped(fieldInIframe));
        }
    }

    public boolean checkIfAnimatedCardIsFlipped(boolean fieldInIframe) throws InterruptedException {
        if (checkIfBrowserNameStartWith("IE"))
            Thread.sleep(2000);
        if (fieldInIframe)
            switchToIframe(FieldType.ANIMATED_CARD.getIframeName());
        boolean isFlipped = false;
        String cardSide = getAttribute(SeleniumExecutor.getDriver().findElement(animatedCard), "class");
        if (cardSide.contains("flip_card")) {
            isFlipped = true;
        }
        switchToDefaultIframe();
        return isFlipped;
    }

    public void fillPaymentFormWithoutIFrames(String cardNumber, String expiryDate, String cvv) throws InterruptedException {
        if (checkIfBrowserNameStartWith("IE")) {
            Thread.sleep(1000);
            click(SeleniumExecutor.getDriver().findElement(cardNumberInputField));
            for (char digit : cardNumber.toCharArray()) {
                sendKeys(SeleniumExecutor.getDriver().findElement(cardNumberInputField), String.valueOf(digit));
                Thread.sleep(300);
            }
        } else
            sendKeys(SeleniumExecutor.getDriver().findElement(cardNumberInputField), cardNumber);

        if (checkIfBrowserNameStartWith("IE"))
            Thread.sleep(1000);
        sendKeys(SeleniumExecutor.getDriver().findElement(expirationDateInputField), expiryDate);
        if (cvv != null)
            sendKeys(SeleniumExecutor.getDriver().findElement(cvcInputField), cvv);
    }

    public String getCardTypeIconFromAnimatedCardText(boolean fieldInIframe) {
        if (fieldInIframe)
            switchToIframe(FieldType.ANIMATED_CARD.getIframeName());
        String cardLogo = getAttribute(SeleniumExecutor.getDriver().findElement(cardTypeLogoFromAnimatedCard), "alt");
        switchToDefaultIframe();
        return cardLogo;
    }

    public void validateIfCardTypeIconWasAsExpected(String expectedCardIcon, boolean fieldInIframe) {
        String cardIcon = getCardTypeIconFromAnimatedCardText(fieldInIframe);
        PicoContainerHelper.updateInContainer(StoredElement.errorMessage, " Card type icon is not correct, should be "
                + expectedCardIcon + " but was: " + cardIcon);
        Assert.assertEquals(PicoContainerHelper.getFromContainer(StoredElement.errorMessage, String.class),
                expectedCardIcon, cardIcon);
    }

    public void validateIfAllProvidedDataOnAnimatedCardWasAsExpected(String cardNumber, String expirationDate,
                                                                     String cvc, boolean fieldInIframe) {
        validateIfProvidedDataOnAnimatedCardWasAsExpected(FieldType.CARD_NUMBER, cardNumber, fieldInIframe);
        validateIfProvidedDataOnAnimatedCardWasAsExpected(FieldType.EXPIRY_DATE, expirationDate, fieldInIframe);
        if (cvc != null)
            validateIfProvidedDataOnAnimatedCardWasAsExpected(FieldType.CVC, cvc, fieldInIframe);
    }

    public void validateIfProvidedDataOnAnimatedCardWasAsExpected(FieldType fieldType, String expectedData, boolean fieldInIframe) {
        String animatedCardData = getDataFromAnimatedCreditCard(fieldType, fieldInIframe);
        PicoContainerHelper.updateInContainer(StoredElement.errorMessage,
                fieldType.toString() + " data from animated credit card is not correct, should be " + expectedData
                        + " but was: " + animatedCardData);
        Assert.assertEquals(PicoContainerHelper.getFromContainer(StoredElement.errorMessage, String.class),
                expectedData, animatedCardData);
    }

    public String getDataFromAnimatedCreditCard(FieldType fieldType, boolean fieldInIframe) {
        if (fieldInIframe)
            switchToIframe(FieldType.ANIMATED_CARD.getIframeName());
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
        switchToDefaultIframe();
        return data;
    }

    public void changeFieldFocus() {
        SeleniumExecutor.getDriver().findElement(cardNumberInputField).click();
    }

    public boolean waitUntilPageIsLoaded() throws InterruptedException {
        return waitUntilElementIsDisplayed(animatedCard, 8);
    }
}
