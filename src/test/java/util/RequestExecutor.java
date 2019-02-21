package util;

import static io.restassured.RestAssured.given;
import static util.PropertiesHandler.getProperty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import util.enums.PropertyType;
import util.enums.StoredElement;
import util.models.ErrorMessage;

import javax.json.Json;
import java.io.IOException;
import java.io.InputStream;

public final class RequestExecutor {

    public static void markTestAsFailed() {
        System.out.println("------------------------------------- rest assured");
            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.setStatus("failed");
            errorMessage.setReason(PicoContainerHelper.getFromContainer(StoredElement.errorMessage).toString());

            System.out.println(given()
//                    .header("Authorization:", "Basic "+getProperty(PropertyType.BS_USERNAME)+":"+getProperty(PropertyType.BS_ACCESS_KEY))
                    .spec(buildJsonRequestSpecification())
                    .when()
                    .body(errorMessage)
                    .put("https://"+getProperty(PropertyType.BS_USERNAME)+":"+getProperty(PropertyType.BS_ACCESS_KEY)+"@api.browserstack.com/automate/sessions/" + PicoContainerHelper.getFromContainer(StoredElement.sessionId) + ".json").asString());
        System.out.println("------------------------------------- rest assured");
    }


    public static RequestSpecification buildJsonRequestSpecification() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();
    }
}