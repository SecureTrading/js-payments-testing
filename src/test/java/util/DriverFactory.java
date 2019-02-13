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
    @Getter @Setter
    private static String parentWindowHandle;
    @Getter
    private static Iterator<String> windowIterator;
    @Getter
    protected static Local local;

    protected static String USERNAME = getProperty("USERNAME");
    protected static String ACCESS_KEY = getProperty("ACCESS_KEY");
    protected static String REMOTE_URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@hub.browserstack.com/wd/hub";

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

        // Desktop configuration
        if (System.getProperty("os") != null)
            caps.setCapability("os", System.getProperty("os"));
        if (System.getProperty("os_version") != null)
            caps.setCapability("os_version", System.getProperty("os_version"));
        if (System.getProperty("browser") != null)
            caps.setCapability("browser", System.getProperty("browser"));
        if (System.getProperty("browser_version") != null)
            caps.setCapability("browser_version", System.getProperty("browser_version"));
        if (System.getProperty("resolution") != null)
            caps.setCapability("resolution", System.getProperty("resolution"));

        // Additional mobile configuration
        if (System.getProperty("device") != null)
            caps.setCapability("device", System.getProperty("device"));
        if (System.getProperty("real_mobile") != null)
            caps.setCapability("real_mobile", System.getProperty("real_mobile"));

        // Logging configuration
        caps.setCapability("browserstack.console", "errors");
        caps.setCapability("browserstack.debug", true);
        caps.setCapability("browserstack.networkLogs", true);

        caps.setCapability("project", "JS Payments Interface");
        caps.setCapability("build", LocalDate.now().toString());
        caps.setCapability("build", LocalDate.now().toString());
        caps.setCapability("name", PicoContainerHelper.getFromContainer(StoredElement.scenarioName) + " --- " + new Date());

        if (System.getProperty("local") != null && System.getProperty("local").equals("true")) {
            caps.setCapability("browserstack.local", "true");
            local = new Local();
            Map<String, String> options = new HashMap<String, String>();
            options.put("key", getProperty("ACCESS_KEY"));
            try {
                local.start(options);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return caps;
    }

    private static WebDriver createDriver() {
        if (!getProperty("TARGET").equals("local")) {
            try {
                driver = new RemoteWebDriver(new URL(REMOTE_URL), GetRemoteDriverCapabilities());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            PicoContainerHelper.addToContainer(StoredElement.sessionId, ((RemoteWebDriver) driver).getSessionId().toString());
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