package cn.edu.fudan.se.function;

import cn.edu.fudan.se.bean.CourseInfo;
import cn.edu.fudan.se.bean.Time;

/**
 * Created by snow on 15-7-4.
 */
public class HashUtil {
    public static int courseHash(Time courseTime) {
        int weekday = courseTime.getWeekday();
        int period = courseTime.getPeriod();
        return (weekday*period)%2;
//        return 0;
    }
}
