package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class PropertiesHandler {

    private PropertiesHandler () {
    }

    public static String getProperty(String property)  {
        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream("src/test/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return appProps.getProperty(property);
    }
}