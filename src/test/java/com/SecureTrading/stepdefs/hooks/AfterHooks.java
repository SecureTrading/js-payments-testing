package com.SecureTrading.stepdefs.hooks;

import static util.MocksHandler.stopWireMockServer;
import static util.RequestExecutor.markTestAsFailed;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import util.SeleniumExecutor;

public class AfterHooks {

    @After
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            markTestAsFailed();
        }

        stopWireMockServer();

        if (SeleniumExecutor.getLocal() != null) {
            try {
                SeleniumExecutor.getLocal().stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        SeleniumExecutor.stop();
    }
}