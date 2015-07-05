package cn.edu.fudan.se.bean;

import java.io.Serializable;

/**
 * Created by snow on 15-7-5.
 */
public class CourseBackUpInfo implements Serializable {
    private CourseInfo course;

    public CourseInfo getCourse() {
        return course;
    }

    public void setCourse(CourseInfo course) {
        this.course = course;
    }

}
