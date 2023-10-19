package com.github.md.analysis.meta;

import com.github.md.analysis.SpringAnalysisManager;
import com.github.md.analysis.db.*;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p> @Date : 2019-08-22 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
//@TxConfig(DbKit.MAIN_CONFIG_NAME)
@Service
@Transactional
//@ConditionalOnBean(JFinalActiveRecordPluginManager.class)
public class DbMetaService {

    private final BusinessService businessService;

    private final MysqlService dbService;

    public DbMetaService(BusinessService businessService, MysqlService dbService) {
        this.businessService = businessService;
        this.dbService = dbService;
    }

    public IMetaObject importFromTable(String schema, String table) {
        Table t = dbService.getTable(schema, table);
        MetaObjectAssembly<Table, IMetaObject> dbMetaObjectAssembly = new MetaObjectMysqlAssembly();
        return dbMetaObjectAssembly.assembly(t);
    }

    /**
     * 查找某表所有的元对象
     *
     * @param schemaName
     * @param tableName
     * @return
     */
    public List<IMetaObject> seriesOfTable(String schemaName, String tableName) {
        List<String> objs = SpringAnalysisManager.me().dbMain().query("select code from meta_object where schema_name=? and table_name=? ", schemaName, tableName);
        List<IMetaObject> result = new ArrayList<>();
        for (String metaObjectCode : objs) {
            result.add(findByCode(metaObjectCode));
        }
        return result;
    }

    public boolean isExists(String objectCode) {
        return SpringAnalysisManager.me().dbMain().queryInt("select count(1) from meta_object where code=?", objectCode) == 0;
    }

    public boolean isExists(IMetaObject metaObject, IMetaField metaField, Object value) {
        return SpringAnalysisManager.me().dbMain().queryInt("select count(1) from " + metaObject.tableName() + " where " + metaField.fieldCode() + "=?", value) > 0;
    }

    public List<IMetaObject> findAll() {
        List<IMetaObject> result = new ArrayList<>();

        List<String> objs = SpringAnalysisManager.me().dbMain().query("select code from meta_object");
        for (String metaObjectCode : objs) {
            result.add(findByCode(metaObjectCode));
        }
        return result;
    }

    public IMetaObject findByCode(String objectCode) {
        if (StrKit.isBlank(objectCode)) {
            throw new MetaOperateException("必须指定元对象编码,当前元对象编码:%s", objectCode);
        }
        Record moRecord = SpringAnalysisManager.me().dbMain().findFirst("select * from meta_object where code=?", objectCode);
        if (moRecord == null) {
            throw new MetaOperateException("无效的元对象编码: %s ", objectCode);
        }
        List<Record> metafields = SpringAnalysisManager.me().dbMain().find("select * from meta_field where object_code=? order by order_num ", objectCode);
        IMetaObject metaObject = new MetaObject(moRecord.getColumns());
        for (Record metafield : metafields) {
            MetaField defaultMetaField = new MetaField(metaObject);
            defaultMetaField.dataMap(metafield.getColumns());
            metaObject.addField(defaultMetaField);
        }
        return metaObject;
    }

    /**
     * id必须是元对象的记录id
     *
     * @param id
     * @return
     */
    public IMetaObject findById(String id) {
        if (StrKit.isBlank(id)) {
            throw new MetaOperateException("必须指定主键, 当前主键:%s", id);
        }

        Record record = SpringAnalysisManager.me().dbMain().findById("meta_object", id);
        if (record == null) {
            return null;
        }

        String code = record.getStr("code");
        return findByCode(code);
    }

    public IMetaField findFieldByCode(String objectCode, String fieldCode) {
        IMetaField metaField = null;
        if (StrKit.notBlank(objectCode, fieldCode)) {
            Record field = SpringAnalysisManager.me().dbMain().findFirst("select * from meta_field where object_code=? and field_code=? order by order_num",
                    objectCode,
                    fieldCode);
            if (field == null) {
                throw new MetaOperateException("未查询到结果.objectCode[%s],fieldCode[%s]", objectCode, fieldCode);
            }
            //为了防止从MetaField 反查Parent时 出现空指针问题,构造MetaField时 保证赋一个MetaObject对象;
            IMetaObject metaObject = new MetaObject(findObjectRecordByCode(objectCode).getColumns());
            metaField = new MetaField(metaObject);
            metaField.dataMap(field.getColumns());
        } else {
            throw new MetaOperateException("元对象编码和字段编码必须指定,objectCode[%s],fieldCode[%s]", objectCode, fieldCode);
        }
        return metaField;
    }

