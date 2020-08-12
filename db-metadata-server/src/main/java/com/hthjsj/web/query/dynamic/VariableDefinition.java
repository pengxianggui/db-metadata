package com.hthjsj.web.query.dynamic;

/**
 * <p> @Date : 2020/8/12 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface VariableDefinition {

    /**
     * 模板中调用时指定的变量名
     *
     * @return
     */
    String name();

    /**
     * 返回该变量实例
     *
     * @return
     */
    Object init();
}
