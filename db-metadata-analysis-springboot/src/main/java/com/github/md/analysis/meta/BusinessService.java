package com.github.md.analysis.meta;

import com.alibaba.fastjson.JSON;
import com.github.md.analysis.SpringAnalysisManager;
import com.github.md.analysis.MetaAnalysisException;
import com.github.md.analysis.db.SnowFlake;
import com.github.md.analysis.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 面向业务数据库查询的方法,主要是基于数据源不同而拆分开的
 * <p> @Date : 2019/12/10 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
@Service
@Transactional
//@ConditionalOnBean(JFinalActiveRecordPluginManager.class)
public class BusinessService {

    /**
     * 根据主键查找一条记录,支持复合主键的查询
     * ids结构:
     * 1. [[pk1]]
     * 2. [[pk1,pk2]]
     *
     * @param object
     * @param ids
     * @return
     */
    public Record findDataByIds(IMetaObject object, Object... ids) {
        return Db.use(object.schemaName()).findByIds(object.tableName(), object.primaryKey(), (Object[]) ids[0]); // FIXME ids参数优化兼容
    }

    public Page<Record> paginate(Integer pageIndex, Integer pageSize, IMetaObject metaObject, String select, String sqlExceptSelect, Object... paras) {
        return Db.use(metaObject.schemaName()).paginate(pageIndex, pageSize, select, sqlExceptSelect, paras);
    }

    /**
     * 根据元对象查询所有数据
     *
     * @param metaObject
     * @return
     */
    public List<Record> findData(IMetaObject metaObject) {
        return Db.use(metaObject.schemaName()).findAll(metaObject.tableName());
    }

    public List<Record> findData(IMetaObject metaObject, String select, String sqlExceptSelect, Object... paras) {
        return Db.use(metaObject.schemaName()).find(select + sqlExceptSelect, paras);
    }

    public <T> T findDataFieldById(IMetaObject object, IMetaField metaField, String id) {
        //select metaField.fileCode() from object.tableName() where object.primarykey()=id
        if (object.isMultiplePrimaryKey()) {
            throw new MetaAnalysisException("%s 元对象为复合主键", object.code());
        }
        return Db.use(object.schemaName()).queryFirst("select " + metaField.fieldCode() + " from " + object.tableName() + " where " + object.primaryKey() + "=?", id);
    }

    public boolean saveData(IMetaObject object, MetaData data) {
        boolean status = Db.use(object.schemaName()).save(object.tableName(), object.primaryKey(), new Record().setColumns(data).removeNullValueColumns());
        buriedPoint(object, Kv.create(), data);
        return status;
    }

    public boolean updateData(IMetaObject object, MetaData data) {
        Record old = Db.use(object.schemaName()).findByIds(object.tableName(), object.primaryKey(), data.getPks(object.primaryKey()));
        boolean status = Db.use(object.schemaName()).update(object.tableName(), object.primaryKey(), new Record().setColumns(data)/*.removeNullValueColumns()*/); // removeNullValueColumns导致无法将一些值置空
        buriedPoint(object, old.getColumns(), data);
        return status;
    }

    /**
     * 删除一条记录或多条
     * 支持复合主键的删除操作
     * WARN:
     * ids :[[v1, v2], [v1, v2]]
     * [v1 v1]
     *
     * @param object
     * @param ids
     * @return
     */
    public boolean deleteDatas(IMetaObject object, Object[] ids) {
        int i = 0;
        for (Object pks : ids) {
            if (pks instanceof Object[]) {
                boolean status = Db.use(object.schemaName()).deleteByIds(object.tableName(), object.primaryKey(), (Object[]) pks);
                if (status) {
                    i++;
                }
            } else {
                boolean status = Db.use(object.schemaName()).deleteById(object.tableName(), pks);
                if (status) {
                    i++;
                }
            }
        }
        return ids.length == i;
    }

    /**
     * 对通过元对象save/update的的数据进行保存;
     *
     * @param object
     * @param oldData
     * @param newData
     */
    private void buriedPoint(IMetaObject object, Map oldData, Map newData) {
        Record record = new Record();
        record.set("id", SnowFlake.me().nextId());
        record.set("object_code", object.code());
        record.set("table_name", object.tableName());
        record.set("pkey", object.primaryKey());
        // TODO support single primaryKey;
        record.set("pvalue", newData.get(object.primaryKey()));
        record.set("olddata", JSON.toJSONString(oldData));
        record.set("newData", JSON.toJSONString(newData));
        SpringAnalysisManager.me().dbMain().save("meta_change_log", record);
    }
}
