package com.SecureTrading.stepdefs.hooks;

import static util.MocksHandler.*;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import org.junit.Assume;
import util.PicoContainerHelper;
import util.enums.StoredElement;

public class BeforeHooks {

    @Before(order = 1)
    public void beforeScenario(Scenario scenario) {
        PicoContainerHelper.cleanContainer();
        PicoContainerHelper.updateInContainer(StoredElement.scenarioName, scenario.getName());
        stubSTRequestType("jsinit.json", "JSINIT"); // Stub so Cardinal can init but don't use cardinal
    }
}