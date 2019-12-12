package com.hthjsj.analysis.db;

import com.hthjsj.analysis.meta.*;
import com.jfinal.kit.StrKit;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/9/24 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class MetaObjectMysqlAssembly implements MetaObjectAssembly<Table, IMetaObject> {

    public static final String PRIMARY = "PRI";

    @Override
    public IMetaObject assembly(Table table) {
        IMetaObject metaObject = MetaFactory.createMetaObject();

        metaObject.code(table.getTableName().toLowerCase());
        metaObject.name(table.getTableComment().trim());
        metaObject.tableName(table.getTableName().toLowerCase());
        metaObject.schemaName(table.getTableSchema().toLowerCase());
        //TODO config 使用string ,jdbc驱动不支持set jsonobject
        metaObject.config(MetaConfigFactory.createV1ObjectConfig(metaObject.code(), "true").toJson());

        List<String> pks = new ArrayList<>();
        for (int i = 0; i < table.getColumns().size(); i++) {
            Column column = table.getColumns().get(i);
            IMetaField mf = MetaFactory.createMetaField(metaObject);

            mf.objectCode(metaObject.code());
            mf.cn(column.getColumnComment().trim());
            mf.en(column.getColumnName().toLowerCase());
            mf.fieldCode(column.getColumnName().toLowerCase());

            mf.dbType(column.getDataType().toLowerCase());
            mf.dbTypeLength(column.getCharacterMaximumLength());
            mf.javaType(MetaDataTypeConvert.getTypeName(column.getDataType().toUpperCase()));

            mf.orderNum(i);

            if (PRIMARY.equals(column.getColumnKey())) {
                mf.isPrimary(true);
                pks.add(mf.fieldCode());
            } else {
                mf.isPrimary(false);
            }

            MetaFieldConfigParse fieldConfig = MetaConfigFactory.createV1FieldConfig(mf, column.getColumnDefault(), column.getIsNullable());

            mf.config(fieldConfig.toJson());
            metaObject.addField(mf);
        }
        metaObject.primaryKey(StrKit.join(pks, ","));
        return metaObject;
    }
}
