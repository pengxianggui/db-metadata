package com.github.md.web.config.json;

import com.alibaba.fastjson.serializer.*;
import com.jfinal.plugin.activerecord.Record;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Support JFinal Record object Serializer
 * <p> @Date : 2021/9/9 </p>
 * <p> @Project : db-metadata-server-springboot</p>
 *
 * <p> @author konbluesky </p>
 */
public class RecordSerializer implements ObjectSerializer {
    private static final ThreadLocal<SerializeFilter[]> customSerializeFilters = new ThreadLocal<>();

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        if (object != null) {
            Record record = (Record) object;
            install(serializer);
            serializer.write(record.getColumns());
            uninstall(serializer); // doubt 是否需要移除呢？若serializer是每次执行序列化时创建的，那就无需移除，否则需要移除
        }

        customSerializeFilters.remove();
    }

    private void install(JSONSerializer serializer) {
        if (customSerializeFilters.get() == null) {
            return;
        }

        for (SerializeFilter filter : customSerializeFilters.get()) {
            serializer.addFilter(filter);
        }
    }

    private void uninstall(JSONSerializer serializer) {
        if (customSerializeFilters.get() == null) {
            return;
        }

        for (SerializeFilter filter : customSerializeFilters.get()) {
            if (filter == null) {
                continue;
            }

            if (filter instanceof PropertyPreFilter) {
                serializer.getPropertyPreFilters().remove(filter);
            }

            if (filter instanceof NameFilter) {
                serializer.getNameFilters().remove(filter);
            }

            if (filter instanceof ValueFilter) {
                serializer.getValueFilters().remove(filter);
            }

            if (filter instanceof ContextValueFilter) {
                serializer.getContextValueFilters().remove(filter);
            }

            if (filter instanceof PropertyFilter) {
                serializer.getPropertyFilters().remove(filter);
            }

            if (filter instanceof BeforeFilter) {
                serializer.getBeforeFilters().remove(filter);
            }

            if (filter instanceof AfterFilter) {
                serializer.getAfterFilters().remove(filter);
            }

            if (filter instanceof LabelFilter) {
                serializer.getLabelFilters().remove(filter);
            }
        }
    }

    /**
     * 设置Record序列化时自定义filter，可用于排除一些字段，或者更多自定义配置
     *
     * @param filters
     */
    public static void setFilters(SerializeFilter... filters) {
        RecordSerializer.customSerializeFilters.set(filters);
    }
}
