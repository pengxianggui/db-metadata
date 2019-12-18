package com.hthjsj.analysis.meta;

/**
 * 配置扩展接口
 * 使用方:
 * 1. MetaConfigFactory
 * 2. ComputedKit
 * <p> @Date : 2019/12/18 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface ConfigExtension<M, C> {

    void config(M metaObj, C config);
}
