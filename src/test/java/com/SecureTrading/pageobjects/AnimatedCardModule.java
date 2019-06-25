package com.SecureTrading.pageobjects;

import org.junit.Assert;
import org.openqa.selenium.By;
import util.PicoContainerHelper;
import util.SeleniumExecutor;
import util.enums.CardFieldType;
import util.enums.StoredElement;

import static util.helpers.IframeHandler.switchToDefaultIframe;
import static util.helpers.IframeHandler.switchToIframe;
import static util.helpers.actions.CustomGetAttributeImpl.getAttribute;
import static util.helpers.actions.CustomGetTextImpl.getText;

public class AnimatedCardModule {

    //iFrame
    public String animatedCardFrameName = "st-animated-card-iframe";

    // animated credit card
    private By animatedCard = By.id("st-animated-card");
    private By creditCardNumberFromAnimatedCard = By.id("st-animated-card-number");
    private By cvcBackSideAnimatedCard = By.id("st-animated-card-security-code");
    private By cvcFrontSideAnimatedCard = By.id("st-animated-card-security-code-front-field");
    private By expirationDateFromAnimatedCard = By.id("st-animated-card-expiration-date");
    private By cardTypeLogoFromAnimatedCard = By.id("st-payment-logo");

    //labels
    public By animatedCardNumberLabel = By.xpath("//div[@class='st-animated-card__pan']/label");
    public By animatedExpirationDateLabel = By.xpath("//div[@class='st-animated-card__expiration-date']/label");


    public String getCardTypeIconFromAnimatedCardText() {
        switchToIframe(animatedCardFrameName);
        String cardLogo = getAttribute(SeleniumExecutor.getDriver().findElement(cardTypeLogoFromAnimatedCard), "alt");
        switchToDefaultIframe();
        return cardLogo;
    }

    public boolean checkIfAnimatedCardIsFlipped() {
        switchToIframe(animatedCardFrameName);
        boolean isFlipped = false;
        String cardSide = getAttribute(SeleniumExecutor.getDriver().findElement(animatedCard), "class");

        if (cardSide.contains("flip_card")) {
            isFlipped = true;
        }
        switchToDefaultIframe();
        return isFlipped;
    }

    public String getDataFromAnimatedCreditCard(CardFieldType fieldType) {
        String data = "";
        switchToIframe(animatedCardFrameName);
        switch (fieldType) {
            case number:
                data = getText(SeleniumExecutor.getDriver().findElement(creditCardNumberFromAnimatedCard));
                break;
            case cvc:
                if (PicoContainerHelper.getFromContainer(StoredElement.cardType).toString().contains("AMEX")) {
                    data = getText(SeleniumExecutor.getDriver().findElement(cvcFrontSideAnimatedCard));
                } else {
                    data = getText(SeleniumExecutor.getDriver().findElement(cvcBackSideAnimatedCard));
                }
                break;
            case expiryDate:
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

    public void validateIfProvidedDataOnAnimatedCardWasAsExpected(CardFieldType fieldType, String expectedData) {
        PicoContainerHelper.updateInContainer(StoredElement.errorMessage,
                fieldType.toString() + " data from animated credit card is not correct, should be " + expectedData
                        + " but was: " + getDataFromAnimatedCreditCard(fieldType));
        Assert.assertEquals(PicoContainerHelper.getFromContainer(StoredElement.errorMessage, String.class),
                expectedData, getDataFromAnimatedCreditCard(fieldType));
    }

    public void validateIfAnimatedCardIsFlipped(String cardType) {
        if (cardType.equals("AMEX")) {
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
        validateIfProvidedDataOnAnimatedCardWasAsExpected(CardFieldType.number, cardNumber);
        validateIfProvidedDataOnAnimatedCardWasAsExpected(CardFieldType.expiryDate, expirationDate);
        validateIfProvidedDataOnAnimatedCardWasAsExpected(CardFieldType.cvc, cvc);
    }
}
