package com.SecureTrading.stepdefs.hooks;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import util.PicoContainerHelper;
import util.enums.StoredElement;

public class BeforeHooks {

    @Before
    public void beforeScenario(Scenario scenario) {
        PicoContainerHelper.cleanContainer();
        PicoContainerHelper.addToContainer(StoredElement.scenarioName, scenario.getName());
    }
}