package yang.yu.configuration;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;


public class AppConfigurationTest {

    private AppConfiguration instance;

    @Before
    public void setUp() throws Exception {
        instance = new AppConfiguration();
    }

    @Test
    public void birthday() throws Exception {
        Date expected = DateUtils.createDate(2002, Calendar.MAY, 11);
        assertThat(instance.birthday()).isEqualTo(expected);
    }

    @Test
    public void size() throws Exception {
        assertThat(instance.size()).isEqualTo(15);
    }

    @Test
    public void isClosed() throws Exception {
        assertThat(instance.isClosed()).isTrue();
    }

    @Test
    public void isLocked() throws Exception {
        assertThat(instance.isLocked()).isFalse();
    }

    @Test
    public void salary() throws Exception {
        assertThat(instance.salary()).isCloseTo(12.5, offset(0.00001));
    }

    @Test
    public void isHighSalaryLevel() throws Exception {
        assertThat(instance.isHighSalaryLevel()).isFalse();
    }

    @Test
    public void name() throws Exception {
        assertThat(instance.name()).isEqualTo("张三");
    }
}