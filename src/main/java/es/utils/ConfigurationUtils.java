package es.utils;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class ConfigurationUtils {

    private static final String CONFIGURE_MAPPING = "configure.properties";
    private static Map<String, String> configure = Maps.newHashMap();

    public static int loadConfigure() {
        Properties properties = new Properties();
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(CONFIGURE_MAPPING);
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String name : properties.stringPropertyNames()) {
            configure.put(name, properties.getProperty(name));
        }
        return configure.size();
    }

    public static String getEnvironment() {
        return configure.get("environment");
    }

    public static String getHost() {
        return configure.get("host");
    }

    public static String getStaticUrl() {
        return configure.get("static_url");
    }


    public static String getItem(String key, String defaultValue) {
        String value = configure.get(key);
        return StringUtils.isEmpty(value) ? defaultValue : value;
    }

    public static String getItem(String key) {
        return configure.get(key);
    }

    public static String getElasticSearchHost(){
        return configure.get("elastic_search_host");
    }
    public static String getElasticSearchPort(){
        return configure.get("elastic_search_port");
    }

}
