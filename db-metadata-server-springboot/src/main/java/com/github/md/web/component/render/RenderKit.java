package com.github.md.web.component.render;

import com.github.md.analysis.meta.ConfigExtension;
import com.github.md.analysis.meta.IMetaField;
import com.github.md.web.component.attr.AttributeBuilder;
import com.github.md.web.component.form.FormField;

import java.util.ArrayList;
import java.util.List;

/**
 * 渲染扩展工具。
 *
 * @author pengxg
 */
public class RenderKit {

    private static List<ConfigExtension<IMetaField, AttributeBuilder.FatAttributeBuilder, FormField>> fieldRenderExtensions;

    /**
     * 字段渲染扩展——渲染阶段扩展。比如以下几个场景：
     * <p>
     * 场景一: 当渲染FormView实例时，需要根据formType动态调整一些field实例配置，这种调整是不能入库的，因为数据库里存储的objectCode.FormView实例配置是不区分
     * 表单类型是ADD, UPDATE还是VIEW。此时只能在渲染阶段动态扩展，而不是实例计算期间扩展。
     * <p>
     * 场景儿: 当渲染TableView实例时，部分field实例配置中可能存在render配置项，其作用为动态渲染表格单元。而2.4版本开始，render配置项的值支持一个编码，指向
     * 一个snippet。因此数据库存储时存的是snippet code，但是页面渲染时，需要将其替换为snippet的代码内容。这个场景下，render值入库时只能是snippet code,
     * 因为编辑时还得下拉带出来，更重要的原因是编辑snippet需要达到效果——所有使用此snippet的实例都能更新。
     *
     * @param metaField 元字段
     * @param builder   配置构造
     * @param component 域组件
     */
    public static void renderExtend(IMetaField metaField, AttributeBuilder.FatAttributeBuilder builder, FormField component) {
        if (fieldRenderExtensions != null) {
            for (ConfigExtension<IMetaField, AttributeBuilder.FatAttributeBuilder, FormField> renderExt : fieldRenderExtensions) {
                renderExt.config(metaField, builder, component);
            }
        }
    }

    /**
     * 扩展配置的追加入口
     *
     * @param extension
     */
    public static void addRenderExtension(ConfigExtension<IMetaField, AttributeBuilder.FatAttributeBuilder, FormField> extension) {
        if (fieldRenderExtensions == null) {
            fieldRenderExtensions = new ArrayList<>();
        }
        fieldRenderExtensions.add(extension);
    }
}
