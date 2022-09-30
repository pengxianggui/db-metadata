package com.github.md.web.aop;

import com.github.md.analysis.meta.IMetaField;
import com.github.md.analysis.meta.IMetaObject;
import com.github.md.analysis.meta.MetaData;
import com.github.md.analysis.meta.aop.AddPointCut;
import com.github.md.analysis.meta.aop.FormInvocation;
import com.github.md.analysis.meta.aop.UpdatePointCut;
import com.github.md.web.ServiceManager;
import com.github.md.web.WebException;
import com.jfinal.plugin.activerecord.Record;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 针对元字段是否唯一的约束性校验
 *
 * @author pengxg
 * @date 2022/9/30 2:24 下午
 */
@Slf4j
public class UniqueConstraintAop implements AddPointCut, UpdatePointCut {
    @Override
    public boolean addBefore(FormInvocation invocation) {
        return notViolationUniqueConstraint(invocation.getMetaObject(), invocation.getFormData());
    }

    @Override
    public boolean updateBefore(FormInvocation invocation) {
        return notViolationUniqueConstraint(invocation.getMetaObject(), invocation.getFormData());
    }

    /**
     * 是否遵循唯一约束。若遵循，则返回true; 否则，抛出异常 {@link WebException}。
     * 校验依据是元字段中声明为unique为true的字段，查看数据库中是否已经存在。TODO 联合unique怎么办？
     *
     * @param metaObject 元对象
     * @param formData   表单数据
     * @return
     */
    private boolean notViolationUniqueConstraint(IMetaObject metaObject, MetaData formData) {
        try {
            Set<IMetaField> fieldSet = metaObject.fields().stream().filter(f -> f.configParser().isUnique()).collect(Collectors.toSet());
            if (CollectionUtils.isEmpty(fieldSet)) {
                return true;
            }

            for (IMetaField metaField : fieldSet) {
                if (notViolationUniqueConstraint(metaField, formData)) {
                    continue;
                }
                throw new WebException(String.format("%s:%s已存在", metaField.cn(), formData.get(metaField.fieldCode())));
            }

            return true;
        } catch (WebException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            return true; // 抛出异常也返回true, 降级让数据库碰撞
        }
    }

    /**
     * 是否遵循唯一约束。若遵循，则返回true, 否则返回false
     *
     * @param metaField 元字段
     * @param formData  表单数据
     * @return
     */
    private boolean notViolationUniqueConstraint(IMetaField metaField, MetaData formData) {
        final String fieldName = metaField.fieldCode();
        List<Record> recordList = ServiceManager.businessService().findData(metaField.getParent(), new String[]{fieldName}, formData.get(fieldName));
        return CollectionUtils.isEmpty(recordList);
    }

}
