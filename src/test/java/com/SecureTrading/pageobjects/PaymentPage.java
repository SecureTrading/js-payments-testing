package com.SecureTrading.pageobjects;

import static util.helpers.IframeHandler.switchToDefaultIframe;
import static util.helpers.IframeHandler.switchToIframe;
import static util.helpers.WebElementHandler.isElementDisplayed;
import static util.helpers.actions.CustomClickImpl.click;
import static util.helpers.actions.CustomGetAttributeImpl.getAttribute;
import static util.helpers.actions.CustomGetTextImpl.getText;
import static util.helpers.actions.CustomScrollImpl.scrollToBottomOfPage;
import static util.helpers.actions.CustomSendKeysImpl.sendKeys;
import static util.helpers.actions.CustomWaitImpl.*;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import util.PicoContainerHelper;
import util.SeleniumExecutor;
import util.enums.CardFieldType;
import util.enums.MerchantFieldType;
import util.enums.PaymentType;
import util.enums.StoredElement;

public class PaymentPage extends BasePage {

    // iFrame locators
    private String cardNumberFrameName = "st-card-number-iframe";
    private String cvcFrameName = "st-security-code-iframe";
    private String expirationDateFrameName = "st-expiration-date-iframe";
    private String animatedCardFrameName = "st-animated-card-iframe";
    private String notificationFrameName = "st-notification-frame-iframe";
    private String cardinalFrame = "Cardinal-CCA-IFrame";
    private By cardinalCollector = By.id("Cardinal-collector");

    // input fields locators
    private By merchantName = By.id("example-form-name");
    private By merchantEmail = By.id("example-form-email");
    private By merchantPhone = By.id("example-form-phone");

    // Credit card form
    private By cardNumberInputField = By.id("st-card-number-input");
    private By cvcInputField = By.id("st-security-code-input");
    private By expirationDateInputField = By.id("st-expiration-date-input");

    // Fields validation messages
    private By creditCardFieldValidationMessage = By.id("st-card-number-message");
    private By cvcFieldValidationMessage = By.id("st-security-code-message");
    private By expirationDateFieldValidationMessage = By.id("st-expiration-date-message");

    // animated credit card
    private By animatedCard = By.id("st-animated-card");
    private By creditCardNumberFromAnimatedCard = By.id("st-animated-card-number");
    private By cvcBackSideAnimatedCard = By.id("st-animated-card-security-code");
    private By cvcFrontSideAnimatedCard = By.id("st-animated-card-security-code-front-field");
    private By expirationDateFromAnimatedCard = By.id("st-animated-card-expiration-date");
    private By cardTypeLogoFromAnimatedCard = By.id("st-payment-logo");

    private By notificationFrame = By.id("st-notification-frame"); // TODO was called paymentStatusMessage
    private By cardinalCommerceAuthModal = By.id("authWindow");

    // paymentMethods
    private By payMockButton = By.xpath("//button[@type='submit']");
    private By visaCheckoutMockButton = By.id("v-button");
    private By applePay = By.id("st-apple-pay");

    public String getPaymentStatusMessage() { // TODO was called public String getTextFromNotificationFrame() {
        switchToIframe(notificationFrameName);
        String statusMessage = getText(SeleniumExecutor.getDriver().findElement(notificationFrame));
        switchToDefaultIframe();
        return statusMessage;
    }

    public String getColorOfNotificationFrame() {
        switchToIframe(notificationFrameName);
        String frameColor = getAttribute(SeleniumExecutor.getDriver().findElement(notificationFrame),
                "data-notification-color");
        switchToDefaultIframe();
        return frameColor;
    }

    // Get info from animated credit card
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

