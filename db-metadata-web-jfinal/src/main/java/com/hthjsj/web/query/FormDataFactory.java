package com.hthjsj.web.query;

import com.google.common.collect.Lists;
import com.hthjsj.analysis.db.MetaDataTypeConvert;
import com.hthjsj.analysis.db.SnowFlake;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.analysis.meta.MetaData;
import com.hthjsj.web.UtilKit;
import com.hthjsj.web.WebException;
import com.hthjsj.web.upload.UploadKit;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * <p> @Date : 2019/10/31 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class FormDataFactory {

    public static MetaData buildFormData(Map<String, String[]> httpParams, IMetaObject metaObject, boolean isInsert) {
        Kv params = Kv.create().set(UtilKit.toObjectFlat(httpParams));
        MetaData formData = new MetaData();


        for (IMetaField metaField : metaObject.fields()) {

            //转值
            Object castedValue = MetaDataTypeConvert.convert(metaField, params.getStr(metaField.fieldCode()));

            try {
                //主键处理
                if (metaField.isPrimary() && isInsert) {
                    formData.set(metaField.fieldCode(), SnowFlake.me().nextId());
                    continue;
                }
                if (castedValue != null) {
                    formData.set(metaField.fieldCode(), castedValue);
                }
                if (metaField.configParser().isFile()) {
                    List<Kv> files = UtilKit.getKvs(String.valueOf(castedValue));
                    if (files != null && files.size() > 0) {
                        formData.set(metaField.fieldCode(), files.get(0).getStr("url"));
                    } else {
                        formData.set(metaField.fieldCode(), "");
                    }
                }
            } catch (MetaDataTypeConvert.MetaDataTypeConvertException e) {
                log.error(e.getMessage(), e);
                throw new WebException("非法转换: 元字段 %s 是%s类型-> %s 失败", metaField.fieldCode(), metaField.dbType().rawData(), (String) castedValue);
            }
        }

//        UtilKit.setUser(formData);
        return formData;
    }

    public static void buildUpdateFormData(IMetaObject metaObject, Record record) {
        for (IMetaField metaField : metaObject.fields()) {

            if (metaField.configParser().isFile()) {
                if (metaObject.primaryKey().contains(",")) {
                    throw new WebException("%s 元对象为复合主键", metaObject.code());
                }
                String id = record.getStr(metaObject.primaryKey());
                String filepath = record.getStr(metaField.fieldCode());
                if (StrKit.isBlank(filepath)) {
                    continue;
                }
                String url = UploadKit.downloadUrl(metaField.objectCode(), metaField.fieldCode(), id);
                log.info("downloadUrl:{}", url);
                Kv file = Kv.create();
                file.setIfNotBlank("name", filepath); // set origin file name
                file.setIfNotBlank("url", filepath);
                file.setIfNotBlank("download_url", url);
                record.set(metaField.fieldCode(), Lists.newArrayList(file));
            }
        }
    }
}
