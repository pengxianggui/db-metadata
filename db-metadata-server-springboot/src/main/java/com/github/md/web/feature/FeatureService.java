package com.github.md.web.feature;

import com.alibaba.fastjson.JSON;
import com.github.md.analysis.SpringAnalysisManager;
import com.github.md.analysis.db.SnowFlake;
import com.github.md.analysis.meta.MetaData;
import com.github.md.web.user.User;
import com.github.md.web.user.UserThreadLocal;
import com.github.md.analysis.kit.Kv;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.DbPro;
import com.jfinal.plugin.activerecord.Record;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p> @Date : 2019/12/9 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
@Service
@Transactional
public class FeatureService {

    private DbPro db() {
        return SpringAnalysisManager.me().dbMain();
    }

    public boolean createFeature(FeatureType type, String name, String code, MetaData config) {
        Record old = loadExistsFeature(code);
        if (old != null) {
            throw new FeatureException("已经有一个名[%s]的[%s]模板使用了该Code:[%s]", old.getStr("name"), old.getStr("type"), old.getStr("code"));
        }
        Record record = getRecord(type, name, code, config);
        return db().save("meta_feature", "id", record);
    }

    // insert or update(先删后加)
    public boolean saveFeature(FeatureType type, String name, String code, MetaData config, boolean buildIn) {
        db().delete("delete from meta_feature where code = ?", code);

        Record record = getRecord(type, name, code, config);
        record.set("build_in", buildIn);
        return db().save("meta_feature", record);
    }

    private Record loadExistsFeature(String featureCode) {
        return db().findFirst("select * from meta_feature where code=?", featureCode);
    }

    public <T> T loadFeatureConfig(String featureCode) {
        Record record = loadExistsFeature(featureCode);
        if (record == null) {
            throw new FeatureException("%s是无效的功能code", featureCode);
        }
        FeatureType type = FeatureType.V(record.getStr("type"));
        return (T) JSON.parseObject(record.getStr("config"), type.configEntity);
    }

    public List<Record> findAll() {
        return db().findAll("meta_feature");
    }

    public boolean deleteFeature(String[] featureCodes) {
        String idsString = StrKit.join(featureCodes, "','");
        return db().update("delete from meta_feature where code in ('" + idsString + "')") > 0;
    }

    private Record getRecord(FeatureType type, String name, String code, Kv config) {
        Record record = new Record();
        record.set("id", SnowFlake.me().nextId());
        record.set("type", type.getCode());
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
