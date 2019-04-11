package com.SecureTrading.runners;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(strict = true,
        features = {"src/test/java/resources/features"},
        format = {"html:target/cucumber-parallel/1.html", "pretty"},
        monochrome = true,
        tags = {"@animatedCard,@visaTest"},
        glue = { "com.SecureTrading.stepdefs" })
public class RunFeatureTest {
}
