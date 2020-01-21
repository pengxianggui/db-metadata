package com.hthjsj.web.kit.tree;

import com.google.common.collect.Lists;
import com.hthjsj.analysis.meta.IMetaObject;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * <p> @Date : 2020/1/21 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
@Before(Tx.class)
public class TreeService {

    public List<TreeNode<String, Record>> findAll(IMetaObject metaObject, TreeConfig treeConfig) {
        List<TreeNode<String, Record>> nodes = Lists.newArrayList();
        List<Record> records = Db.use(metaObject.schemaName()).findAll(metaObject.tableName());
        records.forEach(r -> {
            TreeNode<String, Record> node = new DefaultTreeNode(treeConfig, r);
            nodes.add(node);
        });
        return new TreeBuilder<TreeNode<String, Record>>().getChildTreeObjects(nodes, treeConfig.getRootIdentify());
    }
}
