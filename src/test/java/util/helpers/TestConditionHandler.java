package util.helpers;

import util.PicoContainerHelper;
import util.enums.StoredElement;

public class TestConditionHandler {

    public static boolean checkIfScenarioNameContainsText(String text) {
        return PicoContainerHelper.getFromContainer(StoredElement.scenarioName).toString().contains(text);
    }

    public static boolean checkIfDeviceNameStartWith(String text) {
        return System.getProperty("device") != null && System.getProperty("device").startsWith(text);
    }

    public static boolean checkIfBrowserNameStartWith(String text) {
        return System.getProperty("browser") != null && System.getProperty("browser").startsWith(text);
    }

    public static boolean checkConditionForRunApplePayTest() {
        return ((System.getProperty("device") == null && !System.getProperty("browser").equals("Safari")) ||
                (System.getProperty("browser") == null && !System.getProperty("device").startsWith("i")));
    }
}
