package com.SecureTrading.stepdefs;

import com.SecureTrading.pageobjects.PaymentPage;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import util.SeleniumExecutor;
import util.enums.CardFieldType;
import util.enums.PaymentType;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentPageSteps {

    private PaymentPage paymentPage;

    public PaymentPageSteps(){
        paymentPage = new PaymentPage();
    }

    @Given("^User opens page with payment form$")
    public void userOpensPageWithPaymentForm(String url) {
        SeleniumExecutor.getDriver().get(url);
    }


    @When("^User fills payment form with credit card number ([^\"]*), cvc ([^\"]*) and expiration date ([^\"]*)$")
    public void userFillsPaymentFormWithCreditCardNumberCardNumberCvcCvcAndExpirationDateExpirationDate(String cardNumber, String cvc, String expirationDate) {
        paymentPage.fillCreditCardInputField(CardFieldType.number, cardNumber);
        paymentPage.fillCreditCardInputField(CardFieldType.number, cvc);
        paymentPage.fillCreditCardInputField(CardFieldType.number, expirationDate);
    }

    @And("^User clicks Pay button$")
    public void userClicksPayButton() {
        paymentPage.clickSubmitButton();
    }

    @Then("^User will see information about successful payment 'Successful Payment!'$")
    public void userWillSeeInformationAboutSuccessfulPaymentSuccessfulPayment(String message) {
        //ToDo
        //Simple assert or make same as validateIfFieldValidationMessageWasAsExpected?
        assertEquals(message, paymentPage.getSuccessfulPaymentMessage());
    }

    @When("^User fills credit card number field with number ([^\"]*)$")
    public void userFillsCreditCardNumberFieldWithNumberCardNumber(String cardNumber) {
        paymentPage.fillCreditCardInputField(CardFieldType.number, cardNumber);
    }

    @Then("^User will should see card icon connected to card type$")
    public void userWillShouldSeeCardIconConnectedToCardType() {
        //ToDo
    }

    @Then("^User will see information about declined payment$")
    public void userWillSeeInformationAboutDeclinedPayment(String message) {
        //ToDo
        //Simple assert or make same as validateIfFieldValidationMessageWasAsExpected?
        assertEquals(message, paymentPage.getErrorPaymentMessage());
    }

    @When("^User fills payment form with incorrect or missing data: card number ([^\"]*), cvc ([^\"]*) and expiration date ([^\"]*)$")
    public void userFillsPaymentFormWithIncorrectOrMissingDataCardNumberCardNumberCvcCvcAndExpirationDateExpiration(String cardNumber, String cvc, String expirationDate) {
        paymentPage.fillCreditCardInputField(CardFieldType.number, cardNumber);
        paymentPage.fillCreditCardInputField(CardFieldType.number, cvc);
        paymentPage.fillCreditCardInputField(CardFieldType.number, expirationDate);
    }

    @Then("^User should see validation message ([^\"]*) under ([^\"]*) field$")
    public void userShouldSeeValidationMessageMessageUnderFieldTypeField(String message, String cardFieldType) {
        //Check cast from string to enum
        paymentPage.validateIfFieldValidationMessageWasAsExpected(CardFieldType.fromString(cardFieldType), message);
    }


    @Then("^He will see validation message \"([^\"]*)\" under credit card number field$")
    public void heWillSeeValidationMessageUnderCreditCardNumberField(String message) throws Throwable {
        paymentPage.validateIfFieldValidationMessageWasAsExpected(CardFieldType.number, message);
    }

    @When("^I click tooltip icon next to CVV/CVC$")
    public void iClickTooltipIconNextToCVVCVC() {
        paymentPage.clickCvvTooltipIcon();
    }

    @Then("^I should see information about this field$")
    public void iShouldSeeInformationAboutThisField() {
        assertFalse(paymentPage.getCvvTooltipText().isEmpty());
    }


    @When("^User chooses ApplePay as payment method$")
    public void userChoosesApplePayAsPaymentMethod() {
        paymentPage.choosePaymentMethod(PaymentType.applePay);
    }

    @When("^User chooses VisaCheckout as payment method$")
    public void userChoosesVisaCheckoutAsPaymentMethod() {
        paymentPage.choosePaymentMethod(PaymentType.visaCheckout);
    }

    @When("^User chooses PayPal as payment method$")
    public void userChoosesPayPalAsPaymentMethod() {
        paymentPage.choosePaymentMethod(PaymentType.payPal);
    }

    @And("^User will should see the same provided data on animated credit card \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\"$")
    public void userWillShouldSeeTheSameProvidedDataOnAnimatedCreditCardCardNumberCvcAndExpirationDate(String cardNumber, String cvc, String expirationDate) {
        assertEquals(cardNumber, paymentPage.getCreditCardNumberFromAnimatedCardText());
        assertEquals(cvc, paymentPage.getCvcFromAnimatedCardText());
        assertEquals(expirationDate, paymentPage.getExpirationDateFromAnimatedCard());
    }

}
