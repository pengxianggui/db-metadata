package com.hthjsj.web.kit.tree;

import com.hthjsj.web.controller.TreeController;
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

    Record node;

    List<Record> children;

    TreeController.TreeConfig treeConfig;

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
    public List<Record> getChildren() {
        if (children == null) {
            children = new ArrayList<>();
        }
        return children;
    }

    @Override
    public void setChildren(List<Record> children) {
        this.children = children;
    }

    @Override
    public Record currNode() {
        return node;
    }
}
