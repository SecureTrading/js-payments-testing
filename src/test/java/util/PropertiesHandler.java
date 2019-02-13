package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesHandler {

    private static Properties properties;

    public static void init() {
        properties = new Properties();
        try {
            properties.load(new FileInputStream("src/test/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String property) {
        String value = System.getenv(property);
        return value != null ? value : properties.getProperty(property);
    }
}