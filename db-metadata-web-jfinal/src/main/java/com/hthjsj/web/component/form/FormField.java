package com.hthjsj.web.component.form;

import com.hthjsj.analysis.component.Component;
import com.hthjsj.analysis.component.ViewContainer;
import com.hthjsj.web.component.ManualRender;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public abstract class FormField extends Component {

    public FormField(String name, String label) {
        this.name = name;
        this.label = label;
        this.meta.set("component_name", type());
        this.meta.set("name", name);
        this.meta.set("label", label);
        setRender(new ManualRender<FormField>(this));
    }

    @Override
    public ViewContainer getContainer() {
        throw new UnsupportedOperationException("原子组件不支持此操作");
    }
}
