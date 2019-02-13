package util;

import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;

public class SeleniumExecutor extends DriverFactory {

    public SeleniumExecutor() {
        super();
    }

    private static void startExecutor() {
        if (executor == null) {
            executor = new SeleniumExecutor();
        }
    }

    public static DriverFactory getExecutor() {
        startExecutor();
        return executor;
    }

    public static void stop() {
        if (driver != null) {
            driver.close();
            driver.quit();
        }
        executor = null;
    }

    public static String getTitle() {
        return driver.getTitle();
    }

    public static String getUrl() {
        return driver.getCurrentUrl();
    }

    public void deleteCookies() {
        try {
            driver.manage().deleteAllCookies();
            pause(SHORT_TIME_FOR_THREAD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteLocalStorage() {
        try {
            LocalStorage local = ((WebStorage) driver).getLocalStorage();
            local.clear();
        } catch (WebDriverException e) {
            e.printStackTrace();
        }
    }

    public static void refreshPage() {
        driver.navigate().refresh();
    }

    public static void openPage(String url) {
        try {
            driver.navigate().to(url);
        } catch (UnhandledAlertException e) {
            driver.switchTo().alert().accept();
            driver.navigate().to(url);
        }
    }

    public static void pause(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}