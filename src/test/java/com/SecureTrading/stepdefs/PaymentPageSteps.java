package com.SecureTrading.stepdefs;

import static util.MocksHandler.*;
import static util.PropertiesHandler.getProperty;

import com.SecureTrading.pageobjects.PaymentPage;
import cucumber.api.PendingException;
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

    public PaymentPageSteps(){
        paymentPage = new PaymentPage();
    }

    @Given("^User opens page with payment form$")
    public void userOpensPageWithPaymentForm() {
        SeleniumExecutor.getDriver().get(getProperty(PropertyType.BASE_URI));
    }

    @When("^User fills payment form with credit card number \"([^\"]*)\", expiration date \"([^\"]*)\" and cvc \"([^\"]*)\"$")
    public void userFillsPaymentFormWithCreditCardNumberCardNumberExpirationDateExpirationDateAndCvcCvc(String cardNumber, String expirationDate, String cvc) {
        ((JavascriptExecutor) SeleniumExecutor.getDriver()).executeScript("window.scrollTo(0, document.body.scrollHeight)");
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
    public void userFillsPaymentFormWithIncorrectOrMissingDataCardNumberCardNumberExpirationDateExpirationAndCvcCvc(String cardNumber, String expirationDate, String cvc) {
        paymentPage.fillAllCardData(cardNumber, expirationDate, cvc);
    }

    @Then("^User will see validation message \"([^\"]*)\" under \"([^\"]*)\" field$")
    public void userWillSeeValidationMessageUnderField(String message, String fieldType) {
        paymentPage.validateIfFieldValidationMessageWasAsExpected(CardFieldType.fromString(fieldType), message);
    }

    @And("^User will see the same provided data on animated credit card ([^\"]*), ([^\"]*) and ([^\"]*)$")
    public void userWillSeeTheSameProvidedDataOnAnimatedCreditCardCardNumberExpirationDateAndCvc(String cardNumber, String expirationDate, String cvc) {
        paymentPage.validateIfAllProvidedDataOnAnimatedCardWasAsExpected(cardNumber, expirationDate, cvc);
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

    @And("^THREEDQUERY response set to \"([^\"]*)\"$")
    public void threedqueryResponseSetTo(String response) {
        switch (response) {
            case "entrolled Y":
                stubPaymentStatus(PropertyType.CC_MOCK_THREEDQUERY_URI, "ccTDQEnrolledY.json");
                break;
            case "not-entrolled N":
                stubPaymentStatus(PropertyType.CC_MOCK_THREEDQUERY_URI, "ccTDQEnrolledN.json");
                break;
            case "not-entrolled U":
                stubPaymentStatus(PropertyType.CC_MOCK_THREEDQUERY_URI, "ccTDQEnrolledU.json");
                break;
            case "30000":
                stubPaymentStatus(PropertyType.CC_MOCK_THREEDQUERY_URI, "ccTDQnvalidField.json");
                break;
            case "60031":
                stubPaymentStatus(PropertyType.CC_MOCK_THREEDQUERY_URI, "ccTDQInvalidAcquirer.json");
                break;
        }
    }

    @And("^ACS response set to \"([^\"]*)\"$")
    public void acsResponseSetTo(String response) {
        switch (response) {
            case "OK":
                stubPaymentStatus(PropertyType.CC_MOCK_ACS_URI, "ccACSoK.json");
                break;
        }
    }

    @And("^User clicks Pay button - AUTH response set to ([^\"]*)$")
    public void userClicksPayButtonAUTHResponseSetToPaymentCode(String paymentCode) {
        switch (paymentCode) {
            case "0":
                stubPaymentStatus(PropertyType.CC_AUTH_URI, "ccAUTHoK.json");
                break;
            case "30000":
                stubPaymentStatus(PropertyType.CC_AUTH_URI, "ccAUTHInvalidField.json");
                break;
            case "50000":
                stubPaymentStatus(PropertyType.CC_AUTH_URI, "ccAUTHSocketError.json");
                break;
            case "60022":
                stubPaymentStatus(PropertyType.CC_AUTH_URI, "ccAUTHUnauthenticated.json");
                break;
            case "70000":
                stubPaymentStatus(PropertyType.CC_AUTH_URI, "ccAUTHDeclineError.json");
                break;
        }
        paymentPage.choosePaymentMethodWithMock(PaymentType.cardinalCommerce);
    }

    @When("^User chooses \"([^\"]*)\" as payment method - response set to ([^\"]*)$")
    public void userChoosesAsPaymentMethodResponseSetToPaymentCode(String paymentMethod, String paymentCode) throws InterruptedException {
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
        paymentPage.choosePaymentMethodWithMock(PaymentType.fromString(paymentMethod));
    }

    @And("^User will see that notification frame has ([^\"]*) color$")
    public void userWillSeeThatNotificationFrameHasColorColor(String color) {
        paymentPage.validateIfColorOfNotificationFrameWasAsExpected(color);
    }

    @Then("^User will see Cardinal Commerce authentication modal$")
    public void userWillSeeCardinalCommerceAuthenticationModal() throws InterruptedException {
        paymentPage.validateIfCardinalCommerceAuthenticationModalIsDisplayed();
    }
}
