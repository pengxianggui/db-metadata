package com.github.md.web.ui.meta;

import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.meta.ConfigExtension;
import com.github.md.analysis.meta.IMetaField;
import com.github.md.web.component.attr.AttributeBuilder;
import com.github.md.web.ui.meta.form.*;
import com.github.md.web.ui.meta.table.TableColumnConfigExtension;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

/**
 * TODO 除了定义根据 字段类型的推荐， 还应当支持根据 组件类型。来达到更精确的配置推荐
 * 组件实例配置的扩展,ComponentType 传的是容器type
 * <p> @Date : 2019/12/18 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class InstanceConfigExtension implements ConfigExtension<IMetaField, AttributeBuilder.FatAttributeBuilder, ComponentType> {

    // 表单、搜索面板 下的域配置扩展
    private static final Set<ConfigExtension<IMetaField, AttributeBuilder.FatAttributeBuilder, ComponentType>> formFieldConfigExtensions = Sets.newLinkedHashSet();

    // 表格、树形表格 下的域配置扩展
    private static final Set<ConfigExtension<IMetaField, AttributeBuilder.FatAttributeBuilder, ComponentType>> tableViewFieldConfigExtensions = Sets.newLinkedHashSet();

    public InstanceConfigExtension() {
        // 注意顺序！
        formFieldConfigExtensions.add(new CommonRecommend());
        formFieldConfigExtensions.add(new TextRecommend());
        formFieldConfigExtensions.add(new BoolRecommend());
        formFieldConfigExtensions.add(new NumRecommend());
        formFieldConfigExtensions.add(new DateRecommend());
        formFieldConfigExtensions.add(new JsonRecommend());
        formFieldConfigExtensions.add(new OptionsRecommend());
        formFieldConfigExtensions.add(new FileRecommend());

        tableViewFieldConfigExtensions.add(new TableColumnConfigExtension());
    }

    @Override
    public void config(IMetaField metaField, AttributeBuilder.FatAttributeBuilder config, ComponentType containerType) {
        if (containerType == ComponentType.FORMVIEW || containerType == ComponentType.SEARCHVIEW) {
            for (ConfigExtension<IMetaField, AttributeBuilder.FatAttributeBuilder, ComponentType> configExtension : formFieldConfigExtensions) {
                configExtension.config(metaField, config, containerType);
            }
        }

        if (containerType == ComponentType.TABLEVIEW || containerType == ComponentType.TABLETREEVIEW) {
            for (ConfigExtension<IMetaField, AttributeBuilder.FatAttributeBuilder, ComponentType> configExtension : tableViewFieldConfigExtensions) {
                configExtension.config(metaField, config, containerType);
            }
        }
    }
}
