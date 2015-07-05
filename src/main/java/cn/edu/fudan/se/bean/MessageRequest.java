package cn.edu.fudan.se.bean;

import com.alibaba.fastjson.JSON;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Dawnwords on 2015/5/24.
 */
public class MessageRequest implements Serializable {
    public String id;

    public MessageRequest(JSONObject id) {
        this.id = id.toString();
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
