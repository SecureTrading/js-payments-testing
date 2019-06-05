package util.helpers.actions;

import static util.helpers.actions.CustomWaitImpl.waitForElementDisplayed;
import static util.helpers.actions.CustomWaitImpl.waitForJsJq;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.openqa.selenium.WebElement;

public class CustomGetTextImpl {
    /**
     * Get list of texts from elements list
     * @param list list of WebElements
     * @return list of texts
     */
    public static List<String> getTextsFromElementsList(List<WebElement> list) {
        List<String> finalList = new ArrayList<String>();
        list.stream().forEach(x -> finalList.add(x.getText()));

        return finalList;
    }

    /**
     * Get random text from elements list
     * @param list list of WebElements
     * @return text
     */
    public static String getRandomTextFromElementsList(List<WebElement> list) {
        Random r = new Random();
        return list.get(r.nextInt(list.size())).getText();
    }

    /**
     * Wait for element to be displayed, then get its text
     * @param e WebElement
     * @return text
     */
    public static String getText(WebElement e) {
        waitForJsJq();
        waitForElementDisplayed(e);
        return e.getText();
    }

    /**
     * Wait for element to be displayed, then get its text from value attribute
     * @param e WebElement
     * @return text
     */
    public static String getValue(WebElement e) {
        waitForJsJq();
        waitForElementDisplayed(e);
        return e.getAttribute("value");
    }
}