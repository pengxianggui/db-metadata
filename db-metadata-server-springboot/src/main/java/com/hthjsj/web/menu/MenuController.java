package com.hthjsj.web.menu;

import com.alibaba.fastjson.JSON;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.web.controller.ControllerAdapter;
import com.hthjsj.web.controller.ParameterHelper;
import com.hthjsj.web.kit.tree.TreeConfig;
import com.hthjsj.web.kit.tree.TreeKit;
import com.hthjsj.web.kit.tree.TreeNode;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p> @Date : 2020/8/4 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@RestController
@RequestMapping("menu")
public class MenuController extends ControllerAdapter {

    private String objectCode() {
        return "meta_menu";
    }

    @GetMapping
    public Ret index() {
        IMetaObject metaObject = metaService().findByCode(objectCode());
        ParameterHelper parameterHelper = parameterHelper();
        TreeConfig treeConfig = treeConfig();
        String pid = parameterHelper.getPara(treeConfig.getPidKey(), "").trim();

        treeConfig.setKeepRoot(parameterHelper.getParaToBoolean("keep", false));
        if (StrKit.notBlank(pid)) {
            treeConfig.setRootIdentify(pid);
        }

        List<TreeNode<String, Record>> tree = treeService().tree(metaObject, treeConfig);
        return Ret.ok("data", JSON.parseArray(JSON.toJSONString(tree, TreeKit.afterFilter)));
    }

    private TreeConfig treeConfig() {
        TreeConfig treeConfig = new TreeConfig();
        treeConfig.setIdKey("id");
        treeConfig.setPidKey("pid");
        treeConfig.setLabel("title");
        treeConfig.setOrderBy("order_num");
        return treeConfig;
    }
}
