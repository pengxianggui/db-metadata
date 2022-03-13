package com.github.md.analysis.component;

import com.jfinal.kit.StrKit;
import lombok.AccessLevel;
import lombok.Getter;

/**
 * 组件枚举,显示注册,新增组件时需要在此处增加
 * name: 类名
 * code: 前端组件名
 * <p> @Date : 2019/10/21 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
public enum ComponentType {
    BOOLBOX("boolbox", "布尔框", "BoolBox"),
    BUTTON("button", "按钮组件", "Button"),
    CHECKBOX("checkbox", "复选框", "CheckBox"),
    DATEBOX("datebox", "日期框", "DateBox"),
    DATETIMEBOX("datetimebox", "日期时间框", "DateTimeBox"),
    DROPDOWN("dropdown", "下拉框组件", "DropDownBox"),
    FORMVIEW("formview", "表单组件", "FormView", true),
    NUMBERBOX("numberbox", "数值框", "NumBox"),
    RADIOBOX("radiobox", "单选框", "RadioBox"),
    TABLEVIEW("tableview", "表格组件", "TableView", true),
    TEXTAREABOX("textareabox", "文本域", "TextAreaBox"),
    RICHTEXTBOX("richtextbox", "富文本", "RichTextBox"),
    TEXTBOX("textbox", "输入框组件", "TextBox"),
    PASSBOX("passbox", "密码框组件", "PassBox"),
    TIMEBOX("timebox", "时间框", "TimeBox"),
    REGIONBOX("regionbox", "省市框", "RegionBox"),
    JSONBOX("jsonbox", "Json框", "JsonBox"),
    MINIFORMBOX("miniformbox", "迷你表单框", "MiniFormBox"),
    FINDBOX("findbox", "查找框", "FindBox"),
    SEARCHVIEW("searchview", "搜索面板", "SearchView", true),
    TABLETREEVIEW("tabletreeview", "树形表格组件", "TableTreeView", true),
    FILEBOX("filebox", "上传控件", "FileBox"),
    IMAGEBOX("imagebox", "图片控件", "ImgBox"),
    UNKNOWN("unknow", "未知控件", "unknow");

    @Getter(AccessLevel.PUBLIC)
    String name;

    @Getter(AccessLevel.PUBLIC)
    String code;

    @Getter(AccessLevel.PUBLIC)
    String cn;

    boolean isView;

    ComponentType(String name, String cn, String code) {
        this(name, cn, code, false);
    }

    ComponentType(String name, String cn, String code, boolean isView) {
        this.name = name;
        this.cn = cn;
        this.code = code;
        this.isView = isView;
    }

    public static ComponentType V(String s) {
        if (StrKit.isBlank(s))
            return UNKNOWN;
        for (ComponentType t : ComponentType.values()) {
            if (s.equalsIgnoreCase(t.code.toLowerCase()) || s.equalsIgnoreCase(t.name.toLowerCase()) || s.equalsIgnoreCase(t.cn.toLowerCase())) {
                return t;
            }
        }
        return UNKNOWN;
    }
}
