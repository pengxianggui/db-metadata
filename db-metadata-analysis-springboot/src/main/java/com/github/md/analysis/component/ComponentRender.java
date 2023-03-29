package com.github.md.analysis.component;

import com.github.md.analysis.kit.Kv;

/**
 * 组件渲染工具
 * <p> @Date : 2019/11/27 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface ComponentRender<C> {

    C component();

    /**
     * 渲染json配置，即生成json。需要保证幂等，防止重复调用render导致重复数据
     *
     * @return
     */
    Kv render();
}
