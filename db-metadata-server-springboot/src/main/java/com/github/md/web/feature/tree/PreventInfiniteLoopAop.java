package com.github.md.web.feature.tree;

import com.alibaba.fastjson.JSON;
import com.github.md.web.ServiceManager;
import com.github.md.web.WebException;
import com.google.common.collect.Lists;
import com.github.md.analysis.meta.IMetaField;
import com.github.md.analysis.meta.IMetaObject;
import com.github.md.analysis.meta.MetaData;
import com.github.md.analysis.meta.aop.AopInvocation;
import com.github.md.analysis.meta.aop.UpdatePointCut;
import com.github.md.web.kit.tree.TreeConfig;
import com.github.md.web.kit.tree.TreeNode;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

/**
 * 树结构在编辑时可能引起死循环。此AOP默认注册到全局，防止此问题
 *
 * @author pengxg
 * @date 2021/4/21 8:58 上午
 */
public class PreventInfiniteLoopAop implements UpdatePointCut {

    @Override
    public boolean updateBefore(AopInvocation invocation) {
        IMetaObject metaObject = invocation.getMetaObject();

        if (metaObject.configParser().isTreeStructure()) {
            TreeConfig treeConfig = JSON.parseObject(metaObject.configParser().treeConfig(), TreeConfig.class);

            MetaData data = invocation.getFormData();
            Object idVal = data.get(treeConfig.getIdKey());
            Object pidVal = data.get(treeConfig.getPidKey());

            Object[][] ids = { { idVal } };
            Record record = ServiceManager.businessService().findDataByIds(metaObject, ids);

            List<TreeNode<String, Record>> treeNodeList = ServiceManager.treeService().treeByHitRecordKeepBranch(metaObject, Lists.newArrayList(record), treeConfig);
            if (treeNodeList != null && !treeNodeList.isEmpty()) {
                final boolean infiniteLoop = treeNodeList.get(0).all().stream().anyMatch(node -> node.getId().equals(pidVal));
                if (infiniteLoop) {
                    final String cn = getFieldCn(metaObject, treeConfig);
                    final String msg = String.format("[%s]不允许设置为自身或子节点", cn);
                    invocation.getRet().set("msg", msg);
                    throw new WebException(msg);
                }
            }
        }

        return true;
    }

    private String getFieldCn(IMetaObject metaObject, TreeConfig treeConfig) {
        for (IMetaField field : metaObject.fields()) {
            if (field.fieldCode().equals(treeConfig.getPidKey())) {
                return field.cn();
            }
        }
        return treeConfig.getPidKey();
    }
}
