package com.difficode.community.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JsonUtil {
    public String getJson(int code, String msg, Map<String ,Object> map){
        JSONObject json = new JSONObject();
        json.put("code",code);
        json.put("msg",msg);
        if (map!=null){
            for (String key:map.keySet()){
                json.put(key,map.get(key));
            }
        }
        return json.toJSONString();
    }

    public String getJson(int code, String msg){
        return this.getJson(code,msg,null);
    }

    public String getJson(int code){
        return this.getJson(code,null,null);
    }
}
