package com.github.md.web.component.render;

import com.alibaba.fastjson.JSON;
import com.github.md.analysis.meta.IMetaObject;
import com.github.md.web.component.TreeView;
import com.github.md.web.ui.ComponentInstanceConfig;
import com.google.common.base.Preconditions;

/**
 * @author pengxg
 * @date 2022/3/20 8:12 下午
 */
public class TreeViewRender extends MetaViewRender<TreeView> {

    public TreeViewRender(IMetaObject metaObject, TreeView treeView, ComponentInstanceConfig instanceFlatConfig) {
        super(metaObject, treeView, instanceFlatConfig);

        Preconditions.checkNotNull(metaObject.configParser().treeConfig(), "未找到[%s]对象的数据结构配置信息,请在[元对象配置]设置[数据结构->树形表]", metaObject.code());
        // 将元对象的树结构配置设置到组件实例配置中
        component.getMeta().putIfAbsent("treeConfig", JSON.parseObject(metaObject.configParser().treeConfig()));
    }
}
