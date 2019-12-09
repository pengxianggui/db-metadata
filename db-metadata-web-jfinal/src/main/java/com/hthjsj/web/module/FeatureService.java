package com.hthjsj.web.module;

import com.alibaba.fastjson.JSON;
import com.hthjsj.analysis.db.SnowFlake;
import com.hthjsj.analysis.meta.MetaData;
import com.hthjsj.web.ThreadLocalUserKit;
import com.hthjsj.web.User;
import com.jfinal.aop.Before;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * <p> @Date : 2019/12/9 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
@Before(Tx.class)
public class FeatureService {

    public boolean createFeature(FeatureType type, String name, String code, MetaData config) {
        Record record = getRecord(type, name, code, config);
        return Db.save("meta_feature", "id", record);
    }

    public <T> T loadFeatureConfig(String featureCode) {
        Record record = Db.findFirst("select * from meta_feature where code=?", featureCode);
        FeatureType type = FeatureType.V(record.getStr("type"));
        return (T) JSON.parseObject(record.getStr("config"), type.configEntity);
    }

    private Record getRecord(FeatureType type, String name, String code, Kv config) {
        Record record = new Record();
        record.set("id", SnowFlake.me().nextId());
        record.set("type", type.toString());
        record.set("name", name);
        record.set("code", code);
        record.set("config", config == null ? Kv.create().toJson() : config.toJson());
        User u = ThreadLocalUserKit.getUser();
        if (u != null) {
            record.set("created_by", u.userId());
            record.set("created_time", new Date());
        }
        return record;
    }
}