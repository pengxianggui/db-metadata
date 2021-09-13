package com.github.md.web.query.sqls;

import java.util.Map;

/**
 * 由http参数为基准
 * "lt"
 * "le"
 * "eq"
 * "ne"
 * "ge"
 * "gt"
 * "in"
 * "nin"
 * 每一类解析规则为一类实现, 最终根据httpparam生成一个或一组条件
 */
public interface SQLExtract {

    void loadOfHttpParams(Map<String, Object> httpParams);

    /**
     * 条件结果,将merge回 conds
     *
     * @return
     */
    Map<String, Object> result();
}
