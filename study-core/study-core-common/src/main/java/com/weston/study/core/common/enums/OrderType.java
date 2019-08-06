package com.weston.study.core.common.enums;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;

@Getter
public enum OrderType {

    ASC(0, "升序"),
    DESC(1, "降序");

    private Integer value;
    private String name;

    OrderType(Integer index, String name) {
        this.value = index;
        this.name = name;
    }

    public boolean is(Integer index) {
        return this.value.equals(index);
    }

    public static JSONArray toJSONArray() {
        JSONArray array = new JSONArray();
        for (OrderType item : OrderType.values()) {
            array.add(item.toJSONObject());
        }
        return array;
    }

    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", value);
        jsonObject.put("name", name);
        return jsonObject;
    }

    public static OrderType getEnum(Integer index) {
        for (OrderType type : OrderType.values()) {
            if (type.getValue().equals(index)) {
                return type;
            }
        }
        return null;
    }
}
