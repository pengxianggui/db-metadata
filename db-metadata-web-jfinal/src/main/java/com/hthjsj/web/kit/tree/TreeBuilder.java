package com.hthjsj.web.kit.tree;

import com.alibaba.fastjson.JSON;
import com.jfinal.kit.StrKit;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * Class title:  把一个list集合,里面的bean含有 parentId 转为树形式 <br/>
 * Describe:                    <br/>
 * Created by konbluesky           <br/>
 * Date : 2016/12/3 下午7:11       <br/>
 * Project : oss    <br/>
 */
@Slf4j
public class TreeBuilder {

    List<TreeNode> returnList = new ArrayList<TreeNode>();

    public static void main(String[] args) {
        Note n1 = new Note(1, 0, "根节点");
        Note n2 = new Note(2, 1, "1节点");
        Note n3 = new Note(3, 1, "2节点");
        Note n4 = new Note(4, 3, "3节点");
        Note n5 = new Note(5, 3, "4节点");
        Note n6 = new Note(6, 2, "5节点");
        List list = new ArrayList();
        list.add(n1);
        list.add(n2);
        list.add(n3);
        list.add(n4);
        list.add(n5);
        list.add(n6);
        TreeBuilder tu = new TreeBuilder();
        List<Note> result = tu.getChildTreeObjects(list, Integer.valueOf(0));
        System.out.println(JSON.toJSONString(result));
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
    public List getChildTreeObjects(List<TreeNode> list, Object parentId) {
        List returnList = new ArrayList();
        if (list != null && list.size() > 0) {
            for (Iterator<TreeNode> iterator = list.iterator(); iterator.hasNext(); ) {
                TreeNode t = iterator.next();
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
     * 根据父节点的ID获取所有子节点，该方法顶级节点可以不为空,非树直接返回
     *
     * @param list 分类表
     *
     * @return String
     */
    public List<TreeNode> getChildTreeObjects(List<TreeNode> list) {
        if (list != null && list.size() > 0) {
            List<TreeNode> topList = new ArrayList<>();
            List<TreeNode> subList = new ArrayList<>();

            Map<String, String> idMap = new HashMap<>();

            for (Iterator<TreeNode> iterator = list.iterator(); iterator.hasNext(); ) {
                //归并所有list的id集合
                TreeNode t = iterator.next();
                idMap.put(t.getId().toString(), t.getId().toString());
            }

            for (Iterator<TreeNode> iterator = list.iterator(); iterator.hasNext(); ) {
                //获取最顶级的list
                TreeNode t = iterator.next();
                if (t.getParentId() == null || StrKit.isBlank(t.getParentId().toString())) {
                    topList.add(t);
                } else {
                    String id = idMap.get(t.getParentId().toString());
                    if (StrKit.isBlank(id)) {
                        topList.add(t);
                    } else {
                        subList.add(t);
                    }
                }
            }
            if (topList != null && topList.size() > 0 && subList != null && subList.size() > 0) {
                List<TreeNode> resultList = new ArrayList<>();
                for (TreeNode t : topList) {
                    //将儿子级别的list归并到顶级中
                    List<TreeNode> subOneList = new ArrayList<>();

                    for (TreeNode sub : subList) {
                        // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
                        if (isEqualsParentId(sub.getParentId(), t.getId())) {
                            recursionFn(subList, sub);
                            subOneList.add(sub);
                        }
                    }
                    t.setChildren(subOneList);


                    resultList.add(t);
                }
                return resultList;
            } else {
                return list;
            }
        }
        return list;
    }

    /**
     * 递归列表
     *
     * @param list
     * @param t
     */
    private void recursionFn(List<TreeNode> list, TreeNode t) {
        List<TreeNode> childList = getChildList(list, t);// 得到子节点列表
        t.setChildren(childList);
        for (TreeNode tChild : childList) {
            if (hasChild(list, tChild)) {// 判断是否有子节点
                Iterator<TreeNode> it = childList.iterator();
                while (it.hasNext()) {
                    TreeNode n = it.next();
                    recursionFn(list, n);
                }
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<TreeNode> getChildList(List<TreeNode> list, TreeNode t) {

        List<TreeNode> tlist = new ArrayList<TreeNode>();
        Iterator<TreeNode> it = list.iterator();
        while (it.hasNext()) {
            TreeNode n = it.next();
            if (isEqualsParentId(n.getParentId(), t.getId())) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list     分类表
     * @param parentId 传入的父节点ID
     * @param prefix   子节点前缀
     */
    public List<TreeNode> getChildTreeObjects(List<TreeNode> list, Object parentId, String prefix) {
        if (list == null)
            return null;
        for (Iterator<TreeNode> iterator = list.iterator(); iterator.hasNext(); ) {
            TreeNode node = iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (isEqualsParentId(node.getParentId(), parentId)) {
                recursionFn(list, node, prefix);
            }
            // 二、遍历所有的父节点下的所有子节点
            /*if (node.getParentId()==0) {
                recursionFn(list, node);
            }*/
        }
        return returnList;
    }

    private void recursionFn(List<TreeNode> list, TreeNode node, String p) {
        List<TreeNode> childList = getChildList(list, node);// 得到子节点列表
        if (hasChild(list, node)) {// 判断是否有子节点
            returnList.add(node);
            Iterator<TreeNode> it = childList.iterator();
            while (it.hasNext()) {
                TreeNode n = it.next();
                n.setName(p + n.getName());
                recursionFn(list, n, p + p);
            }
        } else {
            returnList.add(node);
        }
    }

    /**
     * 判断是否有子节点
     *
     * @param list
     * @param t
     *
     * @return
     */
    private boolean hasChild(List<TreeNode> list, TreeNode t) {
        return getChildList(list, t).size() > 0;
    }
}

class Note implements TreeNode<Integer, Object> {

    private int id;

    private int parentid;

    private String name;

    private List children;

    public Note(int id, int parentid, String name) {
        this.id = id;
        this.parentid = parentid;
        this.name = name;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getParentId() {
        return parentid;
    }

    @Override
    public void setParentId(Integer parentId) {
        this.parentid = parentId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public List getChildren() {
        return children;
    }

    @Override
    public void setChildren(List children) {
        this.children = children;
    }

    @Override
    public Object currNode() {
        return null;
    }
}