package com.github.md.web.feature.ms;

import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.meta.IMetaObject;
import com.github.md.web.ServiceManager;
import com.github.md.web.component.ViewFactory;
import com.github.md.web.component.form.FormView;
import com.github.md.web.controller.ControllerAdapter;
import com.github.md.web.controller.ParameterHelper;
import com.github.md.web.ex.WebException;
import com.github.md.web.query.QueryHelper;
import com.github.md.web.res.Res;
import com.github.md.web.ui.ComponentInstanceConfig;
import com.github.md.web.user.auth.annotations.ApiType;
import com.github.md.web.user.auth.annotations.Type;
import com.google.common.base.Preconditions;
import com.jfinal.kit.StrKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
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
 * 功能config数据结构， 参考 {@link MasterSlaveConfig}
 *
 * </pre>
 * <p> @Date : 2019/12/9 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
@RestController
@RequestMapping(value = "feature/masterSlave")
public class MasterSlaveController extends ControllerAdapter {

    /**
     * ?featureCode=feature1&instanceCode=meta_field.FormView&object_code=meta_object
     */
    @ApiType(value = Type.API_WITH_META_OBJECT)
    @GetMapping("toAddS")
    public Res toAddS() {
        QueryHelper queryHelper = queryHelper();
        ParameterHelper parameterHelper = parameterHelper();

        // 加载功能配置
        String featureCode = queryHelper.getFeatureCode();
        Assert.isTrue(StrKit.notBlank(featureCode), "功能编码不能为空");
        MasterSlaveConfig config = ServiceManager.featureService().loadFeatureConfig(featureCode);
        Preconditions.checkNotNull(config, "功能配置加载失败");

        // 加载实例配置
        String objectCode = queryHelper.getObjectCode(); // 指定子表的元对象
        Assert.isTrue(StrKit.notBlank(objectCode), "元对象编码不能为空"); // 否则无法确定是哪个子表(主子表支持1对多)
        String instanceCode = config.get(objectCode).getInstanceCode(ComponentType.FORMVIEW);
        Assert.isTrue(StrKit.notBlank(instanceCode), "实例编码不能为空");
        ComponentInstanceConfig componentInstanceConfig = ServiceManager.componentService().loadObjectConfig(instanceCode);
        Assert.isTrue(componentInstanceConfig != null, String.format("实例配置(%s)不存在", instanceCode));

        IMetaObject metaObject = metaService().findByCode(objectCode);

        FormView formView = ViewFactory.formView(metaObject, componentInstanceConfig)
                .action("/form/doAdd?objectCode=" + objectCode)
                .addForm();

        // 关联域
        String foreignPrimaryKey = config.get(objectCode).getConfig().getForeignPrimaryKey();
        String foreignPrimaryValue = parameterHelper.getPara(foreignPrimaryKey);

        //手工build,方便后面编程式操作表单内元子控件
        formView.buildChildren();

        if (!formView.containField(foreignPrimaryKey)) { // 关联字段可能被设置为禁用，此时formView里不包含关联字段
            throw new WebException("表单中的%s域被禁用, 此元字段为主子表关联键, 请设置为只读或隐藏", foreignPrimaryKey);
        }

        formView.getField(foreignPrimaryKey).disabled(true).defaultVal(foreignPrimaryValue);
        return Res.ok(formView.toKv());
    }

}
