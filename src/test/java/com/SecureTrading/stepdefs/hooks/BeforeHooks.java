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

    @Before(order=0)
    public void beforeScenarioCheckIosSystemForApplePay(Scenario scenario) {
        if(scenario.getName().contains("ApplePay")){
            if((System.getProperty("device") == null && !System.getProperty("browser").equals("Safari")) ||
                    (System.getProperty("browser") == null && !System.getProperty("device").startsWith("i"))){
                Assume.assumeTrue("SKIP SCENARIO as iOS system and Safari is required for Apple pay test: " + scenario.getName(),false);
            }
        }
    }
}