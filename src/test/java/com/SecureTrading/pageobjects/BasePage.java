package com.SecureTrading.pageobjects;

import util.SeleniumExecutor;
import util.enums.PropertyType;

import static util.PropertiesHandler.getProperty;

public class BasePage {

    public BasePage() {
        SeleniumExecutor.getExecutor();
    }

    public void OpenPage(String page){
        SeleniumExecutor.getDriver().get(page);
    }
}