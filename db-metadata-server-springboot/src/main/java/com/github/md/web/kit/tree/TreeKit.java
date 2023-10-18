package com.github.md.web.kit.tree;

import com.alibaba.fastjson.serializer.AfterFilter;
import com.github.md.web.ex.WebException;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;
import java.util.function.Consumer;

/**
 * <p> @Date : 2020/8/6 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class TreeKit {

    public final static AfterFilter getAfterFilter(Consumer<Record> callBack) {
        return new AfterFilter() {
            /* object为运行时传TreeNode */
            @Override
            public void writeAfter(Object object) {
                if (object instanceof TreeNode) {
                    TreeNode treeNode = (TreeNode) object;
                    Object currNode = treeNode.currNode();
                    if (currNode instanceof Record) {
                        callBack.accept((Record) currNode);
                        ((Record) currNode).getColumns().forEach((key, value) -> {
                            writeKeyValue(key, value);
                        });
                    } else {
                        throw new WebException("还未支持的TreeNode Json 转换");
                    }
                }
            }
        };
    }

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

    /**
     * 从tree中提取出所有主键，放到result中
     *
     * @param tree
     * @param result 这是一个主键列表，注意，由于dbmeta需要兼容联合主键的情况，所以result中的元素是数组
     * @param <S>
     * @param <N>
     */
    public static <S, N> void getChildIds(List<TreeNode<S, N>> tree, List<Object> result) {
        for (TreeNode treeNode : tree) {
            if (treeNode.getChildren() != null && !treeNode.getChildren().isEmpty()) {
                getChildIds(treeNode.getChildren(), result);
            } else {
                result.add(new Object[]{treeNode.getId()});
            }
        }
    }
}
