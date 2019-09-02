package com.SecureTrading.stepdefs;

import static util.MocksHandler.*;
import static util.PropertiesHandler.getProperty;
import static util.JsonHandler.getTranslationFromJson;
import static util.helpers.TestConditionHandler.checkIfBrowserNameStartWith;
import static util.helpers.TestConditionHandler.checkIfScenarioNameContainsText;
import static util.helpers.actions.CustomScrollImpl.scrollToBottomOfPage;
import static util.helpers.actions.CustomScrollImpl.scrollToTopOfPage;

import com.SecureTrading.pageobjects.AnimatedCardModule;
import com.SecureTrading.pageobjects.PaymentPage;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.simple.parser.ParseException;
import util.PicoContainerHelper;
import util.SeleniumExecutor;
import util.enums.*;
import util.enums.responses.*;

import java.io.IOException;

public class PaymentPageSteps {

    private PaymentPage paymentPage;
    private AnimatedCardModule animatedCardModule;

    public PaymentPageSteps() {
        paymentPage = new PaymentPage();
        animatedCardModule = new AnimatedCardModule();
    }

    @Given("^User opens page with payment form$")
    public void userOpensPageWithPaymentForm() throws InterruptedException {
        if (checkIfScenarioNameContainsText("SCENARIO SKIPPED")) {
            System.out.println("Step skipped as iOS system and Safari is required for ApplePay test");
        } else if (checkIfScenarioNameContainsText("skipped JSINIT process")) {
            System.out.println("Step skipped as is not required");
        } else {
            //Accept self signed certificates for Safari purpose
            if (checkIfBrowserNameStartWith("Safari")) {
                SeleniumExecutor.getDriver().get(getProperty(PropertyType.WEBSERVICES_DOMAIN));
                SeleniumExecutor.getDriver().get("https://thirdparty.example.com:8443");
            }
            if (!checkIfScenarioNameContainsText("Immediate")) {
                SeleniumExecutor.getDriver().get(getProperty(PropertyType.BASE_URI));
                //Additional try for IE problems
                if (!SeleniumExecutor.getDriver().getTitle().contains("Secure")) {
                    Thread.sleep(4000);
                    SeleniumExecutor.getDriver().get(getProperty(PropertyType.BASE_URI));
                }
            }
        }
    }

    @When("^User fills payment form with credit card number \"([^\"]*)\", expiration date \"([^\"]*)\" and cvc \"([^\"]*)\"$")
    public void userFillsPaymentFormWithCreditCardNumberCardNumberExpirationDateExpirationDateAndCvcCvc(
            String cardNumber, String expirationDate, String cvc) throws InterruptedException {
        paymentPage.fillAllCardData(cardNumber, expirationDate, cvc);
    }

    @When("^User fills merchant data with name \"([^\"]*)\", email \"([^\"]*)\", phone \"([^\"]*)\"$")
    public void userFillsMerchantDataWithNameEmailPhone(String name, String email, String phone) {
        paymentPage.fillAllMerchantData(name, email, phone);
    }

    @And("^User clicks Pay button$")
    public void userClicksPayButton() {
        paymentPage.choosePaymentMethodWithMock(PaymentType.CARDINAL_COMMERCE);
    }

    @Then("^User will see card icon connected to card type ([^\"]*)$")
    public void userWillSeeCardIconConnectedToCardTypeCardType(String cardType) {
        PicoContainerHelper.updateInContainer(StoredElement.cardType, cardType);
        animatedCardModule.validateIfCardTypeIconWasAsExpected(cardType.toLowerCase());
    }

    @And("^User will see that animated card is flipped, except for \"([^\"]*)\"$")
    public void userWillSeeThatAnimatedCardIsFlippedExceptFor(String cardType) throws InterruptedException {
        animatedCardModule.validateIfAnimatedCardIsFlipped(cardType.equals("AMEX"));
    }

