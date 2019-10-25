package com.SecureTrading.stepdefs;

import com.SecureTrading.pageobjects.AnimatedCardModule;
import com.SecureTrading.pageobjects.PaymentPage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.simple.parser.ParseException;
import util.PicoContainerHelper;
import util.SeleniumExecutor;
import util.enums.FieldType;
import util.enums.PropertyType;
import util.enums.StoredElement;

import java.io.IOException;

import static util.PropertiesHandler.getProperty;

public class AnimatedCardSteps {

    private AnimatedCardModule animatedCardModule;
    private PaymentPage paymentPage;
    private boolean fieldInIframe = false;

    public AnimatedCardSteps() {
        animatedCardModule = new AnimatedCardModule();
        paymentPage = new PaymentPage();
    }

    @Given("User opens page with animated card")
    public void userOpensPageWithAnimatedCard() {
        SeleniumExecutor.getDriver().get(getProperty(PropertyType.BASE_URI));
    }

    @When("^User fills payment form with data: \"([^\"]*)\", \"([^\"]*)\"(?: and \"([^\"]*)\"|)$")
    public void userFillsPaymentFormWithDataAnd(String cardNumber, String expiryDate, String cvv) throws InterruptedException {
        animatedCardModule.fillPaymentFormWithoutIFrames(cardNumber, expiryDate, cvv);
    }

    @And("User will see that ([^\"]*) no-iframe-field is highlighted")
    public void userWillSeeThatFieldNoIframeFieldIsHighlighted(FieldType fieldType) {
        paymentPage.validateIfFieldIsHighlighted(fieldType, fieldInIframe);
    }

    @Then("User will see \"([^\"]*)\" message under no-iframe-field: (.*)")
    public void userWillSeeMessageUnderNoIframeFieldField(String message, FieldType fieldType) {
        paymentPage.validateIfFieldValidationMessageWasAsExpected(fieldType, message, fieldInIframe);
    }

    @Then("User will see correct card icon for ([^\"]*)")
    public void userWillSeeCorrectCardIconForCardType(String cardType) throws InterruptedException {
        PicoContainerHelper.updateInContainer(StoredElement.cardType, cardType);
        animatedCardModule.validateIfCardTypeIconWasAsExpected(cardType.toLowerCase(), fieldInIframe);
    }

    @And("^User will see correct data on animated credit card \"([^\"]*)\", \"([^\"]*)\"(?: and \"([^\"]*)\"|)$")
    public void userWillSeeCorrectDataOnAnimatedCreditCardFormattedCardNumberExpirationDateAndCvc(String cardNumber,
                                                                                                  String expirationDate, String cvc) {
        animatedCardModule.validateIfAllProvidedDataOnAnimatedCardWasAsExpected(cardNumber, expirationDate, cvc, fieldInIframe);
    }

    @And("User will see animated card is flipped, except for {string}")
    public void userWillSeeAnimatedCardIsFlippedExceptFor(String cardType) throws InterruptedException {
        animatedCardModule.validateIfAnimatedCardIsFlipped(cardType.equals("AMEX"), fieldInIframe);
    }

    @Then("User will see that labels on animated card are translated into ([^\"]*)")
    public void userWillSeeThatLabelsOnAnimatedCardAreTranslatedIntoLanguage(String language) throws IOException, ParseException {
        paymentPage.validateIfAnimatedCardTranslationWasAsExpected(language, fieldInIframe);
    }

    @And("User changes the field focus")
    public void userChangesTheFieldFocus() {
        animatedCardModule.changeFieldFocus();
    }

    @And("^User will see that (.*) no-iframe-field is disabled$")
    public void userWillSeeThatNoIframeFieldIsDisabled(FieldType fieldType) {
        paymentPage.validateIfFieldIsDisabled(fieldType, fieldInIframe);
    }
}
