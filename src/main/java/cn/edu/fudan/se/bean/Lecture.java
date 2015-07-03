package cn.edu.fudan.se.bean;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * Created by Dawnwords on 2015/5/23.
 */
public class Lecture implements Serializable {
    public final String name;
    public final String teacher;
    public final int capacity;

    public Lecture(String name, String teacher, int capacity) {
        this.name = name;
        this.teacher = teacher;
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
