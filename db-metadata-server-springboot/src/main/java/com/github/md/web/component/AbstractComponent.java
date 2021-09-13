package com.github.md.web.component;

import com.github.md.analysis.component.Component;
import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.component.ViewContainer;

/**
 * @author pengxg
 * @date 2020/11/26 1:57 下午
 */
public class AbstractComponent extends Component {

    private final String code;

    public AbstractComponent(String name, String label, String code) {
        super(name, label);
        this.code = code;
    }

    public static Component newInstance(String compCode) {
        ComponentType type = ComponentType.V(compCode);
        return new AbstractComponent(type.getName(), type.getCn(), type.getCode());
    }

    @Override
    public ComponentType componentType() {
        return ComponentType.V(this.code);
    }

    @Override
    public ViewContainer getContainer() {
        return this.viewContainer;
    }
}
