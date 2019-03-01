package util.helpers.actions;

import com.google.common.base.Predicate;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import util.SeleniumExecutor;

import java.util.List;
import java.util.Random;

import static util.helpers.WebElementHandler.isElementPresent;
import static util.helpers.actions.CustomWaitImpl.waitForElementDisplayed;
import static util.helpers.actions.CustomWaitImpl.waitForJsJq;

public class CustomSelectImpl {
    /**
     * Select element from dropdown by visible text
     * @param e WebElement
     * @param value text
     */
    public static void selectFromDropDown(WebElement e, String value) {
        waitForJsJq();
        isElementPresent(e);
        Select select = new Select(e);
        select.selectByVisibleText(value);
    }

    /**
     * Select element from dropdown by value attribute
     * @param e WebElement
     * @param value
     */
    public static void selectFromDropDownByValue(WebElement e, String value) {
        waitForJsJq();
        waitForElementDisplayed(e);
        Select select = new Select(e);
        select.selectByValue(value);
    }

    /**
     * Select element from dropdown by index
     * @param e WebElement
     * @param index
     */
    public static void selectFromDropDownByIndex(WebElement e, int index) {
        waitForJsJq();
        waitForElementDisplayed(e);
        Select select = new Select(e);
        select.selectByIndex(index);
    }
}
