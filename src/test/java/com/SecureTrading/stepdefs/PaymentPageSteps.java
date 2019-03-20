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
import util.enums.MerchantFieldType;
import util.enums.PropertyType;

public class PaymentPageSteps {

    private PaymentPage paymentPage;

    public PaymentPageSteps(){
        paymentPage = new PaymentPage();
    }

    @Given("^User opens page with payment form$")
    public void userOpensPageWithPaymentForm() throws InterruptedException {
        SeleniumExecutor.getDriver().get(getProperty(PropertyType.BASE_URI));
    }

    @When("^User fills payment form with credit card number ([^\"]*), cvc ([^\"]*) and expiration date ([^\"]*)$")
    public void userFillsPaymentFormWithCreditCardNumberCardNumberCvcCvcAndExpirationDateExpirationDate(String cardNumber, String cvc, String expirationDate) throws InterruptedException {
        paymentPage.fillAllCardData(cardNumber, cvc, expirationDate);
        Thread.sleep(5000);
    }

    @And("^User clicks Pay button$")
    public void userClicksPayButton() {
        paymentPage.clickPayButton();
    }

    @Then("^User will see card icon connected to card type ([^\"]*)$")
    public void userWillSeeCardIconConnectedToCardTypeCardType(String cardType) {
        //ToDo
        paymentPage.validateIfCardTypeIconWasAsExpected(cardType);
    }

    @When("^User fills payment form with incorrect or missing data: card number ([^\"]*), cvc ([^\"]*) and expiration date ([^\"]*)$")
    public void userFillsPaymentFormWithIncorrectOrMissingDataCardNumberCardNumberCvcCvcAndExpirationDateExpiration(String cardNumber, String cvc, String expirationDate) {
        paymentPage.fillAllCardData(cardNumber, cvc, expirationDate);
    }

    @Then("^User will see validation message ([^\"]*) under ([^\"]*) field$")
    public void userWillSeeValidationMessageMessageUnderFieldTypeField(String message, String fieldType) {
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

    @And("^User clicks Pay button - response set to ([^\"]*)$")
    public void userClicksPayButtonResponseSetToPaymentCode(String paymentCode) {
        switch (paymentCode) {
            case "success":
                stubCreditCardSuccessfulPayment();
                break;
            case "30000":
                stubCreditCardDeclinedPayment();
                break;
            case "50000":
                stubCreditCardDeclinedPayment();
                break;
            case "70000":
                stubCreditCardDeclinedPayment();
                break;
        }
        paymentPage.clickPayButton();
    }


    @When("^User fills merchant data name \"([^\"]*)\", email \"([^\"]*)\", phone \"([^\"]*)\"$")
    public void userFillsMerchantDataNameEmailPhone(String name, String email, String phone) throws Throwable {
        paymentPage.fillAllMerchantData(name, email, phone);
    }

    @And("^User clicks Pay button and mock data$")
    public void userClicksPayButtonAndMockData() throws InterruptedException {
        stubCreditCardSuccessfulPayment();
        SeleniumExecutor.getDriver().get("http://localhost:8761/payment");
        Thread.sleep(5000);
    }
}
