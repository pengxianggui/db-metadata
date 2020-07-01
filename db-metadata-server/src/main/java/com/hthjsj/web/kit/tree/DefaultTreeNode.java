package com.hthjsj.web.kit.tree;

import com.jfinal.plugin.activerecord.Record;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> @Date : 2019/12/18 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class DefaultTreeNode implements TreeNode<String, Record> {

    Record node = new Record();

    List<TreeNode<String, Record>> children;

    TreeConfig treeConfig;

    public DefaultTreeNode(TreeConfig treeConfig, Record record) {
        this.treeConfig = treeConfig;
        this.node = record;
    }

    @Override
    public String getId() {
        return node.getStr(treeConfig.getIdKey());
    }

    @Override
    public void setId(String id) {
        node.set(treeConfig.getIdKey(), id);
    }

    @Override
    public String getParentId() {
        return node.getStr(treeConfig.getPidKey());
    }

    @Override
    public void setParentId(String parentId) {
        node.set(treeConfig.getPidKey(), parentId);
    }

    @Override
    public String getName() {
        return node.getStr(treeConfig.getLabel());
    }

    @Override
    public void setName(String name) {
        node.set(treeConfig.getLabel(), name);
    }

    @Override
    public List<TreeNode<String, Record>> getChildren() {
        if (children == null) {
            children = new ArrayList<>();
        }
        return children;
    }

    @Override
    public void setChildren(List<TreeNode<String, Record>> children) {
        this.children = children;
    }

    @Override
    public Record currNode() {
        return node;
    }
}
