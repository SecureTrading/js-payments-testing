package util.helpers.actions;

import static util.helpers.actions.CustomWaitImpl.waitForJsJq;

import org.openqa.selenium.WebElement;
import util.enums.AttributeType;

public class CustomGetAttributeImpl {
    /**
     * Get attribute of an element
     * @param e WebElement
     * @param attribute key
     * @return value
     */
    public static String getAttribute(WebElement e, String attribute) {
        //waitForJsJq();
        return e.getAttribute(attribute);
    }

    /**
     * Get attribute of an element
     * @param e WebElement
     * @param attribute key (enum)
     * @return value
     */
    public static String getAttribute(WebElement e, AttributeType attribute) {
        waitForJsJq();
        return e.getAttribute(attribute.toString());
    }
}