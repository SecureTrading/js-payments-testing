package com.SecureTrading.stepdefs.hooks;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import util.PicoContainerHelper;
import util.enums.StoredElement;

import static util.MocksHandler.startWireMockServer;

public class BeforeHooks {

    @Before
    public void beforeScenario(Scenario scenario) {
        startWireMockServer();
        PicoContainerHelper.cleanContainer();
        PicoContainerHelper.updateInContainer(StoredElement.scenarioName, scenario.getName());
        System.out.println("------------------------------------- test started " + PicoContainerHelper.getFromContainer(StoredElement.scenarioName));
        System.out.println("-------------------------------------");
    }
}