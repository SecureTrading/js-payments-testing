package util.helpers.actions;

import static util.helpers.actions.CustomWaitImpl.waitForJsJq;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import util.SeleniumExecutor;

public class CustomFindImpl {
    /**
     * Find and scroll to children element
     * @param e parent WebElement
     * @param by children locator
     * @return children WebElement
     */
    public static WebElement findElement(WebElement e, By by) {
        waitForJsJq();
        WebElement element = e.findElement(by);
        CustomScrollImpl.scrollToElement(element);
        return element;
    }

    /**
     * Find children elements
     * @param e parent WebElement
     * @param by children locator
     * @return list of children WebElements
     */
    public static List<WebElement> findElements(WebElement e, By by) {
        waitForJsJq();
        return e.findElements(by);
    }

    /**
     * Find elements
     * @param by locator
     * @return list of WebElements
     */
    public static List<WebElement> findElements(By by) {
        waitForJsJq();
        return SeleniumExecutor.getDriver().findElements(by);
    }

    /**
     * Find element if displayed
     * @param by locator
     * @return WebElement
     */
    public static WebElement findElement(By by) {
        WebDriverWait waitDriver = SeleniumExecutor.getWaitDriver();
        return waitDriver.until(ExpectedConditions.visibilityOfElementLocated(by));
    }
}