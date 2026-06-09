package com.bulkley.biglittlefixes.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

/** Lightweight configuration holder for the mod skeleton. Uses classpath properties if present. */
public final class Config {
    private static final Properties PROPS = new Properties();

    static {
        try (InputStream in = Config.class.getResourceAsStream("/biglittlefixes.properties")) {
            if (in != null) {
                PROPS.load(in);
            }
        } catch (IOException e) {
            // Loading failure is non-fatal for skeleton; leave defaults intact.
        }
    }

    private Config() {
        throw new UnsupportedOperationException("Config is a static utility class");
    }

    /**
     * Retrieve a configuration value from the bundled properties.
     *
     * @param key the property key to look up (must not be null)
     * @param defaultValue the value to return when the property is missing
     * @return the property value or defaultValue when absent
     */
    public static String get(final String key, final String defaultValue) {
        Objects.requireNonNull(key, "key must not be null");
        return PROPS.getProperty(key, defaultValue);
    }
}
