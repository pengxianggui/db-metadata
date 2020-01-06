package com.hthjsj.web.feature;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.hthjsj.web.controller.FrontRestController;
import com.hthjsj.web.kit.UtilKit;
import com.hthjsj.web.kit.tree.TreeBuilder;
import com.hthjsj.web.kit.tree.TreeNode;
import com.hthjsj.web.query.QueryHelper;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Record;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author konbluesky
 */
public class FeatureController extends FrontRestController {

    @Override
    public void list() {
        List<Kv> results = Lists.newArrayList();
        for (FeatureType type : FeatureType.values()) {
            if (type != FeatureType.UNKNOWN) {
                results.add(Kv.by("key", type.name).set("value", type.code));
            }
        }
        renderJson(Ret.ok("data", results));
    }

    @Override
    public void doAdd() {
        QueryHelper queryHelper = new QueryHelper(this);
        FeatureType type = FeatureType.V(queryHelper.getFeatureType());
        Preconditions.checkArgument(type != FeatureType.UNKNOWN, "未知的功能模板" + queryHelper.getFeatureType());
        String name = getPara("name");
        String code = getPara("code");
        String jsonStr = getPara("config");
        featureService().createFeature(type, name, code, JSON.parseObject(jsonStr, type.configEntity));
        renderJson(Ret.ok());
    }

    /**
     * 功能 = ((元对象(表),元对象(视图),元对象(非表)) + 控件) * n
     */
    public void load() {
        QueryHelper queryHelper = new QueryHelper(this);
        String featureCode = queryHelper.getFeatureCode();
        Feature feature = featureService().loadFeatureConfig(featureCode);
        renderJson(Ret.ok("data", feature.execute()));
    }

    /**
     * 获取树型的功能数据
     */
    public void menu() {
        //1. 拼接功能菜单树
        FeatureNode root = new FeatureNode("business", "", "业务模块", null);
        TreeBuilder<FeatureNode> treeBuilder = new TreeBuilder<>();
        Collection<FeatureNode> featureNodes = new ArrayList<>();
        featureService().findAll().forEach(record -> {
            FeatureType featureType = FeatureType.V(record.getStr("type"));
            featureNodes.add(new FeatureNode(url(featureType, record.getStr("code")), root.getId(), record.getStr("name"), record));
        });
        JSONArray jsonRoot = new JSONArray();
        treeBuilder.level1Tree(root, featureNodes.toArray(new FeatureNode[0]));
        jsonRoot.set(0, root);
        jsonRoot.set(1, UtilKit.getKv(UtilKit.loadConfigByFile("sysMenu.json")));
        renderJson(Ret.ok("data", jsonRoot));
    }

    private String url(FeatureType featureType, String featureCode) {
        String prefix = "";
        switch (featureType) {
            case MasterSlaveGrid:
                prefix = "/ms-table";
                break;
            case SingleGrid:
                prefix = "/table";
                break;
        }
        return "/main" + prefix + "?featureCode=" + featureCode;
    }

    @Override
    public void delete() {
        String idss = getPara("ids");
        List<String> ids = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(idss);
        boolean status = featureService().deleteFeature(ids.toArray(new String[0]));
        renderJson(status ? Ret.ok() : Ret.fail());
    }

    class FeatureNode implements TreeNode<String, Record> {

        List<TreeNode<String, Record>> children;

        Record node;

        private String id;

        private String pid;

        private String name;

        public FeatureNode(String id, String pid, String name, Record node) {
            this.id = id;
            this.pid = pid;
            this.name = name;
            this.node = node;
        }

        @JSONField(name = "path")
        @Override
        public String getId() {
            return this.id;
        }

        @Override
        public void setId(String id) {
            this.id = id;
        }

        @JSONField(serialize = false)
        @Override
        public String getParentId() {
            return this.pid;
        }

        @Override
        public void setParentId(String parentId) {
            this.pid = parentId;
        }

        @JSONField(name = "label")
        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public void setName(String name) {
            this.name = name;
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

        @JSONField(name = "icon")
        public String getIcon() {
            String defaultIcon = "el-icon-menu";
            if (node != null) {
                Kv k = UtilKit.getKv(node.getStr("config"));
                return k.containsKey("icon") ? k.getStr("icon") : defaultIcon;
            }
            return defaultIcon;
        }
    }
}
