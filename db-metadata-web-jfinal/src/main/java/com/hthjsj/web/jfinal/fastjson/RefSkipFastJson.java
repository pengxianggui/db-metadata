package com.hthjsj.web.jfinal.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jfinal.json.FastJson;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/9/24 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class RefSkipFastJson extends FastJson {

    @Override
    public String toJson(Object object) {
        // 优先使用对象级的属性 datePattern, 然后才是全局性的 defaultDatePattern
        String dp = datePattern != null ? datePattern : getDefaultDatePattern();
        if (dp == null) {
            return JSON.toJSONString(object, SerializerFeature.DisableCircularReferenceDetect);
        } else {
            return JSON.toJSONStringWithDateFormat(object, dp, SerializerFeature.WriteDateUseDateFormat, SerializerFeature.DisableCircularReferenceDetect);    // return JSON.toJSONString(object, SerializerFeature.WriteDateUseDateFormat);
        }
    }
}
