package com.github.md.web.query;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.md.analysis.db.MetaDataTypeConvert;
import com.github.md.analysis.db.SnowFlake;
import com.github.md.analysis.kit.Kv;
import com.github.md.analysis.meta.IMetaField;
import com.github.md.analysis.meta.IMetaObject;
import com.github.md.analysis.meta.MetaData;
import com.github.md.analysis.meta.MetaFieldConfigParse;
import com.github.md.web.WebException;
import com.github.md.web.component.form.FormView;
import com.github.md.web.kit.UtilKit;
import com.github.md.web.file.UploadFile;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p> @Date : 2019/10/31 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class FormDataFactory {

    /**
     * 构建表单数据，注意：此方法用于"入"方向——即接口接收参数后，构建表单数据后入库。
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
     * @return
     */
    public static MetaData buildFormData(Map<String, String[]> httpParams, IMetaObject metaObject, boolean isInsert) {
        Kv params = Kv.create().set(UtilKit.toObjectFlat(httpParams));
        MetaData formData = new MetaData();

        for (IMetaField metaField : metaObject.fields()) {

            //转值  : ""| null | 真实值
            final Object castedValue = MetaDataTypeConvert.convert(metaField, params.getStr(metaField.fieldCode()));

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
                            if (metaObject.configParser().isAutoIncrement()) {
                                // do nothing 由数据库去自增
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

                /*处理完主键后,对于后续的字段上除了处理类型的转换,还需要对字段配置状态进行判断. 除了禁用, 其他该咋咋地*/
                if (isInsert) {
                    if (metaField.configParser().addStatus() == MetaFieldConfigParse.DISABLE) {
                        continue;
                    }
                } else {
                    if (metaField.configParser().updateStatus() == MetaFieldConfigParse.DISABLE) {
                        continue;
                    }
                }

                // 文件类型处理
                if (metaField.configParser().isFile()) {
                    List<UploadFile> files = JSON.parseArray(String.valueOf(castedValue), UploadFile.class);

                    if (metaField.dbType().isJson()) {
                        // 确保入库的文件字段 必须是有效的[]
                        if (files != null && files.size() > 0) {
                            formData.set(metaField.fieldCode(), JSON.toJSONString(files));
                        } else {
                            formData.set(metaField.fieldCode(), new JSONArray().toJSONString());
                        }
                    } else { // 解析出url，并只存储url。 这种情况时只保留一个资源。
                        if (files != null && files.size() > 0) {
                            UploadFile uploadFile = files.get(0);
                            formData.set(metaField.fieldCode(), uploadFile.getUrl());
                        } else {
                            formData.set(metaField.fieldCode(), null);
                        }
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

                //数值类型 value->defaultVal->null
                if (metaField.dbType().isNumber()) {
                    if (StrKit.notNull(castedValue)) {
                        formData.set(metaField.fieldCode(), castedValue);
                    } else {
                        if (Objects.nonNull(metaField.configParser().defaultVal())) {
                            formData.set(metaField.fieldCode(), metaField.configParser().defaultVal());
                        } else {
                            formData.set(metaField.fieldCode(), null);
                        }
                    }
                    continue;
                }

                if (metaField.dbType().isBoolean()) {
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
//                            formData.set(metaField.fieldCode(), ""); // 导致入库时本应该为null的值变成空字符串, 如果字段唯一约束, 则会抛出异常
                            formData.set(metaField.fieldCode(), null);
                        }
                    }
                    continue;
                }

                // FIXME HACK json格式处理: ActiveRecord 无法直接将JSON格式插入，需要转换为json字符串
                if (metaField.dbType().isJson()) {
                    formData.set(metaField.fieldCode(), JSON.toJSONString(castedValue));
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
        if (metaObject.buildIn()) {
            if (isInsert) {
                UtilKit.setCreateUser(formData);
            } else {
                UtilKit.setUpdateUser(formData);
            }
        }
        return formData;
    }

    /**
     * 构建表单数据。注意此方法仅用于"出"方向——即接口响应包装表单数据
     *
     * @param metaObject
     * @param record
     * @param formType
     */
    public static void buildFormData(IMetaObject metaObject, Record record, FormView.FormType formType) {
        for (IMetaField metaField : metaObject.fields()) {
            try {
                if (metaField.configParser().isFile()) {
                    String value = record.getStr(metaField.fieldCode());

                    if (StrKit.isBlank(value)) {
                        record.set(metaField.fieldCode(), new JSONArray());
                        continue;
                    }

                    JSONArray valueJSON;
                    if (metaField.dbType().isJson()) {
                        valueJSON = JSON.parseArray(value);
                    } else { // 包装成JSON
                        valueJSON = new JSONArray();
                        valueJSON.add(new JSONObject(Kv.by("url", value)));
                    }

                    record.set(metaField.fieldCode(), valueJSON);
                    continue;

                }

                if (metaField.dbType().isJson()) {
                    record.set(metaField.fieldCode(), MetaDataTypeConvert.convert(metaField, record.get(metaField.fieldCode())));
                    continue;
                }

                record.set(metaField.fieldCode(), MetaDataTypeConvert.convert(metaField, record.get(metaField.fieldCode())));
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                continue;
            }
        }
    }
}
