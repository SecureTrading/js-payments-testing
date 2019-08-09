package util.helpers.actions;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Function;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.SeleniumExecutor;

import static util.helpers.WebElementHandler.isElementDisplayed;
import static util.helpers.WebElementHandler.isElementPresent;

public class CustomWaitImpl {
    /**
     * Wait for jQuery and Javascript to load
     *
     * @return true if finished
     */
    public static boolean waitForJsJq() {
        WebDriver webDriver = SeleniumExecutor.getDriver();
        WebDriverWait wait = SeleniumExecutor.getWaitDriver();
        // wait for jQuery to load
        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return ((Long) ((JavascriptExecutor) webDriver).executeScript("return jQuery.active") == 0);
                } catch (Exception e) {
                    return true;
                }
            }
        };

        // wait for Javascript to load
        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) webDriver).executeScript("return document.readyState").toString()
                        .equals("complete");
            }
        };

        return wait.until(jQueryLoad) && wait.until(jsLoad);
    }

    /**
     * Wait for element to be displayed
     *
     * @param e WebElement
     */
    public static void waitForElementDisplayed(WebElement e) {
        WebDriverWait waitDriver = SeleniumExecutor.getWaitDriver();
        try {
            waitDriver.until((Function<? super WebDriver, Boolean>) d -> e.isDisplayed());
        } catch (WebDriverException wde) {
            wde.printStackTrace();
        }
    }

    /**
     * Wait for element to be enabled
     *
     * @param e WebElement
     */
    public static void waitForElementEnabled(WebElement e) {
        WebDriverWait waitDriver = SeleniumExecutor.getWaitDriver();
        waitDriver.until((Function<? super WebDriver, Boolean>) d -> e.isEnabled());
    }

    /**
     * Wait for element to be clickable
     *
     * @param e WebElement
     */
    public static void waitForElementClickable(WebElement e) {
        WebDriverWait waitDriver = SeleniumExecutor.getWaitDriver();
        waitDriver.until(ExpectedConditions.elementToBeClickable(e));
    }

    /**
     * Wait for element to disappear
     *
     * @param e WebElement
     */
    public static void waitForElementNotDisplayed(WebElement e) {
        WebDriverWait waitDriver = SeleniumExecutor.getWaitDriver();
        waitDriver.until((Function<? super WebDriver, Boolean>) d -> e.isDisplayed());
        waitDriver.until((Function<? super WebDriver, Boolean>) d -> !isElementPresent(e));
    }

    /**
     * Wait for element located by to disappear
     *
     * @param by locator
     */
    public static void waitForElementNotDisplayed(By by) {
        WebDriverWait waitDriver = SeleniumExecutor.getWaitDriver();
        WebElement element = null;

        try {
            waitDriver.until((Function<? super WebDriver, Boolean>) d -> element.findElement(by).isDisplayed());
        } catch (Exception e) {
            waitDriver.until((Function<? super WebDriver, Boolean>) d -> element.findElements(by).size() == 0);
        }
    }

    /**
     * Wait for element not to contain text
     *
     * @param e    WebElement
     * @param text string to look for
     */
    public static void waitForElementTextNotContains(WebElement e, String text) {
        WebDriverWait waitDriver = SeleniumExecutor.getWaitDriver();
        waitDriver.until((Function<? super WebDriver, Boolean>) d -> !e.getText().toLowerCase().contains(text));
    }

    /**
     * Wait for page title to contain title
     *
     * @param title string to look for
     */
    public static void waitForPageTitleContains(String title) {
        WebDriverWait waitDriver = SeleniumExecutor.getWaitDriver();
        WebDriver driver = SeleniumExecutor.getDriver();

        waitDriver.until((Function<? super WebDriver, Boolean>) d -> driver.getTitle().toLowerCase().contains(title));
    }

    /**
     * Wait for list of elements to be displayed
     *
     * @param list list of WebElements
     */
    public static void waitForElementsDisplayed(List<WebElement> list) {
        WebDriverWait wait = SeleniumExecutor.getWaitDriver();
        wait.until(ExpectedConditions.visibilityOfAllElements(list));
    }

    /**
     * Wait for list of elements to disappear
     *
     * @param list list of WebElements
     */
    public static void waitForElementsNotDisplayed(List<WebElement> list) {
        WebDriverWait wait = SeleniumExecutor.getWaitDriver();
        wait.until(ExpectedConditions.not(ExpectedConditions.visibilityOfAllElements(list)));
    }

    /**
     * Wait for new window to open
     */
    public static void waitForAnyNotParentWindow() {
        WebDriverWait waitDriver = SeleniumExecutor.getWaitDriver();
        waitDriver.until(
                (Function<? super WebDriver, Boolean>) e -> SeleniumExecutor.getDriver().getWindowHandles().size() > 1);
    }

    /**
     * Wait for spinner/progress bar to disappear (hardcoded locator)
     */
    public static void waitUntilSpinnerNotDisplayed() {
        waitUntilSpinnerNotDisplayed(By.id("global-progress-indicator"));
    }

    /**
     * Wait for spinner/progress bar to disappear
     *
     * @param by locator
     */
    public static void waitUntilSpinnerNotDisplayed(By by) {
        List<WebElement> list = new ArrayList<WebElement>();

        do {
            list = SeleniumExecutor.getDriver().findElements(by);
        } while (list.size() != 0);
    }

    public static void waitUntilElementIsDisplayed(By by, int wait) throws InterruptedException {
        try {
            for (int i = 0; i <= wait; i++) {
                if (isElementDisplayed(by))
                    break;
                else
                    Thread.sleep(1000);
            }
        } catch (WebDriverException wde) {
            wde.printStackTrace();
        }
    }
}