package com.SecureTrading.stepdefs;

import static util.MocksHandler.*;
import static util.PropertiesHandler.getProperty;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

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
        SeleniumExecutor.getDriver().navigate().refresh();
    }

    @When("^User fills payment form with credit card number \"([^\"]*)\", expiration date \"([^\"]*)\" and cvc \"([^\"]*)\"$")
    public void userFillsPaymentFormWithCreditCardNumberCardNumberExpirationDateExpirationDateAndCvcCvc(
            String cardNumber, String expirationDate, String cvc) {
        ((JavascriptExecutor) SeleniumExecutor.getDriver())
                .executeScript("window.scrollTo(0, document.body.scrollHeight)");
        paymentPage.fillAllCardData(cardNumber, expirationDate, cvc);
    }

    @When("^User fills merchant data with name \"([^\"]*)\", email \"([^\"]*)\", phone \"([^\"]*)\"$")
    public void userFillsMerchantDataWithNameEmailPhone(String name, String email, String phone) {
        paymentPage.fillAllMerchantData(name, email, phone);
    }

    @And("^User clicks Pay button$")
    public void userClicksPayButton() throws InterruptedException {
        paymentPage.waitUntilNetworwTrafficIsCompleted();
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
        } else
            paymentPage.validateIfPaymentStatusMessageWasAsExpected(paymentStatusMessage);
    }

    @Then("^User will see validation message \"([^\"]*)\" under all fields$")
    public void userWillSeeValidationMessageUnderAllFields(String message) {
        paymentPage.validateIfFieldValidationMessageWasAsExpected(CardFieldType.number, message);
        paymentPage.validateIfFieldValidationMessageWasAsExpected(CardFieldType.expiryDate, message);
        paymentPage.validateIfFieldValidationMessageWasAsExpected(CardFieldType.cvc, message);
    }

    @And("^User clicks Pay button - response set to ([^\"]*)$")
    public void userClicksPayButtonResponseSetToPaymentCode(String paymentCode) {
        switch (paymentCode) {
        case "0":
            stubPaymentStatus(PropertyType.CC_MOCK_URI, "ccOK.json");
            break;
        case "30000":
            stubPaymentStatus(PropertyType.CC_MOCK_URI, "ccInvalidField.json");
            break;
        case "50000":
            stubPaymentStatus(PropertyType.CC_MOCK_URI, "ccSocketError.json");
            break;
        case "60022":
            stubPaymentStatus(PropertyType.CC_MOCK_URI, "ccUnauthenticated.json");
            break;
        case "70000":
            stubPaymentStatus(PropertyType.CC_MOCK_URI, "ccDeclineError.json");
            break;
        }
        paymentPage.choosePaymentMethodWithMock(PaymentType.cardinalCommerce);
    }

    @When("^User chooses Visa Checkout as payment method - response set to ([^\"]*)$")
    public void userChoosesVisaCheckoutAsPaymentMethodResponseSetToPaymentCode(String paymentCode) {
        // TODO consider moving JSINIT stub to BeforeHook
        stubSTRequestType("jsinit.json", "JSINIT"); // Stub so Cardinal can init but don't use cardinal
        // TODO update visaAuthSuccess.json with a real VISACHECKOUT AUTH (currently
        // using a normal card payment auth)
        // TODO should be auth but need to change js-payments to
        // not have step: true
        stubSTRequestType("visaAuthSuccess.json", "CACHETOKENISE");
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
        paymentPage.choosePaymentMethodWithMock(PaymentType.visaCheckout);
    }

    @When("^User chooses ApplePay as payment method - response set to ([^\"]*)$")
    public void userChoosesApplePayAsPaymentMethodResponseSetToPaymentCode(String paymentCode) {
        if (PicoContainerHelper.getFromContainer(StoredElement.scenarioName).toString().contains("SCENARIO SKIPPED")) {
            System.out.println("Step skipped as iOS system and Safari is required for ApplePay test");
        } else {
            switch (paymentCode) {
            case "Success":
                stubPaymentStatus(PropertyType.APPLEPAY_MOCK_URI, "appleSuccess.json");
                break;
            case "Error":
                stubPaymentStatus(PropertyType.APPLEPAY_MOCK_URI, "appleError.json");
                break;
            }
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
