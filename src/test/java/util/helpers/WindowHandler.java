package util.helpers;

import java.util.Iterator;
import org.openqa.selenium.WebDriver;
import util.SeleniumExecutor;

public class WindowHandler {
    /**
     * Switch to browser window identified by window handle
     * @param windowHandle String
     * @return WebDriver
     */
    public static WebDriver switchToWindow(String windowHandle) {
        WebDriver tmp = SeleniumExecutor.getDriver().switchTo().window(windowHandle);
        try {
            Thread.sleep(SeleniumExecutor.TIMEOUT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return tmp;
    }

    /**
     * Switch to parent window, regardless which one is active
     */
    public static void switchToParentWindow() {
        switchToWindow(SeleniumExecutor.getParentWindowHandle());
    }

    /**
     * Switch to any window which is not a parent one
     * @return true if succeeds
     */
    public static Boolean switchToAnyNotParentWindow() {
        SeleniumExecutor.setWindowIterator();
        Iterator<String> windowIterator = SeleniumExecutor.getWindowIterator();
        String parentWindowHandle = SeleniumExecutor.getParentWindowHandle();

        while (windowIterator.hasNext()) {
            String windowHandle = windowIterator.next();

            if (!windowHandle.equals(parentWindowHandle)) {
                switchToWindow(windowHandle);
                return true;
            }
        }

        switchToParentWindow();
        return false;
    }

    /**
     * Switch to browser window identified by url
     * @param url String
     * @return true if succeeds
     */
    public static Boolean switchToWindowWithUrl(String url) {
        if (SeleniumExecutor.getUrl().contains(url))
            return true;

        WebDriver popup = null;
        SeleniumExecutor.setWindowIterator();
        Iterator<String> windowIterator = SeleniumExecutor.getWindowIterator();

        while (windowIterator.hasNext()) {
            String windowHandle = windowIterator.next();

            popup = switchToWindow(windowHandle);
            if (popup.getCurrentUrl().contains(url)) {
                return true;
            }
        }

        switchToParentWindow();
        return false;
    }

    /**
     * Switch to browser window identified by title
     * @param title String
     * @return true if succeeds
     */
    public static Boolean switchToWindowWithTitle(String title) {
        if (SeleniumExecutor.getTitle().contains(title))
            return true;

        WebDriver popup = null;
        SeleniumExecutor.setWindowIterator();
        Iterator<String> windowIterator = SeleniumExecutor.getWindowIterator();

        while (windowIterator.hasNext()) {
            String windowHandle = windowIterator.next();

            popup = switchToWindow(windowHandle);
            if (popup.getTitle().contains(title)) {
                return true;
            }
        }

        switchToParentWindow();
        return false;
    }

    /**
     * Close active browser window
     */
    public static void closeWindow() {
        SeleniumExecutor.getDriver().close();
        SeleniumExecutor.getDriver().navigate().to("");
    }

    /**
     * Close any window which is not a parent one
     */
    public static void closeAnyNotParentWindow() {
        while (switchToAnyNotParentWindow()) {
            SeleniumExecutor.getDriver().close();
            try {
                Thread.sleep(SeleniumExecutor.TIMEOUT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        switchToParentWindow();
    }
}