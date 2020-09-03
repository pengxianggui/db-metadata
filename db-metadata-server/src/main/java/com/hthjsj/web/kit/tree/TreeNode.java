package com.hthjsj.web.kit.tree;

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

    Node currNode();

    default Comparable getOrder() {
        return 0;
    }

    @Override
    default int compareTo(TreeNode node) {
        return getOrder().compareTo(node.getOrder());
    }
}
