// [接口URL]-START ----------------------------------------------------------------------------------------------------
export const META_OBJECT_TO_ADD = '/meta/toAdd';   // 元对象新增(TO)
export const META_OBJECT_TO_EDIT = '/meta/editObject/{objectCode}'; // 元对象编辑(TO)
export const META_OBJECT_DELETE = '/meta/delete?objectCode={objectCode}';  // 元对象删除(DO)

export const META_FIELD_TO_EDIT = '/meta/editField?objectCode={objectCode}&fieldCode={fieldCode}';  // 元字段编辑(TO)

export const OBJECT_CODE_LIST = '/table/list?objectCode=meta_object&fs=code&s=1000';  // 获取所有元对象code
export const FIELD_CODE_LIST_BY_OBJECT = '/table/list?objectCode=meta_field&object_code={objectCode}&fs=field_code,en,cn&en->key&field_code->value&cn->label&s=1000'; // 查询某个元对象的元字段的field_code和cn，并以key,value返回
export const COMPONENT_CODE_LIST = '/component/list'; // 获取所有组件列表, 返回数据格式KV

export const INSTANCE_CODE_LIST = '/table/list/meta_component_instance?fs=code&code->value&s=1000'; // instanceCode
export const COMP_CONF_UPDATE = '/component/doUpdate'; // 组件(默认|实例)配置更新
export const COMP_CONF_ADD = '/component/doAdd';   // 组件(默认|实例)配置新增
export const COMP_GOBAL_CONF_LOAD = '/component/load?componentCode={componentCode}';    // 组件默认配置加载
export const COMP_GOBAL_CONF_DELETE = '/component/delete?componentCode={componentCode}';    // 组件默认配置删除
export const COMP_INSTANCE_CONF_LOAD_EDIT = '/component/load?componentCode={componentCode}&objectCode={objectCode}&instanceCode={instanceCode}'; // 组件实例配置加载
export const COMP_INSTANCE_CONF_LOAD_NEW = '/component/load?componentCode={componentCode}&objectCode={objectCode}'; // 组件实例配置加载[自动计算-新增时]
export const COMP_INSTANCE_CONF_DELETE = '/component/delete/{objectCode}?componentCode={componentCode}'; // 组件实例配置删除

export const COMPONENT_INSTANCE_META = '/component/meta?componentCode={componentCode}&objectCode={objectCode}'; // 组件实例元数据

export const RECORD_TO_ADD = "/form/toAdd/{objectCode}";  // 新增记录(TO)
export const RECORD_TO_UPDATE = '/form/toUpdate/{objectCode}?id={primaryKv}';    // 更新记录(TO)

export const LOAD_COMP_BY_OBJECT = '/meta/contact/{objectCode}?kv={kv}'; // 传入objectCode, 获取该元对象对应的组件实例code, 即componentCode. (kv为true, 返回kv格式)
export const LOAD_INSTANCE_CODE_BY_OBJECT_COMP = '/component/contact?objectCode={objectCode}&componentCode={componentCode}&kv={kv}'; // 根据查询在objectCode+componentCode下的所有实例配置
export const LOAD_INSTANCE_CODE_BY_OBJECT = '/component/contact?objectCode={objectCode}&kv={kv}'; // 根据查询在objectCode下的所有实例配置

export const CHECK_SQL = '/check/sql?sql={sql}';

export const FEATURE_ADD = '/feature/doAdd?ft={featureType}'; // 添加功能类别
export const FEATURE_LOAD = '/feature/load?fc={featureCode}'; // 加载功能配置

export const MASTER_SLAVE_TO_ADD_S = '/feature/masterSlave/toAddS?objectCode={objectCode}&fc={featureCode}&{foreignKeyName}={foreignKeyValue}'; // 主子表子表新增URL(TO)

export const LIST_FEATURE_TYPE = '/feature/list'; // 列出所有的功能类型code
export const MENU_DATA = '/feature/menu';
// export const ROUTE_DATA = '/route/list'; // pxg_todo 路由数据, for mock

//  [接口URL]-END ----------------------------------------------------------------------------------------------------

//  [路由URL]-START --------------------------------------------------------------------------------------------------
export const R_GOBAL_CONF_ADD = '/main/global-conf';    // 组件默认配置界面[新增]
export const R_GOBAL_CONF_EDIT = '/main/global-conf';
export const R_INSTANCE_CONF_NEW = '/main/instance-conf-new?objectCode={objectCode}&&componentCode={componentCode}';   // 组件实例配置界面[新增]
export const R_INSTANCE_CONF_EDIT = '/main/instance-conf-edit?instanceCode={instanceCode}&componentCode={componentCode}&objectCode={objectCode}';    // 组件实例配置界面[编辑]

// 根路由(RR)
export const RR_INSTANCE_CONF_ADD = '/instance-conf-edit';   // 组件默认配置界面[新增]


//  [路由URL]-END --------------------------------------------------------------------------------------------------