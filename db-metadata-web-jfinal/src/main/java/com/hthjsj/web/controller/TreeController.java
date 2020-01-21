package com.hthjsj.web.controller;

import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.web.ServiceManager;
import com.hthjsj.web.kit.tree.TreeConfig;
import com.hthjsj.web.kit.tree.TreeNode;
import com.hthjsj.web.query.QueryHelper;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import lombok.extern.slf4j.Slf4j;

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
public class TreeController extends Controller {

    /**
     * test:
     * objectCode=js_sys_area
     * config -> getConfig()
     */
    public void index() {

        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();
        IMetaObject metaObject = ServiceManager.metaService().findByCode(objectCode);
        List<TreeNode<String, Record>> tree = ServiceManager.treeService().findAll(metaObject, getConfig());
        renderJson(tree);
    }

    /**
     * 组装树型数据逻辑
     * 1. 根据sql或条件取List<Record>,可能是缺陷树,
     * 2. 根据标志取根节点
     * 3. 构建树
     */
    public void child() {

    }

    public void parent() {

    }

    private TreeConfig getConfig() {
        TreeConfig treeConfig = new TreeConfig();
        treeConfig.setIdKey("area_code");
        treeConfig.setPidKey("parent_code");
        treeConfig.setRootIdentify("0");
        treeConfig.setSync(false);
        treeConfig.setLabel("area_name");
        return treeConfig;
    }
}
