export const innerFeatureCode = {
    metadata: 'meta_manager' // TODO 元数据管理模块不应当依赖任何其他配置移除对meta_manger 功能编码的依赖
}

export const innerObjectCode = {
    featureList: 'meta_feature', // 功能objectCode
    globalConfList: 'meta_component', // 组件全局配置列表
    instanceConfList: 'meta_component_instance', // 组件实例配置列表
    metaConfList: 'meta_conf', //
    dictList: 'meta_dict', // 字典维护
    exceptionList: 'meta_exception', // 异常管理
    MenuList: 'meta_menu', // 菜单管理
}

export const access = {
    root: 'ROOT', // 只有ROOT角色的用户才能访问元数据快捷编辑、平台维护模块(菜单的控制目前还有些技术问题)
    roles: [] // 当前用户角色
}