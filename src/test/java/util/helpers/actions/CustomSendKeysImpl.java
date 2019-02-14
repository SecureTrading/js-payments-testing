package util.helpers.actions;

import static util.helpers.actions.CustomWaitImpl.waitForElementDisplayed;
import static util.helpers.actions.CustomWaitImpl.waitForJsJq;

import org.openqa.selenium.WebElement;

public class CustomSendKeysImpl {
    /**
     * Wait for element to be displayed, then send keys
     * @param e WebElement
     * @param text string to type
     */
    public static void sendKeys(WebElement e, String text) {
        waitForJsJq();
        waitForElementDisplayed(e);
        e.sendKeys(text);
    }

    /**
     * Clear element before send keys
     * @param e WebElement
     * @param text string to type
     */
    public static void sendKeysWithClear(WebElement e, String text) {
        CustomClearImpl.clear(e);
        e.sendKeys(text);
    }
}