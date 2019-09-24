package com.hthjsj.analysis.db;

import com.hthjsj.analysis.meta.*;

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
        MetaObject metaObject = new MetaObject();

        metaObject.code(table.getTableName().toLowerCase());
        metaObject.name(table.getTableComment().trim());
        metaObject.tableName(table.getTableName().toLowerCase());
        metaObject.schemaName(table.getTableSchema().toLowerCase());
        metaObject.config(MetaConfigFactory.createV1ObjectConfig(metaObject.code(), "true"));

        for (int i = 0; i < table.getColumns().size(); i++) {
            Column column = table.getColumns().get(i);
            MetaField mf = new MetaField();

            mf.objectCode(metaObject.code());
            mf.cn(column.getColumnComment().trim());
            mf.en(column.getColumnName().toLowerCase());
            mf.fieldCode(column.getColumnName().toLowerCase());

            mf.dbType(column.getDataType().toLowerCase());
            mf.dbTypeLength(column.getCharacterMaximumLength());
            mf.javaType(MetaDataType.getTypeName(column.getDataType().toUpperCase()));

            mf.orderNum(i);

            MetaConfigFactory.MetaFieldConfig fieldConfig = MetaConfigFactory.createV1FieldConfig(
                    mf.objectCode(),
                    mf.fieldCode(),
                    column.getColumnDefault(),
                    column.getIsNullable());

            mf.config(fieldConfig);

            if (PRIMARY.equals(column.getColumnKey())) {
                mf.isPrimary(true);
                metaObject.addPrimary(mf);
            }

            metaObject.addField(mf);
        }
        return metaObject;
    }
}
