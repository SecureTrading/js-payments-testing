package util;

import static io.restassured.RestAssured.given;
import static util.PropertiesHandler.getProperty;

import io.restassured.http.ContentType;
import util.enums.PropertyType;
import util.enums.StoredElement;
import util.models.ErrorMessage;

public final class RequestExecutor {

    public static void markTestAsFailed() {

        ErrorMessage errorMessage = new ErrorMessage() {
            {
                setStatus("failed");
                setReason(PicoContainerHelper.getFromContainer(StoredElement.errorMessage).toString());
            }
        };

        given().contentType(ContentType.JSON).auth().preemptive()
                .basic(getProperty(PropertyType.BROWSERSTACK_USERNAME),
                        getProperty(PropertyType.BROWSERSTACK_ACCESS_KEY))
                .when().body(errorMessage).put("https://api.browserstack.com/automate/sessions/"
                        + PicoContainerHelper.getFromContainer(StoredElement.sessionId) + ".json")
                .asString();
    }

}