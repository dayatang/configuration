package yang.yu.configuration;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by yyang on 16/6/30.
 */
public class DateUtils {

    private DateUtils() {

    }

    public static Date createDate(int year, int month, int day) {
        Calendar result = Calendar.getInstance();
        result.set(year, month, day, 0, 0, 0);
        result.set(Calendar.MILLISECOND, 0);
        return result.getTime();

    }
}
