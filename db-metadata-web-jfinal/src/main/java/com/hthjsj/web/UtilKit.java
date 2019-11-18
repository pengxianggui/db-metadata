package com.hthjsj.web;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.parser.ParserException;
import com.alibaba.druid.util.JdbcConstants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Maps;
import com.jfinal.kit.Kv;

import java.util.List;
import java.util.Map;

/**
 * <p> @Date : 2019/10/31 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class UtilKit {

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

    public static Kv getKv(Kv kv, String key) {
        return JSON.parseObject(kv.getStr(key), Kv.class);
    }

    public static List<Kv> getKvs(Kv kv, String key) {
        return JSONArray.parseArray(kv.getStr(key), Kv.class);
    }

    public static Kv getKv(String json) {
        return JSON.parseObject(json, Kv.class);
    }

    public static boolean verifySQL(String sql) {
        boolean flag = false;
        try {
            SQLUtils.parseStatements(sql, JdbcConstants.MYSQL);
            flag = true;
        } catch (ParserException e) {
            throw new WebException("SQL格式不正确 %s", sql);
        }
        return flag;
    }
}
