package com.hthjsj.web.jfinal;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.json.Json;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;

/**
 * <p> @Date : 2019/11/11 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class ExtKv extends Kv {

    public JSONObject getJson(String key) {
        String jsonString = getStr(key);
        if (StrKit.isBlank(jsonString)) {
            return new JSONObject();
        }
        return Json.getJson().parse(jsonString, JSONObject.class);
    }
}
