package com.hthjsj.web.query;

import com.alibaba.fastjson.JSON;
import com.hthjsj.analysis.db.MetaDataTypeConvert;
import com.hthjsj.analysis.db.SnowFlake;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.analysis.meta.MetaData;
import com.hthjsj.analysis.meta.MetaFieldConfigParse;
import com.hthjsj.web.WebException;
import com.hthjsj.web.kit.UtilKit;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;
import lombok.Data;
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

    /**
     * <pre>
     *      根据request 参数,metaObject 构建MetaData,其中包含对元字段特殊配置的处理
     *      关于数据库保存"null"字符串的情况的几种说明:
     *          1. httpParams 中未传,但metaField中获得
     *          2. httpParams 中某字段 value为"null"
     *
     *      函数调用时机:
     *          1. form.doAdd
     *          2. form.doUpdate
     *          为了配合ConfigParser字段设置的新增,编辑时的各种状态,需要在处理metafield之前做一遍处理;
     * </pre>
     *
     * @param httpParams
     * @param metaObject
     * @param isInsert
     *
     * @return
     */
    public static MetaData buildFormData(Map<String, String[]> httpParams, IMetaObject metaObject, boolean isInsert) {
        Kv params = Kv.create().set(UtilKit.toObjectFlat(httpParams));
        MetaData formData = new MetaData();

        for (IMetaField metaField : metaObject.fields()) {

            //转值  : ""| null | 真实值
            Object castedValue = MetaDataTypeConvert.convert(metaField, params.getStr(metaField.fieldCode()));

            try {
                //主键处理
                if (metaField.isPrimary()) {
                    //新增时主键设置
                    if (isInsert) {
                        //非联合主键时,根据策略开关(uuid或数值序列)对主键进行赋值
                        if (!metaObject.isMultiplePrimaryKey()) {
                            if (metaObject.configParser().isNumberSequence()) {
                                formData.set(metaField.fieldCode(), SnowFlake.me().nextId());
                            }
                            if (metaObject.configParser().isUUIDPrimary()) {
                                formData.set(metaField.fieldCode(), StrKit.getRandomUUID());
                            }
                        } else {
                            formData.set(metaField.fieldCode(), castedValue);
                        }
                        //自增主键时删除主键字段,交给数据库自增,
                        if (metaObject.configParser().isAutoIncrement()) {
                            formData.remove(metaObject.primaryKey());
                        }
                    } else {//更新时主键赋值
                        formData.set(metaField.fieldCode(), castedValue);
                    }
                    continue;
                }

                /*处理完主键后,对于后续的字段上除了处理类型的转换,还需要对字段配置状态进行判断 新增[只读,隐藏,禁用],更新[只读,隐藏,禁用],*/
                if (isInsert) {
                    if (metaField.configParser().addStatus() == MetaFieldConfigParse.READONLY || metaField.configParser().addStatus() == MetaFieldConfigParse.DISABLE) {
                        continue;
                    }
                } else {
                    if (metaField.configParser().updateStatus() == MetaFieldConfigParse.READONLY
                            || metaField.configParser().updateStatus() == MetaFieldConfigParse.DISABLE) {
                        continue;
                    }
                }


                // 文件类型处理
                if (metaField.configParser().isFile()) {
                    List<String> fileConfig = metaField.configParser().fileConfig();
                    List<UploadFile> files = JSON.parseArray(String.valueOf(castedValue), UploadFile.class);
                    // 确保入库的文件字段 必须是有效的[]
                    if (files != null && files.size() > 0) {
                        for (UploadFile uploadFile : files) {
                            if (fileConfig == null || fileConfig.isEmpty()) {
                                uploadFile.setSeat("default");
                            }
                        }
                        formData.set(metaField.fieldCode(), JSON.toJSONString(files));
                    } else {
                        formData.set(metaField.fieldCode(), "[]");
                    }
                    continue;
                }
                //日期类型处理
                if (metaField.dbType().isDate()) {
                    if (StrKit.notNull(castedValue)) {
                        formData.set(metaField.fieldCode(), castedValue);
                    } else {
                        formData.set(metaField.fieldCode(), null);
                    }
                    continue;
                }
                //数值类型 value->defaultVal->0
                if (metaField.dbType().isNumber()) {
                    if (StrKit.notNull(castedValue)) {
                        formData.set(metaField.fieldCode(), castedValue);
                    } else {
                        if (StrKit.notBlank(metaField.configParser().defaultVal())) {
                            formData.set(metaField.fieldCode(), metaField.configParser().defaultVal());
                        } else {
                            formData.set(metaField.fieldCode(), 0);
                        }
                    }
                    continue;
                }

                if (metaField.dbType().isBoolean(metaField.dbTypeLength().intValue())) {
                    formData.set(metaField.fieldCode(), Boolean.parseBoolean(String.valueOf(castedValue)));
                    continue;
                }

                //文本类型处理
                if (metaField.dbType().isText()) {
                    if (StrKit.notNull(castedValue)) {
                        formData.set(metaField.fieldCode(), castedValue);
                    } else {
                        if (StrKit.notBlank(metaField.configParser().defaultVal())) {
                            formData.set(metaField.fieldCode(), metaField.configParser().defaultVal());
                        } else {
                            formData.set(metaField.fieldCode(), "");
                        }
                    }
                    continue;
                }
                // set 该metafile 转换后的value
                if (castedValue != null) {
                    formData.set(metaField.fieldCode(), castedValue);
                }
            } catch (MetaDataTypeConvert.MetaDataTypeConvertException e) {
                log.error(e.getMessage(), e);
                throw new WebException("非法转换: 元字段 %s 是%s类型-> %s 失败", metaField.fieldCode(), metaField.dbType().rawData(), (String) castedValue);
            }
        }

        //此处设置updateby和time字段,系统相关的表都有ccuu四个字段
        if (metaObject.isSystem()) {
            if (isInsert) {
                UtilKit.setCreateUser(formData);
            } else {
                UtilKit.setUpdateUser(formData);
            }
        }
        return formData;
    }

    public static void buildUpdateFormData(IMetaObject metaObject, Record record) {
        for (IMetaField metaField : metaObject.fields()) {
            // 确保回传给前端的文件字段 必须是有效的[]
            if (metaField.configParser().isFile()) {
                String filepath = record.getStr(metaField.fieldCode());
                if (StrKit.isBlank(filepath)) {
                    filepath = "[]";
                }
                record.set(metaField.fieldCode(), JSON.parseArray(filepath));
            }
        }
    }

    @Data
    public static class UploadFile {

        String name;

        String url;

        String seat;
    }
}
