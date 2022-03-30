package com.github.md.web.aop;

import com.github.md.analysis.meta.IMetaField;
import com.github.md.analysis.meta.MetaData;
import com.github.md.analysis.meta.aop.AddPointCut;
import com.github.md.analysis.meta.aop.AopInvocation;
import com.github.md.analysis.meta.aop.UpdatePointCut;
import com.github.md.web.user.User;
import com.github.md.web.user.UserThreadLocal;

import java.util.Date;
import java.util.Optional;

/**
 * 审计AOP， 对以下四个字段进行填充:
 * <pre>
 *     created_by(varchar)、created_time(timestamp)、updated_by(varchar)、updated_time(timestamp)
 * </pre>
 * <p>
 * 若没有这个四个字段，或字段类型不匹配则忽视。
 *
 * @author pengxg
 * @date 2022/3/30 11:32 上午
 */
public class AuditAop implements AddPointCut, UpdatePointCut {

    @Override
    public boolean addBefore(AopInvocation invocation) {
        MetaData formData = invocation.getFormData();
        IMetaField createdTimeMetaField = invocation.getMetaObject().getField("created_time");
        if (createdTimeMetaField != null && createdTimeMetaField.dbType().isDate()) {
            formData.set("created_time", new Date());
        }

        IMetaField createdByMetaField = invocation.getMetaObject().getField("created_by");
        if (createdByMetaField != null && createdByMetaField.dbType().isText()) {
            User user = UserThreadLocal.getUser();
            formData.set("created_by", Optional.ofNullable(user).map(User::userId).orElse("UnKnow"));
        }

        return true;
    }

    @Override
    public boolean updateBefore(AopInvocation invocation) {
        MetaData formData = invocation.getFormData();
        IMetaField updatedTimeMetaField = invocation.getMetaObject().getField("updated_time");
        if (updatedTimeMetaField != null && updatedTimeMetaField.dbType().isDate()) {
            formData.set("updated_time", new Date());
        }

        IMetaField updatedByMetaField = invocation.getMetaObject().getField("updated_by");
        if (updatedByMetaField != null && updatedByMetaField.dbType().isText()) {
            User user = UserThreadLocal.getUser();
            formData.set("updated_by", Optional.ofNullable(user).map(User::userId).orElse("UnKnow"));
        }

        return true;
    }
}
