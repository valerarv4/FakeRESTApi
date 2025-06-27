package net.azurewebsites.fakerestapi.config;

import lombok.Getter;
import net.azurewebsites.fakerestapi.exceptions.TAFRuntimeException;

import java.util.Properties;

import static java.lang.Integer.parseInt;
import static java.lang.System.getProperty;
import static java.util.Objects.isNull;

@Getter
public class EnvironmentConfig {

    private final String appUrl;
    private final int appVersion;
    private final String DEFAULT_ENV = "dev";
    private final String DEFAULT_APP_VERSION = "1";
    private static final EnvironmentConfig INSTANCE = new EnvironmentConfig();

    public static EnvironmentConfig getInstance() {
        return INSTANCE;
    }

    private EnvironmentConfig() {
        var env = getProperty("env", DEFAULT_ENV);
        var filePath = "configs/" + env + ".properties";

        var props = loadProperties(filePath);

        this.appUrl = getRequiredProperty(props, "app.url", filePath);
        this.appVersion = parseInt(getProperty("apiVersion", DEFAULT_APP_VERSION));
    }

    private Properties loadProperties(String path) {
        var props = new Properties();
        try (var input = getClass().getClassLoader().getResourceAsStream(path)) {
            if (isNull(input)) {
                throw new TAFRuntimeException("Config file not found: " + path);
            }
            props.load(input);
        } catch (Exception e) {
            throw new TAFRuntimeException("Failed to load config file: " + path, e);
        }

        return props;
    }

    private String getRequiredProperty(Properties props, String key, String filePath) {
        var value = props.getProperty(key);
        if (isNull(value) || value.isBlank()) {
            throw new TAFRuntimeException("Missing required property: " + key + " in file: " + filePath);
        }

        return value;
    }
}
