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

    @Then("Visit pet endpoint$")
    public void visit_pet_endpoint() {
        SeleniumExecutor.getDriver().get("https://mtx2z9j2k7.execute-api.us-east-1.amazonaws.com/prod/pet");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("Make rest assured request$")
    public void make_rest_assured_request() {
        System.out.println("----------------------------------------------------------### external");
        System.out.println(given()
                .spec(buildJsonRequestSpecification())
                .when()
                .get("/pet").asString());
        System.out.println("----------------------------------------------------------### external");
    }

    @Then("Make rest assured request on local host$")
    public void make_rest_assured_request_with_proxy() {
        System.out.println("----------------------------------------------------------### local host");
        System.out.println(given()
                .spec(buildJsonRequestSpecification())
                .when()
                .get("http://localhost:8760/pet").asString());
        System.out.println("----------------------------------------------------------### local host");
    }

    public RequestSpecification buildJsonRequestSpecification() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("https://mtx2z9j2k7.execute-api.us-east-1.amazonaws.com/prod/")
                .build();
    }
}