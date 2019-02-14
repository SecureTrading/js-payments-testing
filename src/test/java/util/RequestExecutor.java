package util;

import static util.PropertiesHandler.getProperty;

import util.enums.PropertyType;
import util.enums.StoredElement;

public final class RequestExecutor {

    public static void markTestAsFailed() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec("curl -u \"" + getProperty(PropertyType.BS_USERNAME.toString()) + ":" + getProperty(PropertyType.BS_ACCESS_KEY.toString()) + "\" -X PUT -H \"Content-Type: " +
                    "application/json\" -d \"{\\\"status\\\":\\\"failed\\\", \\\"reason\\\":\\\""
                    + PicoContainerHelper.getFromContainer(StoredElement.errorMessage) + "\\\"}\" https://api.browserstack.com/automate/sessions/"
                    + PicoContainerHelper.getFromContainer(StoredElement.sessionId) + ".json");
            int resultCode = process.waitFor();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}