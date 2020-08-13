package com.hthjsj.web.menu;

import com.alibaba.fastjson.JSON;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.web.controller.FrontRestController;
import com.hthjsj.web.kit.tree.TreeConfig;
import com.hthjsj.web.kit.tree.TreeKit;
import com.hthjsj.web.kit.tree.TreeNode;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

/**
 * <p> @Date : 2020/8/4 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class RouterController extends FrontRestController {

    public String objectCode() {
        return "meta_router";
    }

    @Override
    public void index() {
        IMetaObject metaObject = metaService().findByCode(objectCode());
        TreeConfig treeConfig = treeConfig();
        String pid = getPara(treeConfig.getPidKey(), "").trim();
        if (StrKit.notBlank(pid)) {
            treeConfig.setRootIdentify(pid);
        }
        List<TreeNode<String, Record>> tree = treeService().tree(metaObject, treeConfig());
        renderJsonExcludes(Ret.ok("data", JSON.parseArray(JSON.toJSONString(tree, TreeKit.afterFilter))), "created_time", "updated_time");
    }


    private TreeConfig treeConfig() {
        TreeConfig treeConfig = new TreeConfig();
        treeConfig.setIdKey("id");
        treeConfig.setPidKey("pid");
        treeConfig.setLabel("title");
        return treeConfig;
    }
}
