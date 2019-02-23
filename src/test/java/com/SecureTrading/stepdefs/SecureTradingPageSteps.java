package com.SecureTrading.stepdefs;

import static io.restassured.RestAssured.given;
import static util.MocksHandler.stubVersion1;
import static util.MocksHandler.stubVersion2;

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

    @Given("^User is on the website '(.+)'$")
    public void User_is_on_the_website(String url) {
        SeleniumExecutor.getDriver().get(url);
    }

    @Then("The page header contains '(.+)'$")
    public void page_header_contains(String expectedText) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        secureTradingPage.checkIfPageHeaderContainsRequiredText(expectedText);
    }

    //Only as example to be removed
    @Then("User visits pet endpoint version 1$")
    public void user_visits_pet_endpoint_version_1() {
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% - version 1");
        stubVersion1();

        SeleniumExecutor.getDriver().get("http://localhost:8760/pet");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% - version 1");
    }

    //Only as example to be removed
    @Then("User visits pet endpoint version 2$")
    public void user_visits_pet_endpoint_version_2() {
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% - version 2");
        stubVersion2();

        SeleniumExecutor.getDriver().get("http://localhost:8760/pet");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% - version 2");
    }

    //Only as example to be removed
    @Then("Make rest assured pet request on local host$")
    public void make_rest_assured_request_with_proxy() {
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% - restassured call");
        System.out.println(given()
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:8760/pet").asString());
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% - restassured call");
    }
}