package cn.edu.fudan.se.messager;


import cn.edu.fudan.se.Parameter;
import cn.edu.fudan.se.bean.MessageRequest;
import cn.edu.fudan.se.bean.MessageResponse;
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
        super(Parameter.TOPIC, Parameter.RESPONSER_CONSUMER_GROUP, Parameter.RESPONSER_PRODUCER_GROUP);
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
        //TODO 从invoke接受到,改成专有代码 传来的值为messagebody,messagebody 可能为多个request,待做,
        MessageRequest messageRequest = (MessageRequest) messageBody;
        int functionTag = messageRequest.functionTag;
        try {
            JSONObject jsonObject = new JSONObject(messageRequest.id);
            MessageResponse response = null;
            if (tags.contains(Parameter.REQUEST_TAG_SCHOOL)) {
                // 添加学院信息
                JSONObject jsob = Servlet.addSchoolInfo(jsonObject);
                List<String> ret = new ArrayList<String>();
                ret.add(jsob.toString());
                response = new MessageResponse(messageId, ret);
                sendMessage(Parameter.RESPONSE_TAG_SCHOOL,
                        Parameter.RESPONSER_KEY, response);
            } else if (tags.contains(Parameter.REQUEST_TAG_STUDENT)) {
                // 添加学生信息
                JSONObject jsob = Servlet.addStudentInfo(jsonObject);
                List<String> ret = new ArrayList<String>();
                ret.add(jsob.toString());
                response = new MessageResponse(messageId, ret);
                sendMessage(Parameter.RESPONSE_TAG_STUDENT,
                        Parameter.RESPONSER_KEY, response);
            } else if (tags.contains(Parameter.REQUEST_TAG_CLEAR)) {
                // 清除数据
                Servlet.clearData();
            } else if (tags.contains(Parameter.REQUEST_TAG_COURSE)) {

                if (functionTag == Parameter.REQUEST_SELECT) {
                    //  选课
                    JSONObject jsob = Servlet.selectCourse(jsonObject);
                    List<String> ret = new ArrayList<String>();
                    ret.add(jsob.toString());
                    response = new MessageResponse(messageId, ret);
                    sendMessage(Parameter.RESPONSE_TAG_COURSE + 0,
                            Parameter.RESPONSER_KEY, response);
                } else if (functionTag == Parameter.REQUEST_DROP) {
                    // 退课
                    JSONObject jsob = Servlet.dropCourse(jsonObject);
                    List<String> ret = new ArrayList<String>();
                    ret.add(jsob.toString());
                    response = new MessageResponse(messageId, ret);
                    sendMessage(Parameter.RESPONSE_TAG_COURSE + 0,
                            Parameter.RESPONSER_KEY, response);
                } else if (functionTag == Parameter.REQUEST_QUERY_BY_TIME) {
                    // 根据时间查课程
                    JSONObject jsob = Servlet.queryCourseByTime(jsonObject);
                    List<String> ret = new ArrayList<String>();
                    ret.add(jsob.toString());
                    response = new MessageResponse(messageId, ret);
                    sendMessage(Parameter.RESPONSE_TAG_COURSE + 0,
                            Parameter.RESPONSER_KEY, response);
                } else if (functionTag == Parameter.REQUEST_COURSE_INFO) {
                    // 添加课程信息
                    JSONObject jsob = Servlet.addCourseInfo(jsonObject);
                    List<String> ret = new ArrayList<String>();
                    ret.add(jsob.toString());
                    response = new MessageResponse(messageId, ret);
                    sendMessage(Parameter.RESPONSE_TAG_COURSE + 0,
                            Parameter.RESPONSER_KEY, response);
                } else if (functionTag == Parameter.REQUEST_QUERY_BY_ID) {
                    // 根据id查课表
                    JSONObject jsob = Servlet.queryCourseById(jsonObject);
                    List<String> ret = new ArrayList<String>();
                    ret.add(jsob.toString());
                    response = new MessageResponse(messageId, ret);
                    sendMessage(Parameter.RESPONSE_TAG_COURSE + 0,
                            Parameter.RESPONSER_KEY, response);
                }

            } else if (tags.contains(Parameter.REQUEST_TAG_SCHEDULE)) {
                // 学生选课情况
                JSONObject jsob = Servlet.querySchedule(jsonObject);
                List<String> ret = new ArrayList<String>();
                ret.add(jsob.toString());
                response = new MessageResponse(messageId, ret);
                sendMessage(Parameter.RESPONSE_TAG_SCHEDULE,
                        Parameter.RESPONSER_KEY, response);
            } else
                return false;

        } catch (JSONException e) {
            e.printStackTrace();
            return false;


        }

        return true;
    }

    @Override
    public void run() {
        start(Parameter.REQUEST_TAG_COURSE + 0 + "||" + Parameter.REQUEST_TAG_STUDENT + "||"
                + Parameter.REQUEST_TAG_SCHOOL + "||" + Parameter.REQUEST_TAG_CLEAR+"||"+Parameter.REQUEST_TAG_SCHEDULE);
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
