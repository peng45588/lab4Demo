package cn.edu.fudan.se.function;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by MXQTmac on 2015/7/3.
 */
class  out extends TimerTask {
    public void run(){
        System.out.println("snow");
    }
}
public class Test {
    public static void main(String [] args){
        Timer timer = new Timer();
        timer.schedule(new out(), 3 * 1000);
        System.out.println("myqTmac");
//        Servlet.clearData();
//        addSchoolInfoTest();
//        addStudentInfoTest();
//        addCourseInfoTest();
//        queryScheduleTest();
//        dropCourseTest();
//        queryScheduleTest();
    }
    public static void addSchoolInfoTest() {
        JSONObject addSchoolInfoJSON = new JSONObject();
        try {
            addSchoolInfoJSON.put("schoolName", "SS");
            addSchoolInfoJSON.put("creditRequirement",32);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject addSchoolInfoResult = Servlet.addSchoolInfo(addSchoolInfoJSON);

        System.out.println(addSchoolInfoResult.toString());
    }

    public static void addCourseInfoTest() {
        JSONObject addCourseInfoJSON = new JSONObject();
        try {
            addCourseInfoJSON.put("courseId", "SS100120120121");
            addCourseInfoJSON.put("schoolName", "SS");
            addCourseInfoJSON.put("courseName","CS");
            addCourseInfoJSON.put("teacherName", "John");
            addCourseInfoJSON.put("credit",4);
            addCourseInfoJSON.put("location", "2103");
            JSONObject timeJSON = new JSONObject();
            timeJSON.put("weekday", 3);
            timeJSON.put("period", 3);
            addCourseInfoJSON.put("time", timeJSON);
            addCourseInfoJSON.put("capacity", 90);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        CourseBackUp.SaveCourse(addCourseInfoJSON);
        JSONObject addCourseInfoResult = Servlet.addCourseInfo(addCourseInfoJSON);
        System.out.println(addCourseInfoResult.toString());
    }

    public static void addStudentInfoTest() {
        JSONObject addStudentInfoJSON = new JSONObject();
        try {
            addStudentInfoJSON.put("studentId", "12302010023");
            addStudentInfoJSON.put("name", "梅元乔");
            addStudentInfoJSON.put("gender","男");
            addStudentInfoJSON.put("schoolName", "SS");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject addStudentInfoResult = Servlet.addStudentInfo(addStudentInfoJSON);

        System.out.println(addStudentInfoResult.toString());
    }

    public static void queryCourseByTimeTest() {
        JSONObject queryCourseByTimeJSON = new JSONObject();
        try {
            JSONObject timeJSON = new JSONObject();
            timeJSON.put("weekday", 1);
            timeJSON.put("period", 2);
            queryCourseByTimeJSON.put("time", timeJSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject queryCourseByTimeResult = Servlet.queryCourseByTime(queryCourseByTimeJSON);

        System.out.println(queryCourseByTimeResult.toString());
    }

    public static void queryCourseByIdTest() {
        JSONObject queryCourseByIdJSON = new JSONObject();
        try {
            queryCourseByIdJSON.put("courseId", "SS100120120120");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject queryCourseByIdResult = Servlet.queryCourseById(queryCourseByIdJSON);

        System.out.println(queryCourseByIdResult.toString());
    }

    public static void queryScheduleTest() {
        JSONObject in = new JSONObject();
        try {
            in.put("studentId", "12302010023");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject out = Servlet.querySchedule(in);
        System.out.println(out.toString());
    }

    public static void selectCourseTest() {
        JSONObject in = new JSONObject();
        try {
            in.put("studentId","12302010023");
            in.put("courseId","SS100120120121");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject out = Servlet.selectCourse(in);
        System.out.println(out.toString());
    }

    public static void dropCourseTest() {
        JSONObject in = new JSONObject();
        try {
            in.put("studentId","12302010023");
            in.put("courseId","SS100120120120");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject out = Servlet.dropCourse(in);
        System.out.println(out.toString());
    }

}