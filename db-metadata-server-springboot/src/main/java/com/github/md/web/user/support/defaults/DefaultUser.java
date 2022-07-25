package com.github.md.web.user.support.defaults;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONValidator;
import com.github.md.analysis.kit.Kv;
import com.github.md.web.kit.UtilKit;
import com.github.md.web.file.JsonUploadFileResolve;
import com.github.md.web.user.User;
import com.jfinal.kit.StrKit;
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
            if (attrMap == null) attrMap = new HashMap();
        } catch (Exception e) {
            attrMap = new HashMap();
        }

        // FIXME 存成字符串是防止 ActiveRecord 入库时json值保存出错。这是一个需要解决的问题： ActiveRecord 支持JSON存储
        data.set("attrs", JSONObject.toJSONString(attrMap));
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
    public String avatar() {
        String avatarUrl;
        String avatarValue = data.getStr("avatar");
        if (StrKit.notBlank(avatarValue) && JSONValidator.from(avatarValue).getType() == JSONValidator.Type.Array) {
            JsonUploadFileResolve uploadFileResolve = new JsonUploadFileResolve(avatarValue);
            if (uploadFileResolve.hasFile()) {
                avatarUrl = uploadFileResolve.getFiles().get(0).getUrl();
                return avatarUrl;
            }
            return null;
        }

        return avatarValue;
    }

    @Override
    public String phone() {
        return null;
    }

    @Override
    public String email() {
        return null;
    }

    @Override
    public Kv attrs() {
        return Kv.create().set(JSONObject.parseObject(data.get("attrs")));
    }

    @Override
    public Kv attrs(Map attrs) {
        UtilKit.deepMerge(attrs(), attrs, true);
        return attrs();
    }

    @Override
    public Kv toKv() {
        return Kv.create().set(this.data.getColumns());
    }
}
