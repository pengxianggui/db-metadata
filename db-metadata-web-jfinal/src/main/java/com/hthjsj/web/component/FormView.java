package com.hthjsj.web.component;

import com.jfinal.kit.Kv;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/14 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public class FormView extends ViewComponent {

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

    @Override
    public String config() {
        return null;
    }

    @Override
    public void config(String config) {

    }

    public Kv renderMeta() {
        return null;
    }
}
