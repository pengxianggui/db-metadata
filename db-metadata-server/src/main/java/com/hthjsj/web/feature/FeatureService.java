package com.hthjsj.web.feature;

import com.alibaba.fastjson.JSON;
import com.hthjsj.AnalysisConfig;
import com.hthjsj.analysis.db.SnowFlake;
import com.hthjsj.analysis.meta.MetaData;
import com.hthjsj.web.user.User;
import com.hthjsj.web.user.UserThreadLocal;
import com.jfinal.aop.Before;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

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
        return AnalysisConfig.me().dbMain().save("meta_feature", "id", record);
    }

    public <T> T loadFeatureConfig(String featureCode) {
        Record record = Db.findFirst("select * from meta_feature where code=?", featureCode);
        if (record == null) {
            throw new FeatureException("%s是无效的功能code", featureCode);
        }
        FeatureType type = FeatureType.V(record.getStr("type"));
        return (T) JSON.parseObject(record.getStr("config"), type.configEntity);
    }

    public List<Record> findAll() {
        return AnalysisConfig.me().dbMain().findAll("meta_feature");
    }

    public boolean deleteFeature(String[] featureCodes) {
        String idsString = StrKit.join(featureCodes, "','");
        return Db.update("delete from meta_feature where code in ('" + idsString + "')") > 0;
    }

    private Record getRecord(FeatureType type, String name, String code, Kv config) {
        Record record = new Record();
        record.set("id", SnowFlake.me().nextId());
        record.set("type", type.toString());
        record.set("name", name);
        record.set("code", code);
        record.set("config", config == null ? Kv.create().toJson() : config.toJson());
        User u = UserThreadLocal.getUser();
        if (u != null) {
            record.set("created_by", u.userId());
            record.set("created_time", new Date());
        }
        return record;
    }
}