    public boolean saveMetaObject(IMetaObject metaObject, boolean saveFields) {
        metaObject.dataMap().put("id", SnowFlake.me().nextId());
        boolean moSaved = SpringAnalysisManager.me().dbMain().save("meta_object", metaObject.primaryKey(), new Record().setColumns(metaObject.dataMap()));
        if (saveFields) {
            List<Record> updateRecords = new ArrayList<>();
            metaObject.fields().forEach((re) -> {
                re.dataMap().put("id", SnowFlake.me().nextId());
                //TODO independent setting
                re.objectCode(metaObject.code());
                re.dataMap().put("build_in", metaObject.buildIn());
                updateRecords.add(new Record().setColumns(re.dataMap()));
            });
            int[] result = SpringAnalysisManager.me().dbMain().batchSave("meta_field", updateRecords, 50);
            log.info("batchSave result:{}", Arrays.toString(result));
        }
        return moSaved;
    }

    public boolean updateMetaObject(IMetaObject metaObject) {
        boolean moUpdated = SpringAnalysisManager.me().dbMain().update("meta_object", metaObject.primaryKey(), new Record().setColumns(metaObject.dataMap()));
        List<Record> updateRecords = new ArrayList<>();
        metaObject.fields().forEach((re) -> {
            updateRecords.add(new Record().setColumns(re.dataMap()));
        });
        SpringAnalysisManager.me().dbMain().batchUpdate("meta_field", updateRecords, 50);
        return moUpdated;
    }

    /**
     * 删除元对象
     *
     * @param objectCode
     * @return
     */
    public boolean deleteMetaObject(String objectCode) {
        return SpringAnalysisManager.me().dbMain().delete("delete from meta_object where code=?", objectCode) > 0
                && deleteMetaFields(objectCode);
    }

    /**
     * 删除元对象对应的元字段
     *
     * @param objectCode
     * @return
     */
    public boolean deleteMetaFields(String objectCode) {
        return SpringAnalysisManager.me().dbMain().delete("delete from meta_field where object_code=?", objectCode) > 0;
    }

    /**
     * 获取该元对象在meta_object当中的记录
     * TODO [绕]
     *
     * @param objectCode
     * @return
     */
    public Record findObjectRecordByCode(String objectCode) {
        Record moRecord = SpringAnalysisManager.me().dbMain().findFirst("select * from meta_object where code=?", objectCode);
        if (moRecord == null) {
            throw new MetaOperateException("无效的元对象编码: %s ", objectCode);
        }
        return moRecord;
    }

    /**
     * 获取指定元对象,元字段 在meta_field当中的记录
     * TODO [绕]
     *
     * @param code
     * @param fieldCode
     * @return
     */
    public Record findFieldRecordByCode(String code, String fieldCode) {
        return SpringAnalysisManager.me().dbMain().findFirst("select * from meta_field where object_code=? and field_code=?", code, fieldCode);
    }

    //*****************业务操作(metadata数据库以外的操作迁移至businessService)**************************

    public boolean updateData(IMetaObject object, MetaData data) {
        return businessService.updateData(object, data);
    }

    public boolean deleteDatas(IMetaObject object, Object[] ids) {
        return businessService.deleteDatas(object, ids);
    }

    public <T> T findDataFieldById(IMetaObject metaObject, IMetaField metaField, String id) {
        return businessService.findDataFieldById(metaObject, metaField, id);
    }

    public boolean saveData(IMetaObject object, MetaData data) {
        return businessService.saveData(object, data);
    }

    public Record findDataByIds(IMetaObject object, Object... ids) {
        return businessService.findDataByIds(object, ids);
    }

    public Page<Record> paginate(Integer pageIndex, Integer pageSize, IMetaObject metaObject, String select, String sqlExceptSelect, Object... paras) {
        return businessService.paginate(pageIndex, pageSize, metaObject, select, sqlExceptSelect, paras);
    }
}
