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
import util.enums.FieldType;
import util.enums.MerchantFieldType;
import util.enums.PaymentType;
import util.enums.StoredElement;

import java.io.IOException;

public class PaymentPage extends BasePage {

    private AnimatedCardModule animatedCardModule = new AnimatedCardModule();

    // iFrame locators
    private String cardNumberFrameName = "st-card-number-iframe";
    private String cvcFrameName = "st-security-code-iframe";
    private String expirationDateFrameName = "st-expiration-date-iframe";

    private String notificationFrameName = "st-notification-frame-iframe";

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


    // Immediate payment
    private By immediatePaymentErrorMessage = By.id("errormessage");
    private By immediatePaymentErrorCode = By.id("errorcode");

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

    public String getTextFromImmediatePaymentPage(String status) {
        String text = "";
        switch (status) {
            case "message":
                text = getText(SeleniumExecutor.getDriver().findElement(immediatePaymentErrorMessage));
                break;
            case "code":
                text = getText(SeleniumExecutor.getDriver().findElement(immediatePaymentErrorCode));
                break;
        }
        return text;
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

    public void switchToFrameByFieldType(FieldType fieldType) {
        switch (fieldType) {
            case CARD_NUMBER:
                switchToIframe(cardNumberFrameName);
                break;
            case CVC:
                switchToIframe(cvcFrameName);
                break;
            case EXPIRY_DATE:
                switchToIframe(expirationDateFrameName);
                break;
            case ANIMATED_CARD:
                switchToIframe(animatedCardModule.animatedCardFrameName);
                break;
            case NOTIFICATION_FRAME:
                switchToIframe(notificationFrameName);
                break;
        }
    }

    public void choosePaymentMethodWithMock(PaymentType paymentType) {
        switch (paymentType) {
            case VISA_CHECKOUT:
                click(SeleniumExecutor.getDriver().findElement(visaCheckoutMockButton));
                break;
            case APPLE_PAY:
                click(SeleniumExecutor.getDriver().findElement(applePay));
                break;
            case CARDINAL_COMMERCE:
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

    public void fillAllCardData(String cardNumber, String expiryDate, String cvc) throws InterruptedException {
        if (System.getProperty("device") != null && System.getProperty("device").startsWith("i")) {
            fillCreditCardInputFieldByJavaScript(FieldType.CARD_NUMBER, cardNumber);
            fillCreditCardInputFieldByJavaScript(FieldType.EXPIRY_DATE, expiryDate);
            scrollToBottomOfPage();
            fillCreditCardInputFieldByJavaScript(FieldType.CVC, cvc);
        } else {
            fillCreditCardInputField(FieldType.CARD_NUMBER, cardNumber);
            fillCreditCardInputField(FieldType.EXPIRY_DATE, expiryDate);
            scrollToBottomOfPage();
            fillCreditCardInputField(FieldType.CVC, cvc);
        }
    }

    public void fillAllMerchantData(String name, String email, String phone) {
        fillMerchantInputField(MerchantFieldType.NAME, name);
        fillMerchantInputField(MerchantFieldType.EMAIL, email);
        fillMerchantInputField(MerchantFieldType.PHONE, phone);
    }

    public void fillCreditCardInputField(FieldType fieldType, String value) throws InterruptedException {
        switchToFrameByFieldType(fieldType);
        switch (fieldType) {
            case CARD_NUMBER:
                sendKeys(SeleniumExecutor.getDriver().findElement(cardNumberInputField), value);
                break;
            case CVC:
                sendKeys(SeleniumExecutor.getDriver().findElement(cvcInputField), value);
                break;
            case EXPIRY_DATE:
                if (System.getProperty("browser") != null && System.getProperty("browser").startsWith("IE")) {
                    sendKeys(SeleniumExecutor.getDriver().findElement(expirationDateInputField), value.substring(0, 2));
                    Thread.sleep(1000);
                    sendKeys(SeleniumExecutor.getDriver().findElement(expirationDateInputField), value.substring(3, 5));
                } else
                    sendKeys(SeleniumExecutor.getDriver().findElement(expirationDateInputField), value);
                break;
        }
        switchToDefaultIframe();
    }

    public void fillCreditCardInputFieldByJavaScript(FieldType fieldType, String value) {
        switchToFrameByFieldType(fieldType);
        switch (fieldType) {
            case CARD_NUMBER:
                waitForElementDisplayed(SeleniumExecutor.getDriver().findElement(cardNumberInputField));
                enterTextByJavaScript(cardNumberInputField, value);
                break;
            case CVC:
                waitForElementDisplayed(SeleniumExecutor.getDriver().findElement(cvcInputField));
                enterTextByJavaScript(cvcInputField, value);
                break;
            case EXPIRY_DATE:
                waitForElementDisplayed(SeleniumExecutor.getDriver().findElement(expirationDateInputField));
                enterTextByJavaScript(expirationDateInputField, value);
                break;
        }
        switchToDefaultIframe();
    }

    public void fillMerchantInputField(MerchantFieldType fieldType, String value) {
        switch (fieldType) {
            case NAME:
                sendKeys(SeleniumExecutor.getDriver().findElement(merchantName), value);
                break;
            case EMAIL:
                sendKeys(SeleniumExecutor.getDriver().findElement(merchantEmail), value);
                break;
            case PHONE:
                sendKeys(SeleniumExecutor.getDriver().findElement(merchantPhone), value);
                break;
        }
    }

    public String getCreditCardFieldValidationMessage(FieldType fieldType) {
        String message = "";
        switchToFrameByFieldType(fieldType);
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
        switchToDefaultIframe();
        return message;
    }

    public boolean checkIfFieldIsHighlighted(FieldType fieldType) {
        boolean highlight = false;
        String className = "";
        switchToFrameByFieldType(fieldType);
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
        switchToDefaultIframe();
        return highlight;
    }

    public boolean checkIfMerchantFieldIsHighlighted(MerchantFieldType fieldType) {
        boolean ishighlighted = false;
        String className = "";
        switch (fieldType) {
            case NAME:
                className = getAttribute(SeleniumExecutor.getDriver().findElement(merchantName), "class");
                break;
            case EMAIL:
                className = getAttribute(SeleniumExecutor.getDriver().findElement(merchantEmail), "class");
                break;
            case PHONE:
                className = getAttribute(SeleniumExecutor.getDriver().findElement(merchantPhone), "class");
                break;
        }
        if (className.contains("error-field"))
            ishighlighted = true;

        return ishighlighted;
    }

    public String getElementTranslation(FieldType fieldType, By element) {
        if (fieldType != null)
            switchToFrameByFieldType(fieldType);

        String translation = getText(SeleniumExecutor.getDriver().findElement(element));
        switchToDefaultIframe();
        return translation;
    }

    public void validateIfFieldValidationMessageWasAsExpected(FieldType fieldType, String expectedMessage) {
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

    public void validateIfColorOfNotificationFrameWasAsExpected(String color) {
        PicoContainerHelper.updateInContainer(StoredElement.errorMessage,
                " Color of notification frame is not correct, should be " + color + " but was: "
                        + getColorOfNotificationFrame());
        Assert.assertEquals(PicoContainerHelper.getFromContainer(StoredElement.errorMessage, String.class), color,
                getColorOfNotificationFrame());
    }

    public void validateIfFieldIsHighlighted(FieldType fieldType) {
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

    public void validateIfElelemtTranslationWasAsExpected(String translation, FieldType fieldType, By element) {
        PicoContainerHelper.updateInContainer(StoredElement.errorMessage, " Translation is not correct, should be "
                + translation + " but was: " + getElementTranslation(fieldType, element));
        Assert.assertEquals(PicoContainerHelper.getFromContainer(StoredElement.errorMessage, String.class), translation,
                getElementTranslation(fieldType, element));
    }

    // ToDo - Complete translations key for: PayButton, name, email, phone
    public void validateIfLabelsTranslationWasAsExpected(String translation) throws IOException, ParseException {
        validateIfElelemtTranslationWasAsExpected(getTranslationFromJson("Card number", translation),
                FieldType.CARD_NUMBER, cardNumberLabel);
        validateIfElelemtTranslationWasAsExpected(getTranslationFromJson("Expiration date", translation),
                FieldType.EXPIRY_DATE, expirationDateLabel);
        validateIfElelemtTranslationWasAsExpected(getTranslationFromJson("Security code", translation),
                FieldType.CVC, securityCodeLabel);
        // validateIfElelemtTranslationWasAsExpected(getTranslationFromJson("",
        // translation), null, merchantNameLabel);
        // validateIfElelemtTranslationWasAsExpected(getTranslationFromJson("",
        // translation), null, merchantEmailLabel);
        // validateIfElelemtTranslationWasAsExpected(getTranslationFromJson("",
        // translation), null, merchantPhoneLabel);
        // validateIfElelemtTranslationWasAsExpected(getTranslationFromJson("",
        // translation), null, payButtonLabel);
        validateIfElelemtTranslationWasAsExpected(getTranslationFromJson("Card number", translation).toUpperCase(),
                FieldType.ANIMATED_CARD, animatedCardModule.animatedCardNumberLabel);
        validateIfElelemtTranslationWasAsExpected(getTranslationFromJson("Expiration date", translation).toUpperCase(),
                FieldType.ANIMATED_CARD, animatedCardModule.animatedExpirationDateLabel);
    }

    // ToDo - Complete translations for: Decline, Unknown error, Unauthenticated,
    // Ivalid field
    public void validateIfPaymentStatusTranslationWasAsExpected(String paymentStatus, String translation)
            throws IOException, ParseException {
        switch (paymentStatus) {
            case "Success":
                validateIfElelemtTranslationWasAsExpected(
                        getTranslationFromJson("Payment has been successfully processed", translation),
                        FieldType.NOTIFICATION_FRAME, notificationFrame);
                break;
            case "Error":
                validateIfElelemtTranslationWasAsExpected(getTranslationFromJson("An error occurred", translation),
                        FieldType.NOTIFICATION_FRAME, notificationFrame);
                break;
            case "Cancel":
                validateIfElelemtTranslationWasAsExpected(getTranslationFromJson("Payment has been cancelled", translation),
                        FieldType.NOTIFICATION_FRAME, notificationFrame);
                break;
            case "Decline":
                validateIfElelemtTranslationWasAsExpected(getTranslationFromJson("", translation),
                        FieldType.NOTIFICATION_FRAME, notificationFrame);
                break;
            case "Unknown error":
                validateIfElelemtTranslationWasAsExpected(getTranslationFromJson("", translation),
                        FieldType.NOTIFICATION_FRAME, notificationFrame);
                break;
            case "Unauthenticated":
                validateIfElelemtTranslationWasAsExpected(getTranslationFromJson("", translation),
                        FieldType.NOTIFICATION_FRAME, notificationFrame);
                break;
            case "Invalid field":
                validateIfElelemtTranslationWasAsExpected(getTranslationFromJson("", translation),
                        FieldType.NOTIFICATION_FRAME, notificationFrame);
                break;
        }
    }

    public void validateIfValidationMessageUnderFieldWasAsExpected(FieldType fieldType, String translation,
                                                                   String translationKey) throws IOException, ParseException {
        switch (fieldType) {
            case CARD_NUMBER:
                validateIfElelemtTranslationWasAsExpected(getTranslationFromJson(translationKey, translation),
                        FieldType.CARD_NUMBER, creditCardFieldValidationMessage);
                break;
            case EXPIRY_DATE:
                validateIfElelemtTranslationWasAsExpected(getTranslationFromJson(translationKey, translation),
                        FieldType.EXPIRY_DATE, expirationDateFieldValidationMessage);
                break;
            case CVC:
                validateIfElelemtTranslationWasAsExpected(getTranslationFromJson(translationKey, translation),
                        FieldType.CVC, cvcFieldValidationMessage);
                break;
        }
    }

    public void validateIfMessageFromImmediateWasAsExpected(String expectedMessage) {
        PicoContainerHelper.updateInContainer(StoredElement.errorMessage,
                " payment status message is not correct, should be " + expectedMessage + " but was: "
                        + getTextFromImmediatePaymentPage("message"));
        Assert.assertEquals(PicoContainerHelper.getFromContainer(StoredElement.errorMessage, String.class),
                expectedMessage, getTextFromImmediatePaymentPage("message"));
    }

    public void validateIfErrorCodeFromImmediateWasAsExpected(String expectedCode) {
        PicoContainerHelper.updateInContainer(StoredElement.errorMessage,
                " payment status message is not correct, should be " + expectedCode + " but was: "
                        + getTextFromImmediatePaymentPage("code"));
        Assert.assertEquals(PicoContainerHelper.getFromContainer(StoredElement.errorMessage, String.class),
                expectedCode, getTextFromImmediatePaymentPage("code"));
    }
}