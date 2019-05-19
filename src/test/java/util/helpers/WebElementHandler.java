package util.helpers;

import static util.helpers.IframeHandler.switchToIframe;
import static util.helpers.actions.CustomWaitImpl.waitForElementDisplayed;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.SeleniumExecutor;

public class WebElementHandler {
    /**
     * Get how metadata from element
     * @param we WebElement
     * @return String
     */
    public static String getHowFromWebElement(WebElement we) {
        String webElementDesc = we.toString();
        String byDesc = webElementDesc.split("->")[1];
        byDesc = StringUtils.removeEnd(byDesc, "]");
        return byDesc.split(":")[0].trim();
    }

    /**
     * Get using metadata from element
     * @param we WebElement
     * @return String
     */
    public static String getUsingFromWebElement(WebElement we) {
        String webElementDesc = we.toString();
        String byDesc = webElementDesc.split("->")[1];
        byDesc = StringUtils.removeEnd(byDesc, "]");
        return byDesc.split(":")[1].trim();
    }

    /**
     * Update DOM to display hidden element
     * @param we WebElement
     */
    public static void showHiddenWebElement(WebElement we) {
        if (!we.isDisplayed()) {
            String using = getUsingFromWebElement(we);
            JavascriptExecutor js = (JavascriptExecutor) SeleniumExecutor.getDriver();
            js.executeScript("$('#" + using + "').css('display','block')");
        }
    }

    /**
     * Check if element is present
     * @param by locator
     * @return boolean
     */
    public static boolean isElementPresent(By by) {
        WebDriverWait waitDriver = SeleniumExecutor.getWaitDriver();
        try {
            waitDriver.until(ExpectedConditions.presenceOfElementLocated(by));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * Check if element is present
     * @param we WebElement
     * @return boolean
     */
    public static boolean isElementPresent(WebElement we) {
        WebDriverWait waitDriver = SeleniumExecutor.getWaitDriver();
        try {
            waitDriver.until(ExpectedConditions.elementToBeClickable(we));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        } catch (TimeoutException e) {
            return false;
        } catch (StaleElementReferenceException e)  {
            return false;
        }
    }

    /**
     * Check if element with exact given text is displayed
     * @param we WebElement
     * @param value text
     * @return boolean
     */
    public static boolean isElementWithExactTextDisplayed(WebElement we, String value) {
        try {
            waitForElementDisplayed(we);
        } catch (TimeoutException e) {
            e.printStackTrace();
            return false;
        }

        return we.getText().trim().equals(value);
    }

    /**
     * Check if element containing given text is displayed
     * @param we WebElement
     * @param value text
     * @return boolean
     */
    public static boolean isElementWithTextDisplayed(WebElement we, String value) {
        try {
            waitForElementDisplayed(we);
        } catch (TimeoutException e) {
            e.printStackTrace();
            return false;
        }

        return we.getText().trim().contains(value);
    }

    /**
     * Check if element is displayed
     * @param by locator
     * @return boolean
     */
    public static boolean isElementDisplayed(By by) {
        WebDriver driver = SeleniumExecutor.getDriver();
        List<WebElement> list = driver.findElements(by);

        if (list.size() == 0) {
            return false;
        } else {
            return list.get(0).isDisplayed();
        }
    }

    /**
     * Check if element is displayed
     * @param we WebElement
     * @return boolean
     */
    public static boolean isElementDisplayed(WebElement we) {

        try {
            waitForElementDisplayed(we);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}