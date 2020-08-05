package com.hthjsj.web.component.render;

import com.hthjsj.analysis.component.ComponentRender;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.web.component.TreeTableView;
import com.jfinal.kit.Kv;

/**
 * <p> @Date : 2020/8/5 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class TreeInTableViewRender implements ComponentRender<TreeTableView> {

    private final IMetaObject metaObject;

    private final TreeTableView treeTableView;

    private final Kv instanceFlatConfig;

    public TreeInTableViewRender(IMetaObject metaObject, TreeTableView treeTableView, Kv instanceFlatConfig) {
        this.metaObject = metaObject;
        this.treeTableView = treeTableView;
        this.instanceFlatConfig = instanceFlatConfig;
    }

    @Override
    public TreeTableView component() {
        return treeTableView;
    }

    @Override
    public Kv render() {
        return treeTableView.getMeta();
    }
}
