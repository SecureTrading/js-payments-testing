package com.SecureTrading.stepdefs;

import static util.MocksHandler.*;
import static util.PropertiesHandler.getProperty;

import com.SecureTrading.pageobjects.PaymentPage;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import util.SeleniumExecutor;
import util.enums.CardFieldType;
import util.enums.PaymentType;
import util.enums.PropertyType;

public class PaymentPageSteps {

    private PaymentPage paymentPage;

    public PaymentPageSteps(){
        paymentPage = new PaymentPage();
    }

    @Given("^User opens page with payment form$")
    public void userOpensPageWithPaymentForm() {
        SeleniumExecutor.getDriver().get(getProperty(PropertyType.BASE_URI));
    }

    @When("^User fills payment form with credit card number ([^\"]*), cvc ([^\"]*) and expiration date ([^\"]*)$")
    public void userFillsPaymentFormWithCreditCardNumberCardNumberCvcCvcAndExpirationDateExpirationDate(String cardNumber, String cvc, String expirationDate) {
        paymentPage.fillAllCardData(cardNumber, cvc, expirationDate);
    }

    @And("^User clicks Pay button$")
    public void userClicksPayButton() {
        paymentPage.clickPayButton();
    }

    @Then("^User will see card icon connected to card type ([^\"]*)$")
    public void userWillSeeCardIconConnectedToCardTypeCardType(String cardType) {
        paymentPage.validateIfCardTypeIconWasAsExpected(cardType);
    }

    @When("^User fills payment form with incorrect or missing data: card number ([^\"]*), cvc ([^\"]*) and expiration date ([^\"]*)$")
    public void userFillsPaymentFormWithIncorrectOrMissingDataCardNumberCardNumberCvcCvcAndExpirationDateExpiration(String cardNumber, String cvc, String expirationDate) {
        paymentPage.fillAllCardData(cardNumber, cvc, expirationDate);
    }

    @Then("^User will see validation message \"([^\"]*)\" under \"([^\"]*)\" field$")
    public void userWillSeeValidationMessageUnderField(String message, String fieldType) {
        paymentPage.validateIfFieldValidationMessageWasAsExpected(CardFieldType.fromString(fieldType), message);
    }

    @And("^User will see the same provided data on animated credit card ([^\"]*), ([^\"]*) and ([^\"]*)$")
    public void userWillSeeTheSameProvidedDataOnAnimatedCreditCardCardNumberCvcAndExpirationDate(String cardNumber, String cvc, String expirationDate) {
        paymentPage.validateIfAllProvidedDataOnAnimatedCardWasAsExpected(cardNumber, cvc, expirationDate);
    }

    @Then("^User will see information about payment status \"([^\"]*)\"$")
    public void userWillSeeInformationAboutPaymentStatusPaymentStatusMessage(String paymentStatusMessage) {
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
            case "success":
                stubCcSuccessPaymentPayment();
                break;
            case "30000":
                stubCcFieldErrorPayment();
                break;
            case "50000":
                stubCcDeclineErrorPayment();
                break;
            case "70000":
                stubCcSocketReceiveErrorPayment();
                break;
        }
        paymentPage.clickPayButton();
    }

    @When("^User chooses \"([^\"]*)\" as payment method - response set to ([^\"]*)$")
    public void userChoosesAsPaymentMethodResponseSetToPaymentCode(String paymentMethod, String paymentCode) {
        switch (paymentCode) {
            case "Success":
                stubVisaSuccessPayment();
                break;
            case "Error":
                stubVisaErrorPayment();
                break;
            case "Cancel":
                stubVisaCancelPayment();
                break;
        }
        paymentPage.choosePaymentMethod(PaymentType.fromString(paymentMethod));
    }
}
