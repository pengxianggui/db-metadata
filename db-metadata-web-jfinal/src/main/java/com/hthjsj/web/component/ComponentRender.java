package com.hthjsj.web.component;

import com.jfinal.kit.Kv;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/18 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface ComponentRender {

    /**
     * 返回组件Kv数据,用来序列json用
     *
     * @return
     */
    Kv toKv();
}
