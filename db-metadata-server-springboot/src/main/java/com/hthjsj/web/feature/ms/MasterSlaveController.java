package com.hthjsj.web.feature.ms;

import com.google.common.base.Preconditions;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.web.ServiceManager;
import com.hthjsj.web.component.ViewFactory;
import com.hthjsj.web.component.form.FormView;
import com.hthjsj.web.query.QueryHelper;
import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * 主子表作为功能存在
 *
 *  1. 主对象
 *      1.1 增加 (无特殊,可用Form)
 *      1.2 修改 (无特殊,可用Form)
 *      1.3 删除 (!!级联,删除子对象)
 *  2. 子对象
 *      > 1 主 -> N 子对象
 *      2.1 增加子对象时 关联主对象
 *
 * 需要[功能]的实现,基于"Feature"的设计
 *
 * 功能config:
 * {
 *     featureCode:"",
 *     master:{
 *      objectCode:
 *      key:
 *      },
 *     slaves:[{
 *         objectCode:
 *         key:
 *         order:
 *     },{元对象2}],
 * }
 *
 * </pre>
 * <p> @Date : 2019/12/9 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class MasterSlaveController extends Controller {

    /**
     * ?featureCode=feature1&
     * objectCode=meta_field&
     * object_code=aasdf
     */
    public void toAddS() {
        /**
         * 1. 获取功能配置
         * 2. 获取子元对象
         * 3. 修改子元对象对应操作(ADD,Update)的formView
         * 4. render
         */

        QueryHelper queryHelper = new QueryHelper(this);
        String objectCode = queryHelper.getObjectCode();
        MasterSlaveConfig config = getConfig();
        Preconditions.checkNotNull(config, "功能配置加载失败");
        String slaveFieldCode = config.get(objectCode).getForeignFieldCode();
        String foreignValue = getPara(slaveFieldCode);

        IMetaObject metaObject = ServiceManager.metaService().findByCode(objectCode);
        FormView formView = ViewFactory.formView(metaObject).action("/form/doAdd").addForm();

        //手工build,方便后面编程式操作表单内元子控件
        formView.buildChildren();
        formView.getField(slaveFieldCode).disabled(true).defaultVal(foreignValue);

        renderJson(Ret.ok("data", formView.toKv()));
    }

    /**
     * 从request中拆解出- 功能Code 来 获取配置
     *
     * @return
     */
    private MasterSlaveConfig getConfig() {
        QueryHelper queryHelper = new QueryHelper(this);
        String featureCode = queryHelper.getFeatureCode();
        MasterSlaveConfig masterSlaveConfig = ServiceManager.featureService().loadFeatureConfig(featureCode);
        return masterSlaveConfig;
    }
}
