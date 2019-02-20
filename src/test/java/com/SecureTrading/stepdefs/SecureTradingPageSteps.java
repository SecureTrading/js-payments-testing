package com.SecureTrading.stepdefs;

import com.SecureTrading.pageobjects.SecureTradingPage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import util.SeleniumExecutor;

public class SecureTradingPageSteps {

    private SecureTradingPage secureTradingPage;

    public SecureTradingPageSteps(){
        secureTradingPage = new SecureTradingPage();
    }

    @Given("^I am on the website '(.+)'$")
    public void I_am_on_the_website(String url) {
        SeleniumExecutor.getDriver().get(url);
    }

    @Then("The page header should contain '(.+)'$")
    public void page_should_contain(String expectedText) {
        secureTradingPage.checkIfPageHeaderContainsRequiredText(expectedText);
    }


}