package com.hthjsj.web.kit.tree;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.hthjsj.analysis.meta.IMetaObject;
import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p> @Date : 2020/1/21 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
@Before(Tx.class)
public class TreeService {

    public List<TreeNode<String, Record>> findAll(IMetaObject metaObject, TreeConfig treeConfig) {
        List<TreeNode<String, Record>> nodes = Lists.newArrayList();
        List<Record> records = Db.use(metaObject.schemaName()).findAll(metaObject.tableName());
        records.forEach(r -> {
            TreeNode<String, Record> node = new DefaultTreeNode(treeConfig, r);
            nodes.add(node);
        });
        return new TreeBuilder<TreeNode<String, Record>>().getChildTreeObjects(nodes, treeConfig.getRootIdentify());
    }

    public List<TreeNode<String, Record>> findAllByKeywords(IMetaObject metaObject, TreeConfig treeConfig, String... keywords) {
        List<Record> allRecords = Db.use(metaObject.schemaName()).findAll(metaObject.tableName());
        Map<String, Record> allRecordsMap = recordToMap(allRecords, treeConfig);
        List<Record> hitRecords = findHitRecordByKeyWords(allRecords, keywords);
        Set<UniqueRecord> resultRecords = Sets.newHashSet();

        recursiveParent(hitRecords, allRecordsMap, resultRecords, treeConfig);

        List<TreeNode<String, Record>> nodes = Lists.newArrayList();
        resultRecords.forEach(record -> {
            TreeNode<String, Record> node = new DefaultTreeNode(treeConfig, record);
            nodes.add(node);
        });
        return new TreeBuilder<TreeNode<String, Record>>().getChildTreeObjects(nodes, treeConfig.getRootIdentify());
    }

    public List<TreeNode<String, Record>> findAllByKeywords(IMetaObject metaObject, List<Record> hitRecords, TreeConfig treeConfig) {
        List<Record> allRecords = Db.use(metaObject.schemaName()).findAll(metaObject.tableName());
        Map<String, Record> allRecordsMap = recordToMap(allRecords, treeConfig);
        Set<UniqueRecord> resultRecords = Sets.newHashSet();

        recursiveParent(hitRecords, allRecordsMap, resultRecords, treeConfig);

        List<TreeNode<String, Record>> nodes = Lists.newArrayList();
        resultRecords.forEach(record -> {
            TreeNode<String, Record> node = new DefaultTreeNode(treeConfig, record);
            nodes.add(node);
        });
        return new TreeBuilder<TreeNode<String, Record>>().getChildTreeObjects(nodes, treeConfig.getRootIdentify());
    }

    private Map<String, Record> recordToMap(List<Record> records, TreeConfig treeConfig) {
        Map<String, Record> map = Maps.newHashMap();
        records.parallelStream().forEach(record -> {
            map.put(record.getStr(treeConfig.getIdKey()), record);
        });
        return map;
    }

    private void recursiveParent(List<Record> hitRecords, Map<String, Record> allRecordsMap, Set<UniqueRecord> resultRecords, TreeConfig treeConfig) {

        Set<UniqueRecord> nextSets = Sets.newHashSet();

        for (Record hitRecord : hitRecords) {
            //将自身节点加入结果Set,贯穿全局传递
            resultRecords.add(new UniqueRecord(hitRecord, treeConfig));

            //如果父节点存在加入到结果节点
            String pid = hitRecord.getStr(treeConfig.getPidKey());
            if (!StrKit.isBlank(pid)) {
                //对根标识的判断
                if (allRecordsMap.get(pid) != null) {
                    nextSets.add(new UniqueRecord(allRecordsMap.get(pid), treeConfig));
                }
            }
        }
        //下次节点列表空时 就退出
        if (nextSets.isEmpty()) {
            return;
        }
        recursiveParent(Lists.newArrayList(nextSets), allRecordsMap, resultRecords, treeConfig);
    }

    /**
     * 根据关键字在List<Record>中找到匹配的记录
     * <pre>
     *     1. load完成数据
     *     2. 多重遍历,keywords与每条Record的column匹配
     *     3. 将命中的Record返回
     * </pre>
     *
     * @param records
     * @param keywords
     *
     * @return
     */
    private List<Record> findHitRecordByKeyWords(List<Record> records, String... keywords) {
        List<Record> hitRecords = Lists.newArrayList();
        for (Record record : records) {
            record.getColumns().forEach((key, value) -> {
                for (String keyword : keywords) {
                    if (String.valueOf(value).contains(keyword)) {
                        hitRecords.add(record);
                    }
                }
            });
        }
        return hitRecords;
    }
}
