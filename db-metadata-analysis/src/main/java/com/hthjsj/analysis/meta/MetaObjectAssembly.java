package com.hthjsj.analysis.meta;

import com.hthjsj.analysis.db.Column;
import com.hthjsj.analysis.db.Table;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019-08-22 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface MetaObjectAssembly<T, M> {

    M assembly(T t);
}

class DBMetaObjectAssembly implements MetaObjectAssembly<Table, IMetaObject> {

    public static final String PRIMARY = "PRI";

    @Override
    public IMetaObject assembly(Table table) {
        MetaObject metaObject = new MetaObject();

        metaObject.code(table.getTableName().toLowerCase());
        metaObject.name(table.getTableName().toLowerCase());
        metaObject.schemaName(table.getTableSchema().toLowerCase());

        for (Column column : table.getColumns()) {
            MetaField mf = new MetaField();
            if (PRIMARY.equals(column.getColumnKey())) {
                mf.isPrimary(true);
            }
            mf.objectCode(metaObject.code());
            mf.cn(column.getColumnComment());
            mf.en(column.getColumnName().toLowerCase());
            mf.fieldCode(column.getColumnName().toLowerCase());


            metaObject.addField(mf);
        }
        return metaObject;
    }
}
