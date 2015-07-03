package cn.edu.fudan.se.messager;

import cn.edu.fudan.se.Parameter;
import cn.edu.fudan.se.bean.Lecture;
import cn.edu.fudan.se.bean.LectureRequest;
import cn.edu.fudan.se.bean.LectureResponse;
import com.alibaba.rocketmq.client.producer.SendResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dawnwords on 2015/5/23.
 */
public class Invoker extends Messager implements Runnable {


    private ConcurrentHashMap<String, ListResponseHandler<Lecture>> idHandlerMap;
    private int responsorCount;

    public Invoker(int responsorCount) {
        super(Parameter.TOPIC, Parameter.INVOKER_CONSUMER_GROUP, Parameter.INVOKER_PRODUCER_GROUP);
        this.responsorCount = responsorCount;
        this.idHandlerMap = new ConcurrentHashMap<>();
    }

    @Override
    protected boolean onReceiveMessage(String messageId, Object messageBody) {
        //TODO 从responsor接受到的返回值,改成专有的类 传回来的值为messagebody
        
        if (!(messageBody instanceof LectureResponse)) {
            log("response type error");
            return false;
        }
        LectureResponse response = (LectureResponse) messageBody;
        if (!idHandlerMap.containsKey(response.requestId)) {
            log("request id not exists");
        } else {
            idHandlerMap.get(response.requestId).addResponse(response.jsob);
        }
        return true;
    }

    @Override
    public void run() {
        start(Parameter.RESPONSE_TAG);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String line;
            try {
                if ("stop".equals(line = reader.readLine())) {
                    break;
                } else {
                    JSONObject jsob = new JSONObject(line);
                    LectureRequest body = new LectureRequest(jsob);
                    SendResult sendResult = sendMessage(Parameter.REQUEST_TAG, Parameter.INVOKER_KEY, body);
                    //回调函数,用以得到返回值
                    idHandlerMap.put(sendResult.getMsgId(), new Handler(responsorCount, sendResult.getMsgId()));
                    log(String.format("[%s]%s", sendResult.getMsgId(), jsob));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        stop();
    }

    private class Handler extends ListResponseHandler<Lecture> {
        Handler(int expectResponseCount, String messageId) {
            super(expectResponseCount, messageId);
        }
        //回调函数
        @Override
        void enoughResponse(JSONObject jsob, String key) {
            String print = "";
//            for (Lecture lecture : result) {
//                print += lecture + "\n";
//            }
            Iterator it = jsob.keys();
            while(it.hasNext()){
                print += it.next().toString() + "\n";
            }
            idHandlerMap.remove(key);
            log(String.format("enough Response:\n%s", print));
        }
    }

    public static void main(String[] args) {
        new Thread(new Invoker(3)).start();
    }
}
