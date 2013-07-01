package org.easy.broadjms.config;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class ConfigurationManager {

    private static ConfigurationManager instance;

    private Properties props;

    private ConfigurationManager() {
        InputStream config = getClass().getResourceAsStream("/jms.properties");
        props = new Properties();
        try {
            props.load(config);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ConfigurationManager getInstance() {
        if (instance == null) {
            instance = new ConfigurationManager();
        }
        return instance;
    }

    public String getConfig(String key) {
        Object value = props.get(key);
        return (String) value;
    }

    public List<String> getConfigList(String key) {
        if (!key.startsWith("L")) {
            throw new IllegalArgumentException(
                    "Specified config is not single value");
        }
        String valueList = (String) props.get(key);
        String[] values = valueList.split(",");
        return Arrays.asList(values);
    }

}
