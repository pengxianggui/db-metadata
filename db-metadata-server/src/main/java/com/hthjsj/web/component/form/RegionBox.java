package com.hthjsj.web.component.form;

import com.hthjsj.analysis.component.ComponentType;
import com.jfinal.kit.Kv;

/**
 * @author pengxg
 * @date 2021/2/2 10:47 上午
 */
public class RegionBox extends FormField {

    public RegionBox(String name, String label) {
        super(name, label);
    }

    @Override
    public ComponentType componentType() {
        return ComponentType.REGIONBOX;
    }


    @Override
    public Kv toKv() {
        return render.render();
    }
}