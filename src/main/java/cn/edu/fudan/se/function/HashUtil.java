package cn.edu.fudan.se.function;

import cn.edu.fudan.se.bean.CourseInfo;
import cn.edu.fudan.se.bean.Time;

/**
 * Created by snow on 15-7-4.
 */
public class HashUtil {
    public static String courseHash(Time courseTime) {
        int weekday = courseTime.getWeekday();
        int period = courseTime.getPeriod();
        return String.valueOf((weekday*period)%4);
    }
}
