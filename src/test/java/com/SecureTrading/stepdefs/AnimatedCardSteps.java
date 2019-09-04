package com.SecureTrading.stepdefs;

import com.SecureTrading.pageobjects.AnimatedCardModule;
import com.SecureTrading.pageobjects.PaymentPage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import util.PicoContainerHelper;
import util.enums.FieldType;
import util.enums.StoredElement;

import static util.helpers.IframeHandler.switchToDefaultIframe;

public class AnimatedCardSteps {

    private PaymentPage paymentPage;
    private AnimatedCardModule animatedCardModule;

    public AnimatedCardSteps() {
        paymentPage = new PaymentPage();
        animatedCardModule = new AnimatedCardModule();
    }

    @When("User fills payment form with data: \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\"")
    public void userFillsPaymentFormWithDataAnd(String cardNumber, String expiryDate, String cvv) {
        animatedCardModule.fillPaymentFormWithoutIFrames(cardNumber, expiryDate, cvv);
    }

    @And("User changes the field")
    public void userChangesTheField() {
        switchToDefaultIframe();
    }

    @And("User will see that ([^\"]*) no-iframe-field is highlighted")
    public void userWillSeeThatFieldNoIframeFieldIsHighlighted(FieldType fieldType) {
        animatedCardModule.validateIfNoIFrameFieldIsHighlighted(fieldType);
    }

    @Then("User will see \"([^\"]*)\" message under no-iframe-field: (.*)")
    public void userWillSeeMessageUnderNoIframeFieldField(String message, FieldType fieldType) {
        animatedCardModule.validateIfNoIFrameFieldValidationMessageWasAsExpected(fieldType, message);
    }

    @Then("User will see correct card icon for ([^\"]*)")
    public void userWillSeeCorrectCardIconForCardType(String cardType) {
        PicoContainerHelper.updateInContainer(StoredElement.cardType, cardType);
        animatedCardModule.validateIfCardTypeIconWasAsExpectedForWithoutIframe(cardType.toLowerCase());
    }

    @And("User will see correct data on animated credit card ([^\"]*), ([^\"]*) and ([^\"]*)")
    public void userWillSeeCorrectDataOnAnimatedCreditCardFormattedCardNumberExpirationDateAndCvc(String cardNumber,
                                                                                                  String expirationDate, String cvc) {
        animatedCardModule.validateIfAllProvidedDataOnAnimatedCardWasAsExpectedWithoutIframe(cardNumber, expirationDate, cvc);
    }

    @And("User will see animated card is flipped, except for {string}")
    public void userWillSeeAnimatedCardIsFlippedExceptFor(String cardType) throws InterruptedException {
        animatedCardModule.validateIfAnimatedCardIsFlippedWithoutIframe(cardType.equals("AMEX"));
    }
}
