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
import static util.JsonHandler.getTranslationFromJson;

import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import util.PicoContainerHelper;
import util.SeleniumExecutor;
import util.enums.CardFieldType;
import util.enums.MerchantFieldType;
import util.enums.PaymentType;
import util.enums.StoredElement;

import java.io.IOException;

public class PaymentPage extends BasePage {

    // iFrame locators
    private String cardNumberFrameName = "st-card-number-iframe";
    private String cvcFrameName = "st-security-code-iframe";
    private String expirationDateFrameName = "st-expiration-date-iframe";
    private String animatedCardFrameName = "st-animated-card-iframe";
    private String notificationFrameName = "st-notification-frame-iframe";
    private String cardinalFrame = "Cardinal-CCA-IFrame";

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

    private By notificationFrame = By.id("st-notification-frame");
    private By cardinalCommerceAuthModal = By.id("authWindow");

    // paymentMethods
    private By payMockButton = By.xpath("//button[@type='submit']");
    private By visaCheckoutMockButton = By.id("v-button");
    private By applePay = By.id("st-apple-pay");

    // Labels
    private By cardNumberLabel = By.xpath("//label[@for='st-card-number-input']");
    private By expirationDateLabel = By.xpath("//label[@for='st-expiration-date-input']");
    private By securityCodeLabel = By.xpath("//label[@for='st-security-code-input']");
    private By merchantNameLabel = By.xpath("//label[@for='example-form-name']");
    private By merchantEmailLabel = By.xpath("//label[@for='example-form-email']");
    private By merchantPhoneLabel = By.xpath("//label[@for='example-form-phone']");
    private By payButtonLabel = By.xpath("//button[@type='submit']");
    private By animatedCardNumberLabel = By.xpath("//div[@class='st-animated-card__pan']/label");
    private By animatedExpirationDateLabel = By.xpath("//div[@class='st-animated-card__expiration-date']/label");