    @When("^User fills payment form with incorrect or missing data: card number ([^\"]*), expiration date ([^\"]*) and cvc ([^\"]*)$")
    public void userFillsPaymentFormWithIncorrectOrMissingDataCardNumberCardNumberExpirationDateExpirationAndCvcCvc
            (
                    String cardNumber, String expirationDate, String cvc) throws InterruptedException {
        paymentPage.fillAllCardData(cardNumber, expirationDate, cvc);
    }

    @And("^User will see \"([^\"]*)\" message under field: (.*)$")
    public void userWillSeeMessageUnderField(String message, FieldType fieldType) {
        paymentPage.validateIfFieldValidationMessageWasAsExpected(fieldType, message);
    }

    @And("^User will see the same provided data on animated credit card ([^\"]*), ([^\"]*) and ([^\"]*)$")
    public void userWillSeeTheSameProvidedDataOnAnimatedCreditCardCardNumberExpirationDateAndCvc(String cardNumber,
                                                                                                 String expirationDate, String cvc) {
        animatedCardModule.validateIfAllProvidedDataOnAnimatedCardWasAsExpected(cardNumber, expirationDate, cvc);
    }

    @Then("^User will see payment status information: \"([^\"]*)\"$")
    public void userWillSeePaymentStatusInformationPaymentStatusMessage(String paymentStatusMessage) throws InterruptedException {
        if (checkIfScenarioNameContainsText("SCENARIO SKIPPED")) {
            System.out.println("Step skipped as iOS system and Safari is required for ApplePay test");
        } else {
            scrollToTopOfPage();
            paymentPage.validateIfPaymentStatusMessageWasAsExpected(paymentStatusMessage);
        }
    }

    @Then("^User will see validation message \"([^\"]*)\" under all fields$")
    public void userWillSeeValidationMessageUnderAllFields(String message) {
        paymentPage.validateIfFieldValidationMessageWasAsExpected(FieldType.CARD_NUMBER, message);
        paymentPage.validateIfFieldValidationMessageWasAsExpected(FieldType.EXPIRY_DATE, message);
        paymentPage.validateIfFieldValidationMessageWasAsExpected(FieldType.CVC, message);
    }

    @And("^THREEDQUERY response set to (.*)$")
    public void threedqueryResponseSetTo(TDQresponsne threedquery) {
        stubSTRequestType(threedquery.getMockJson(), RequestType.THREEDQUERY);
    }

    @And("^ACS response set to (.*)$")
    public void acsResponseSetTo(ACSresponse response) {
        switch (response) {
            case OK:
                stubPaymentStatus(PropertyType.CC_MOCK_ACS_URI, response.getMockJson());
                break;
            case NOACTION:
                stubPaymentStatus(PropertyType.CC_MOCK_ACS_URI, response.getMockJson());
                stubSTRequestType(AUTHresponse.OK.getMockJson(), RequestType.AUTH);
                break;
            case FAILURE:
                stubPaymentStatus(PropertyType.CC_MOCK_ACS_URI, response.getMockJson());
                stubSTRequestType(AUTHresponse.MERCHANT_DECLINE.getMockJson(), RequestType.AUTH);
                break;
            case ERROR:
                stubPaymentStatus(PropertyType.CC_MOCK_ACS_URI, response.getMockJson());
                break;
        }
    }

    @And("^User clicks Pay button - AUTH response set to (.*)$")
    public void userClicksPayButtonAUTHResponseSetToPaymentCode(AUTHresponse response) {
        stubSTRequestType(response.getMockJson(), RequestType.AUTH);
        paymentPage.choosePaymentMethodWithMock(PaymentType.CARDINAL_COMMERCE);
    }

    @When("^User chooses Visa Checkout as payment method - response set to (.*)$")
    public void userChoosesVisaCheckoutAsPaymentMethodResponseSetTo(VisaResponse response) {
        stubSTRequestType(VisaResponse.VISA_AUTH_SUCCESS.getMockJson(), RequestType.AUTH);
        stubPaymentStatus(PropertyType.VISA_MOCK_URI, response.getMockJson());
        scrollToBottomOfPage();
        paymentPage.choosePaymentMethodWithMock(PaymentType.VISA_CHECKOUT);
    }

