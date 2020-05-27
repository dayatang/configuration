package yang.yu.configuration;

/**
 * Created by yyang on 16/6/30.
 */
public class ConfigurationKeyNotFoundException extends ConfigurationException {
    public ConfigurationKeyNotFoundException() {
    }

    public ConfigurationKeyNotFoundException(String message) {
        super(message);
    }

    public ConfigurationKeyNotFoundException(Throwable cause) {
        super(cause);
    }

    public ConfigurationKeyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
