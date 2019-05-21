package com.SecureTrading.stepdefs;

import static util.MocksHandler.*;
import static util.PropertiesHandler.getProperty;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static util.helpers.actions.CustomScrollImpl.scrollToBottomOfPage;
import static util.helpers.actions.CustomScrollImpl.scrollToTopOfPage;

import cucumber.api.PendingException;
import com.SecureTrading.pageobjects.PaymentPage;
import com.github.tomakehurst.wiremock.client.WireMock;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.JavascriptExecutor;
import util.PicoContainerHelper;
import util.SeleniumExecutor;
import util.enums.CardFieldType;
import util.enums.PaymentType;
import util.enums.PropertyType;
import util.enums.StoredElement;

public class PaymentPageSteps {

    private PaymentPage paymentPage;

    public PaymentPageSteps() {
        paymentPage = new PaymentPage();
    }

    @Given("^User opens page with payment form$")
    public void userOpensPageWithPaymentForm() {
        if (PicoContainerHelper.getFromContainer(StoredElement.scenarioName).toString().contains("SCENARIO SKIPPED")) {
            System.out.println("Step skipped as iOS system and Safari is required for ApplePay test");
        } else
            SeleniumExecutor.getDriver().get(getProperty(PropertyType.BASE_URI));
    }

    @When("^User fills payment form with credit card number \"([^\"]*)\", expiration date \"([^\"]*)\" and cvc \"([^\"]*)\"$")
    public void userFillsPaymentFormWithCreditCardNumberCardNumberExpirationDateExpirationDateAndCvcCvc(
            String cardNumber, String expirationDate, String cvc) {
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
            String cardNumber, String expirationDate, String cvc) {
        paymentPage.fillAllCardData(cardNumber, expirationDate, cvc);
    }

    @Then("^User will see validation message \"([^\"]*)\" under \"([^\"]*)\" field$")
    public void userWillSeeValidationMessageUnderField(String message, String fieldType) {
        paymentPage.validateIfFieldValidationMessageWasAsExpected(CardFieldType.fromString(fieldType), message);
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

    @And("^User clicks Pay button - AUTH response set to ([^\"]*)$")
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

    @When("^User chooses Visa Checkout as payment method - response set to ([^\"]*)$")
    public void userChoosesVisaCheckoutAsPaymentMethodResponseSetToPaymentCode(String paymentCode) {
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

    @When("^User chooses ApplePay as payment method - response set to ([^\"]*)$")
    public void userChoosesApplePayAsPaymentMethodResponseSetToPaymentCode(String paymentCode) {
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

    @And("^User will see that notification frame has ([^\"]*) color$")
    public void userWillSeeThatNotificationFrameHasColorColor(String color) {
        if (PicoContainerHelper.getFromContainer(StoredElement.scenarioName).toString().contains("SCENARIO SKIPPED")) {
            System.out.println("Step skipped as iOS system and Safari is required for ApplePay test");
        } else
            paymentPage.validateIfColorOfNotificationFrameWasAsExpected(color);
    }

    @Then("^User will see Cardinal Commerce authentication modal$")
    public void userWillSeeCardinalCommerceAuthenticationModal() throws InterruptedException {
        paymentPage.validateIfCardinalCommerceAuthenticationModalIsDisplayed();
    }
}
