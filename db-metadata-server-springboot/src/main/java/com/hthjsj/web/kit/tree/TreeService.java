package com.hthjsj.web.kit.tree;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.hthjsj.analysis.meta.IMetaObject;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p> @Date : 2020/1/21 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
@Service
@Transactional
public class TreeService {

    /**
     * 根据元数据构建所有数据的树群
     *
     * @param metaObject
     * @param treeConfig
     *
     * @return
     */
    public List<TreeNode<String, Record>> tree(IMetaObject metaObject, TreeConfig treeConfig) {
        List<TreeNode<String, Record>> nodes = Lists.newArrayList();
        List<Record> records = Db.use(metaObject.schemaName()).findAll(metaObject.tableName());
        records.forEach(r -> {
            TreeNode<String, Record> node = new DefaultTreeNode(treeConfig, r);
            nodes.add(node);
        });
        TreeBuilder treeBuilder = new TreeBuilder<TreeNode<String, Record>>();
        List<TreeNode<String, Record>> treeNodes = treeBuilder.getChildTreeObjects(nodes, treeConfig.getRootIdentify());
        if (!treeConfig.isKeepRoot()) {
            return treeNodes;
        }
        TreeNode root = treeBuilder.getRootObject(nodes, treeConfig.getRootIdentify());
        return root == null ? treeNodes : Lists.newArrayList(treeBuilder.level1Tree(root, treeNodes.toArray(new DefaultTreeNode[treeNodes.size()])));
    }

    /**
     * 根据关键字构建树群，先筛选记录(根据所有字段值做模糊匹配keywords)，再构建树
     *
     * @param metaObject
     * @param treeConfig
     * @param keywords
     *
     * @return
     */
    public List<TreeNode<String, Record>> treeByKeywords(IMetaObject metaObject, TreeConfig treeConfig, String... keywords) {
        List<Record> allRecords = Db.use(metaObject.schemaName()).findAll(metaObject.tableName());
        List<Record> hitRecords = findHitRecordByKeyWords(allRecords, keywords);
        return buildTreeRelyOnHitRecords(allRecords, hitRecords, treeConfig).getTreeList();
    }

    /**
     * 根据确定的records构建树。hitRecords作为节点，它们所在的树都会被构建出来。
     * <p>
     * 只保留树干
     *
     * @param metaObject
     * @param hitRecords
     * @param treeConfig
     *
     * @return
     */
    public List<TreeNode<String, Record>> treeByHitRecords(IMetaObject metaObject, List<Record> hitRecords, TreeConfig treeConfig) {
        List<Record> allRecords = Db.use(metaObject.schemaName()).findAll(metaObject.tableName());
        return buildTreeRelyOnHitRecords(allRecords, hitRecords, treeConfig).getTreeList();
    }

    /**
     * 根据确定的records构建树。hitRecords作为节点，它们所在的树都会被构建出来。
     * <p>
     * 只保留树杈。这意味着，可能存在多个彼此不连接的树杈。
     *
     * @param metaObject
     * @param hitRecords
     * @param treeConfig
     */
    public List<TreeNode<String, Record>> treeByHitRecordKeepBranch(IMetaObject metaObject, List<Record> hitRecords, TreeConfig treeConfig) {
        List<Record> allRecords = Db.use(metaObject.schemaName()).findAll(metaObject.tableName());
        List<TreeNode<String, Record>> nodeList = allRecords.stream().map(r -> new DefaultTreeNode(treeConfig, r)).collect(Collectors.toList());

        List<TreeNode<String, Record>> result = Lists.newArrayList();

        for (Record hitRecord : hitRecords) {
            List<TreeNode<String, Record>> treeNodes = new TreeBuilder<TreeNode<String, Record>>().getChildTreeObjects(nodeList, hitRecord.get(treeConfig.getIdKey()));
            DefaultTreeNode branch = new DefaultTreeNode(treeConfig, hitRecord);
            branch.setChildren(treeNodes);
            result.add(branch);
        }

        return result;
    }

    /**
     * 返回构建树阶段数据
     *
     * @param metaObject
     * @param hitRecords
     * @param treeConfig
     *
     * @return
     */
    public TreePhases treePhasesByHitRecords(IMetaObject metaObject, List<Record> hitRecords, TreeConfig treeConfig) {
        List<Record> allRecords = Db.use(metaObject.schemaName()).findAll(metaObject.tableName());
        return buildTreeRelyOnHitRecords(allRecords, hitRecords, treeConfig);
    }

    private TreePhases buildTreeRelyOnHitRecords(List<Record> allRecords, List<Record> hitRecords, TreeConfig treeConfig) {
        Map<String, Record> allRecordsMap = recordToMap(allRecords, treeConfig);
        Set<UniqueRecord> resultRecords = Sets.newHashSet();
        /** 返回阶段数据 */
        TreePhases treePhases = new TreePhases();

        recursiveParent(hitRecords, allRecordsMap, resultRecords, treeConfig);

        treePhases.setResultRecords(resultRecords);
        List<TreeNode<String, Record>> nodes = Lists.newArrayList();
        resultRecords.forEach(record -> {
            TreeNode<String, Record> node = new DefaultTreeNode(treeConfig, record);
            nodes.add(node);
        });
        treePhases.setTreeList(new TreeBuilder<TreeNode<String, Record>>().getChildTreeObjects(nodes, treeConfig.getRootIdentify()));
        return treePhases;
    }

    private Map<String, Record> recordToMap(List<Record> records, TreeConfig treeConfig) {
        Map<String, Record> map = Maps.newHashMap();
        records.forEach(record -> {
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
            for (Map.Entry<String, Object> recordEntry : record.getColumns().entrySet()) {
                if (Arrays.stream(keywords).anyMatch(k -> String.valueOf(recordEntry.getValue()).contains(k))) {
                    hitRecords.add(record);
                    break;
                }
            }
        }
        return hitRecords;
    }
}
