package com.github.md.web.kit.tree;

import com.jfinal.plugin.activerecord.Record;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p> @Date : 2019/12/18 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Getter
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

    @Override
    public int compareTo(TreeNode node) {
        try {
            if (this.getTreeConfig().isAsc()) {
                return getOrder().compareTo(node.getOrder());
            } else {
                return 0 - getOrder().compareTo(node.getOrder());
            }
        } catch (ClassCastException e) { // 排序前后的两个值类型不同时会抛出异常,
            return 0;
        }
    }

    @Override
    public Comparable getOrder() {
        if (treeConfig.getOrderBy() == null) {
            return Integer.MAX_VALUE;
        }

        Object value = this.node.getObject(strippingQuotes(treeConfig.getOrderBy()), Integer.MAX_VALUE);
        if (value instanceof String) {
            return (String) value;
        }
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        if (value instanceof Date) {
            return (int) ((Date) value).getTime();
        }

        return Integer.MAX_VALUE;
    }

    /**
     * 防止 数据库 内置字段时会带上反引号。如: `index`
     *
     * @param orderBy
     * @return
     */
    private String strippingQuotes(String orderBy) {
        Pattern pattern = Pattern.compile("^`(.*)`$");
        Matcher matcher = pattern.matcher(orderBy);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return orderBy;
    }
}
