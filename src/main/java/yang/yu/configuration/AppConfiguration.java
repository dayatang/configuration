package yang.yu.configuration;

import java.util.Date;

public class AppConfiguration {

    private static String confFile = "/conf.properties";
    private static final String KEY_BIRTHDAY = "birthday";
    private static final String KEY_SIZE = "size";
    private static final String KEY_CLOSED = "closed";
    private static final String KEY_LOCKED = "locked";
    private static final String KEY_SALARY = "salary";
    private static final String KEY_NAME = "name";
    private static final double HIGH_SALARY_THRESHOLD = 10000;

    private Configuration configuration;

    public AppConfiguration() {
        this.configuration = Configuration.builder()
                .fromClasspath(confFile)
                .dateFormat("yyyy-MM-dd")
                .defaultWhenParseFailed(true)
                .build();
    }

    public AppConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public Date birthday() {
        return configuration.getDate(KEY_BIRTHDAY);
    }

    public int size() {
        return configuration.getInt(KEY_SIZE);
    }

    public boolean isClosed() {
        return configuration.getBoolean(KEY_CLOSED);
    }

    public boolean isLocked() {
        return configuration.getBoolean(KEY_LOCKED);
    }

    public double salary() {
        return configuration.getDouble(KEY_SALARY);
    }

    public boolean isHighSalaryLevel() {
        return salary() >= HIGH_SALARY_THRESHOLD;
    }

    public String name() {
        return configuration.getString("name");
    }
}
