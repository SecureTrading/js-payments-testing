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
    private boolean fieldInIframe = PicoContainerHelper.getFromContainer(StoredElement.isFieldInIframe, Boolean.class);

    public AnimatedCardSteps() {
        animatedCardModule = new AnimatedCardModule();
    }

    @Given("User opens page with animated card")
    public void userOpensPageWithAnimatedCard() throws InterruptedException {
        animatedCardModule.OpenPage(getProperty(PropertyType.BASE_URI));
        if (!animatedCardModule.waitUntilPageIsLoaded())
            animatedCardModule.OpenPage(getProperty(PropertyType.BASE_URI));
    }

    @When("^User fills payment form with data: \"([^\"]*)\", \"([^\"]*)\"(?: and \"([^\"]*)\"|)$")
    public void userFillsPaymentFormWithDataAnd(String cardNumber, String expiryDate, String cvv) throws InterruptedException {
        animatedCardModule.fillPaymentFormWithoutIFrames(cardNumber, expiryDate, cvv);
    }

    @Then("^User will see card icon connected to card type ([^\"]*)$")
    public void userWillSeeCardIconConnectedToCardTypeCardType(String cardType) {
        PicoContainerHelper.updateInContainer(StoredElement.cardType, cardType);
        animatedCardModule.validateIfCardTypeIconWasAsExpected(cardType.toLowerCase(), fieldInIframe);
    }

    @And("^User will see the same provided data on animated credit card \"([^\"]*)\", \"([^\"]*)\"(?: and \"([^\"]*)\"|)$")
    public void userWillSeeTheSameProvidedDataOnAnimatedCreditCardCardNumberExpirationDateAndCvc(String cardNumber,
                                                                                                 String expirationDate, String cvc) {
        animatedCardModule.validateIfAllProvidedDataOnAnimatedCardWasAsExpected(cardNumber, expirationDate, cvc, fieldInIframe);
    }

    @And("^User will see that animated card is flipped, except for \"([^\"]*)\"$")
    public void userWillSeeThatAnimatedCardIsFlippedExceptFor(String cardType) throws InterruptedException {
        animatedCardModule.validateIfAnimatedCardIsFlipped(cardType.equals("AMEX"), fieldInIframe);
    }

    @And("User changes the field focus")
    public void userChangesTheFieldFocus() {
        animatedCardModule.changeFieldFocus();
    }
}