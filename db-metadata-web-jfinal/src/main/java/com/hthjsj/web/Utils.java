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
     * 普通值过滤
     * 数值 : key=value
     * 字符 : key=value ( %like% )
     * 日期 : key=2019-10-10  ->
     * <p>
     * 连续区间过滤
     * 日期 : key_start={} & key_end={}
     * [ok] 数值 : key_lt={} & key_eq={}
     * 字符 :
     * <p>
     * 非连续区间过滤
     * 数值 : key_in = 1,3,4,5,6
     * 字符 : key_in = "1","2","3","4"
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
