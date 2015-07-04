package cn.edu.fudan.se.messager;


import cn.edu.fudan.se.Parameter;
import cn.edu.fudan.se.bean.Lecture;
import cn.edu.fudan.se.bean.LectureRequest;
import cn.edu.fudan.se.bean.LectureResponse;
import cn.edu.fudan.se.function.Servlet;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Dawnwords on 2015/5/23.
 */
public class Responsor extends Messager implements Runnable{
    private int responsorId;

    public Responsor(int responsorId) {
        super(Parameter.TOPIC, Parameter.RESPONSOR_CONSUMER_GROUP, Parameter.RESPONSOR_PRODUCER_GROUP);
        this.responsorId = responsorId;
    }

    @Override
    protected boolean onReceiveMessage(String messageId, Object messageBody) {
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
            JSONObject jsob = Servlet.addSchoolInfo(jsonObject);
            List<String> ret = new ArrayList<String>();
            ret.add(jsob.toString());
            LectureResponse response = new LectureResponse(messageId, ret);
            sendMessage(Parameter.RESPONSE_TAG, Parameter.RESPONSOR_KEY, response);
        } catch (JSONException e) {
            e.printStackTrace();
            return false;



        }

        return true;
    }

    @Override
    public void run() {
        start(Parameter.REQUEST_TAG);
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
        new Thread(new Responsor((int) (System.currentTimeMillis()%1000))).start();
    }
}
