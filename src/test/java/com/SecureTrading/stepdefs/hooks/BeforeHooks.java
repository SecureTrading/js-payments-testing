package com.SecureTrading.stepdefs.hooks;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import org.junit.Assume;
import util.PicoContainerHelper;
import util.enums.StoredElement;

public class BeforeHooks {

    @Before(order=1)
    public void beforeScenario(Scenario scenario) {
        PicoContainerHelper.cleanContainer();
        PicoContainerHelper.updateInContainer(StoredElement.scenarioName, scenario.getName());
    }
}