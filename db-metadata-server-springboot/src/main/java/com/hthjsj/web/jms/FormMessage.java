package com.hthjsj.web.jms;

import com.hthjsj.analysis.meta.aop.AopInvocation;
import lombok.Getter;

/**
 * <p> @Date : 2020/1/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Getter
public class FormMessage implements EventMessage {

    private AopInvocation invocation;

    private String type;

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
