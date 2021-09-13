package com.github.md.web.jms;

import com.github.md.analysis.meta.aop.AopInvocation;
import lombok.Getter;

/**
 * <p> @Date : 2020/1/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Getter
public class FormMessage implements EventMessage {

    private final AopInvocation invocation;

    private final String type;

    private FormMessage(AopInvocation invocation, String formType) {
        this.invocation = invocation;
        this.type = formType;
    }

    public static FormMessage AddMessage(AopInvocation invocation) {
        return new FormMessage(invocation, "add");
    }

    public static FormMessage UpdateMessage(AopInvocation invocation) {
        return new FormMessage(invocation, "update");
    }
}
