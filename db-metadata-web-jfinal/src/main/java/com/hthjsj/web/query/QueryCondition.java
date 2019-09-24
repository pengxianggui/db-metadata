package com.hthjsj.web.query;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/9/24 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class QueryCondition {

    /**
     * 普通值过滤
     * 数值 : key=value
     * 字符 : key=value ( %like% )
     *
     * 区间过滤
     * 日期 : key_start={} & key_end={}
     * 数值 : key_lt={} & key_eq={}
     * 字符 :
     *
     * 范围过滤
     * 数值 : key = 1,3,4,5,6
     * 字符 : key = "1","2","3","4"
     *
     */


    /**
     *
     * lt （less than）               小于
     * le （less than or equal to）   小于等于
     * eq （equal to）                等于
     * ne （not equal to）            不等于
     * ge （greater than or equal to）大于等于
     * gt （greater than）            大于
     *
     */


}
