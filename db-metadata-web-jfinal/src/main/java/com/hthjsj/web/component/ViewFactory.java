package com.hthjsj.web.component;

import com.hthjsj.analysis.component.Component;
import com.hthjsj.analysis.component.ComponentRender;
import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.analysis.meta.IMetaObject;
import com.hthjsj.web.ServiceManager;
import com.hthjsj.web.component.form.FormView;
import com.hthjsj.web.component.render.MetaViewRender;
import com.jfinal.kit.Kv;
import lombok.extern.slf4j.Slf4j;

/**
 * <p> @Date : 2019/10/21 </p>
 * <p> @Project : db-meta-serve</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
public class ViewFactory {

    public static TableView tableView(IMetaObject metaObject) {
        Kv instanceFlatConfig = ServiceManager.componentService().loadObjectConfigFlat(ComponentType.TABLEVIEW.getCode(), metaObject.code());
        return tableView(metaObject, instanceFlatConfig);
    }

    public static TableView tableView(IMetaObject metaObject, Kv instanceFlatConfig) {
        TableView tableView = new TableView(metaObject.code(), metaObject.name());
        ComponentRender<TableView> componentRender = new MetaViewRender<TableView>(metaObject, tableView, instanceFlatConfig);
        tableView.setRender(componentRender);
        return tableView;
    }

    public static FormView formView(IMetaObject metaObject) {
        Kv instanceFlatConfig = ServiceManager.componentService().loadObjectConfigFlat(ComponentType.FORMVIEW.getCode(), metaObject.code());
        return formView(metaObject, instanceFlatConfig);
    }

    public static FormView formView(IMetaObject metaObject, Kv instanceFlatConfig) {

        FormView formView = new FormView(metaObject.code(), metaObject.name());
        ComponentRender<FormView> componentRender = new MetaViewRender<FormView>(metaObject, formView, instanceFlatConfig);
        formView.setRender(componentRender);
        return formView;
    }

    public static SearchView searchView(IMetaObject metaObject) {
        Kv instanceFlatConfig = ServiceManager.componentService().loadObjectConfigFlat(ComponentType.SEARCHVIEW.getCode(), metaObject.code());
        return searchView(metaObject, instanceFlatConfig);
    }

    public static SearchView searchView(IMetaObject metaObject, Kv instanceFlatConfig) {
        SearchView searchView = new SearchView(metaObject.code() + ComponentType.SEARCHVIEW.getCode(), metaObject.name());
        searchView.setRender(new MetaViewRender<SearchView>(metaObject, searchView, instanceFlatConfig));
        return searchView;
    }

    public static Component createViewComponent(IMetaObject metaObject, ComponentType componentType) {
        Component component = null;
        switch (componentType) {
            case FORMVIEW:
                component = formView(metaObject);
                break;
            case TABLEVIEW:
                component = tableView(metaObject);
                break;
            case SEARCHVIEW:
                component = searchView(metaObject);
                break;
            default:
        }
        return component;
    }

    public static Component createViewComponent(IMetaObject metaObject, ComponentType componentType, Kv instanceFlatConfig) {
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
            default:
        }
        return component;
    }

    /**
     * 构建view容器
     *
     * @param typeString
     *
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
            default:
                throw new ComponentException("此操作不支持创建非容器控件 [%s]", typeString);
        }
        return component;
    }
}
