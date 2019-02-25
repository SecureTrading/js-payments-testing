package com.SecureTrading.stepdefs;

import static util.PropertiesHandler.getProperty;

import com.SecureTrading.pageobjects.PaymentPage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import util.SeleniumExecutor;
import util.enums.CardFieldType;

public class PaymentPageSteps {

    private PaymentPage paymentPage;

    public PaymentPageSteps(){
        paymentPage = new PaymentPage();
    }

    @Given("^User opens page with payment form$")
    public void userOpensPageWithPaymentForm() {
        SeleniumExecutor.getDriver().get(getProperty("baseUri"));
    }

    @When("^User fills payment form with credit card number ([^\"]*), cvc ([^\"]*) and expiration date ([^\"]*)$")
    public void userFillsPaymentFormWithCreditCardNumberCardNumberCvcCvcAndExpirationDateExpirationDate(String cardNumber, String cvc, String expirationDate) {
        paymentPage.fillAllCardData(cardNumber, cvc, expirationDate);
    }

    @And("^User clicks Pay button$")
    public void userClicksPayButton() {
        paymentPage.clickSubmitButton();
    }

    @Then("^User will see information about successful payment 'Successful Payment!'$")
    public void userWillSeeInformationAboutSuccessfulPaymentSuccessfulPayment(String message) {
        paymentPage.validateIfPaymentStatusMessageWasAsExpected(message);
    }

    @When("^User fills credit card number field with number ([^\"]*)$")
    public void userFillsCreditCardNumberFieldWithNumberCardNumber(String cardNumber) {
        paymentPage.fillCreditCardInputField(CardFieldType.number, cardNumber);
    }

    @Then("^User will see card icon connected to card type ([^\"]*)$")
    public void userWillSeeCardIconConnectedToCardTypeCardType(String cardType) {
        //ToDo
        paymentPage.validateIfCardTypeIconWasAsExpected(cardType);
    }

    @Then("^User will see information about declined payment$")
    public void userWillSeeInformationAboutDeclinedPayment(String message) {
        paymentPage.validateIfPaymentStatusMessageWasAsExpected(message);
    }

    @When("^User fills payment form with incorrect or missing data: card number ([^\"]*), cvc ([^\"]*) and expiration date ([^\"]*)$")
    public void userFillsPaymentFormWithIncorrectOrMissingDataCardNumberCardNumberCvcCvcAndExpirationDateExpiration(String cardNumber, String cvc, String expirationDate) {
        paymentPage.fillAllCardData(cardNumber, cvc, expirationDate);
    }

    @Then("^User will see validation message ([^\"]*) under ([^\"]*) field$")
    public void userWillSeeValidationMessageMessageUnderFieldTypeField(String message, String cardFieldType) {
        paymentPage.validateIfFieldValidationMessageWasAsExpected(CardFieldType.fromString(cardFieldType), message);
    }

    @Then("^He will see validation message \"([^\"]*)\" under credit card number field$")
    public void heWillSeeValidationMessageUnderCreditCardNumberField(String message) {
        paymentPage.validateIfFieldValidationMessageWasAsExpected(CardFieldType.number, message);
    }

    @When("^User clicks tooltip icon next to CVV/CVC$")
    public void userClicksTooltipIconNextToCVVCVC() {
        paymentPage.clickCvcTooltipIcon();
    }

    @Then("^User will see information about this field ([^\"]*)$")
    public void userWillSeeInformationAboutThisFieldCvcText(String cvcTooltipText) {
        //ToDo
        paymentPage.validateIfCvcTooltipTextWasAsExpected(cvcTooltipText);
    }

    @And("^User will see the same provided data on animated credit card ([^\"]*), ([^\"]*) and ([^\"]*)$")
    public void userWillSeeTheSameProvidedDataOnAnimatedCreditCardCardNumberCvcAndExpirationDate(String cardNumber, String cvc, String expirationDate) {
        paymentPage.validateIfAllProvidedDataOnAnimatedCardWasAsExpected(cardNumber, cvc, expirationDate);
    }
}
