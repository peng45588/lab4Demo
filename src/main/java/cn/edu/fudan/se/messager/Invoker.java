package cn.edu.fudan.se.messager;

import cn.edu.fudan.se.Parameter;
import cn.edu.fudan.se.bean.MessageRequest;
import cn.edu.fudan.se.bean.MessageResponse;
import com.alibaba.rocketmq.client.producer.SendResult;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Dawnwords on 2015/5/23.
 */
public class Invoker extends Messager{


    private ConcurrentHashMap<String, ListResponseHandler> idHandlerMap;
    private int responsorCount;
    private static Invoker invoker = null;
    HttpServletResponse response;
    public Invoker(int responsorCount) {
        super(Parameter.TOPIC, Parameter.INVOKER_CONSUMER_GROUP, Parameter.INVOKER_PRODUCER_GROUP);
        this.responsorCount = responsorCount;
        this.idHandlerMap = new ConcurrentHashMap<>();
    }
//    public static Invoker getInstance(){
//        if (invoker == null)
//            invoker = new Invoker(3);
//        return invoker;
//    }

    @Override
    protected boolean onReceiveMessage(String messageId, Object messageBody,String tag) {
        //TODO 从responsor接受到的返回值,改成专有的类 传回来的值为messagebody
        
        if (!(messageBody instanceof MessageResponse)) {
            log("response type error");
            return false;
        }
        MessageResponse ret = (MessageResponse) messageBody;
        if (!idHandlerMap.containsKey(ret.requestId)) {
            log("request id not exists");
        } else {
            idHandlerMap.get(ret.requestId).addResponse(ret.jsob);
            PrintToHtml.PrintToHtml(this.response, ret.jsob.get(0));
        }
        return true;
    }

    public void setUp(JSONObject jsob,HttpServletResponse response,String tagResponse,String tagRequest,int functionTag){
        start(tagResponse);
        this.response = response;
        try {
            if ("stop".equals(jsob.toString())) {

            } else {
                MessageRequest body = new MessageRequest(jsob,functionTag);
                SendResult sendResult = sendMessage(tagRequest, Parameter.INVOKER_KEY, body);
                //回调函数,用以得到返回值
                idHandlerMap.put(sendResult.getMsgId(), new Handler(responsorCount, sendResult.getMsgId()));
                log(String.format("[%s]%s", sendResult.getMsgId(), jsob));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private class Handler extends ListResponseHandler {
        Handler(int expectResponseCount, String messageId) {
            super(expectResponseCount, messageId);
        }
        //回调函数
        @Override
        void enoughResponse(List<String> ret, String key) {
            String print = "";
            for (int i=0;i<ret.size();i++) {
                JSONObject jsob = null;
                try {
                    jsob = new JSONObject(ret.get(i));Iterator it = jsob.keys();
                    while (it.hasNext()) {
                        print += it.next().toString() + ",";
                    }
                    print += "\n";
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            idHandlerMap.remove(key);
            log(String.format("enough Response:\n%s", print));
        }
    }

    @Override
    public void stop() {
        super.stop();
    }

    private HttpServletRequest request = null;



    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
}
