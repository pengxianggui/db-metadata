package com.github.md.web.kit.tree;

import com.jfinal.plugin.activerecord.Record;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * <p> @Date : 2020/12/3 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Data
public class TreePhases {

    private Set<UniqueRecord> resultRecords;

    private List<TreeNode<String, Record>> treeList;
}
