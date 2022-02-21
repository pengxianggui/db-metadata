package com.github.md.web.feature;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.annotation.JSONField;
import com.github.md.web.user.auth.meta.Type;
import com.github.md.web.user.auth.meta.MetaAccess;
import com.github.md.web.controller.ControllerAdapter;
import com.github.md.web.controller.ParameterHelper;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.github.md.web.kit.UtilKit;
import com.github.md.web.kit.tree.TreeBuilder;
import com.github.md.web.kit.tree.TreeNode;
import com.github.md.web.query.QueryHelper;
import com.github.md.analysis.kit.Kv;
import com.github.md.analysis.kit.Ret;
import com.jfinal.plugin.activerecord.Record;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author konbluesky
 */
@RestController
@RequestMapping(value = { "feature", "f" })
public class FeatureController extends ControllerAdapter {

    @MetaAccess(value = Type.API)
    @GetMapping("list")
    public Ret list() {
        List<Kv> results = Lists.newArrayList();
        for (FeatureType type : FeatureType.values()) {
            if (type != FeatureType.UNKNOWN) {
                results.add(Kv.by("key", type.name).set("value", type.code));
            }
        }
        return Ret.ok("data", results);
    }

    @MetaAccess(value = Type.API)
    @PostMapping("doAdd")
    public Ret doAdd() {
        QueryHelper queryHelper = queryHelper();
        ParameterHelper parameterHelper = parameterHelper();

        FeatureType type = FeatureType.V(queryHelper.getFeatureType());
        Preconditions.checkArgument(type != FeatureType.UNKNOWN, "未知的功能模板" + queryHelper.getFeatureType());
        String name = parameterHelper.getPara("name");
        String code = parameterHelper.getPara("code");
        String jsonStr = parameterHelper.getPara("config");
        featureService().createFeature(type, name, code, JSON.parseObject(jsonStr, type.configEntity));
        return Ret.ok();
    }

    /**
     * 功能 = ((元对象(表),元对象(视图),元对象(非表)) + 控件) * n
     */
    @GetMapping("load")
    public Ret load() {
        String featureCode = queryHelper().getFeatureCode();
        FeatureConfig feature = featureService().loadFeatureConfig(featureCode);
        return Ret.ok("data", feature);
    }

    /**
     * 获取树型的功能数据
     */
    @GetMapping("menu")
    public Ret menu() {
        //1. 拼接功能菜单树
        FeatureNode root = new FeatureNode("business", "", "业务模块", null);
        TreeBuilder<FeatureNode> treeBuilder = new TreeBuilder<>();
        Collection<FeatureNode> featureNodes = new ArrayList<>();
        featureService().findAll().forEach(record -> {
            FeatureType featureType = FeatureType.V(record.getStr("type"));
            String code = record.getStr("code");
            FeatureConfig featureConfig = featureService().loadFeatureConfig(code);
            featureNodes.add(new FeatureNode(url(featureType, code, featureConfig), root.getId(), record.getStr("name"), record));
        });
        JSONArray jsonRoot = new JSONArray();
        treeBuilder.level1Tree(root, featureNodes.toArray(new FeatureNode[0]));
        jsonRoot.set(0, root);
        return Ret.ok("data", jsonRoot);
    }

    private String url(FeatureType featureType, String featureCode, FeatureConfig config) {
        String prefix = "";
        if (config.hasRouter()) {
            return "/main/cover/" + config.componentName() + "?featureCode=" + featureCode;
        }
        switch (featureType) {
            case MasterSlaveGrid:
                prefix = "/ms-table";
                break;
            case SingleGrid:
                prefix = "/table";
                break;
            case TreeInTable:
                prefix = "/tree/in";
                break;
            case TreeAndTable:
                prefix = "/tree/and";
                break;
        }
        return "/main" + prefix + "?featureCode=" + featureCode;
    }

    @MetaAccess(value = Type.API)
    @GetMapping("delete")
    public Ret delete() {
        String idss = parameterHelper().getPara("ids");
        List<String> ids = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(idss);
        boolean status = featureService().deleteFeature(ids.toArray(new String[0]));
        return (status ? Ret.ok() : Ret.fail());
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
