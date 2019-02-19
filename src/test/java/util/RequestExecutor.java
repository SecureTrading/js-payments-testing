package util;

import static util.PropertiesHandler.getProperty;

import util.enums.PropertyType;
import util.enums.StoredElement;

public final class RequestExecutor {

    public static void markTestAsFailed() {
        Runtime runtime = Runtime.getRuntime();
        System.out.println("----------------------------------2");
        System.out.println("----------------------------------2");

        System.out.println(PicoContainerHelper.getFromContainer(StoredElement.errorMessage));
        System.out.println(PicoContainerHelper.getFromContainer(StoredElement.sessionId));
        System.out.println(PicoContainerHelper.getFromContainer(getProperty(PropertyType.BS_USERNAME)));
        System.out.println(PicoContainerHelper.getFromContainer(getProperty(PropertyType.BS_ACCESS_KEY)));

        System.out.println("----------------------------------2");
        System.out.println("----------------------------------2");
        try {
            Process process = runtime.exec("curl -u \"" + getProperty(PropertyType.BS_USERNAME) + ":" + getProperty(PropertyType.BS_ACCESS_KEY) + "\" -X PUT -H \"Content-Type: " +
                    "application/json\" -d \"{\\\"status\\\":\\\"failed\\\", \\\"reason\\\":\\\""
                    + PicoContainerHelper.getFromContainer(StoredElement.errorMessage) + "\\\"}\" https://api.browserstack.com/automate/sessions/"
                    + PicoContainerHelper.getFromContainer(StoredElement.sessionId) + ".json");
            int resultCode = process.waitFor();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}