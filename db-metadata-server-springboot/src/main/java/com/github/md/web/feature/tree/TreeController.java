package com.github.md.web.feature.tree;

import com.alibaba.fastjson.JSON;
import com.github.md.web.kit.tree.TreeKit;
import com.github.md.web.user.auth.annotations.Type;
import com.github.md.web.user.auth.annotations.MetaAccess;
import com.github.md.analysis.meta.IMetaObject;
import com.github.md.web.controller.ControllerAdapter;
import com.github.md.web.kit.tree.TreeNode;
import com.github.md.web.query.QueryHelper;
import com.github.md.analysis.kit.Ret;
import com.jfinal.plugin.activerecord.Record;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 处理树型结构请求
 * <p>
 * 1. 如果元对象需要树型展示
 *
 * @see <a href="http://mikehillyer.com/articles/managing-hierarchical-data-in-mysql/">managing-hierarchical-data-in-mysql</a>
 * @see <a href="https://www.cnblogs.com/youxin/p/3614726.html?utm_source=tuicool&utm_medium=referral/">译文:managing-hierarchical-data-in-mysql</a>
 * @see <a href="https://blog.csdn.net/qq_31482599/article/details/81947445">Mysql 采用树前序算法优化无限层级结构</a>
 * <p> @Date : 2019/12/18 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
@RestController
@RequestMapping(value = {"f/t", "feature/tree"})
public class TreeController extends ControllerAdapter {

    /**
     * 获取树机构数据。依据功能编码&元对象编码
     * <p>
     * test:
     * objectCode=js_sys_area
     * config -> getConfig()
     */
    @MetaAccess(value = Type.API_WITH_META_FEATURE)
    @GetMapping()
    public Ret index() {
        QueryHelper queryHelper = queryHelper();
        String featureCode = queryHelper.getFeatureCode();
        String objectCode = queryHelper.getObjectCode();

        IMetaObject metaObject = metaService().findByCode(objectCode);
        TreeConfigGetter treeConfigGetter = featureService().loadFeatureConfig(featureCode);

        List<TreeNode<String, Record>> tree = treeService().tree(metaObject, treeConfigGetter.getTreeConfig());
        return Ret.ok("data", JSON.parseArray(JSON.toJSONString(tree, TreeKit.afterFilter)));
    }
}
