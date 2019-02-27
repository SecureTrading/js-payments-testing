package com.SecureTrading.stepdefs.hooks;

import static util.RequestExecutor.markTestAsFailed;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import util.PicoContainerHelper;
import util.SeleniumExecutor;
import util.enums.StoredElement;

public class AfterHooks {

    @After
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            markTestAsFailed();
        }

        if (SeleniumExecutor.getLocal() != null) {
            try {
                SeleniumExecutor.getLocal().stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        SeleniumExecutor.stop();

        System.out.println("------------------------------------- test ended " + PicoContainerHelper.getFromContainer(StoredElement.scenarioName));
        System.out.println("-------------------------------------");
    }
}