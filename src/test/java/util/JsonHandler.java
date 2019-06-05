package util;

import java.io.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonHandler {

    public static String getTranslationFromJson(String key, String language) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        String translation = "";
        try {
            BufferedReader buff = new BufferedReader(new InputStreamReader
                    (new FileInputStream("src/test/resources/languages/" + language + ".json"), "UTF-8"));
            JSONObject json = (JSONObject) parser.parse(buff);
            translation = (String) json.get(key);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return translation;
    }

}

