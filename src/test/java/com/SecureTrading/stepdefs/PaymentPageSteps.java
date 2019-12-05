package com.SecureTrading.stepdefs;

import static util.MocksHandler.*;
import static util.PropertiesHandler.getProperty;
import static util.JsonHandler.getTranslationFromJson;
import static util.helpers.TestConditionHandler.*;
import static util.helpers.actions.CustomScrollImpl.scrollToBottomOfPage;
import static util.helpers.actions.CustomScrollImpl.scrollToTopOfPage;
import com.SecureTrading.pageobjects.PaymentPage;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.simple.parser.ParseException;
import util.PicoContainerHelper;
import util.PropertiesHandler;
import util.SeleniumExecutor;
import util.enums.*;
import util.enums.responses.*;

import java.io.IOException;

public class PaymentPageSteps {

    private PaymentPage paymentPage;
    private boolean fieldInIframe = PicoContainerHelper.getFromContainer(StoredElement.isFieldInIframe, Boolean.class);

    public PaymentPageSteps() {
        paymentPage = new PaymentPage();
    }

    @Given("JavaScript configuration is set for scenario based on scenario's @config tag")
    public void javascriptConfigurationIsSetForScenarioBasedOnScenarioSConfigTag() {
        String scenarioTagsList = getScenarioTagsList();
        if(scenarioTagsList.contains("@configSubmitOnSuccessTrue"))
            stubConfig(PropertyType.CONFIG_MOCK_URI, Configuration.SUBMIT_ON_SUCCESS_TRUE.getMockJson());
        else if (scenarioTagsList.contains("@configFieldStyle"))
            stubConfig(PropertyType.CONFIG_MOCK_URI, Configuration.FIELD_STYLE.getMockJson());
        else if (scenarioTagsList.contains("@configAnimatedCardTrue"))
            stubConfig(PropertyType.CONFIG_MOCK_URI, Configuration.ANIMATED_CARD.getMockJson());
        else if (scenarioTagsList.contains("@configImmediatePayment"))
            stubConfig(PropertyType.CONFIG_MOCK_URI, Configuration.IMMEDIATE_PAYMENT.getMockJson());
        else if (scenarioTagsList.contains("@configUpdateJwtTrue"))
            stubConfig(PropertyType.CONFIG_MOCK_URI, Configuration.UPDATE_JWT.getMockJson());
        else if (scenarioTagsList.contains("@configSkipJSinit"))
            stubConfig(PropertyType.CONFIG_MOCK_URI, Configuration.SKIP_JSINIT.getMockJson());
        else if (scenarioTagsList.contains("@configDeferInitAndStartOnLoadTrue"))
            stubConfig(PropertyType.CONFIG_MOCK_URI, Configuration.DEFER_INIT_START_ON_LOAD.getMockJson());
        else if (scenarioTagsList.contains("@configSubmitCvvOnly"))
            stubConfig(PropertyType.CONFIG_MOCK_URI, Configuration.SUBMIT_CVV_ONLY.getMockJson());
        else
            stubConfig(PropertyType.CONFIG_MOCK_URI, Configuration.CONFIG.getMockJson());
    }

    @Given("^User opens page with payment form$")
    public void userOpensPageWithPaymentForm() throws InterruptedException {
        if (checkIfScenarioNameContainsText("SCENARIO SKIPPED")) {
            System.out.println("Step skipped as iOS system and Safari is required for ApplePay test");
        } else {
            //Accept self signed certificates for Safari purpose
            if (checkIfBrowserNameStartWith("Safari")) {
                paymentPage.OpenPage(getProperty(PropertyType.WEBSERVICES_DOMAIN));
                paymentPage.OpenPage("https://thirdparty.example.com:8443");
            }
            if (!(checkIfScenarioNameContainsText("Immediate") || checkIfScenarioNameContainsText("StartOnLoad"))) {
                if (checkIfBrowserNameStartWith("IE"))
                    Thread.sleep(2000);
                paymentPage.OpenPage(getProperty(PropertyType.BASE_URI));
                paymentPage.waitUntilPageIsLoaded();
                //Additional try for IE problems
                if (!SeleniumExecutor.getDriver().getTitle().contains("Secure")) {
                    paymentPage.OpenPage(getProperty(PropertyType.WEBSERVICES_DOMAIN));
                    Thread.sleep(4000);
                    paymentPage.OpenPage(getProperty(PropertyType.BASE_URI));
                    paymentPage.waitUntilPageIsLoaded();
                }
            }
        }
    }

    @When("^User fills payment form with credit card number \"([^\"]*)\", expiration date \"([^\"]*)\"(?: and cvc \"([^\"]*)\"|)$")
    public void userFillsPaymentFormWithCreditCardNumberCardNumberExpirationDateExpirationDateAndCvcCvc(
            String cardNumber, String expirationDate, String cvc) throws InterruptedException {
        paymentPage.fillPaymentForm(cardNumber, expirationDate, cvc);
    }

    @When("^User fills merchant data with name \"([^\"]*)\", email \"([^\"]*)\", phone \"([^\"]*)\"$")
    public void userFillsMerchantDataWithNameEmailPhone(String name, String email, String phone) {
        paymentPage.fillAllMerchantData(name, email, phone);
    }

    @And("^User clicks Pay button$")
    public void userClicksPayButton() throws InterruptedException {
        paymentPage.choosePaymentMethodWithMock(PaymentType.CARDINAL_COMMERCE);
    }

