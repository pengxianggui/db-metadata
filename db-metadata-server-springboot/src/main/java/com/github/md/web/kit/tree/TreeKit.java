package com.github.md.web.kit.tree;

import com.alibaba.fastjson.serializer.AfterFilter;
import com.github.md.web.WebException;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

/**
 * <p> @Date : 2020/8/6 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class TreeKit {

    /* TreeNode渲染时,通过filter 将currNode内容,渲染到json 根下 */
    public final static AfterFilter afterFilter = new AfterFilter() {

        /* object为运行时传TreeNode */
        @Override
        public void writeAfter(Object object) {
            if (object instanceof TreeNode) {
                TreeNode treeNode = (TreeNode) object;
                Object currNode = treeNode.currNode();
                if (currNode instanceof Record) {
                    ((Record) currNode).getColumns().forEach((key, value) -> {
                        writeKeyValue(key, value);
                    });
                } else {
                    throw new WebException("还未支持的TreeNode Json 转换");
                }
            }
        }
    };

    public static <S, N> void getChildIds(List<TreeNode<S, N>> tree, List<Object> result) {
        for (TreeNode treeNode : tree) {
            if (treeNode.getChildren() != null && !treeNode.getChildren().isEmpty()) {
                getChildIds(treeNode.getChildren(), result);
            } else {
                result.add(treeNode.getId());
            }
        }
    }
}
