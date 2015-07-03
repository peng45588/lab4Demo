package cn.edu.fudan.se.messager;

//import com.alibaba.fastjson.JSONObject;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Dawnwords on 2015/5/24.
 */
public abstract class ListResponseHandler<T> {
    private final int expectResponseCount;
    private AtomicInteger responseCount;
//    private Vector<T> result;
    private JSONObject result;
    private String key;

    ListResponseHandler(int expectResponseCount, String key) {
        this.key = key;
        this.expectResponseCount = expectResponseCount;
        this.responseCount = new AtomicInteger(0);
        this.result = new JSONObject();
    }

    void addResponse(String jsob) {
//        result.addAll(lecture);
        try {
            result = new JSONObject(jsob);
            if (responseCount.incrementAndGet() == expectResponseCount) {
                enoughResponse(result, key);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    abstract void enoughResponse(JSONObject jsob, String key);
}