    @When("^User fills payment form with incorrect or missing data: card number ([^\"]*), expiration date ([^\"]*) and cvc ([^\"]*)$")
    public void userFillsPaymentFormWithIncorrectOrMissingDataCardNumberCardNumberExpirationDateExpirationAndCvcCvc
            (
                    String cardNumber, String expirationDate, String cvc) throws InterruptedException {
        paymentPage.fillPaymentForm(cardNumber, expirationDate, cvc);
    }

    @And("^User will see \"([^\"]*)\" message under field: (.*)$")
    public void userWillSeeMessageUnderField(String message, FieldType fieldType) {
        paymentPage.validateIfFieldValidationMessageWasAsExpected(fieldType, message, fieldInIframe);
    }

    @Then("^User will see payment status information: (.*)$")
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
        paymentPage.validateIfFieldValidationMessageWasAsExpected(FieldType.CARD_NUMBER, message, fieldInIframe);
        paymentPage.validateIfFieldValidationMessageWasAsExpected(FieldType.EXPIRY_DATE, message, fieldInIframe);
        paymentPage.validateIfFieldValidationMessageWasAsExpected(FieldType.CVC, message, fieldInIframe);
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
    public void userClicksPayButtonAUTHResponseSetToPaymentCode(AUTHresponse response) throws InterruptedException {
        stubSTRequestType(response.getMockJson(), RequestType.AUTH);
        paymentPage.choosePaymentMethodWithMock(PaymentType.CARDINAL_COMMERCE);
    }

    @When("^User chooses Visa Checkout as payment method - response set to (.*)$")
    public void userChoosesVisaCheckoutAsPaymentMethodResponseSetTo(VisaResponse response) throws InterruptedException {
        stubSTRequestType(VisaResponse.VISA_AUTH_SUCCESS.getMockJson(), RequestType.AUTH);
        stubPaymentStatus(PropertyType.VISA_MOCK_URI, response.getMockJson());
        scrollToBottomOfPage();
        paymentPage.choosePaymentMethodWithMock(PaymentType.VISA_CHECKOUT);
    }

    @When("^User chooses ApplePay as payment method - response set to (.*)$")
    public void userChoosesApplePayAsPaymentMethodResponseSetTo(ApplePayResponse response) throws InterruptedException {
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
        paymentPage.validateIfFieldIsHighlighted(fieldType, fieldInIframe);
    }

    @Then("^User will see that merchant field (.*) is highlighted$")
    public void userWillSeeThatMerchantFieldIsHighlighted(MerchantFieldType field) {
        scrollToTopOfPage();
        paymentPage.validateIfMerchantFieldIsHighlighted(field);
    }

    @And("^User will see that all fields are highlighted$")
    public void userWillSeeThatAllFieldsAreHighlighted() {
        paymentPage.validateIfFieldIsHighlighted(FieldType.CARD_NUMBER, fieldInIframe);
        paymentPage.validateIfFieldIsHighlighted(FieldType.EXPIRY_DATE, fieldInIframe);
        paymentPage.validateIfFieldIsHighlighted(FieldType.CVC, fieldInIframe);
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
    public void userChangesPageLanguageToLanguage(String language) throws IOException, ParseException, InterruptedException {
        paymentPage.OpenPage(getProperty(PropertyType.BASE_URI) + "?jwt=" + getTranslationFromJson("jwt", language));
        paymentPage.waitUntilPageIsLoaded();
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

    @And("^User opens payment page$")
    public void userOpensImmediatePaymentPage() {
        SeleniumExecutor.getDriver().get(getProperty(PropertyType.BASE_URI));
    }

    @Then("User will see that labels displayed on animated card are translated into ([^\"]*)$")
    public void userWillSeeThatLabelsDisplayedOnAnimatedCardAreTranslatedIntoLanguage(String language) throws IOException, ParseException {
        paymentPage.validateIfAnimatedCardTranslationWasAsExpected(language, fieldInIframe);
    }

    @Then("User will see payment status information included in url")
    public void userWillSeePaymentStatusInformationIncludedInUrl() throws InterruptedException {
        String scenarioName = PicoContainerHelper.getFromContainer(StoredElement.scenarioName).toString();
        switch (scenarioName.substring(0, scenarioName.indexOf(" "))) {
            case "Cardinal":
                paymentPage.validateIfUrlConstainsInfoAboutPayment(PropertiesHandler.getTestProperty("stepPaymentCardinalUrl"));
                break;
            case "Visa":
                paymentPage.validateIfUrlConstainsInfoAboutPayment(PropertiesHandler.getTestProperty("stepPaymentVisaUrl"));
                break;
        }
    }

    @Then("User will see that (.*) field has correct style")
    public void userWillSeeThatFieldHasCorrectStyle(FieldType fieldType) throws InterruptedException {
        switch (fieldType) {
            case CARD_NUMBER:
                paymentPage.validateIfFieldHasCorrectStyle(fieldType, "rgba(240, 248, 255, 1)");
                break;
            case CVC:
                paymentPage.validateIfFieldHasCorrectStyle(fieldType, "rgba(255, 243, 51, 1)");
                break;
        }
    }

    @Then("^User will see that (.*) field is disabled$")
    public void userWillSeeThatFieldIsDisabled(FieldType fieldType) throws InterruptedException {
        paymentPage.validateIfFieldIsDisabled(fieldType, fieldInIframe);
    }

    @When("^User fills (.*) field (.*)$")
    public void userFillsCVVField(FieldType fieldType, String text) throws InterruptedException {
        paymentPage.fillCreditCardInputField(fieldType, text);
    }

    @And("User will not see card number and expiration date fields")
    public void userWillNotSeeCardNumberAndExpirationDateFields() {
        paymentPage.validateIfFieldIsPresent(FieldType.CARD_NUMBER);
        paymentPage.validateIfFieldIsPresent(FieldType.EXPIRY_DATE);
    }
}