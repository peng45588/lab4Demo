package cn.edu.fudan.se.messager;


import cn.edu.fudan.se.Parameter;
import cn.edu.fudan.se.bean.LectureRequest;
import cn.edu.fudan.se.bean.LectureResponse;
import cn.edu.fudan.se.function.Servlet;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dawnwords on 2015/5/23.
 */
public class ResponsorHost0 extends Messager implements Runnable {
    private int responsorId;

    public ResponsorHost0(int responsorId) {
        super(Parameter.TOPIC, Parameter.RESPONSOR_CONSUMER_GROUP, Parameter.RESPONSOR_PRODUCER_GROUP);
        this.responsorId = responsorId;
    }

    @Override
    protected boolean onReceiveMessage(String messageId, Object messageBody, String tags) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        //TODO 从invoke接受到,改成专有代码 传来的值为messagebody,messagebody 可能为多个request?,
        LectureRequest lectureRequest = (LectureRequest) messageBody;
        try {
            JSONObject jsonObject = new JSONObject(lectureRequest.id);
            if (tags.contains(Parameter.REQUEST_TAG_SCHOOL)) {
                JSONObject jsob = Servlet.addSchoolInfo(jsonObject);
                List<String> ret = new ArrayList<String>();
                ret.add(jsob.toString());
                LectureResponse response = new LectureResponse(messageId, ret);
                sendMessage(Parameter.RESPONSE_TAG_COURSE + 0, Parameter.RESPONSOR_KEY, response);
            } else if (tags.contains(Parameter.REQUEST_TAG_STUDENT)) {
                // TODO 添加学生信息
            } else if (tags.contains(Parameter.REQUEST_TAG_CLEAR)) {
                // TODO 清除数据
            } else if (tags.contains(Parameter.REQUEST_TAG_COURSE)) {
                if (tags.contains(Parameter.REQUEST_TAG_SELECT)) {
                    // TODO 选课
                } else if (tags.contains(Parameter.REQUEST_TAG_DROP)) {
                    // TODO 退课
                } else if (tags.contains(Parameter.REQUEST_TAG_QUERY_BY_TIME)) {
                    // TODO 根据时间查课程
                } else if (tags.contains(Parameter.REQUEST_TAG_COURSE_INFO)) {
                    // TODO 添加课程信息
                }
            } else if (tags.contains(Parameter.REQUEST_TAG_SCHEDULE)) {
                // TODO 学生选课情况
            } else if (tags.contains(Parameter.REQUEST_TAG_QUERY_BY_ID)) {
                // TODO 根据id查课表
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return false;


        }

        return true;
    }

    @Override
    public void run() {
        start(Parameter.REQUEST_TAG_COURSE + 0 + "||" + Parameter.REQUEST_TAG_STUDENT + "||"
                + Parameter.REQUEST_TAG_SCHOOL + "||" + Parameter.REQUEST_TAG_CLEAR);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                if ("stop".equals(reader.readLine())) {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        stop();
    }

    public static void main(String[] args) {
        new Thread(new ResponsorHost0((int) (System.currentTimeMillis() % 1000))).start();
    }
}
