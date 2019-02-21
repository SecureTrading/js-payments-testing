package com.SecureTrading.stepdefs;

import static io.restassured.RestAssured.given;

import com.SecureTrading.pageobjects.SecureTradingPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.restassured.http.ContentType;
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

    @Then("Visit pet endpoint$")
    public void visit_pet_endpoint() {
        System.out.println("----------------------------------------------------------### visiting external endpoint using browser");
        SeleniumExecutor.getDriver().get("https://mtx2z9j2k7.execute-api.us-east-1.amazonaws.com/prod/pet");
        System.out.println("----------------------------------------------------------### visiting external endpoint using browser");
    }

    @Then("Make rest assured request$")
    public void make_rest_assured_request() {
        System.out.println("----------------------------------------------------------### external endpoint by api");
        System.out.println(given()
                .when()
                .get("https://mtx2z9j2k7.execute-api.us-east-1.amazonaws.com/prod/pet").asString());
        System.out.println("----------------------------------------------------------### external endpoint by api");
    }

    @Then("Make rest assured request on local host$")
    public void make_rest_assured_request_with_proxy() {
        System.out.println("----------------------------------------------------------### external endpoint by api as defined host");
        System.out.println(given()
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:8760/pet").asString());
        System.out.println("----------------------------------------------------------### external endpoint by api as defined host");
    }
}