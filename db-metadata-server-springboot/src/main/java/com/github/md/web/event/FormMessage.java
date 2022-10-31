package com.github.md.web.event;

import com.github.md.analysis.meta.aop.AopInvocation;
import com.github.md.web.component.form.FormView;
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

    private final FormView.FormType type;

    private FormMessage(AopInvocation invocation, FormView.FormType formType) {
        this.invocation = invocation;
        this.type = formType;
    }

    public static FormMessage AddMessage(AopInvocation invocation) {
        return new FormMessage(invocation, FormView.FormType.ADD);
    }

    public static FormMessage UpdateMessage(AopInvocation invocation) {
        return new FormMessage(invocation, FormView.FormType.UPDATE);
    }

}
