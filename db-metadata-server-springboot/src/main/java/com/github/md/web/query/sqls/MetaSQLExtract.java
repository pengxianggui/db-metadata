package com.github.md.web.query.sqls;

import cn.hutool.core.util.ArrayUtil;
import com.github.md.analysis.db.MetaDataTypeConvert;
import com.github.md.analysis.meta.IMetaField;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public abstract class MetaSQLExtract implements SQLExtract {

    public static final String SQL_PREFIX = "sql_";

    private boolean mutiple = false;

    public abstract void init(IMetaField metaField, Map<String, Object> httpParams);

    @Override
    public void loadOfHttpParams(Map<String, Object> httpParams) {

    }

    public boolean isMutiple() {
        return mutiple;
    }

    protected void setMutiple(boolean m) {
        mutiple = m;
    }

    /**
     * 请求参数的类型转换。依据元字段的数据库类型确定需要转换的目标类型
     *
     * @param metaField
     */
    public void typeChange(IMetaField metaField) {
        Map<String, Object> values = result();
        if (values == null) {
            return;
        }

        for (Map.Entry<String, Object> entry : values.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (ArrayUtil.isArray(value)) {
                Object[] valueArr = (Object[]) value;
                Object[] newValueArr = new Object[valueArr.length];
                for (int i = 0; i < valueArr.length; i++) {
                    newValueArr[i] = MetaDataTypeConvert.convert(metaField, valueArr[i]);
                }

                values.put(key, newValueArr);
            } else {
                values.put(key, MetaDataTypeConvert.convert(metaField, value));
            }
        }
    }
}
