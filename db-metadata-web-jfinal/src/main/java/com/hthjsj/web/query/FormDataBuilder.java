package com.hthjsj.web.query;

import com.hthjsj.analysis.db.MetaDataTypeConvert;
import com.hthjsj.analysis.db.SnowFlake;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.analysis.meta.MetaData;
import com.hthjsj.analysis.meta.MetaObject;
import com.hthjsj.web.ThreadLocalUserKit;
import com.hthjsj.web.User;
import com.hthjsj.web.Utils;
import com.hthjsj.web.WebException;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Map;

/**
 * <p> @Date : 2019/10/31 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class FormDataBuilder {

    /**
     *
     */
    public static MetaData build(Map<String, String[]> httpParams, MetaObject metaObject) {
        Kv params = Kv.create().set(Utils.toObjectFlat(httpParams));
        MetaData formData = new MetaData();


        for (IMetaField metaField : metaObject.fields()) {

            Class dbType = MetaDataTypeConvert.getType(metaField.dbType().toUpperCase());
            String value = params.getStr(metaField.fieldCode());
            Object castedValue = null;
            try {
                //主键处理
                if (metaField.isPrimary()) {
                    formData.set(metaField.fieldCode(), SnowFlake.me().nextId());
                    continue;
                }

                castedValue = MetaDataTypeConvert.cast(value, dbType);
                if (StrKit.notBlank(value)) {
                    formData.set(metaField.fieldCode(), castedValue);
                }
            } catch (MetaDataTypeConvert.MetaDataTypeConvertException e) {
                log.error(e.getMessage(), e);
                throw new WebException("非法转换: 元字段 %s 是%s类型-> %s 失败", metaField.fieldCode(), dbType.getSimpleName(), value);
            }
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
