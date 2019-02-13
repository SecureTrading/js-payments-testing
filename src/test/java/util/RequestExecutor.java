package util;

import util.enums.StoredElement;

import static util.PropertiesHandler.getProperty;

public final class RequestExecutor {

    public static void markTestAsFailed() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec("curl -u \"" + getProperty("USERNAME") + ":" + getProperty("ACCESS_KEY") + "\" -X PUT -H \"Content-Type: " +
                    "application/json\" -d \"{\\\"status\\\":\\\"failed\\\", \\\"reason\\\":\\\""
                    + PicoContainerHelper.getFromContainer(StoredElement.errorMessage) + "\\\"}\" https://api.browserstack.com/automate/sessions/"
                    + PicoContainerHelper.getFromContainer(StoredElement.sessionId) + ".json");
            int resultCode = process.waitFor();

            if (resultCode == 0) {
                // all is good
            }
        } catch (Throwable cause) {
            // process cause
        }
    }
}