package com.hthjsj.web.query;

import com.hthjsj.analysis.db.MetaDataTypeConvert;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.analysis.meta.MetaData;
import com.hthjsj.analysis.meta.MetaObject;
import com.hthjsj.web.ThreadLocalUserKit;
import com.hthjsj.web.User;
import com.hthjsj.web.Utils;
import com.jfinal.kit.Kv;

import java.util.Date;
import java.util.Map;

/**
 * <p> @Date : 2019/10/31 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class FormDataBuilder {

    /**
     *
     */
    public static MetaData build(Map<String, String[]> httpParams, MetaObject metaObject, Kv formObjectConfig) {
        Kv params = Kv.create().set(Utils.toObjectFlat(httpParams));
        MetaData formData = new MetaData();


        for (IMetaField metaField : metaObject.fields()) {

            Class dbType = MetaDataTypeConvert.getType(metaField.dbType());

            Object v = MetaDataTypeConvert.cast(params.getStr(metaField.fieldCode()), dbType);

            formData.set(metaField.fieldCode(), v);
        }

        setUser(formData);
        return formData;
    }

    private static void setUser(MetaData data) {
        User user = ThreadLocalUserKit.getUser();
        data.set("created_by", user.userId());
        data.set("created_time", new Date());
    }
}
