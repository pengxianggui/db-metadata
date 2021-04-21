package com.hthjsj.web.kit.tree;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * 树型节点顶层接口
 * <p>Created by konbluesky </p>
 * <p>Date : 2016/12/3 下午7:10</p>
 */
public interface TreeNode<IdType, Node> extends Comparable<TreeNode> {

    IdType getId();

    void setId(IdType id);

    IdType getParentId();

    void setParentId(IdType parentId);

    String getName();

    void setName(String name);

    List<TreeNode<IdType, Node>> getChildren();

    void setChildren(List<TreeNode<IdType, Node>> children);

    /**
     * 全家福合照: 将树的子孙后代(包括顶层根节点)节点全部拔出来, 拍平了放到列表里
     *
     * @return
     */
    default List<TreeNode<IdType, Node>> all() {
        List<TreeNode<IdType, Node>> heirs = Lists.newArrayList(this);
        for (TreeNode<IdType, Node> child : getChildren()) {
            heirs.addAll(child.all());
        }
        return heirs;
    }

    Node currNode();

    default Comparable getOrder() {
        return 0;
    }

    @Override
    default int compareTo(TreeNode node) {
        return getOrder().compareTo(node.getOrder());
    }
}
