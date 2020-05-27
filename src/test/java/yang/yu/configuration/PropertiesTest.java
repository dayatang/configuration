package yang.yu.configuration;

import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

public class PropertiesTest {

    private String confFile = "/conf.properties";
    private Properties properties;

    @Before
    public void setUp() throws Exception {
        properties = new Properties();
        try(InputStream in = getClass().getResourceAsStream(confFile)) {
            properties.load(in);
        }
    }

    @Test
    public void testGetString() {
        String name = null;
        try {
            String temp = properties.getProperty("name");
            name = new String(temp.getBytes("iso-8859-1"), "utf-8");
            System.out.println(name);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        assertThat(name).isEqualTo("张三");
    }

    @Test
    public void testGetStringWithDefault() {
        String defaultValue = "abc";
        String temp = properties.getProperty("notExists");
        String name = null;
        if (temp == null) {
            name = defaultValue;
        } else {
            try {
                name = new String(temp.getBytes("iso-8859-1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        assertThat(name).isEqualTo("abc");
    }

    @Test
    public void testGetInt() {
        String temp = properties.getProperty("size");
        if (temp == null) {
            throw new ConfigurationKeyNotFoundException();
        }
        int value = -1;
        try {
            value = Integer.parseInt(temp);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Cannot parse string '" + temp + "' to int");
        }
        assertThat(value).isEqualTo(15);
    }

    @Test
    public void testGetIntWithDefault() {
        int defaultValue = 0;
        int value = -1;
        String temp = properties.getProperty("size");
        if (temp == null) {
            value = defaultValue;
        }
        try {
            value = Integer.parseInt(temp);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Cannot parse string '" + temp + "' to int");
        }
        assertThat(value).isEqualTo(15);
    }
}
