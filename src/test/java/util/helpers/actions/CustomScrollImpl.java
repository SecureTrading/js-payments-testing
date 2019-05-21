package util.helpers.actions;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import util.SeleniumExecutor;

public class CustomScrollImpl {
    /**
     * Scroll to element using Javascript
     * @param e WebElement
     * @return WebElement
     */
    public static WebElement scrollToElement(WebElement e) {
        String position = String.valueOf(e.getLocation().getY());
        String script = String.format("$('#main-container').scrollTop({0})", position);
        ((JavascriptExecutor) SeleniumExecutor.getDriver()).executeScript(script);

        return e;
    }

    public static void scrollToBottomOfPage() {
        ((JavascriptExecutor) SeleniumExecutor.getDriver())
                .executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public static void scrollToTopOfPage() {
        ((JavascriptExecutor) SeleniumExecutor.getDriver())
                .executeScript("window.scrollTo(0, -document.body.scrollHeight)");
    }
}