package yang.yu.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ConfigurationTest {

    private String confFile = "/conf.properties";
    private Configuration instance;

    @BeforeEach
    public void setUp() throws Exception {
        instance = Configuration.builder()
                .fromClasspath(confFile)
                .dateFormat("yyyy-MM-dd")
                .build();
    }

    ////////////////////////////////1. 各种创建Configuration的方法

    /**
     * 从类路径读取配置文件
     */
    @Test
    public void testFromClasspath() {
        instance = Configuration.builder().
                fromClasspath(confFile)
                .build();
        assertThat(instance.getString("name")).isEqualTo("张三");
    }

    /**
     * 从文件系统根据文件名读取配置文件
     */
    @Test
    public void testFromFilePath() {
        String pathname = getClass().getResource(confFile).getFile();
        instance = Configuration.builder().
                fromFile(pathname)
                .build();
        assertThat(instance.getString("name")).isEqualTo("张三");
    }

    /**
     * 从文件系统根据文件对象读取文件
     */
    @Test
    public void testFromFile() {
        String pathname = getClass().getResource(confFile).getFile();
        instance = Configuration.builder().
                fromFile(new File(pathname))
                .build();
        assertThat(instance.getString("name")).isEqualTo("张三");
    }


    ///////////////////////////////2. 字符串型配置项

    //////////////2.1. 字符串型,无缺省值

    /**
     * key存在,value存在,应当返回value
     */
    @Test
    public void get_string_without_defaultValue_happy() {
        assertThat(instance.getString("name")).isEqualTo("张三");
    }

    /**
     *  key存在,value不存在,,应当返回空字符串
     */
    @Test
    public void get_string_without_defaultValue_and_without_value() {
        assertThat(instance.getString("noneValue")).isEmpty();
    }

    /**
     * key不存在,应当抛出ConfigurationKeyNotFoundException异常
     */
    @Test
    public void get_string_without_defaultValue_and_without_key() {
        assertThrows(ConfigurationKeyNotFoundException.class, () -> instance.getString("noneKey"));
    }

    ///////////////2.2. 字符串型, 有缺省值

    /**
     * key存在,value存在,应当返回value
     */
    @Test
    public void get_string_with_defaultValue_and_with_value() {
        assertThat(instance.getString("name", "abc")).isEqualTo("张三");
    }

    /**
     * key存在,value不存在,应当返回缺省值
     */
    @Test
    public void get_string_with_defaultValue_and_without_value() {
        assertThat(instance.getString("noneValue", "abc")).isEqualTo("abc");
    }

    /**
     * key不存在,应当返回缺省值
     */
    @Test
    public void get_string_with_defaultValue_and_withoutout_key() {
        assertThat(instance.getString("noneKey", "abc")).isEqualTo("abc");
    }

    ///////////////////////////////////////////3. Int型配置项

    //////////////////3.1. Int型配置项,无缺省值

    /**
     * key存在,value存在,格式正确,应当返回value
     */
    @Test
    public void get_int_without_defaultValue_happy() {
        assertThat(instance.getInt("size")).isEqualTo(15);
    }

    /**
     * key存在,value存在,格式不正确,应当抛出ConfigurationValueParseException异常
     */
    @Test
    public void get_int_without_defaultValue_and_with_invalid_value() {
        assertThrows(ConfigurationValueParseException.class, () -> instance.getInt("name"));
    }

    /**
     * key存在,value不存在,应当抛出ConfigurationValueParseException异常
     */
    @Test
    public void get_int_without_defaultValue_and_without_value() {
        assertThrows(ConfigurationValueParseException.class, () -> instance.getInt("noneValue"));
    }

    /**
     * key不存在,应当抛出ConfigurationKeyNotFoundException异常
     */
    @Test
    public void get_int_without_defaultValue_and_without_key() {
        assertThrows(ConfigurationKeyNotFoundException.class, () -> instance.getInt("noneKey"));
    }

    /////////////3.2. Int型配置项,有缺省值

    /**
     * key存在, value存在,格式正确,应当返回value
     */
    @Test
    public void get_int_with_defaultValue_and_with_value() {
        assertThat(instance.getInt("size", 1000)).isEqualTo(15);
    }

    /**
     * key存在,value存在,格式不正确,defaultWhenParseFailed=true,应当返回缺省值
     */
    @Test
    public void get_int_with_defaultValue_and_with_invalid_value_and_defaultWhenParseFailed_is_true() {
        instance.setDefaultWhenParseFailed(true);
        assertThat(instance.getInt("name", 1000)).isEqualTo(1000);
    }

    /**
     * key存在,value存在,格式不正确,defaultWhenParseFailed=false,应当抛出ConfigurationValueParseException异常
     */
    @Test
    public void get_int_with_defaultValue_and_with_invalid_value_and_defaultWhenParseFailed_is_false() {
        instance.setDefaultWhenParseFailed(false);
        assertThrows(ConfigurationValueParseException.class, () -> instance.getInt("name", 1000));
    }

    /**
     * key存在,value不存在,defaultWhenParseFailed=true,应当返回缺省值
     */
    @Test
    public void get_int_with_defaultValue_and_without_value_and_defaultWhenParseFailed_is_true() {
        instance.setDefaultWhenParseFailed(true);
        assertThat(instance.getInt("noneValue", 1000)).isEqualTo(1000);
    }

    /**
     * key存在,value不存在,defaultWhenParseFailed=false,应当抛出ConfigurationValueParseException异常
     */
    @Test
    public void get_int_with_defaultValue_and_without_value_and_defaultWhenParseFailed_is_false() {
        instance.setDefaultWhenParseFailed(false);
        assertThrows(ConfigurationValueParseException.class, () -> instance.getInt("noneValue", 1000));
    }

    /**
     * key不存在,应当返回缺省值
     */
    @Test
    public void get_int_with_defaultValue_and_without_key() {
        assertThat(instance.getInt("noneKey", 1000)).isEqualTo(1000);
    }


    ///////////////////////////////////////////4. Long型配置项

    //////////////////4.1. Long型配置项,无缺省值

    /**
     * key存在,value存在,格式正确,应当返回value
     */
    @Test
    public void get_long_without_defaultValue_happy() {
        assertThat(instance.getLong("size")).isEqualTo(15);
    }

    /**
     * key存在,value存在,格式不正确,应当抛出ConfigurationValueParseException异常
     */
    @Test
    public void get_long_without_defaultValue_and_with_invalid_value() {
        assertThrows(ConfigurationValueParseException.class, () -> instance.getLong("name"));
    }

    /**
     * key存在,value不存在,应当抛出ConfigurationValueParseException异常
     */
    @Test
    public void get_long_without_defaultValue_and_without_value() {
        assertThrows(ConfigurationValueParseException.class, () -> instance.getLong("noneValue"));
    }

    /**
     * key不存在,应当抛出ConfigurationKeyNotFoundException异常
     */
    @Test
    public void get_long_without_defaultValue_and_without_key() {
        assertThrows(ConfigurationKeyNotFoundException.class, () -> instance.getLong("noneKey"));
    }

    /////////////4.2. Long型配置项,有缺省值

    /**
     * key存在, value存在,格式正确,应当返回value
     */
    @Test
    public void get_long_with_defaultValue_and_with_value() {
        assertThat(instance.getLong("size", 1000)).isEqualTo(15);
    }

    /**
     * key存在,value存在,格式不正确,defaultWhenParseFailed=true,应当返回缺省值
     */
    @Test
    public void get_long_with_defaultValue_and_with_invalid_value_and_defaultWhenParseFailed_is_true() {
        instance.setDefaultWhenParseFailed(true);
        assertThat(instance.getLong("name", 1000)).isEqualTo(1000);
    }

    /**
     * key存在,value存在,格式不正确,defaultWhenParseFailed=false,应当抛出ConfigurationValueParseException异常
     */
    @Test
    public void get_long_with_defaultValue_and_with_invalid_value_and_defaultWhenParseFailed_is_false() {
        instance.setDefaultWhenParseFailed(false);
        assertThrows(ConfigurationValueParseException.class, () -> instance.getLong("name", 1000));
    }

    /**
     * key存在,value不存在,defaultWhenParseFailed=true,应当返回缺省值
     */
    @Test
    public void get_long_with_defaultValue_and_without_value_and_defaultWhenParseFailed_is_true() {
        instance.setDefaultWhenParseFailed(true);
        assertThat(instance.getLong("noneValue", 1000)).isEqualTo(1000);
    }

    /**
     * key存在,value不存在,defaultWhenParseFailed=false,应当抛出ConfigurationValueParseException异常
     */
    @Test
    public void get_long_with_defaultValue_and_without_value_and_defaultWhenParseFailed_is_false() {
        instance.setDefaultWhenParseFailed(false);
        assertThrows(ConfigurationValueParseException.class, () -> instance.getLong("noneValue", 1000));
    }

    /**
     * key不存在,应当返回缺省值
     */
    @Test
    public void get_long_with_defaultValue_and_without_key() {
        assertThat(instance.getLong("noneKey", 1000)).isEqualTo(1000);
    }


    ///////////////////////////////////////////5. Double型配置项

    //////////////////5.1. Double型配置项,无缺省值

    /**
     * key存在,value存在,格式正确,应当返回value
     */
    @Test
    public void get_double_without_defaultValue_happy() {
        assertThat(instance.getDouble("salary")).isEqualTo(12.5);
    }

    /**
     * key存在,value存在,格式不正确,应当抛出ConfigurationValueParseException异常
     */
    @Test
    public void get_double_without_defaultValue_and_with_invalid_value() {
        assertThrows(ConfigurationValueParseException.class, () -> instance.getDouble("name"));
    }

    /**
     * key存在,value不存在,应当抛出ConfigurationValueParseException异常
     */
    @Test
    public void get_double_without_defaultValue_and_without_value() {
        assertThrows(ConfigurationValueParseException.class, () -> instance.getDouble("noneValue"));
    }

    /**
     * key不存在,应当抛出ConfigurationKeyNotFoundException异常
     */
    @Test
    public void get_double_without_defaultValue_and_without_key() {
        assertThrows(ConfigurationKeyNotFoundException.class, () -> instance.getDouble("noneKey"));
    }

    /////////////5.2. Double型配置项,有缺省值

    /**
     * key存在, value存在,格式正确,应当返回value
     */
    @Test
    public void get_double_with_defaultValue_and_with_value() {
        assertThat(instance.getDouble("salary", 1000.0)).isCloseTo(12.5, offset(0.00001));
    }

    /**
     * key存在,value存在,格式不正确,defaultWhenParseFailed=true,应当返回缺省值
     */
    @Test
    public void get_double_with_defaultValue_and_with_invalid_value_and_defaultWhenParseFailed_is_true() {
        instance.setDefaultWhenParseFailed(true);
        assertThat(instance.getDouble("name", 1000.0)).isCloseTo(1000.0, offset(0.00001));
    }

    /**
     * key存在,value存在,格式不正确,defaultWhenParseFailed=false,应当抛出ConfigurationValueParseException异常
     */
    @Test
    public void get_double_with_defaultValue_and_with_invalid_value_and_defaultWhenParseFailed_is_false() {
        instance.setDefaultWhenParseFailed(false);
        assertThrows(ConfigurationValueParseException.class, () -> instance.getDouble("name", 1000.0));
    }

    /**
     * key存在,value不存在,defaultWhenParseFailed=true,应当返回缺省值
     */
    @Test
    public void get_double_with_defaultValue_and_without_value_and_defaultWhenParseFailed_is_true() {
        instance.setDefaultWhenParseFailed(true);
        assertThat(instance.getDouble("noneValue", 1000.0)).isCloseTo(1000.0, offset(0.00001));
    }

    /**
     * key存在,value不存在,defaultWhenParseFailed=false,应当抛出ConfigurationValueParseException异常
     */
    @Test
    public void get_double_with_defaultValue_and_without_value_and_defaultWhenParseFailed_is_false() {
        instance.setDefaultWhenParseFailed(false);
        assertThrows(ConfigurationValueParseException.class, () -> instance.getDouble("noneValue", 1000.0));
    }

    /**
     * key不存在,应当返回缺省值
     */
    @Test
    public void get_double_with_defaultValue_and_without_key() {
        assertThat(instance.getDouble("noneKey", 1000.0)).isCloseTo(1000, offset(0.00001));
    }


    ///////////////////////////////////////////6. Boolean型配置项

    //////////////////6.1. Boolean型配置项,无缺省值

    /**
     * key存在,value存在,格式正确,应当返回value
     */
    @Test
    public void get_boolean_without_defaultValue_happy() {
        assertThat(instance.getBoolean("closed")).isTrue();
        assertThat(instance.getBoolean("locked")).isFalse();
    }

    /**
     * key存在,value存在,格式不正确,应当抛出ConfigurationValueParseException异常
     */
    @Test
    public void get_boolean_without_defaultValue_and_with_invalid_value() {
        assertThrows(ConfigurationValueParseException.class, () -> instance.getBoolean("name"));
    }

    /**
     * key存在,value不存在,应当抛出ConfigurationValueParseException异常
     */
    @Test
    public void get_boolean_without_defaultValue_and_without_value() {
        assertThrows(ConfigurationValueParseException.class, () -> instance.getBoolean("noneValue"));
    }

    /**
     * key不存在,应当抛出ConfigurationKeyNotFoundException异常
     */
    @Test
    public void get_boolean_without_defaultValue_and_without_key() {
        assertThrows(ConfigurationKeyNotFoundException.class, () -> instance.getBoolean("noneKey"));
    }

    /////////////6.2. Boolean型配置项,有缺省值

    /**
     * key存在, value存在,格式正确,应当返回value
     */
    @Test
    public void get_boolean_with_defaultValue_and_with_value() {
        assertThat(instance.getBoolean("closed", false)).isTrue();
        assertThat(instance.getBoolean("locked", true)).isFalse();
    }

    /**
     * key存在,value存在,格式不正确,defaultWhenParseFailed=true,应当返回缺省值
     */
    @Test
    public void get_boolean_with_defaultValue_and_with_invalid_value_and_defaultWhenParseFailed_is_true() {
        instance.setDefaultWhenParseFailed(true);
        assertThat(instance.getBoolean("name", true)).isFalse();
        assertThat(instance.getBoolean("name", false)).isFalse();
    }

    /**
     * key存在,value存在,格式不正确,defaultWhenParseFailed=false,应当抛出ConfigurationValueParseException异常
     */
    @Test
    public void get_boolean_with_defaultValue_and_with_invalid_value_and_defaultWhenParseFailed_is_false() {
        instance.setDefaultWhenParseFailed(false);
        assertThrows(ConfigurationValueParseException.class, () -> instance.getBoolean("name", true));
    }

    /**
     * key存在,value不存在,defaultWhenParseFailed=true,应当返回缺省值
     */
    @Test
    public void get_boolean_with_defaultValue_and_without_value_and_defaultWhenParseFailed_is_true() {
        instance.setDefaultWhenParseFailed(true);
        assertThat(instance.getBoolean("noneValue1", true)).isTrue();
        assertThat(instance.getBoolean("noneValue", false)).isFalse();
    }

    /**
     * key存在,value不存在,defaultWhenParseFailed=false,应当抛出ConfigurationValueParseException异常
     */
    @Test
    public void get_boolean_with_defaultValue_and_without_value_and_defaultWhenParseFailed_is_false() {
        instance.setDefaultWhenParseFailed(false);
        assertThrows(ConfigurationValueParseException.class, () -> instance.getBoolean("noneValue", true));
    }

    /**
     * key不存在,应当返回缺省值
     */
    @Test
    public void get_boolean_with_defaultValue_and_without_key() {
        assertThat(instance.getBoolean("noneKey", true)).isTrue();
        assertThat(instance.getBoolean("noneKey", false)).isFalse();
    }


    ///////////////////////////////////////////7. Date型配置项

    //////////////////7.1. Date型配置项,无缺省值

    /**
     * key存在,value存在,格式正确,应当返回value
     */
    @Test
    public void get_date_without_defaultValue_happy() {
        Date expectedBirthday = DateUtils.createDate(2002, Calendar.MAY, 11);
        assertThat(instance.getDate("birthday")).isEqualTo(expectedBirthday);
    }

    /**
     * key存在,value存在,格式不正确,应当抛出ConfigurationValueParseException异常
     */
    @Test
    public void get_date_without_defaultValue_and_with_invalid_value() {
        assertThrows(ConfigurationValueParseException.class, () -> instance.getDate("name"));
    }

    /**
     * key存在,value不存在,应当抛出ConfigurationValueParseException异常
     */
    @Test
    public void get_date_without_defaultValue_and_without_value() {
        assertThrows(ConfigurationValueParseException.class, () -> instance.getDate("noneValue"));
    }

    /**
     * key不存在,应当抛出ConfigurationKeyNotFoundException异常
     */
    @Test
    public void get_date_without_defaultValue_and_without_key() {
        assertThrows(ConfigurationKeyNotFoundException.class, () -> instance.getDate("noneKey"));
    }

    /////////////7.2. Date型配置项,有缺省值

    /**
     * key存在, value存在,格式正确,应当返回value
     */
    @Test
    public void get_date_with_defaultValue_and_with_value() {
        Date expectedBirthday = DateUtils.createDate(2002, Calendar.MAY, 11);
        Date defaultValue = DateUtils.createDate(1968, Calendar.MAY, 20);
        assertThat(instance.getDate("birthday", defaultValue)).isEqualTo(expectedBirthday);
    }

    /**
     * key存在,value存在,格式不正确,defaultWhenParseFailed=true,应当返回缺省值
     */
    @Test
    public void get_date_with_defaultValue_and_with_invalid_value_and_defaultWhenParseFailed_is_true() {
        Date defaultValue = DateUtils.createDate(1968, Calendar.MAY, 20);
        instance.setDefaultWhenParseFailed(true);
        assertThat(instance.getDate("name", defaultValue)).isEqualTo(defaultValue);
    }

    /**
     * key存在,value存在,格式不正确,defaultWhenParseFailed=false,应当抛出ConfigurationValueParseException异常
     */
    @Test
    public void get_date_with_defaultValue_and_with_invalid_value_and_defaultWhenParseFailed_is_false() {
        Date defaultValue = DateUtils.createDate(1968, Calendar.MAY, 20);
        instance.setDefaultWhenParseFailed(false);
        assertThrows(ConfigurationValueParseException.class, () -> instance.getDate("name", defaultValue));
    }

    /**
     * key存在,value不存在,defaultWhenParseFailed=true,应当返回缺省值
     */
    @Test
    public void get_date_with_defaultValue_and_without_value_and_defaultWhenParseFailed_is_true() {
        Date defaultValue = DateUtils.createDate(1968, Calendar.MAY, 20);
        instance.setDefaultWhenParseFailed(true);
        assertThat(instance.getDate("noneValue", defaultValue)).isEqualTo(defaultValue);
    }

    /**
     * key存在,value不存在,defaultWhenParseFailed=false,应当抛出ConfigurationValueParseException异常
     */
    @Test
    public void get_date_with_defaultValue_and_without_value_and_defaultWhenParseFailed_is_false() {
        Date defaultValue = DateUtils.createDate(1968, Calendar.MAY, 20);
        instance.setDefaultWhenParseFailed(false);
        assertThrows(ConfigurationValueParseException.class, () -> instance.getDate("noneValue", defaultValue));
    }

    /**
     * key不存在,应当返回缺省值
     */
    @Test
    public void get_date_with_defaultValue_and_without_key() {
        Date defaultValue = DateUtils.createDate(1968, Calendar.MAY, 20);
        assertThat(instance.getDate("noneKey", defaultValue)).isEqualTo(defaultValue);
    }
}