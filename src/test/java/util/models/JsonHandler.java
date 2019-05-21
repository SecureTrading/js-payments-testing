package util.models;

import java.io.FileReader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonHandler {

    public static String getTranslationFromJson(String key, String language) {
        JSONParser parser = new JSONParser();
        String translation = "";
        try {
            FileReader fileReader = new FileReader("src/test/resources/languages/" + language + ".json");
            JSONObject json = (JSONObject) parser.parse(fileReader);
            translation = (String) json.get(key);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return translation;
    }
}

