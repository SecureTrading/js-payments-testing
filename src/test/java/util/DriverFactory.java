package util;

import static util.PropertiesHandler.getProperty;

import com.browserstack.local.Local;
import lombok.Getter;
import lombok.Setter;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.enums.PropertyType;
import util.enums.StoredElement;

abstract class DriverFactory {

    public final static int TIMEOUT = 60;
    public final static int LONG_TIMEOUT = 120;
    public final static int MIDDLE_TIMEOUT = 90;
    public final static int SHORT_TIMEOUT = 6;
    public final static int TINY_TIMEOUT = 3;
    public final static int MINIMUM_TIMEOUT = 1;

    public final static int PAGE_LOAD_TIMEOUT = 100;
    public final static int VERY_LONG_TIMEOUT = 300;

    public final static int SHORT_TIME_FOR_THREAD = 1000;
    public final static int MEDIUM_TIME_FOR_THREAD = 3000;
    public final static int LONG_TIME_FOR_THREAD = 6000;

    @Getter
    protected static DriverFactory executor;
    @Getter
    protected static WebDriver driver;
    @Getter
    private static WebDriverWait waitDriver;
    @Getter
    @Setter
    private static String parentWindowHandle;
    @Getter
    private static Iterator<String> windowIterator;
    @Getter
    protected static Local local;

    public DriverFactory() {
        driver = createDriver();
        parentWindowHandle = driver.getWindowHandle();
    }

    public static void setWaitDriverTimeOut(int timeout) {
        waitDriver = new WebDriverWait(driver, timeout);
    }

    public static void setWindowIterator() {
        windowIterator = driver.getWindowHandles().iterator();
    }

    public static DesiredCapabilities GetRemoteDriverCapabilities() {

        DesiredCapabilities caps = new DesiredCapabilities();

        // Browser/device configuration
        for (String property : new String[] { "os", "os_version", "browser", "browser_version", "resolution", "device",
                "real_mobile" }) {
            String value = System.getProperty(property);
            if (value != null) {
                caps.setCapability(property, value);
            }
        }

        // Logging configuration
        caps.setCapability("browserstack.console", "errors");
        caps.setCapability("browserstack.debug", true);
        caps.setCapability("browserstack.networkLogs", true);

        caps.setCapability("project", "JS Payments Interface");
        caps.setCapability("build", LocalDate.now().toString());
        caps.setCapability("name",
                PicoContainerHelper.getFromContainer(StoredElement.scenarioName) + " --- " + new Date());

        if (System.getProperty(PropertyType.LOCAL.toString()) != null
                && System.getProperty(PropertyType.LOCAL.toString()).equals("true")) {
            caps.setCapability("browserstack.local", "true");

            // local = new Local();
            // Map<String, String> options = new HashMap<String, String>();
            // options.put("key", getProperty(PropertyType.BROWSERSTACK_ACCESS_KEY));
            // options.put("onlyAutomate", "true");

            // try {
            // local.start(options);
            // } catch (Exception e) {
            // e.printStackTrace();
            // }
        }

        return caps;
    }

    private static WebDriver createDriver() {
        if (!getProperty(PropertyType.TARGET).equals("local")) {
            try {
                driver = new RemoteWebDriver(
                        new URL("https://" + getProperty(PropertyType.BROWSERSTACK_USERNAME) + ":"
                                + getProperty(PropertyType.BROWSERSTACK_ACCESS_KEY) + "@hub.browserstack.com/wd/hub"),
                        GetRemoteDriverCapabilities());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            PicoContainerHelper.updateInContainer(StoredElement.sessionId,
                    ((RemoteWebDriver) driver).getSessionId().toString());
        } else {
            System.setProperty("webdriver.chrome.driver", "./src/binary/chrome/chromedriver.exe");
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        }

        waitDriver = new WebDriverWait(driver, TIMEOUT);
        driver.manage().timeouts().pageLoadTimeout(DriverFactory.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);

        return driver;
    }
}