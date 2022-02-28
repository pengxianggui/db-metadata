export const routeUrl = {
    baseURL: '/meta',
    R_GOBAL_CONF_ADD: '/global-conf',
    R_GOBAL_CONF_EDIT: '/global-conf',
    R_INSTANCE_CONF_NEW: '/instance-conf-new?objectCode={objectCode}&&componentCode={componentCode}',   // 组件实例配置界面[新增]
    R_INSTANCE_CONF_EDIT: '/instance-conf-edit?instanceCode={instanceCode}&componentCode={componentCode}&objectCode={objectCode}&fieldCode={fieldCode}',    // 组件实例配置界面[编辑]

    R_LOGIN: '/login',
    R_401: '/401',
    R_403: '/403',
    R_404: '/404'
};

export const restUrl = {
    GET_APP_CONFIG: '/app/config',
    META_OBJECT_TO_ADD: '/meta/toAdd',   // 元对象新增(TO)
    META_OBJECT_TO_EDIT: '/meta/editObject/?objectCode={objectCode}', // 元对象编辑(TO)
    META_OBJECT_DELETE: '/meta/delete?objectCode={objectCode}',  // 元对象删除(DO)
    META_FIELD_SYNC: '/meta/incrementImport?objectCode={objectCode}',   // 元
    META_FIELD_TO_EDIT: '/meta/editField?objectCode={objectCode}&fieldCode={fieldCode}',  // 元字段编辑(TO)
    OBJECT_CODE_LIST: '/table/list?objectCode=meta_object&fs=code&s=1000',  // 获取所有元对象code
    FIELD_CODE_LIST_BY_OBJECT: '/table/list?objectCode=meta_field&object_code={objectCode}&fs=field_code,en,cn&en->key=&field_code->value=&cn->label=&s=1000', // 查询某个元对象的元字段的field_code和cn，并以key,value返回
    FIELD_CODE_CONF: '/table/list?objectCode=meta_field&object_code={objectCode}&field_code={fieldCode}&fs=config,id', // 获取某个元字段的逻辑配置
    COMPONENT_CODE_LIST: '/component/list?scope={scope}', // 获取所有组件列表, 返回数据格式KV
    INSTANCE_CODE_LIST: '/table/list?objectCode=meta_component_instance&fs=code&code->value=&s=1000', // instanceCode
    COMP_CONF_UPDATE: '/component/doUpdate', // 组件(默认|实例)配置更新
    COMP_CONF_ADD: '/component/doAdd',   // 组件(默认|实例)配置新增
    COMP_GOBAL_CONF_LOAD: '/component/load?componentCode={componentCode}',    // 组件默认配置加载
    COMP_GOBAL_CONF_DELETE: '/component/delete?componentCode={componentCode}',    // 组件默认配置删除
    COMP_INSTANCE_CONF_LOAD_EDIT: '/component/load?componentCode={componentCode}&objectCode={objectCode}&instanceCode={instanceCode}', // 组件实例配置加载
    COMP_INSTANCE_CONF_LOAD_NEW: '/component/load?componentCode={componentCode}&objectCode={objectCode}', // 组件实例配置加载[自动计算-新增时]
    COMP_INSTANCE_CONF_DELETE: '/component/delete/?componentCode={componentCode}&objectCode={objectCode}', // 组件实例配置删除
    COMPONENT_INSTANCE_META: '/component/meta?componentCode={componentCode}&objectCode={objectCode}', // 组件实例元数据
    FEATURE_TREE_AND_TABLE_META: '/feature/treeAndTable/meta?featureCode={featureCode}',
    TREE_TABLE_TO_ADD_S: '/feature/treeAndTable/toAdd?featureCode={featureCode}&{foreignKeyName}={foreignKeyValue}',
    RECORD_TO_ADD: "/form/toAdd/?objectCode={objectCode}",  // 新增记录(TO)
    RECORD_TO_UPDATE: '/form/toUpdate/?id={primaryKv}&objectCode={objectCode}',    // 更新记录(TO)
    RECORD_DO_UPDATE: '/form/doUpdate?objectCode={objectCode}', // 更新记录(DO)
    RECORD_TO_VIEW: '/form/detail/?id={primaryKv}&objectCode={objectCode}', // 详情
    LOAD_COMP_BY_OBJECT: '/meta/contact/?kv={kv}&objectCode={objectCode}', // 传入objectCode, 获取该元对象对应的组件实例code, 即componentCode. (kv为true, 返回kv格式)
    LOAD_INSTANCE_CODE_BY_OBJECT_COMP: '/component/contact?objectCode={objectCode}&componentCode={componentCode}&kv={kv}', // 根据查询在objectCode+componentCode下的所有实例配置
    LOAD_INSTANCE_CODE_BY_OBJECT: '/component/contact?objectCode={objectCode}&kv={kv}', // 根据查询在objectCode下的所有实例配置
    CHECK_SQL: '/check/sql?sql={sql}',
    FEATURE_ADD: '/feature/doAdd?ft={featureType}', // 添加功能类别
    FEATURE_LOAD: '/feature/load?fc={featureCode}', // 加载功能配置
    MASTER_SLAVE_TO_ADD_S: '/feature/masterSlave/toAddS?objectCode={objectCode}&fc={featureCode}&{foreignKeyName}={foreignKeyValue}', // 主子表子表新增URL(TO)
    LIST_FEATURE_TYPE: '/feature/list', // 列出所有的功能类型code
    MENU_DATA: '/menu',
    ROUTE_DATA: '/router',

    // TODO 这部分太偏业务了，耦合在db-meta里显得比较突兀，有两个优化方案：
    //  1. db-meta提供多对多关联的数据维护功能。这样给用户绑定角色、给角色绑定权限，就通过内置的多对多功能模板实现
    //  2. 认证和授权这块内容，挪到单独的模块里。比如后端单独通过pom坐标引入，前端通过单独的npm包引入。
    //  优选方案1
    ROLE_LIST: '/table/list?objectCode=meta_role&s=100000', // 所有角色列表
    ROLE_LIST_FOR_USER: '/user/{userId}/roles', // 用户拥有的角色
    ROLE_SET_FOR_USER: '/user/{userId}/roles', // 用户角色绑定
    AUTH_LIST: '/table/list?objectCode=meta_auth&s=100000', // 所有权限列表
    AUTH_SET_FOR_ROLE: '/role/{roleId}/auths', // 角色权限绑定
    AUTH_LIST_FOR_ROLE: '/role/{roleId}/auths', // 角色拥有的权限
    LOGIN_URL: '/user/login', // 登录接口地址
    LOGOUT_URL: '/user/logout', // 登出接口地址
    LOGIN_INFO: '/user/info', // 获取登录用户信息接口地址
};
