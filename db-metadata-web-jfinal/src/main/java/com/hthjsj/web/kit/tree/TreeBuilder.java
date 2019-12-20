package com.hthjsj.web.kit.tree;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class title:  把一个list集合,里面的bean含有 parentId 转为树形式 <br/>
 * Describe:                    <br/>
 * Created by konbluesky           <br/>
 * Date : 2016/12/3 下午7:11       <br/>
 * Project : oss    <br/>
 */
@Slf4j
public class TreeBuilder<Node extends TreeNode> {

    List<Node> returnList = new ArrayList<>();

    /**
     * 构建一级树
     *
     * @param rootNode
     * @param childrens
     * @param <T>
     * @param <N>
     *
     * @return
     */
    public Node level1Tree(Node rootNode, Node... childrens) {
        rootNode.getChildren().addAll(Lists.newArrayList(childrens));
        return rootNode;
    }

    /**
     * 判断两个父ID是否相同
     *
     * @param p1
     * @param p2
     *
     * @return
     */
    private boolean isEqualsParentId(Object p1, Object p2) {
        if (p1 != null && p1 != null) {
            return p1.equals(p2);
        } else if (p1 == null && p2 == null) {
            return true;
        } else if (p1 == null && p2 != null) {
            return "".equals(p2.toString());
        } else if (p1 != null && p2 == null) {
            return "".equals(p1.toString());
        } else {
            return false;
        }
    }

    /**
     * 根据父节点的ID获取所有子节点，该方法顶级节点必须为空
     *
     * @param list     分类表
     * @param parentId 传入的父节点ID
     *
     * @return String
     */
    public List<Node> getChildTreeObjects(List<Node> list, Object parentId) {
        List<Node> returnList = new ArrayList();
        if (list != null && list.size() > 0) {
            for (Iterator<Node> iterator = list.iterator(); iterator.hasNext(); ) {
                Node t = iterator.next();
                // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
                if (isEqualsParentId(t.getParentId(), parentId)) {
                    recursionFn(list, t);
                    returnList.add(t);
                }
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     *
     * @param list
     * @param t
     */
    private void recursionFn(List<Node> list, Node t) {
        List<Node> childList = getChildList(list, t);// 得到子节点列表
        t.setChildren(childList);
        for (Node tChild : childList) {
            if (hasChild(list, tChild)) {// 判断是否有子节点
                Iterator<Node> it = childList.iterator();
                while (it.hasNext()) {
                    Node n = it.next();
                    recursionFn(list, n);
                }
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<Node> getChildList(List<Node> list, Node t) {

        List<Node> tlist = new ArrayList<>();
        Iterator<Node> it = list.iterator();
        while (it.hasNext()) {
            Node n = it.next();
            if (isEqualsParentId(n.getParentId(), t.getId())) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     *
     * @param list
     * @param t
     *
     * @return
     */
    private boolean hasChild(List<Node> list, Node t) {
        return getChildList(list, t).size() > 0;
    }
}
