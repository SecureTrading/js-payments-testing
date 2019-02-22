package com.SecureTrading.stepdefs.hooks;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import util.PicoContainerHelper;
import util.enums.StoredElement;

public class BeforeHooks {

    @Before
    public void beforeScenario(Scenario scenario) {
        PicoContainerHelper.cleanContainer();
        PicoContainerHelper.updateInContainer(StoredElement.scenarioName, scenario.getName());
        System.out.println("------------------------------------- test started " + PicoContainerHelper.getFromContainer(StoredElement.scenarioName));
        System.out.println("-------------------------------------");
    }
}