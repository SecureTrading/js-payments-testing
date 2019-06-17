package com.SecureTrading.stepdefs;

import static util.MocksHandler.*;
import static util.PropertiesHandler.getProperty;
import static util.JsonHandler.getTranslationFromJson;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static util.helpers.actions.CustomScrollImpl.scrollToBottomOfPage;
import static util.helpers.actions.CustomScrollImpl.scrollToTopOfPage;

import cucumber.api.PendingException;
import com.SecureTrading.pageobjects.PaymentPage;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.simple.parser.ParseException;
import util.PicoContainerHelper;
import util.SeleniumExecutor;
import util.enums.CardFieldType;
import util.enums.PaymentType;
import util.enums.PropertyType;
import util.enums.StoredElement;

import java.io.IOException;

public class PaymentPageSteps {

    private PaymentPage paymentPage;

    public PaymentPageSteps() {
        paymentPage = new PaymentPage();
    }

    @Given("^User opens page with payment form$")
    public void userOpensPageWithPaymentForm() throws InterruptedException {
        if (PicoContainerHelper.getFromContainer(StoredElement.scenarioName).toString().contains("SCENARIO SKIPPED")) {
            System.out.println("Step skipped as iOS system and Safari is required for ApplePay test");
        } else {
            SeleniumExecutor.getDriver().get(getProperty(PropertyType.BASE_URI));
            //Additional try for IE problems
            if(!SeleniumExecutor.getDriver().getTitle().contains("Secure")){
                Thread.sleep(4000);
                SeleniumExecutor.getDriver().get(getProperty(PropertyType.BASE_URI));
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
    public void userClicksPayButton() throws InterruptedException {
        paymentPage.choosePaymentMethodWithMock(PaymentType.cardinalCommerce);
    }

    @Then("^User will see card icon connected to card type ([^\"]*)$")
    public void userWillSeeCardIconConnectedToCardTypeCardType(String cardType) {
        PicoContainerHelper.updateInContainer(StoredElement.cardType, cardType);
        paymentPage.validateIfCardTypeIconWasAsExpected(cardType.toLowerCase());
    }

    @And("^User will see that animated card is flipped, except for \"([^\"]*)\"$")
    public void userWillSeeThatAnimatedCardIsFlippedExceptFor(String cardType) {
        paymentPage.validateIfAnimatedCardIsFlipped(cardType);
    }

    @When("^User fills payment form with incorrect or missing data: card number ([^\"]*), expiration date ([^\"]*) and cvc ([^\"]*)$")
    public void userFillsPaymentFormWithIncorrectOrMissingDataCardNumberCardNumberExpirationDateExpirationAndCvcCvc(
            String cardNumber, String expirationDate, String cvc) throws InterruptedException {
        paymentPage.fillAllCardData(cardNumber, expirationDate, cvc);
    }

    @And("^User will see \"([^\"]*)\" message under field: \"([^\"]*)\"$")
    public void userWillSeeMessageUnderField(String message, String field) throws Throwable {
        paymentPage.validateIfFieldValidationMessageWasAsExpected(CardFieldType.fromString(field), message);
    }

    @And("^User will see the same provided data on animated credit card ([^\"]*), ([^\"]*) and ([^\"]*)$")
    public void userWillSeeTheSameProvidedDataOnAnimatedCreditCardCardNumberExpirationDateAndCvc(String cardNumber,
            String expirationDate, String cvc) {
        paymentPage.validateIfAllProvidedDataOnAnimatedCardWasAsExpected(cardNumber, expirationDate, cvc);
    }

    @Then("^User will see information about payment status \"([^\"]*)\"$")
    public void userWillSeeInformationAboutPaymentStatusPaymentStatusMessage(String paymentStatusMessage) {
        if (PicoContainerHelper.getFromContainer(StoredElement.scenarioName).toString().contains("SCENARIO SKIPPED")) {
            System.out.println("Step skipped as iOS system and Safari is required for ApplePay test");
        } else {
            scrollToTopOfPage();
            paymentPage.validateIfPaymentStatusMessageWasAsExpected(paymentStatusMessage);
        }
    }

    @Then("^User will see validation message \"([^\"]*)\" under all fields$")
    public void userWillSeeValidationMessageUnderAllFields(String message) {
        paymentPage.validateIfFieldValidationMessageWasAsExpected(CardFieldType.number, message);
        paymentPage.validateIfFieldValidationMessageWasAsExpected(CardFieldType.expiryDate, message);
        paymentPage.validateIfFieldValidationMessageWasAsExpected(CardFieldType.cvc, message);
    }

    @And("^THREEDQUERY response set to \"([^\"]*)\"$")
    public void threedqueryResponseSetTo(String response) {
        switch (response) {
        case "entrolled Y":
            stubSTRequestType("ccTDQEnrolledY.json", "THREEDQUERY");
            break;
        case "not-entrolled N":
            stubSTRequestType("ccTDQEnrolledN.json", "THREEDQUERY");
            break;
        case "not-entrolled U":
            stubSTRequestType("ccTDQEnrolledU.json", "THREEDQUERY");
            break;
        case "30000":
            stubSTRequestType("ccTDQnvalidField.json", "THREEDQUERY");
            break;
        case "60031":
            stubSTRequestType("ccTDQInvalidAcquirer.json", "THREEDQUERY");
            break;
        }
    }

    @And("^ACS response set to \"([^\"]*)\"$")
    public void acsResponseSetTo(String response) {
        switch (response) {
        case "OK":
            stubPaymentStatus(PropertyType.CC_MOCK_ACS_URI, "ccACSoK.json");
            break;
        case "NOACTION":
            stubPaymentStatus(PropertyType.CC_MOCK_ACS_URI, "ccACSnoaction.json");
            stubSTRequestType("ccAUTHoK.json", "AUTH");
            break;
        case "FAILURE":
            stubPaymentStatus(PropertyType.CC_MOCK_ACS_URI, "ccACSfailure.json");
            stubSTRequestType("ccAUTHMerchantDeclineError.json", "AUTH");
            break;
        case "ERROR":
            stubPaymentStatus(PropertyType.CC_MOCK_ACS_URI, "ccACSerror.json");
            break;
        }
    }

    @And("^User clicks Pay button - AUTH response set to \"([^\"]*)\"$")
    public void userClicksPayButtonAUTHResponseSetToPaymentCode(String paymentCode) {
        switch (paymentCode) {
        case "0":
            stubSTRequestType("ccAUTHoK.json", "AUTH");
            break;
        case "30000":
            stubSTRequestType("ccAUTHInvalidField.json", "AUTH");
            break;
        case "50000":
            stubSTRequestType("ccAUTHSocketError.json", "AUTH");
            break;
        case "60022":
            stubSTRequestType("ccAUTHUnauthenticated.json", "AUTH");
            break;
        case "70000":
            stubSTRequestType("ccAUTHDeclineError.json", "AUTH");
            break;
        case "99999":
            stubSTRequestType("ccAUTHUnknownError.json", "AUTH");
            break;
        }
        paymentPage.choosePaymentMethodWithMock(PaymentType.cardinalCommerce);
    }

    @When("^User chooses Visa Checkout as payment method - response set to \"([^\"]*)\"$")
    public void userChoosesVisaCheckoutAsPaymentMethodResponseSetTo(String paymentCode) throws Throwable {
        stubSTRequestType("visaAuthSuccess.json", "AUTH");
        switch (paymentCode) {
        case "Success":
            stubPaymentStatus(PropertyType.VISA_MOCK_URI, "visaSuccess.json");
            break;
        case "Error":
            stubPaymentStatus(PropertyType.VISA_MOCK_URI, "visaError.json");
            break;
        case "Cancel":
            stubPaymentStatus(PropertyType.VISA_MOCK_URI, "visaCancel.json");
            break;
        }
        scrollToBottomOfPage();
        paymentPage.choosePaymentMethodWithMock(PaymentType.visaCheckout);
    }

    @When("^User chooses ApplePay as payment method - response set to \"([^\"]*)\"$")
    public void userChoosesApplePayAsPaymentMethodResponseSetTo(String paymentCode) throws Throwable {
        stubSTRequestType("appleSuccess.json", "WALLETVERIFY"); // Stub so wallet verify works
        if (PicoContainerHelper.getFromContainer(StoredElement.scenarioName).toString().contains("SCENARIO SKIPPED")) {
            System.out.println("Step skipped as iOS system and Safari is required for ApplePay test");
        } else {
            switch (paymentCode) {
            case "Success":
                stubPaymentStatus(PropertyType.APPLEPAY_MOCK_URI, "appleSuccess.json");
                stubSTRequestType("appleAuthSuccess.json", "AUTH");
                break;
            case "Error":
                stubPaymentStatus(PropertyType.APPLEPAY_MOCK_URI, "appleSuccess.json");
                // TODO update sttransport to handle this and automatically do the success
                // message too then applepay/visa become simpler
                // TODO once this works comment back in in .feature file and add visa equivalent
                stubSTRequestTypeServerError("AUTH");
                break;
            case "Decline":
                stubPaymentStatus(PropertyType.APPLEPAY_MOCK_URI, "appleSuccess.json");
                stubSTRequestType("appleAuthError.json", "AUTH");
                break;
            case "Cancel":
                stubPaymentStatus(PropertyType.APPLEPAY_MOCK_URI, "appleCancel.json");
                break;
            }
            scrollToBottomOfPage();
            paymentPage.choosePaymentMethodWithMock(PaymentType.applePay);
        }
    }

    @And("^User will see that notification frame has \"([^\"]*)\" color$")
    public void userWillSeeThatNotificationFrameHasColorColor(String color) {
        if (PicoContainerHelper.getFromContainer(StoredElement.scenarioName).toString().contains("SCENARIO SKIPPED")) {
            System.out.println("Step skipped as iOS system and Safari is required for ApplePay test");
        } else
            paymentPage.validateIfColorOfNotificationFrameWasAsExpected(color);
    }

    @Then("^User will see notification frame with message: \"([^\"]*)\"$")
    public void userWillSeeNotificationFrameWithMessage(String message) throws Throwable {
        paymentPage.validateIfPaymentStatusMessageWasAsExpected(message);
    }

    @And("^User will see that ([^\"]*) field is highlighted$")
    public void userWillSeeThatFieldFieldIsHighlighted(String field) {
        paymentPage.validateIfFieldIsHighlighted(CardFieldType.fromString(field));
    }

    @And("^User will see that all fields are highlighted$")
    public void userWillSeeThatAllFieldsAreHighlighted() {
        paymentPage.validateIfFieldIsHighlighted(CardFieldType.number);
        paymentPage.validateIfFieldIsHighlighted(CardFieldType.expiryDate);
        paymentPage.validateIfFieldIsHighlighted(CardFieldType.cvc);
    }

    @And("^InvalidField response set for ([^\"]*)")
    public void invalidfieldResponseSetForField(String fieldType) {
        switch (fieldType) {
            case "number":
                stubSTRequestType("numberInvalidField.json", "THREEDQUERY");
                break;
            case "expiryDate":
                stubSTRequestType("expiryDateInvalidField.json", "THREEDQUERY");
                break;
            case "cvc":
                stubSTRequestType("cvvInvalidField.json", "THREEDQUERY");
                break;
        }
    }

    @Then("^User will see that Submit button is enabled after payment$")
    public void userWillSeeThatSubmitButtonIsEnabledAfterPayment() {
        paymentPage.validateIfNotificationFrameIsDisplayed();
        paymentPage.validateIfElementIsEnabledAfterPayment("submitButton");
    }

    @And("^User will see that all input fields are enabled$")
    public void userWillSeeThatAllInputFieldsAreEnabled() {
        paymentPage.validateIfElementIsEnabledAfterPayment("cardNumberInput");
        paymentPage.validateIfElementIsEnabledAfterPayment("cvcInput");
        paymentPage.validateIfElementIsEnabledAfterPayment("expirationDateInput");
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
    public void userWillSeeInformationAboutPaymentStatusTranslatedIntoLanguage(String paymentStatus, String language)
            throws Throwable {
        if (PicoContainerHelper.getFromContainer(StoredElement.scenarioName).toString().contains("SCENARIO SKIPPED")) {
            System.out.println("Step skipped as iOS system and Safari is required for ApplePay test");
        } else {
            paymentPage.validateIfPaymentStatusTranslationWasAsExpected(paymentStatus, language);
        }
    }

    @Then("^User will see validation message \"([^\"]*)\" under \"([^\"]*)\" field translated into ([^\"]*)$")
    public void userWillSeeValidationMessageUnderFieldTranslatedIntoLanguage(String message, String fieldType,
            String language) throws Throwable {
        paymentPage.validateIfValidationMessageUnderFieldWasAsExpected(fieldType, language, message);
    }

    @Then("^User will see validation message \"([^\"]*)\" under all fields translated into ([^\"]*)$")
    public void userWillSeeValidationMessageUnderAllFieldsTranslatedIntoLanguage(String message, String language)
            throws Throwable {
        paymentPage.validateIfValidationMessageUnderFieldWasAsExpected("number", language, message);
        paymentPage.validateIfValidationMessageUnderFieldWasAsExpected("expiryDate", language, message);
        paymentPage.validateIfValidationMessageUnderFieldWasAsExpected("cvc", language, message);
    }
}
