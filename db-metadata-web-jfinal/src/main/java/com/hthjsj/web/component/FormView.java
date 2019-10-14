package com.hthjsj.web.component;

import com.hthjsj.analysis.meta.Component;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/14 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class FormView implements Component {

    final String cn = "表单视图";

    final String en = "FormView";

    @Override
    public String name() {
        return cn;
    }

    @Override
    public String code() {
        return en;
    }

    @Override
    public String type() {
        return getClass().getSimpleName();
    }
}
