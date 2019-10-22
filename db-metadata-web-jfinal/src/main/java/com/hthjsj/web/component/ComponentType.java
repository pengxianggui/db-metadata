package com.hthjsj.web.component;

import lombok.Getter;

/**
 * <p> @Date : 2019/10/21 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public enum ComponentType {
    TABLEVIEW("tableview", "TableList"),
    FORMVIEW("formview", "FormTmpl"),
    BUTTON("button", "Button"),
    DROPDOWN("dropdown", "DropDownBox"),
    INPUTFIELD("inputfield", "TextBox");

    @Getter
    String name;

    @Getter
    String code;

    ComponentType(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
