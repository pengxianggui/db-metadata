package com.github.md.analysis.meta.aop;

import com.github.md.analysis.meta.IMetaObject;
import com.jfinal.plugin.activerecord.Record;
import lombok.Getter;
import lombok.Setter;

/**
 * @author pengxg
 * @date 2021/4/9 10:56 上午
 */
public class DetailQueryInvocation extends QueryInvocation {
    @Setter
    @Getter
    private Record data;

    public DetailQueryInvocation(IMetaObject metaObject) {
        super(metaObject);
    }
}
