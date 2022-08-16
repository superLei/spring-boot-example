package com.example.service.qa.model;

import com.google.gson.Gson;
import lombok.Data;
import org.springframework.util.ReflectionUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Data
public abstract class ResultInfo {
//    private static final Gson gson = new GsonBuilder().setLongSerializationPolicy(LongSerializationPolicy.STRING).create();

    private String traceID;

    private String code = "000";

    private String message = "执行成功";

    /**
     * @return
     */
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", this.code);
        map.put("message", this.message);
        map.put("traceID", this.traceID);
        return map;
    }

    public String toJson() {
        Map<String, Object> map = this.toMap();
        Arrays.asList(this.getClass().getDeclaredFields()).stream().forEach((field) -> {
            ReflectionUtils.makeAccessible(field);
            if (!"code".equals(field.getName()) && !"message".equals(field.getName()) && !"traceID".equals(field.getName())) {
                map.put(field.getName(), ReflectionUtils.getField(field, this));
            }

        });
        return (new Gson()).toJson(map);
    }

}
