package cn.edu.fudan.se.function;

import cn.edu.fudan.se.bean.CourseBackUpInfo;
import cn.edu.fudan.se.bean.CourseInfo;
import cn.edu.fudan.se.bean.Time;
import cn.edu.fudan.se.dac.Condition;
import cn.edu.fudan.se.dac.DACFactory;
import cn.edu.fudan.se.dac.DataAccessInterface;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by snow on 15-7-5.
 */
public class CourseBackUp {
    //加入dac
    static DataAccessInterface<CourseBackUpInfo> dac = DACFactory.getInstance().createDAC(CourseBackUpInfo.class);

    //添加课程后，可以选择返回time
    public static Time SaveCourse(JSONObject jsInput) {
        Time time = new Time();
        try {
            CourseInfo ci = new CourseInfo();
            String courseId = jsInput.getString("courseId");
            ci.setCourseId(jsInput.getString("courseId"));
            ci.setSchoolName(jsInput.getString("schoolName"));
            ci.setCourseName(jsInput.getString("courseName"));
            ci.setTeacherName(jsInput.getString("teacherName"));
            ci.setCredit(jsInput.getInt("credit"));
            ci.setLocation(jsInput.getString("location"));
            ci.setCapacity(jsInput.getInt("capacity"));
            ci.setSurplus(jsInput.getInt("capacity"));//课程余量与容量一致

            JSONObject jsobTime = jsInput.getJSONObject("time");

            time.setWeekday(jsobTime.getInt("weekday"));
            time.setPeriod(jsobTime.getInt("period"));
            ci.setTime(time);

            CourseBackUpInfo backUp = new CourseBackUpInfo();
            backUp.setCourse(ci);
            //判断课程、时间地点、教师冲突、院系

            if (isCourse(courseId,dac))//课程不存在
                if (Judge.isTimeOrLocation(time, jsInput.getString("location")))//时间地点不冲突
                    if (Judge.isTeacherTime(time, jsInput.getString("teacherName")))//教师时间不冲突
                        if (!Judge.isSchool(jsInput.getString("schoolName"))) {//院系存在
                            dac.beginTransaction();
                            dac.add(backUp);
                            dac.commit();
                        }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    //  课程不存在 不存在：true
    public static boolean isCourse(final String courseId,DataAccessInterface<CourseBackUpInfo> dac){
        Condition<CourseBackUpInfo> conditionCou = new Condition<CourseBackUpInfo>() {
            @Override
            public boolean assertBean(CourseBackUpInfo courseBackUp) {
                return courseBackUp.getCourse().getCourseId().equals(courseId);
            }
        };
        List list = dac.selectByCondition(conditionCou);
        if (list.size() == 0)
            return true;
        else
            return false;
    }

    // 获得 time
    public static Time getTime(String courseId){
        Condition<CourseBackUpInfo> condition = new Condition<CourseBackUpInfo>() {
            @Override
            public boolean assertBean(CourseBackUpInfo courseBackUp) {
                return courseId.equals(courseBackUp.getCourse().getCourseId());
            }
        };
        List list = dac.selectByCondition(condition);
        CourseBackUpInfo ci = (CourseBackUpInfo) list.get(0);
        Time time = ci.getCourse().getTime();
        return time;
    }
}