    public String getPaymentStatusMessage() {
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

    public boolean checkIfElementIsEnabled(String element) {
        boolean isDisabled = true;
        switch (element) {
            case "cardNumberInput":
                switchToIframe(cardNumberFrameName);
                if (getAttribute(SeleniumExecutor.getDriver().findElement(cardNumberInputField), "class").contains("disabled"))
                    isDisabled = false;
                break;
            case "cvcInput":
                switchToIframe(cvcFrameName);
                if (getAttribute(SeleniumExecutor.getDriver().findElement(cvcInputField), "class").contains("disabled"))
                    isDisabled = false;
                break;
            case "expirationDateInput":
                switchToIframe(expirationDateFrameName);
                if (getAttribute(SeleniumExecutor.getDriver().findElement(expirationDateInputField), "class").contains("disabled"))
                    isDisabled = false;
                break;
            case "submitButton":
                if (getAttribute(SeleniumExecutor.getDriver().findElement(payMockButton), "class").contains("disabled"))
                    isDisabled = false;
                break;
        }
        switchToDefaultIframe();
        return isDisabled;
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
        case animatedCard:
            switchToIframe(animatedCardFrameName);
            break;
        case notificationFrame:
            switchToIframe(notificationFrameName);
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
        ((JavascriptExecutor) SeleniumExecutor.getDriver()).executeScript(
                "document.getElementById('" + inputLocator.toString().substring(7) + "').value='" + value + "'");
        ((JavascriptExecutor) SeleniumExecutor.getDriver()).executeScript("document.getElementById('"
                + inputLocator.toString().substring(7) + "').dispatchEvent(new Event('input'))");
    }

    public void fillAllCardData(String cardNumber, String expiryDate, String cvc) {
        if (System.getProperty("device") != null && System.getProperty("device").startsWith("i")) {
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
                waitForElementDisplayed(SeleniumExecutor.getDriver().findElement(cardNumberInputField));
                enterTextByJavaScript(cardNumberInputField,value);
                break;
            case cvc:
                waitForElementDisplayed(SeleniumExecutor.getDriver().findElement(cvcInputField));
                enterTextByJavaScript(cvcInputField, value);
                break;
            case expiryDate:
                waitForElementDisplayed(SeleniumExecutor.getDriver().findElement(expirationDateInputField));
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

    public boolean checkIfFieldIsHighlighted(CardFieldType fieldType) {
        boolean highlight = false;
        String className = "";
        switchToFrameByFieldType(fieldType);
        switch (fieldType) {
            case number:
                className = getAttribute(SeleniumExecutor.getDriver().findElement(cardNumberInputField),"class");
                break;
            case cvc:
                className = getAttribute(SeleniumExecutor.getDriver().findElement(cvcInputField),"class");
                break;
            case expiryDate:
                className = getAttribute(SeleniumExecutor.getDriver().findElement(expirationDateInputField),"class");
                break;
        }

        if(className.contains("error-field")){
            highlight = true;
        }
        switchToDefaultIframe();
        return highlight;
    }


    public boolean checkIfCardinalAuthModalIsDisplayed() throws InterruptedException {
        waitUntilModalIsDisplayed(cardinalCommerceAuthModal);
        switchToIframe(cardinalFrame);
        return isElementDisplayed(cardinalCommerceAuthModal);
    }

    public String getElementTranslation(CardFieldType fieldType, By element) {
        if (fieldType != null)
            switchToFrameByFieldType(fieldType);

        String translation = getText(SeleniumExecutor.getDriver().findElement(element));
        switchToDefaultIframe();
        return translation;
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

    public void validateIfAllProvidedDataOnAnimatedCardWasAsExpected(String cardNumber, String expirationDate,
            String cvc) {
        validateIfProvidedDataOnAnimatedCardWasAsExpected(CardFieldType.number, cardNumber);
        validateIfProvidedDataOnAnimatedCardWasAsExpected(CardFieldType.expiryDate, expirationDate);
        validateIfProvidedDataOnAnimatedCardWasAsExpected(CardFieldType.cvc, cvc);
    }

    public void validateIfFieldIsHighlighted(CardFieldType fieldType) {
        PicoContainerHelper.updateInContainer(StoredElement.errorMessage,
                " Field is not highlighted but should be");
        Assert.assertTrue(PicoContainerHelper.getFromContainer(StoredElement.errorMessage, String.class),
                checkIfFieldIsHighlighted(fieldType));
    }

    public void validateIfElementIsEnabledAfterPayment(String element) {
        PicoContainerHelper.updateInContainer(StoredElement.errorMessage,
                element + " should be enabled but it isn't ");
        Assert.assertTrue(PicoContainerHelper.getFromContainer(StoredElement.errorMessage, String.class),
                checkIfElementIsEnabled(element));
    }

    public void validateIfNotificationFrameIsDisplayed() {
        PicoContainerHelper.updateInContainer(StoredElement.errorMessage,
                "Notification frame is not displayed but should be");
        Assert.assertTrue(PicoContainerHelper.getFromContainer(StoredElement.errorMessage, String.class),
                getPaymentStatusMessage() != null);
    }

    public void validateIfElelemtTranslationWasAsExpected(String translation, CardFieldType fieldType, By element) {
        PicoContainerHelper.updateInContainer(StoredElement.errorMessage, " Translation is not correct, should be "
                + translation + " but was: " + getElementTranslation(fieldType, element));
        Assert.assertEquals(PicoContainerHelper.getFromContainer(StoredElement.errorMessage, String.class), translation,
                getElementTranslation(fieldType, element));
    }

    // ToDo - Complete translations key for: PayButton, name, email, phone
    public void validateIfLabelsTranslationWasAsExpected(String translation) throws IOException, ParseException {
        validateIfElelemtTranslationWasAsExpected(getTranslationFromJson("Card number", translation),
                CardFieldType.number, cardNumberLabel);
        validateIfElelemtTranslationWasAsExpected(getTranslationFromJson("Expiration date", translation),
                CardFieldType.expiryDate, expirationDateLabel);
        validateIfElelemtTranslationWasAsExpected(getTranslationFromJson("Security code", translation),
                CardFieldType.cvc, securityCodeLabel);
        // validateIfElelemtTranslationWasAsExpected(getTranslationFromJson("",
        // translation), null, merchantNameLabel);
        // validateIfElelemtTranslationWasAsExpected(getTranslationFromJson("",
        // translation), null, merchantEmailLabel);
        // validateIfElelemtTranslationWasAsExpected(getTranslationFromJson("",
        // translation), null, merchantPhoneLabel);
        // validateIfElelemtTranslationWasAsExpected(getTranslationFromJson("",
        // translation), null, payButtonLabel);
        validateIfElelemtTranslationWasAsExpected(getTranslationFromJson("Card number", translation).toUpperCase(),
                CardFieldType.animatedCard, animatedCardNumberLabel);
        validateIfElelemtTranslationWasAsExpected(getTranslationFromJson("Expiration date", translation).toUpperCase(),
                CardFieldType.animatedCard, animatedExpirationDateLabel);
    }

    // ToDo - Complete translations for: Decline, Unknown error, Unauthenticated,
    // Ivalid field
    public void validateIfPaymentStatusTranslationWasAsExpected(String paymentStatus, String translation)
            throws IOException, ParseException {
        switch (paymentStatus) {
        case "Success":
            validateIfElelemtTranslationWasAsExpected(
                    getTranslationFromJson("Payment has been successfully processed", translation),
                    CardFieldType.notificationFrame, notificationFrame);
            break;
        case "Error":
            validateIfElelemtTranslationWasAsExpected(getTranslationFromJson("An error occurred", translation),
                    CardFieldType.notificationFrame, notificationFrame);
            break;
        case "Cancel":
            validateIfElelemtTranslationWasAsExpected(getTranslationFromJson("Payment has been cancelled", translation),
                    CardFieldType.notificationFrame, notificationFrame);
            break;
        case "Decline":
            validateIfElelemtTranslationWasAsExpected(getTranslationFromJson("", translation),
                    CardFieldType.notificationFrame, notificationFrame);
            break;
        case "Unknown error":
            validateIfElelemtTranslationWasAsExpected(getTranslationFromJson("", translation),
                    CardFieldType.notificationFrame, notificationFrame);
            break;
        case "Unauthenticated":
            validateIfElelemtTranslationWasAsExpected(getTranslationFromJson("", translation),
                    CardFieldType.notificationFrame, notificationFrame);
            break;
        case "Invalid field":
            validateIfElelemtTranslationWasAsExpected(getTranslationFromJson("", translation),
                    CardFieldType.notificationFrame, notificationFrame);
            break;
        }
    }

    public void validateIfValidationMessageUnderFieldWasAsExpected(String fieldType, String translation,
            String translationKey) throws IOException, ParseException {
        switch (fieldType) {
        case "number":
            validateIfElelemtTranslationWasAsExpected(getTranslationFromJson(translationKey, translation),
                    CardFieldType.number, creditCardFieldValidationMessage);
            break;
        case "expiryDate":
            validateIfElelemtTranslationWasAsExpected(getTranslationFromJson(translationKey, translation),
                    CardFieldType.expiryDate, expirationDateFieldValidationMessage);
            break;
        case "cvc":
            validateIfElelemtTranslationWasAsExpected(getTranslationFromJson(translationKey, translation),
                    CardFieldType.cvc, cvcFieldValidationMessage);
            break;
        }
    }
}