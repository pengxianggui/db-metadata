package com.hthjsj.analysis.meta;

import com.alibaba.fastjson.JSON;
import com.hthjsj.analysis.meta.aop.*;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import lombok.extern.slf4j.Slf4j;

/**
 * 对象配置信息与MiniFormObject.vue 文件一一对应
 * <p>
 * 元对象配置解析器
 * <p> @Date : 2019/11/14 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class MetaObjectConfigParse extends MetaData {

    private final PointCutFactory pointCutFactory = new PointCutFactory(this);

    MetaObjectConfigParse(Kv config) {
        set(config);
    }

    MetaObjectConfigParse(String config) {
        set(JSON.parseObject(config));
    }

    public boolean isUUIDPrimary() {
        return Boolean.parseBoolean(getStr("isUUIDPrimary"));
    }

    public boolean isNumberSequence() {
        return Boolean.parseBoolean(getStr("isNumberSequence"));
    }

    public boolean isAutoIncrement() {
        return Boolean.parseBoolean(getStr("isAutoIncrement"));
    }

    public void isNumberSequence(boolean v) {
        set("isNumberSequence", v);
    }

    public void isUUIDPrimary(boolean v) {
        set("isUUIDPrimary", v);
    }

    public void isAutoIncrement(boolean v) {
        set("isAutoIncrement", v);
    }

    /**
     * 排序
     */
    public String orderBy() {
        if (StrKit.notBlank(getStr("orderBy"))) {
            return getStr("orderBy").trim().replaceFirst("order by", "");
        }
        return "";
    }

    /**
     * where 条件
     */
    public String where() {
        if (StrKit.notBlank(getStr("where"))) {
            return getStr("where").replaceFirst("where", "");
        }
        return "";
    }

    /**
     * 客户端可以按需选择实现add,update,delete,view切入点
     */
    public DeletePointCut[] deletePointCut() {
        return pointCutFactory.deletePointCut();
    }

    public AddPointCut[] addPointCut() {
        return pointCutFactory.addPointCut();
    }

    public UpdatePointCut[] updatePointCut() {
        return pointCutFactory.updatePointCut();
    }

    public ViewPointCut[] viewPointCut() {
        return pointCutFactory.viewPointCut();
    }

    /**
     * 考虑到干预查询动作,是建立在前端动态条件的基础上来做的,只支持单个PointCut
     *
     * @return
     */
    public QueryPointCut queryPointCut() {
        return pointCutFactory.queryPointCut();
    }

    public boolean isTreeStructure() {
        if (StrKit.notBlank(getStr("structure"))) {
            return getStr("structure").equalsIgnoreCase("tree");
        }
        return false;
    }

    /**
     * TODO 前端直传,但是使用com.hthjsj.web.kit.tree.TreeConfig数据结构
     *
     * @return
     *
     * @see com.hthjsj.web.kit.tree.TreeConfig
     */
    public String treeConfig() {
        if (StrKit.notBlank(getStr("structureConfig"))) {
            return getStr("structureConfig");
        }
        return "";
    }
}