    public void switchToFrameByFieldType(CardFieldType fieldType) {
        switch (fieldType) {
        case number:
            switchToIframe(cardNumberFrameName);
            break;
        case cvc:
            switchToIframe(cvcFrameName);
            break;
        case expiryDate:
            switchToIframe(expirationDateFrameName);
            break;
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

    public void choosePaymentMethodWithMock(PaymentType paymentType) {
        switch (paymentType) {
        case visaCheckout:
            click(SeleniumExecutor.getDriver().findElement(visaCheckoutMockButton));
            break;
        case applePay:
            click(SeleniumExecutor.getDriver().findElement(applePay));
            break;
        case cardinalCommerce:
            click(SeleniumExecutor.getDriver().findElement(payMockButton));
            break;
        }
    }

    public void enterTextByJavaScript(By inputLocator, String value) {
        ((JavascriptExecutor) SeleniumExecutor.getDriver())
                .executeScript("document.getElementById('" +inputLocator.toString().substring(7)+ "').value='" +value+ "'");
        ((JavascriptExecutor) SeleniumExecutor.getDriver())
                .executeScript("document.getElementById('" +inputLocator.toString().substring(7)+ "').dispatchEvent(new Event('input'))");
    }

    public void fillAllCardData(String cardNumber, String expiryDate, String cvc) {
        if(System.getProperty("device") != null && System.getProperty("device").startsWith("i")){
            fillCreditCardInputFieldByJavaScript(CardFieldType.number, cardNumber);
            fillCreditCardInputFieldByJavaScript(CardFieldType.expiryDate, expiryDate);
            scrollToBottomOfPage();
            fillCreditCardInputFieldByJavaScript(CardFieldType.cvc, cvc);
        } else {
            fillCreditCardInputField(CardFieldType.number, cardNumber);
            fillCreditCardInputField(CardFieldType.expiryDate, expiryDate);
            scrollToBottomOfPage();
            fillCreditCardInputField(CardFieldType.cvc, cvc);
        }
    }

    public void fillAllMerchantData(String name, String email, String phone) {
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

    public void fillCreditCardInputFieldByJavaScript(CardFieldType fieldType, String value) {
        switchToFrameByFieldType(fieldType);
        switch (fieldType) {
            case number:
                enterTextByJavaScript(cardNumberInputField,value);
                break;
            case cvc:
                enterTextByJavaScript(cvcInputField, value);
                break;
            case expiryDate:
                enterTextByJavaScript(expirationDateInputField, value);
                break;
        }
        switchToDefaultIframe();
    }

    public void fillMerchantInputField(MerchantFieldType fieldType, String value) {
        switch (fieldType) {
        case name:
            sendKeys(SeleniumExecutor.getDriver().findElement(merchantName), value);
            break;
        case email:
            sendKeys(SeleniumExecutor.getDriver().findElement(merchantEmail), value);
            break;
        case phone:
            sendKeys(SeleniumExecutor.getDriver().findElement(merchantPhone), value);
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

    public boolean checkIfCardinalAuthModalIsDisplayed() throws InterruptedException {
        waitUntilModalIsDisplayed(cardinalCommerceAuthModal);
        switchToIframe(cardinalFrame);
        return isElementDisplayed(cardinalCommerceAuthModal);
    }

    public void waitUntilNetworwTrafficIsCompleted() throws InterruptedException {
        waitUntilNetworkIsCompleted(cardinalCollector);
    }

    public void validateIfFieldValidationMessageWasAsExpected(CardFieldType fieldType, String expectedMessage) {
        PicoContainerHelper.updateInContainer(StoredElement.errorMessage,
                fieldType.toString() + " error message is not correct, should be " + expectedMessage + " but was: "
                        + getCreditCardFieldValidationMessage(fieldType));
        Assert.assertEquals(PicoContainerHelper.getFromContainer(StoredElement.errorMessage, String.class),
                expectedMessage, getCreditCardFieldValidationMessage(fieldType));
    }

    public void validateIfPaymentStatusMessageWasAsExpected(String expectedMessage) {
        PicoContainerHelper.updateInContainer(StoredElement.errorMessage,
                " payment status message is not correct, should be " + expectedMessage + " but was: "
                        + getPaymentStatusMessage());
        Assert.assertEquals(PicoContainerHelper.getFromContainer(StoredElement.errorMessage, String.class),
                expectedMessage, getPaymentStatusMessage());
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

    public void validateIfColorOfNotificationFrameWasAsExpected(String color) {
        PicoContainerHelper.updateInContainer(StoredElement.errorMessage,
                " Color of notification frame is not correct, should be " + color + " but was: "
                        + getColorOfNotificationFrame());
        Assert.assertEquals(PicoContainerHelper.getFromContainer(StoredElement.errorMessage, String.class), color,
                getColorOfNotificationFrame());
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

    public void validateIfCardinalCommerceAuthenticationModalIsDisplayed() throws InterruptedException {
        PicoContainerHelper.updateInContainer(StoredElement.errorMessage,
                " Cardinal commerce authentication modal is not displayed");
        Assert.assertTrue(PicoContainerHelper.getFromContainer(StoredElement.errorMessage, String.class),
                checkIfCardinalAuthModalIsDisplayed());
    }

    public void validateIfAllProvidedDataOnAnimatedCardWasAsExpected(String cardNumber, String expirationDate,
            String cvc) {
        validateIfProvidedDataOnAnimatedCardWasAsExpected(CardFieldType.number, cardNumber);
        validateIfProvidedDataOnAnimatedCardWasAsExpected(CardFieldType.expiryDate, expirationDate);
        validateIfProvidedDataOnAnimatedCardWasAsExpected(CardFieldType.cvc, cvc);
    }
}