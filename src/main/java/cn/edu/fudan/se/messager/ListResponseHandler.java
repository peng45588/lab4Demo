package cn.edu.fudan.se.messager;

//import com.alibaba.fastjson.JSONObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Dawnwords on 2015/5/24.
 */
public abstract class ListResponseHandler {
    private final int expectResponseCount;
    private AtomicInteger responseCount;
    //    private Vector<T> result;
    private List<String> result;
    private String key;

    ListResponseHandler(int expectResponseCount, String key) {
        this.key = key;
        this.expectResponseCount = expectResponseCount;
        this.responseCount = new AtomicInteger(0);
        this.result = new ArrayList<String>();
    }

    void addResponse(List<String> jsob) {
//        result.addAll(lecture);
        result = jsob;
        if (responseCount.incrementAndGet() == expectResponseCount) {
            enoughResponse(result, key);
        }
    }

    abstract void enoughResponse(List<String> jsob, String key);
}
