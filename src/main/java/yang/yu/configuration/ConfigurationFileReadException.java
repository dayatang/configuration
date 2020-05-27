package yang.yu.configuration;

/**
 * Created by yyang on 16/6/30.
 */
public class ConfigurationFileReadException extends ConfigurationException {
    public ConfigurationFileReadException() {
    }

    public ConfigurationFileReadException(String message) {
        super(message);
    }

    public ConfigurationFileReadException(Throwable cause) {
        super(cause);
    }

    public ConfigurationFileReadException(String message, Throwable cause) {
        super(message, cause);
    }
}
