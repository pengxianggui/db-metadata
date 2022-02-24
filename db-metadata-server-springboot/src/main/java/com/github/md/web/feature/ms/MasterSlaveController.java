package com.github.md.web.feature.ms;

import com.github.md.web.user.auth.annotations.Type;
import com.github.md.web.user.auth.annotations.MetaAccess;
import com.github.md.web.ServiceManager;
import com.github.md.web.component.ViewFactory;
import com.google.common.base.Preconditions;
import com.github.md.analysis.meta.IMetaObject;
import com.github.md.web.component.form.FormView;
import com.github.md.web.controller.ControllerAdapter;
import com.github.md.web.controller.ParameterHelper;
import com.github.md.web.query.QueryHelper;
import com.github.md.analysis.kit.Ret;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
@RequestMapping(value = { "f/ms", "feature/masterSlave" })
public class MasterSlaveController extends ControllerAdapter {

    /**
     * ?featureCode=feature1&
     * objectCode=meta_field&
     * object_code=aasdf
     */
    @MetaAccess(value = Type.API_WITH_META_OBJECT)
    @GetMapping("toAddS")
    public Ret toAddS() {
        /**
         * 1. 获取功能配置
         * 2. 获取子元对象
         * 3. 修改子元对象对应操作(ADD,Update)的formView
         * 4. render
         */

        QueryHelper queryHelper = queryHelper();
        ParameterHelper parameterHelper = parameterHelper();
        String objectCode = queryHelper.getObjectCode();
        MasterSlaveConfig config = getConfig();
        Preconditions.checkNotNull(config, "功能配置加载失败");
        String slaveFieldCode = config.get(objectCode).getForeignFieldCode();
        String foreignValue = parameterHelper.getPara(slaveFieldCode);

        IMetaObject metaObject = ServiceManager.metaService().findByCode(objectCode);
        FormView formView = ViewFactory.formView(metaObject).action("/form/doAdd").addForm();

        //手工build,方便后面编程式操作表单内元子控件
        formView.buildChildren();
        formView.getField(slaveFieldCode).disabled(true).defaultVal(foreignValue);

        return Ret.ok("data", formView.toKv());
    }

    /**
     * 从request中拆解出- 功能Code 来 获取配置
     *
     * @return
     */
    private MasterSlaveConfig getConfig() {
        QueryHelper queryHelper = queryHelper();
        String featureCode = queryHelper.getFeatureCode();
        MasterSlaveConfig masterSlaveConfig = ServiceManager.featureService().loadFeatureConfig(featureCode);
        return masterSlaveConfig;
    }
}
