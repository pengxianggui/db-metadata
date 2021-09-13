package com.github.md.analysis.meta;

/**
 * <p>配置扩展接口 使用方分别代表两类扩展需求</p>
 * 1. MetaConfigFactory  生成 元对象 or 元子段 配置信息
 * <pre>
 *     M : MetaObject | MetaField
 *     C : Kv (config)
 *     T : ComponentType
 * </pre>
 * 2. ComputedKit 自动计算UI配置时用
 * <pre>
 *     M : MetaObject | MetaField
 *     C : xxxConfigBuilder
 *     T : ComponentType
 * </pre>
 * <p> @Date : 2019/12/18 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public interface ConfigExtension<M, C, T> {

    void config(M metaObj, C config, T type);
}
