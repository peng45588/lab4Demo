package cn.edu.fudan.se.bean;


import java.io.Serializable;

/**
 * Created by snow on 15-6-20.
 */
public class Time implements Serializable {
    private int weekday;
    private int period;

    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }
}
