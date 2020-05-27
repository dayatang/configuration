package yang.yu.configuration;

/**
 * Created by yyang on 16/6/30.
 */
public class ConfigurationFileNotFoundException extends ConfigurationException {
    public ConfigurationFileNotFoundException() {
    }

    public ConfigurationFileNotFoundException(String message) {
        super(message);
    }

    public ConfigurationFileNotFoundException(Throwable cause) {
        super(cause);
    }

    public ConfigurationFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
