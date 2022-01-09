package eapli.base.smm.base;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppSettings {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppSettings.class);

    private static final String PROPERTIES_RESOURCE = "application.properties";
    private static final String SMM_SLEEPTIME_SECONDS = "sleeptimeInSeconds";
    private static final String SMM_TIMEOUT_SECONDS = "timeoutInSeconds";
    private static final String SMM_RESET_ARGUMENT = "resetArgument";

    private final Properties applicationProperties = new Properties();

    public AppSettings() {
        loadProperties();
    }

    private void loadProperties() {
        try (InputStream propertiesStream = this.getClass().getClassLoader()
                .getResourceAsStream(PROPERTIES_RESOURCE)) {
            if (propertiesStream != null) {
                this.applicationProperties.load(propertiesStream);
            } else {
                throw new FileNotFoundException(
                        "property file '" + PROPERTIES_RESOURCE + "' not found in the classpath");
            }
        } catch (final IOException exio) {
            setDefaultProperties();

            LOGGER.warn("Loading default properties", exio);
        }
    }

    private void setDefaultProperties() {
        this.applicationProperties.setProperty(SMM_SLEEPTIME_SECONDS, "30");
        this.applicationProperties.setProperty(SMM_TIMEOUT_SECONDS, "60");
        this.applicationProperties.setProperty(SMM_RESET_ARGUMENT, "-reset");
    }

    public Integer getSMMSleeptime() {
        return Integer.valueOf(this.applicationProperties.getProperty(SMM_SLEEPTIME_SECONDS));
    }

    public Integer getSMMTimeout() {
        return Integer.valueOf(this.applicationProperties.getProperty(SMM_TIMEOUT_SECONDS));
    }

    public String getSMMResetArgument() {
        return this.applicationProperties.getProperty(SMM_RESET_ARGUMENT);
    }

    public String getProperty(String prop) {
        return this.applicationProperties.getProperty(prop);
    }
}
