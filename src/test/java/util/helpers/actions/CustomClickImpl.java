package util.helpers.actions;

import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import util.SeleniumExecutor;

import static util.helpers.actions.CustomWaitImpl.waitForElementDisplayed;

public class CustomClickImpl {
    /**
     * Click on element, then wait for Javascript to load
     * @param e WebElement
     */
    public static void click(WebElement e) {
        e.click();
        try {
            CustomWaitImpl.waitForJsJq();
        } catch (UnhandledAlertException ex) {
            ex.printStackTrace();
        }
        waitForElementDisplayed(e);
    }

    /**
     * Click on element using Actions
     * @param e WebElement
     */
    public static void performClick(WebElement e) {
        Actions action = new Actions(SeleniumExecutor.getDriver());
        action.moveToElement(e).click().perform();
    }
}