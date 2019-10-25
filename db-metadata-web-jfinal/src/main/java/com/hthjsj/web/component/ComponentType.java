package com.hthjsj.web.component;

import lombok.Getter;

/**
 * <p> @Date : 2019/10/21 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public enum ComponentType {
    TABLEVIEW("tableview", "表格组件", "TableList"),
    FORMVIEW("formview", "表单组件", "FormTmpl"),
    BUTTON("button", "按钮组件", "Button"),
    DROPDOWN("dropdown", "下拉框组件", "DropDownBox"),
    INPUTFIELD("inputfield", "输入框组件", "TextBox");

    @Getter
    String name;

    @Getter
    String code;

    @Getter
    String cn;

    ComponentType(String name, String cn, String code) {
        this.name = name;
        this.cn = cn;
        this.code = code;
    }
}
