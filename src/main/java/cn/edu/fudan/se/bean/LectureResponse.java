package cn.edu.fudan.se.bean;

//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;
import org.json.JSONObject;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Dawnwords on 2015/5/24.
 */
public class LectureResponse implements Serializable {
    public final String requestId;
    public final String jsob;


    public LectureResponse(String requestId, JSONObject jsob) {
        this.requestId = requestId;
        this.jsob = jsob.toString();
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
