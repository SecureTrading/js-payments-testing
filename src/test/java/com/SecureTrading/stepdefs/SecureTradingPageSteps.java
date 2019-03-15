package com.SecureTrading.stepdefs;

import com.SecureTrading.pageobjects.SecureTradingPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.junit.Assert;
import util.PicoContainerHelper;
import util.SeleniumExecutor;
import util.enums.StoredElement;

public class SecureTradingPageSteps {

    private SecureTradingPage secureTradingPage;

    public SecureTradingPageSteps() {
        secureTradingPage = new SecureTradingPage();
    }

    @Then("The page content contains '(.+)'$")
    public void page_header_contains(String expectedText) {
        String text = SeleniumExecutor.getDriver().getPageSource();
        try {
            Assert.assertTrue(text.contains(expectedText));
        } catch (Exception e) {
            PicoContainerHelper.updateInContainer(StoredElement.errorMessage,
                    "Wrong endpoint triggered e: " + expectedText + " a:" + text);
        }
    }

    // Only as example to be removed
    @Given("User visits pet endpoint version 1$")
    public void user_visits_pet_endpoint_version_1() throws InterruptedException {
        //stubVersion1();
        Thread.sleep(1000);
        SeleniumExecutor.getDriver().get("http://localhost:8760/pet");
        SeleniumExecutor.getDriver().navigate().refresh();
    }

    // Only as example to be removed
    @Given("User visits pet endpoint version 2$")
    public void user_visits_pet_endpoint_version_2() throws InterruptedException {
        //stubVersion2();
        Thread.sleep(1000);
        SeleniumExecutor.getDriver().get("http://localhost:8760/pet");
        SeleniumExecutor.getDriver().navigate().refresh();
    }
}