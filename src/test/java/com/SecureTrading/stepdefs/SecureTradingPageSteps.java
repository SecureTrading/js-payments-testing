package com.SecureTrading.stepdefs;

import com.SecureTrading.pageobjects.SecureTradingPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import util.SeleniumExecutor;

import static io.restassured.RestAssured.given;

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

    @Then("Make rest assured request$")
    public void test() {
        System.out.println("--------------------------------------------------");
        System.out.println(given()
                .spec(buildJsonRequestSpecification())
                .when()
                .get("/pet").asString());
        System.out.println("--------------------------------------------------");
    }

    public RequestSpecification buildJsonRequestSpecification() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("http://localhost:8089/")
                .build();
    }
}