package com.hthjsj.web.query;

import com.hthjsj.analysis.db.MetaDataTypeConvert;
import com.hthjsj.analysis.db.SnowFlake;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.analysis.meta.MetaData;
import com.hthjsj.analysis.meta.MetaObject;
import com.hthjsj.web.UtilKit;
import com.hthjsj.web.WebException;
import com.jfinal.kit.Kv;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * <p> @Date : 2019/10/31 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class FormDataBuilder {

    public static MetaData buildFormData(Map<String, String[]> httpParams, MetaObject metaObject, boolean isInsert) {
        Kv params = Kv.create().set(UtilKit.toObjectFlat(httpParams));
        MetaData formData = new MetaData();


        for (IMetaField metaField : metaObject.fields()) {

//            Class dbType = MetaDataTypeConvert.getType(metaField.dbType().rawData());
//            String value = params.getStr(metaField.fieldCode());
            //转值
            Object castedValue = MetaDataTypeConvert.convert(metaField, params.getStr(metaField.fieldCode()));

            try {
                //TODO 兼容逻辑 用来处理 col_checkbox -> ["2","1"] 类型的值
//                if (metaField.configParser().isMultiple()) {
//                    String t = String.valueOf(castedValue);
//                    castedValue = t.replace("[", "").replace("]", "").replace("\"", "");
//                }
                //主键处理
                if (metaField.isPrimary() && isInsert) {
                    formData.set(metaField.fieldCode(), SnowFlake.me().nextId());
                    continue;
                }
                if (castedValue != null) {
                    formData.set(metaField.fieldCode(), castedValue);
                }
            } catch (MetaDataTypeConvert.MetaDataTypeConvertException e) {
                log.error(e.getMessage(), e);
                throw new WebException("非法转换: 元字段 %s 是%s类型-> %s 失败", metaField.fieldCode(), metaField.dbType().rawData(), (String) castedValue);
            }
        }

        UtilKit.setUser(formData);
        return formData;
    }
}
