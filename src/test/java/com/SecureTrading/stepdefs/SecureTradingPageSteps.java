package com.SecureTrading.stepdefs;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.given;

import com.SecureTrading.pageobjects.SecureTradingPage;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.servlet.WireMockHandlerDispatchingServlet;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.restassured.http.ContentType;
import util.SeleniumExecutor;

public class SecureTradingPageSteps {

    private SecureTradingPage secureTradingPage;
    MocksHandler mocksHandler;

    public SecureTradingPageSteps(){
        secureTradingPage = new SecureTradingPage();
        mocksHandler = new MocksHandler();
        mocksHandler.startWireMockServer();
    }

    @Given("^User is on the website '(.+)'$")
    public void User_is_on_the_website(String url) {
        SeleniumExecutor.getDriver().get(url);
    }

    @Then("The page header contains '(.+)'$")
    public void page_header_contains(String expectedText) {
        secureTradingPage.checkIfPageHeaderContainsRequiredText(expectedText);
    }

    //Only as example to be removed
    @Then("User visits pet endpoint version 1$")
    public void user_visits_pet_endpoint_version_1() {
        mocksHandler.stubVersion1();

        SeleniumExecutor.getDriver().get("http://localhost:8760/pet");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mocksHandler.stopWireMockServer();
    }

    //Only as example to be removed
    @Then("User visits pet endpoint version 2$")
    public void user_visits_pet_endpoint_version_2() {
        mocksHandler.stubVersion2();

        SeleniumExecutor.getDriver().get("http://localhost:8760/pet");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mocksHandler.stopWireMockServer();
    }

    //Only as example to be removed
    @Then("Make rest assured pet request on local host$")
    public void make_rest_assured_request_with_proxy() {
        System.out.println(given()
                .contentType(ContentType.JSON)
                .when()
                .get("http://localhost:8760/pet").asString());
    }

    public class MocksHandler {

        public WireMockServer wireMockServer;

        public  void init() {
            wireMockServer = new WireMockServer(wireMockConfig().port(8760));
            wireMockServer.start();
        }

        public void startWireMockServer() {
            init();
        }

        public void stopWireMockServer() {
            wireMockServer.stop();
        }

        public void stubVersion1() {
            wireMockServer.stubFor(get(urlEqualTo("/pet"))
                    .willReturn(aResponse().withStatus(200).withBody("Hello world version 1")));
        }

        public  void stubVersion2() {
            wireMockServer.stubFor(get(urlEqualTo("/pet"))
                    .willReturn(aResponse().withStatus(200).withBody("Hello world version 2")));
        }
    }
}