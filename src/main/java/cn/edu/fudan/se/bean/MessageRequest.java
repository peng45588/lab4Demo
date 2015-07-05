package cn.edu.fudan.se.bean;

import com.alibaba.fastjson.JSON;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by Dawnwords on 2015/5/24.
 */
public class MessageRequest implements Serializable {
    public String id;
    public int functionTag;
    public MessageRequest(JSONObject id,int functionTag) {
        if (id==null)
            this.id = System.currentTimeMillis()+"";
        else
            this.id = id.toString();
        this.functionTag = functionTag;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
