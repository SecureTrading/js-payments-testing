package util.helpers;

import org.openqa.selenium.WebDriver;
import util.SeleniumExecutor;

public class IframeHandler {
    /**
     * Switch to iframe identified by name
     * @param frameNameId String
     * @return WebDriver
     */
    public static WebDriver switchToIframe(String frameNameId) {
        WebDriver tmp = SeleniumExecutor.getDriver().switchTo().frame(frameNameId);
        try {
            Thread.sleep(SeleniumExecutor.TIMEOUT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return tmp;
    }

    /**
     * Switch to parent iframe
     */
    public static void switchToParentIframe() {
        SeleniumExecutor.getDriver().switchTo().parentFrame();
    }

    /**
     * Switch to base iframe
     */
    public static void switchToDefaultIframe() {
        SeleniumExecutor.getDriver().switchTo().defaultContent();
    }
}