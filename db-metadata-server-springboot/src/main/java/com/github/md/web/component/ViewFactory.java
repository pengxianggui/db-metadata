package com.github.md.web.component;

import com.github.md.analysis.component.Component;
import com.github.md.analysis.component.ComponentRender;
import com.github.md.analysis.component.ComponentType;
import com.github.md.analysis.meta.IMetaObject;
import com.github.md.web.ServiceManager;
import com.github.md.web.component.form.FormView;
import com.github.md.web.component.render.MetaViewRender;
import com.github.md.web.component.render.TreeInTableViewRender;
import com.github.md.web.component.render.TreeViewRender;
import com.github.md.web.ui.ComponentInstanceConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * 容器类组件的构建工厂
 * <p> @Date : 2019/10/21 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class ViewFactory {

    public static TableView tableView(IMetaObject metaObject) {
        ComponentInstanceConfig instanceFlatConfig = ServiceManager.componentService().loadObjectConfig(ComponentType.TABLEVIEW.getCode(), metaObject.code());
        return tableView(metaObject, instanceFlatConfig);
    }

    public static TableView tableView(IMetaObject metaObject, ComponentInstanceConfig instanceFlatConfig) {
        TableView tableView = new TableView(metaObject.code(), metaObject.name());
        ComponentRender<TableView> componentRender = new MetaViewRender<TableView>(metaObject, tableView, instanceFlatConfig);
        tableView.setRender(componentRender);
        return tableView;
    }

    public static FormView formView(IMetaObject metaObject) {
        ComponentInstanceConfig instanceFlatConfig = ServiceManager.componentService().loadObjectConfig(ComponentType.FORMVIEW.getCode(), metaObject.code());
        return formView(metaObject, instanceFlatConfig);
    }

    public static FormView formView(IMetaObject metaObject, ComponentInstanceConfig instanceFlatConfig) {

        FormView formView = new FormView(metaObject.code(), metaObject.name());
        ComponentRender<FormView> componentRender = new MetaViewRender<FormView>(metaObject, formView, instanceFlatConfig);
        formView.setRender(componentRender);
        return formView;
    }

    public static SearchView searchView(IMetaObject metaObject) {
        ComponentInstanceConfig instanceFlatConfig = ServiceManager.componentService().loadObjectConfig(ComponentType.SEARCHVIEW.getCode(), metaObject.code());
        return searchView(metaObject, instanceFlatConfig);
    }

    public static SearchView searchView(IMetaObject metaObject, ComponentInstanceConfig instanceFlatConfig) {
        SearchView searchView = new SearchView(metaObject.code() + ComponentType.SEARCHVIEW.getCode(), metaObject.name());
        searchView.setRender(new MetaViewRender<SearchView>(metaObject, searchView, instanceFlatConfig));
        return searchView;
    }

//    public static TableTreeView tableTreeView(IMetaObject metaObject) {
//        ComponentInstanceConfig instanceFlatConfig = ServiceManager.componentService().loadObjectConfig(ComponentType.TABLETREEVIEW.getCode(), metaObject.code());
//        return tableTreeView(metaObject, instanceFlatConfig);
//    }

    public static TableTreeView tableTreeView(IMetaObject metaObject, ComponentInstanceConfig instanceFlatConfig) {
        TableTreeView tableTreeView = new TableTreeView(metaObject.code() + ComponentType.TABLETREEVIEW.getCode(), metaObject.name());
        tableTreeView.setRender(new TreeInTableViewRender(metaObject, tableTreeView, instanceFlatConfig));
        return tableTreeView;
    }

//    public static TreeView treeView(IMetaObject metaObject) {
//        ComponentInstanceConfig instanceFlatConfig = ServiceManager.componentService().loadObjectConfig(ComponentType.TREEVIEW.getCode(), metaObject.code());
//        return treeView(metaObject, instanceFlatConfig);
//    }

    public static TreeView treeView(IMetaObject metaObject, ComponentInstanceConfig instanceFlatConfig) {
        TreeView treeView = new TreeView(metaObject.code() + ComponentType.TREEVIEW.getCode(), metaObject.name());
        ComponentRender<TreeView> componentRender = new TreeViewRender(metaObject, treeView, instanceFlatConfig);
        treeView.setRender(componentRender);
        return treeView;
    }

//    public static Component createViewComponent(IMetaObject metaObject, ComponentType componentType) {
//        Component component = null;
//        switch (componentType) {
//            case FORMVIEW:
//                component = formView(metaObject);
//                break;
//            case TABLEVIEW:
//                component = tableView(metaObject);
//                break;
//            case SEARCHVIEW:
//                component = searchView(metaObject);
//                break;
//            case TABLETREEVIEW:
//                component = tableTreeView(metaObject);
//                break;
//            case TREEVIEW:
//                component = treeView(metaObject);
//                break;
//            default:
//        }
//        return component;
//    }

    public static Component createViewComponent(IMetaObject metaObject, ComponentType componentType, ComponentInstanceConfig instanceFlatConfig) {
        Component component = null;
        switch (componentType) {
            case FORMVIEW:
                component = formView(metaObject, instanceFlatConfig);
                break;
            case TABLEVIEW:
                component = tableView(metaObject, instanceFlatConfig);
                break;
            case SEARCHVIEW:
                component = searchView(metaObject, instanceFlatConfig);
                break;
            case TABLETREEVIEW:
                component = tableTreeView(metaObject, instanceFlatConfig);
                break;
            case TREEVIEW:
                component = treeView(metaObject, instanceFlatConfig);
                break;
            default:
        }
        return component;
    }

    /**
     * 构建view容器
     *
     * @param typeString
     * @return
     */
    public static Component createEmptyViewComponent(String typeString) {
        ComponentType type = ComponentType.V(typeString);
        Component component = null;

        switch (type) {
            case FORMVIEW:
                component = new FormView(type.getCn(), type.getCode());
                break;
            case TABLEVIEW:
                component = new TableView(type.getCn(), type.getCode());
                break;
            case SEARCHVIEW:
                component = new SearchView(type.getCn(), type.getCode());
                break;
            case TABLETREEVIEW:
                component = new TableTreeView(type.getCn(), type.getCode());
                break;
            case TREEVIEW:
                component = new TreeView(type.getCn(), type.getCode());
                break;
            default:
                throw new ComponentException("此操作不支持创建非容器控件 [%s]", typeString);
        }
        return component;
    }
}
