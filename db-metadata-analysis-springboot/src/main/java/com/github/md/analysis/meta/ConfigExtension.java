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
 * <p>
 * 3. RenderKit 渲染时使用。渲染的结果是不会入库的，只是实例要渲染使用的时候调用此扩展。
 * <pre>
 *     M: MetaObject | MetaField
 *     C: FatAttributeBuilder
 *     T: ComponentType
 * </pre>
 *
 * <p> @author konbluesky </p>
 */
public interface ConfigExtension<M, C, T> {

    /**
     * 执行扩展配置
     *
     * @param metaObj 元对象或元字段
     * @param config  配置
     * @param type
     */
    void config(M metaObj, C config, T type);
}
