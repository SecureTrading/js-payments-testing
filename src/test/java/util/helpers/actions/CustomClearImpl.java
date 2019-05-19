package util.helpers.actions;

import static util.helpers.actions.CustomWaitImpl.waitForElementDisplayed;
import static util.helpers.actions.CustomWaitImpl.waitForJsJq;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class CustomClearImpl {
    /**
     * Wait for element to be displayed, then clear
     * @param e WebElement
     */
    public static void clear(WebElement e) {
        waitForJsJq();
        waitForElementDisplayed(e);
        e.clear();
    }

    /**
     * Wait for element to be displayed, then clear using ctrl + a, delete
     * @param e WebElement
     */
    public static void clearByKeys(WebElement e) {
        waitForJsJq();
        waitForElementDisplayed(e);
        e.sendKeys(Keys.CONTROL + "a");
        e.sendKeys(Keys.DELETE);
    }
}