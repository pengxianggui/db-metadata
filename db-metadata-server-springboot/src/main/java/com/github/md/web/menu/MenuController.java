package com.github.md.web.menu;

import com.alibaba.fastjson.JSON;
import com.github.md.web.user.auth.annotations.Type;
import com.github.md.web.user.auth.annotations.ApiType;
import com.github.md.web.controller.ControllerAdapter;
import com.github.md.web.controller.ParameterHelper;
import com.github.md.web.kit.tree.TreeConfig;
import com.github.md.web.kit.tree.TreeKit;
import com.github.md.web.kit.tree.TreeNode;
import com.github.md.analysis.meta.IMetaObject;
import com.github.md.analysis.kit.Ret;
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

    @ApiType(value = Type.API)
    @GetMapping
    public Ret index() {
        IMetaObject metaObject = metaService().findByCode("meta_menu");
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

    @ApiType(value = Type.API)
    @GetMapping("profile")
    public Ret profileMenu() {
        IMetaObject metaObject = metaService().findByCode("meta_profile_menu");
        List<Record> records = businessService().findData(metaObject);
        return Ret.ok("data", JSON.parseArray(JSON.toJSONString(records)));
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
