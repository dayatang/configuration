package yang.yu.configuration;

/**
 * Created by yyang on 16/7/1.
 */
public class ConfigurationValueParseException extends ConfigurationException {
    public ConfigurationValueParseException() {
    }

    public ConfigurationValueParseException(String message) {
        super(message);
    }

    public ConfigurationValueParseException(Throwable cause) {
        super(cause);
    }

    public ConfigurationValueParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
