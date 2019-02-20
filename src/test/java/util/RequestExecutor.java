package util;

import static util.PropertiesHandler.getProperty;

import util.enums.PropertyType;
import util.enums.StoredElement;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.Json;

public final class RequestExecutor {

    public static void markTestAsFailed() {
        Runtime runtime = Runtime.getRuntime();
        try {
            String jsonData = Json.createObjectBuilder()
                .add("status", "failed")
                .add("reason", PicoContainerHelper.getFromContainer(StoredElement.errorMessage).toString())
                .build().toString();

            String cmd = "curl -u '" + getProperty(PropertyType.BS_USERNAME) 
            + ":" + getProperty(PropertyType.BS_ACCESS_KEY) + 
            "' -X PUT -H \"Content-Type: " + "application/json\" -d '"+ jsonData 
            + "' https://api.browserstack.com/automate/sessions/"
            + PicoContainerHelper.getFromContainer(StoredElement.sessionId) + ".json";
            
            Process process = runtime.exec(cmd);
            int resultCode = process.waitFor();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}