    @When("^User chooses ApplePay as payment method - response set to (.*)$")
    public void userChoosesApplePayAsPaymentMethodResponseSetTo(ApplePayResponse response) {
        stubSTRequestType(ApplePayResponse.SUCCESS.getMockJson(), RequestType.WALLETVERIFY); // Stub so wallet verify works
        if (checkIfScenarioNameContainsText("SCENARIO SKIPPED")) {
            System.out.println("Step skipped as iOS system and Safari is required for ApplePay test");
        } else {
            switch (response) {
                case SUCCESS:
                    stubPaymentStatus(PropertyType.APPLEPAY_MOCK_URI, response.getMockJson());
                    stubSTRequestType(ApplePayResponse.APPLE_AUTH_SUCCESS.getMockJson(), RequestType.AUTH);
                    break;
                case ERROR:
                    stubPaymentStatus(PropertyType.APPLEPAY_MOCK_URI, "appleSuccess.json");
                    // TODO update sttransport to handle this and automatically do the success
                    // message too then applepay/visa become simpler
                    // TODO once this works comment back in in .feature file and add visa equivalent
                    stubSTRequestTypeServerError(RequestType.AUTH);
                    break;
                case DECLINE:
                    stubPaymentStatus(PropertyType.APPLEPAY_MOCK_URI, ApplePayResponse.SUCCESS.getMockJson());
                    stubSTRequestType(ApplePayResponse.ERROR.getMockJson(), RequestType.AUTH);
                    break;
                case CANCEL:
                    stubPaymentStatus(PropertyType.APPLEPAY_MOCK_URI, response.getMockJson());
                    break;
            }
            scrollToBottomOfPage();
            paymentPage.choosePaymentMethodWithMock(PaymentType.APPLE_PAY);
        }
    }

    @And("^User will see that notification frame has \"([^\"]*)\" color$")
    public void userWillSeeThatNotificationFrameHasColorColor(String color) throws InterruptedException {
        if (checkIfScenarioNameContainsText("SCENARIO SKIPPED")) {
            System.out.println("Step skipped as iOS system and Safari is required for ApplePay test");
        } else
            paymentPage.validateIfColorOfNotificationFrameWasAsExpected(color);
    }

    @Then("^User will see notification frame with message: \"([^\"]*)\"$")
    public void userWillSeeNotificationFrameWithMessage(String message) throws Throwable {
        paymentPage.validateIfPaymentStatusMessageWasAsExpected(message);
    }

    @And("^User will see that (.*) field is highlighted$")
    public void userWillSeeThatFieldFieldIsHighlighted(FieldType fieldType) {
        paymentPage.validateIfFieldIsHighlighted(fieldType);
    }

    @Then("^User will see that merchant field (.*) is highlighted$")
    public void userWillSeeThatMerchantFieldIsHighlighted(MerchantFieldType field) {
        scrollToTopOfPage();
        paymentPage.validateIfMerchantFieldIsHighlighted(field);
    }

    @And("^User will see that all fields are highlighted$")
    public void userWillSeeThatAllFieldsAreHighlighted() {
        paymentPage.validateIfFieldIsHighlighted(FieldType.CARD_NUMBER);
        paymentPage.validateIfFieldIsHighlighted(FieldType.EXPIRY_DATE);
        paymentPage.validateIfFieldIsHighlighted(FieldType.CVC);
    }

    @And("^InvalidField response set for (.*)$")
    public void invalidfieldResponseSetForField(InvalidFieldResponse fieldType) {
        stubSTRequestType(fieldType.getMockJson(), RequestType.THREEDQUERY);
    }

    @Then("^User will see that Submit button is enabled after payment$")
    public void userWillSeeThatSubmitButtonIsEnabledAfterPayment() throws InterruptedException {
        paymentPage.validateIfNotificationFrameIsDisplayed();
        paymentPage.validateIfElementIsEnabledAfterPayment(FieldType.SUBMIT_BUTTON);
    }

    @And("^User will see that all input fields are enabled$")
    public void userWillSeeThatAllInputFieldsAreEnabled() {
        paymentPage.validateIfElementIsEnabledAfterPayment(FieldType.CARD_NUMBER);
        paymentPage.validateIfElementIsEnabledAfterPayment(FieldType.CVC);
        paymentPage.validateIfElementIsEnabledAfterPayment(FieldType.EXPIRY_DATE);
    }

