package com.hthjsj;

import com.alibaba.fastjson.JSON;
import com.hthjsj.web.kit.tree.TreeBuilder;
import com.hthjsj.web.kit.tree.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> @Date : 2019/12/18 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class TreeTest {

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