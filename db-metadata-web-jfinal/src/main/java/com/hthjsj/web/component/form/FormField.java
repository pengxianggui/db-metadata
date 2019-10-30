package com.hthjsj.web.component.form;

import com.hthjsj.web.component.ViewComponent;
import com.hthjsj.web.ui.AccessBehavior;
import lombok.Getter;
import lombok.Setter;

/**
 * <p> Class title: </p>
 * <p> @Describe: </p>
 * <p> @Date : 2019/10/16 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public abstract class FormField extends ViewComponent {

    @Getter
    @Setter
    AccessBehavior accessBehavior;

    @Override
    public String config() {
        return null;
    }
}
