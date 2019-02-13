package com.SecureTrading.pageobjects;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.openqa.selenium.By;
import util.PicoContainerHelper;
import util.SeleniumExecutor;
import util.enums.StoredElement;

public class SecureTradingPage extends BasePage {

    private By pageHeader = By.cssSelector("h1");

    public void checkIfPageHeaderContainsRequiredText(String headerText) {
        PicoContainerHelper.addToContainer(StoredElement.errorMessage,"Page Header does not contain required text: " + headerText);
        assertTrue(SeleniumExecutor.getDriver().findElement(pageHeader).getText().contains(headerText), PicoContainerHelper.getFromContainer(StoredElement.errorMessage, String.class));
    }
}