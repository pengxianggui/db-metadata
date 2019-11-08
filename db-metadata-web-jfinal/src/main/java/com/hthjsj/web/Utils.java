package com.hthjsj.web;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * <p> @Date : 2019/10/31 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class Utils {

    /**
     * HttpServletRequest 中 request.getParameterMap 类型为Map<String, String[]>,计算时需要判断String[] 和String
     * 为了方便存取,统一转换为Map<String, Object> 格式;
     *
     * @param maps
     *
     * @return
     */
    public static Map<String, Object> toObjectFlat(Map<String, String[]> maps) {
        Map<String, Object> result = Maps.newHashMap();
        for (Map.Entry<String, String[]> e : maps.entrySet()) {
            String[] values = e.getValue();
            if (values.length == 1) {
                result.put(e.getKey(), values[0]);
            } else {
                result.put(e.getKey(), values);
            }
        }
        return result;
    }
}
