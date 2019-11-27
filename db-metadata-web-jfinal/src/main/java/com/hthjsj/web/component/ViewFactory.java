package com.hthjsj.web.component;

import com.hthjsj.analysis.component.Component;
import com.hthjsj.analysis.component.ComponentRender;
import com.hthjsj.analysis.component.ComponentType;
import com.hthjsj.analysis.meta.MetaObject;
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

    public static TableView createTableView(MetaObject metaObject) {
        Kv instanceFlatConfig = ServiceManager.componentService().loadObjectConfigFlat(ComponentType.TABLEVIEW.getCode(), metaObject.code());
        return createTableView(metaObject, instanceFlatConfig);
    }

    public static TableView createTableView(MetaObject metaObject, Kv instanceFlatConfig) {
        TableView tableView = new TableView(metaObject.code(), metaObject.name());
        ComponentRender<TableView> componentRender = new MetaViewRender<TableView>(metaObject, tableView, instanceFlatConfig);
        tableView.setRender(componentRender);
        return tableView;
    }

    public static FormView createFormView(MetaObject metaObject) {
        Kv instanceFlatConfig = ServiceManager.componentService().loadObjectConfigFlat(ComponentType.FORMVIEW.getCode(), metaObject.code());
        return createFormView(metaObject, instanceFlatConfig);
    }

    public static FormView createFormView(MetaObject metaObject, Kv instanceFlatConfig) {

        FormView formView = new FormView(metaObject.code(), metaObject.name());
        ComponentRender<FormView> componentRender = new MetaViewRender<FormView>(metaObject, formView, instanceFlatConfig);
        formView.setRender(componentRender);
        return formView;
    }

    public static SearchView createSearchView(MetaObject metaObject) {
        Kv instanceFlatConfig = ServiceManager.componentService().loadObjectConfigFlat(ComponentType.FORMVIEW.getCode(), metaObject.code());
        return createSearchView(metaObject, instanceFlatConfig);
    }

    public static SearchView createSearchView(MetaObject metaObject, Kv instanceFlatConfig) {
        SearchView searchView = new SearchView(metaObject.code() + ComponentType.SEARCHVIEW.getCode(), metaObject.name());
        searchView.setRender(new MetaViewRender<SearchView>(metaObject, searchView, instanceFlatConfig));
        return searchView;
    }

    public static Component createViewComponent(MetaObject metaObject, ComponentType componentType) {
        Component component = null;
        switch (componentType) {
            case FORMVIEW:
                component = createFormView(metaObject);
                break;
            case TABLEVIEW:
                component = createTableView(metaObject);
                break;
            case SEARCHVIEW:
                component = createSearchView(metaObject);
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
