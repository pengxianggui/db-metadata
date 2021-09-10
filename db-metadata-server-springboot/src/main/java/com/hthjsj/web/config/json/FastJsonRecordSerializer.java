package com.hthjsj.web.config.json;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
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
public class FastJsonRecordSerializer implements ObjectSerializer {

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        if (object != null) {
            Record record = (Record)object;
            serializer.write(record.getColumns());
        }
    }
}
