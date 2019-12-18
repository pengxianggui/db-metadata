package com.hthjsj.web.kit.tree;

import java.util.List;

/**
 * 树型节点顶层接口
 * <p>Created by konbluesky </p>
 * <p>Date : 2016/12/3 下午7:10</p>
 */
public interface TreeNode<IdType, Node> {

    IdType getId();

    void setId(IdType id);

    IdType getParentId();

    void setParentId(IdType parentId);

    String getName();

    void setName(String name);

    List<Node> getChildren();

    void setChildren(List<Node> children);

    Node currNode();
}
