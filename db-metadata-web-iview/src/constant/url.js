// [接口URL]-START ----------------------------------------------------------------------------------------------------
export const META_OBJECT_TO_ADD = '/meta/toAdd';   // 元对象新增(TO)
export const META_OBJECT_TO_EDIT = '/meta/editObject/{objectCode}'; // 元对象编辑(TO)
export const META_OBJECT_DELETE = '/meta/delete?objectCode={objectCode}';  // 元对象删除(DO)

export const META_FIELD_TO_EDIT = '/meta/editField?objectCode={objectCode}&fieldCode={fieldCode}';  // 元字段编辑(TO)

export const OBJECT_CODE_LIST = '/table/list?objectCode=meta_object&fs=code';  // 获取所有元对象code
export const COMPONENT_CODE_LIST = '/component/list'; // 获取所有组件列表, 返回数据格式KV

export const COMP_CONF_UPDATE = '/component/doUpdate'; // 组件(默认|实例)配置更新
export const COMP_CONF_ADD = '/component/doAdd';   // 组件(默认|实例)配置新增
export const COMP_GOBAL_CONF_LOAD = '/component/load?componentCode={componentCode}';    // 组件默认配置加载
export const COMP_GOBAL_CONF_DELETE = '/component/delete?componentCode={componentCode}';    // 组件默认配置删除
export const COMP_INSTANCE_CONF_LOAD = '/component/load?componentCode={componentCode}&objectCode={objectCode}'; // 组件实例配置加载
export const COMP_INSTANCE_CONF_DELETE = '/component/delete/{objectCode}?componentCode={componentCode}'; // 组件实例配置删除

export const TABLE_INSTANCE_META = '/table/meta/{objectCode}'; // 表实例元数据
export const COMPONENT_INSTANCE_META = '/component/meta?componentCode={componentCode}&objectCode={objectCode}'; // 组件实例元数据

export const RECORD_DELETE = '/table/delete?objectCode={objectCode}&ids={ids}'; // 删除记录
export const RECORD_TO_ADD = "/form/toAdd/{objectCode}";  // 新增记录(TO)
export const RECORD_TO_UPDATE = '/form/toUpdate/{objectCode}?id={primaryKey}';    // 更新记录(TO)

export const LOAD_COMP_BY_OBJECT = '/meta/contact/{objectCode}?kv={kv}'; // 传入objectCode, 获取该元对象对应的组件实例code, 即componentCode. (kv为true, 返回kv格式)

export const CHECK_SQL = '/check/sql?sql={sql}';

export const FEATURE_ADD = '/m/doAdd?ft={featureType}'; // 添加功能类别
export const FEATURE_LOAD = '/m/load?fc={featureCode}'; // 加载功能配置

export const MASTER_SLAVE_TO_ADD_S = '/m/ms/toAddS?objectCode={objectCode}&fc={featureCode}&{foreignKeyName}={foreignKeyValue}'; // 主子表子表新增URL(TO)

export const LIST_FEATURE_TYPE = '/m/list'; // 列出所有的功能类型code

export const ROUTE_DATA = '/route/list'; // pxg_todo 路由数据,临时写

//  [接口URL]-END ----------------------------------------------------------------------------------------------------

//  [路由URL]-START --------------------------------------------------------------------------------------------------
export const R_GOBAL_CONF_ADD = '/main/global-conf';    // 组件默认配置界面[新增]
export const R_INSTANCE_CONF_ADD = '/main/instance-conf';   // 组件实例配置界面[新增]
export const R_INSTANCE_CONF_EDIT = '/main/instance-conf?componentCode={componentCode}&objectCode={objectCode}';    // 组件实例配置界面[编辑]

// 根路由(RR)
export const RR_INSTANCE_CONF_ADD = '/instance-conf';   // 组件默认配置界面[新增]


//  [路由URL]-END --------------------------------------------------------------------------------------------------