    @When("^User changes page language to ([^\"]*)$")
    public void userChangesPageLanguageToLanguage(String language) throws IOException, ParseException {
        SeleniumExecutor.getDriver()
                .get(getProperty(PropertyType.BASE_URI) + "?jwt=" + getTranslationFromJson("jwt", language));
    }

    @Then("^User will see all labels displayed on page translated into ([^\"]*)$")
    public void userWillSeeAllLabelsDisplayedOnPageTranslatedIntoLanguage(String language)
            throws IOException, ParseException {
        paymentPage.validateIfLabelsTranslationWasAsExpected(language);
    }

    @Then("^User will see information about \"([^\"]*)\" payment status translated into ([^\"]*)$")
    public void userWillSeeInformationAboutPaymentStatusTranslatedIntoLanguage(String paymentStatus, String
            language)
            throws Throwable {
        if (checkIfScenarioNameContainsText("SCENARIO SKIPPED")) {
            System.out.println("Step skipped as iOS system and Safari is required for ApplePay test");
        } else {
            paymentPage.validateIfPaymentStatusTranslationWasAsExpected(paymentStatus, language);
        }
    }

    @Then("^User will see validation message \"([^\"]*)\" under (.*) field translated into ([^\"]*)$")
    public void userWillSeeValidationMessageUnderFieldTranslatedIntoLanguage(String message, FieldType fieldType,
                                                                             String language) throws Throwable {
        paymentPage.validateIfValidationMessageUnderFieldWasAsExpected(fieldType, language, message);
    }

    @Then("^User will see validation message \"([^\"]*)\" under all fields translated into ([^\"]*)$")
    public void userWillSeeValidationMessageUnderAllFieldsTranslatedIntoLanguage(String message, String language)
            throws Throwable {
        paymentPage.validateIfValidationMessageUnderFieldWasAsExpected(FieldType.CARD_NUMBER, language, message);
        paymentPage.validateIfValidationMessageUnderFieldWasAsExpected(FieldType.EXPIRY_DATE, language, message);
        paymentPage.validateIfValidationMessageUnderFieldWasAsExpected(FieldType.CVC, language, message);
    }

    @And("^AUTH response set to \"([^\"]*)\"$")
    public void authResponseSetTo(AUTHresponse response) {
        stubSTRequestType(response.getMockJson(), RequestType.AUTH);
    }

    @And("^User opens immediate payment page$")
    public void userOpensImmediatePaymentPage() {
        SeleniumExecutor.getDriver().get(getProperty(PropertyType.BASE_URI) + "/immediate.html");
    }

    @Then("^User will see message \"([^\"]*)\" displayed on page$")
    public void userWillSeeMessageDisplayedOnPage(String message) throws InterruptedException {
        paymentPage.validateIfMessageFromImmediateWasAsExpected(message);
    }

    @And("^User will see error code: \"([^\"]*)\"$")
    public void userWillSeeErrorCode(String errorcode) {
        paymentPage.validateIfErrorCodeFromImmediateWasAsExpected(errorcode);
    }

    @When("User opens payment page without JSINIT process")
    public void userOpensPaymentPageWithoutJSINITProcess() {
        if (checkIfBrowserNameStartWith("Safari")) {
            SeleniumExecutor.getDriver().get(getProperty(PropertyType.WEBSERVICES_DOMAIN));
            SeleniumExecutor.getDriver().get("https://thirdparty.example.com:8443");
        }
        SeleniumExecutor.getDriver().get(getProperty(PropertyType.BASE_URI) + "/bypass.html");
    }

    @Then("User will see that labels displayed on animated card are translated into ([^\"]*)$")
    public void userWillSeeThatLabelsDisplayedOnAnimatedCardAreTranslatedIntoLanguage(String language) throws IOException, ParseException {
        paymentPage.validateIfAnimatedCardTranslationWasAsExpected(language);
    }
}
