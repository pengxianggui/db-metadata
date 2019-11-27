package com.hthjsj.web.ui;

import com.google.common.collect.Lists;
import com.hthjsj.analysis.component.Component;
import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.analysis.meta.IMetaField;
import com.hthjsj.analysis.meta.MetaObject;
import com.hthjsj.web.ServiceManager;
import com.hthjsj.web.UtilKit;
import com.hthjsj.web.component.ViewFactory;
import com.hthjsj.web.component.form.FormFieldFactory;
import com.jfinal.kit.Kv;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;

/**
 * <p> @Date : 2019/11/4 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class ViewAssembleFactory implements MetaViewAdapterFactory {

    public static final MetaViewAdapterFactory me = new ViewAssembleFactory();

    public static MetaViewAdapterFactory me() {
        return me;
    }

    /**
     * 计算元子段列 实例配置,组装成MetaFieldViewAdapter对象
     *
     * @param fields
     * @param instanceAllConfig
     *
     * @return
     */
    private List<MetaFieldViewAdapter> fetchFieldsAdapter(Collection<IMetaField> fields, Kv instanceAllConfig) {

        List<MetaFieldViewAdapter> metaFields = Lists.newArrayList();

        for (IMetaField field : fields) {
            Kv fieldInstanceConfig = UtilKit.getKv(instanceAllConfig, field.fieldCode());
            //TODO 配置为空时,该字段则不存在实例配置
            if (!fieldInstanceConfig.isEmpty()) {
                Component fieldComponent = FormFieldFactory.createFormFieldDefault(field, fieldInstanceConfig);
                metaFields.add(new MetaFieldViewAdapter(field, fieldComponent));
            }
        }

        return metaFields;
    }

    /**
     * 使用系统默认ComponentConfig 信息构建容器View组件
     *
     * @param metaObject
     * @param componentType
     *
     * @return
     */
    @Override
    public MetaObjectViewAdapter createMetaObjectViewAdapter(MetaObject metaObject, ComponentType componentType) {

        /**
         * 校验元对象
         * 查找 元对象+component_instance 配置
         *
         * 构建 MetaFieldViewAdapter ( 需要 MetaFields + component_instance)
         *
         * 装配 MetaObjectViewAdapter
         *
         */
        Component containerComponent = ViewFactory.createEmptyViewComponent(componentType.getCode());
        //全部全局配置
        Kv globalComponentAllConfig = ServiceManager.componentService().loadComponentsFlatMap();
        //某一组件全局配置
        Kv globalComponentConfig = UtilKit.getKv(globalComponentAllConfig, componentType.getCode());
        //完整的实例配置,元对象级+字段级
        Kv allLevelConfig = ServiceManager.componentService().loadObjectConfigFlat(componentType.getCode(), metaObject.code());
        //对象级配置
        Kv levelObjectConfig = UtilKit.getKv(allLevelConfig, metaObject.code());

        List<MetaFieldViewAdapter> fields = fetchFieldsAdapter(metaObject.fields(), allLevelConfig);

        return new MetaObjectViewAdapter(metaObject, containerComponent, globalComponentConfig, levelObjectConfig, fields);
    }
}
