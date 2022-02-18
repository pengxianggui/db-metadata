package com.github.md.web.user.support.defaults;

import com.alibaba.fastjson.JSONObject;
import com.github.md.analysis.kit.Kv;
import com.github.md.web.kit.UtilKit;
import com.github.md.web.user.User;
import com.jfinal.plugin.activerecord.Record;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * dbMeta内置基于数据库的用户
 *
 * @author pengxg
 * @date 2022/2/18 10:23 上午
 */
public class DefaultUser implements User {
    @Getter
    private Record data;

    public DefaultUser(Record data) {
        Object attrs = data.get("attrs");

        Map attrMap;
        try {
            attrMap = JSONObject.parseObject(JSONObject.toJSONString(attrs));
        } catch (Exception e) {
            attrMap = new HashMap();
        }

        data.set("attrs", attrMap);
        this.data = data;
    }

    @Override
    public String userId() {
        return data.getStr("id");
    }

    @Override
    public String userName() {
        return data.getStr("username");
    }

    @Override
    public Kv attrs() {
        return data.get("attrs");
    }

    @Override
    public Kv attrs(Map attrs) {
        UtilKit.deepMerge(attrs(), attrs, true);
        return attrs();
    }
}
