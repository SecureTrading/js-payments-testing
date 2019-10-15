package com.SecureTrading.stepdefs.hooks;

import static util.MocksHandler.*;
import static util.helpers.TestConditionHandler.checkIfScenarioNameContainsText;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import util.PicoContainerHelper;
import util.enums.PropertyType;
import util.enums.RequestType;
import util.enums.StoredElement;
import util.enums.responses.Configuration;

public class BeforeHooks {

    @Before(order = 0)
    public void beforeScenario(Scenario scenario) {
        PicoContainerHelper.cleanContainer();
        PicoContainerHelper.updateInContainer(StoredElement.scenarioName, scenario.getName());
        if (!checkIfScenarioNameContainsText("skipped JSINIT process"))
            stubSTRequestType("jsinit.json", RequestType.JSINIT); // Stub so Cardinal can init but don't use cardinal
    }

    @Before("(@smokeTest or #fullTest) and (not @updatedJwtTrue) " +
            "and (not @submitOnSuccessTrue) and (not @fieldStyle) " +
            "and (not @animatedCardTrue) and (not @animatedCardRepoTest)")
    public void beforeTest() {
        stubConfig(PropertyType.CONFIG_MOCK_URI, Configuration.CONFIG.getMockJson());
    }

    @Before("@submitOnSuccessTrue")
    public void beforeSubmitOnSuccessTest() {
        stubConfig(PropertyType.CONFIG_MOCK_URI, Configuration.SUBMIT_ON_SUCCESS_TRUE.getMockJson());
    }

    @Before("@animatedCardTrue")
    public void beforeAnimatedCardTest() {
        stubConfig(PropertyType.CONFIG_MOCK_URI, Configuration.ANIMATED_CARD.getMockJson());
    }

    @Before("@fieldStyle")
    public void beforeIndividualFieldStyleTest() {
        stubConfig(PropertyType.CONFIG_MOCK_URI, Configuration.FIELD_STYLE.getMockJson());
    }

//ToDo Uncomment when Update Jwt feature will be merged into develop
//    @Before("@updatedJwtTrue")
//    public void beforeUpdateJwtTest() {
//        stubConfig(PropertyType.CONFIG_MOCK_URI, Configuration.UPDATE_JWT.getMockJson());
//    }
}