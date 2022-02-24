/**
 * meta 内置功能code
 * @type {{}}
 */
export const metaFeatureCode = {}

/**
 * meta 内置元对象编码
 * @type {{featureList: string, globalConfList: string, MenuList: string, instanceConfList: string, exceptionList: string, metaConfList: string, dictList: string}}
 */
export const metaObjectCode = {
    featureList: 'meta_feature', // 功能objectCode
    globalConfList: 'meta_component', // 组件全局配置列表
    instanceConfList: 'meta_component_instance', // 组件实例配置列表
    metaConfList: 'meta_config', //
    dictList: 'meta_dict', // 字典维护
    exceptionList: 'meta_exception', // 异常管理
    MenuList: 'meta_menu', // 菜单管理
    AuthList: 'meta_auth', // 权限管理
    RoleList: 'meta_role', // 角色管理
    UserList: 'meta_user', // 用户管理
    ApiResourceList: 'meta_api_resource', // 接口资源管理
}
