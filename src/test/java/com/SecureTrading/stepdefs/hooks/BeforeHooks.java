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
        PicoContainerHelper.updateInContainer(StoredElement.scenarioTag, scenario.getSourceTagNames());
        if (!checkIfScenarioNameContainsText("skipped JSINIT process"))
            stubSTRequestType("jsinit.json", RequestType.JSINIT); // Stub so Cardinal can init but don't use cardinal
    }
}