package com.github.md.web.kit.tree;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;

/**
 * UniqueRecord 包装Record ,因为Record未实现Comparable接口,存入TreeSet中无法保证元素 业务上唯一性
 *
 * <p> @Date : 2020/8/6 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class UniqueRecord extends Record implements Comparable<UniqueRecord> {

    private final TreeConfig treeConfig;

    public UniqueRecord(Record record, TreeConfig treeConfig) {
        this.setColumns(record.getColumns());
        this.treeConfig = treeConfig;
    }

    @Override
    public boolean equals(Object o) {
        return compareTo((UniqueRecord) o) == 0;
    }

    @Override
    public int hashCode() {
        String selfId = getStr(treeConfig.getIdKey());
        return selfId.hashCode();
    }

    /**
     * 树型结构下,一般Pid可能会设置null或者空串,s2变量的判断是针对种情况
     *
     * @param o
     *
     * @return
     */
    @Override
    public int compareTo(UniqueRecord o) {
        String selfId = getStr(treeConfig.getIdKey());
        String compareId = o.getStr(treeConfig.getIdKey());

        String selfPid = getStr(treeConfig.getPidKey());
        String comparePid = o.getStr(treeConfig.getPidKey());

        boolean s1 = false;

        if (StrKit.notBlank(selfId, compareId)) {
            s1 = selfId.equalsIgnoreCase(compareId);
        }

        boolean s2 = false;
        if (selfPid == null && comparePid == null) {
            s2 = true;
        } else if (selfPid != null && comparePid != null) {
            s2 = selfPid.equalsIgnoreCase(comparePid);
        } else if (selfPid == null || comparePid == null) {
            s2 = false;
        }
        return s1 && s2 ? 0 : -1;
    }
}
