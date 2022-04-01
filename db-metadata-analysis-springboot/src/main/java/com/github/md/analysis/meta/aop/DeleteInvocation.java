package com.github.md.analysis.meta.aop;

import com.github.md.analysis.kit.Kv;
import com.github.md.analysis.meta.IMetaObject;
import lombok.Getter;

import javax.servlet.http.HttpServletRequest;

/**
 * @author pengxg
 * @date 2022/4/1 2:58 下午
 */
public class DeleteInvocation extends AopInvocation {
    /**
     * 要删除记录的主键数组。由于兼容可能存在的联合数组，结构如下:
     * <p>
     * 复合主键: [[v1, v2], [v1, v2]]; 单主键: [[v1],[v2]]
     * 因此，此值始终是一个二维数组。
     */
    @Getter
    private Object[] ids;

    public DeleteInvocation(IMetaObject metaObject, Kv httpParams, HttpServletRequest request, Object[] ids) {
        super(metaObject, httpParams, request);
        this.ids = ids;
    }
}
