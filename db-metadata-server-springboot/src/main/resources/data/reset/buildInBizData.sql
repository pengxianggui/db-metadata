
-- ----------------------------
-- Records of meta_api_resource
-- ----------------------------
BEGIN;
INSERT INTO `meta_api_resource` VALUES ('694223070646374400', '路由数据', '0', '/router', '', b'0', b'0', 'auth', '', 'any', '', 'any', '路由接口, 属于系统数据, 需要不登录也能正常获取', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('694225274480496640', '菜单数据', '0', '/menu', '', b'1', b'1', 'auth', '', 'any', '', 'any', '菜单数据，登录后才能进入管理界面;登录即可访问', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('694227116228743168', '系统属性', '0', '/app/config', '', b'0', b'0', 'auth', '', 'any', '', 'any', '系统配置数据, 无需鉴权', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('694231657120665600', '登录接口', '0', '/user/login', '', b'0', b'0', 'auth', '', 'any', '', 'any', '用户登录接口', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('694231832820060160', '当前登录用户信息接口', '0', '/user/info', '', b'1', b'1', 'auth', '', 'any', '', 'any', '当前用户获取其登录信息的接口', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('694231925002473472', '登出接口', '0', '/user/logout', '', b'0', b'0', 'auth', '', 'any', '', 'any', '登出接口', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('694232534690697216', '数据库列表接口', '0', '/db/index', NULL, b'1', b'0', 'auth', 'add:meta_object', 'any', NULL, 'any', '获取数据库列表。等同/db/list', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('694232700655112192', '关系表列表接口', '0', '/db/tables', NULL, b'1', b'0', 'role', 'add:meta_object', 'any', NULL, 'any', '获取所有表', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('694233825454198784', '系统初始化接口', '0', '/db/init', NULL, b'1', b'0', 'auth', 'api:app-init', 'any', NULL, 'any', '系统初始化接口', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('694238218186526720', '字典数据接口', '0', '/dict', NULL, b'1', b'1', 'auth', NULL, 'any', NULL, 'any', '系统字典', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('694238636744511488', '文件上传接口', '0', '/file/upload', '', b'1', b'1', 'auth', '', 'any', '', 'any', '', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('694238822514429952', '富文本文件上传', '0', '/file/upload/richText', '', b'1', b'1', 'auth', '', 'any', '', 'any', '', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696167536378646528', '元对象列表', '1', '/table/list', 'meta_object', b'1', b'0', 'auth', 'query:meta_object', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696167915807969280', '新增元对象接口', '0', '/meta/doAdd', NULL, b'1', b'0', 'auth', 'add:meta_object', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696168277629603840', '元对象更新', '1', '/form/doUpdate', 'meta_object', b'1', b'0', 'auth', 'update:meta_object', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696168807957401600', '元对象删除', '0', '/meta/delete', NULL, b'1', b'0', 'auth', 'delete:meta_object', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696169111574679552', '元字段同步', '0', '/meta/incrementImport', NULL, b'1', b'0', 'auth', 'sync:meta_field', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696169336997548032', '元字段更新', '1', '/form/doUpdate', 'meta_field', b'1', b'0', 'auth', 'update:meta_field', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696169751176679424', '元字段删除', '1', '/table/delete', 'meta_field', b'1', b'0', 'auth', 'delete:meta_field', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696170147102199808', '元字段列表', '1', '/table/list', 'meta_field', b'1', b'0', 'auth', 'query:meta_field', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696170439403245568', '功能配置列表', '1', '/table/list', 'meta_feature', b'1', b'0', 'auth', 'query:meta_feature', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696170795436740608', '功能新增', '0', '/feature/doAdd', NULL, b'1', b'0', 'auth', 'add:meta_feature', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696171090757685248', '功能更新', '1', '/form/doUpdate', 'meta_feature', b'1', b'0', 'auth', 'update:meta_feature', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696171222936981504', '功能删除', '1', '/table/delete', 'meta_feature', b'1', b'0', 'auth', 'delete:meta_feature', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696171563858399232', '组件默认配置更新', '1', '/form/doUpdate', 'meta_component', b'1', b'0', 'auth', 'update:meta_component', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696171773636513792', '组件默认配置删除', '1', '/table/delete', 'meta_component', b'1', b'0', 'auth', 'delete:meta_component', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696171997700427776', '组件默认配置/组件实例配置更新接口', '0', '/component/doUpdate', NULL, b'1', b'0', 'auth', 'update:meta_component,update:meta_component_instance', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696172202269216768', '组件实例配置列表', '1', '/table/list', 'meta_component_instance', b'1', b'0', 'auth', 'query:meta_component_instance', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696172577076416512', '组件实例配置更新接口', '1', '/form/doUpdate', 'meta_component_instance', b'1', b'0', 'auth', 'update:meta_component_instance', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696172706986594304', '组件实例配置删除接口', '1', '/table/delete', 'meta_component_instance', b'1', b'0', 'auth', 'delete:meta_component_instance', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696175296763793408', '菜单数据列表', '1', '/table/tree', 'meta_menu', b'1', b'0', 'auth', 'query:meta_menu', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696175533460951040', '新增菜单', '1', '/form/doAdd', 'meta_menu', b'1', b'0', 'auth', 'add:meta_menu', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696175709512667136', '更新菜单', '1', '/form/doUpdate', 'meta_menu', b'1', b'0', 'auth', 'update:meta_menu', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696175862860615680', '菜单删除', '1', '/table/delete', 'meta_menu', b'1', b'0', 'auth', 'delete:meta_menu', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696176715805888512', '路由列表', '1', '/table/tree', 'meta_router', b'1', b'0', 'auth', 'query_meta_router', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696176920043327488', '更新路由', '1', '/form/doUpdate', 'meta_router', b'1', b'0', 'auth', 'update:meta_router', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696177008371175424', '新增路由', '1', '/form/doAdd', 'meta_router', b'1', b'0', 'auth', 'add:meta_router', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696177092982870016', '删除路由', '1', '/table/delete', 'meta_router', b'1', b'0', 'auth', 'delete:meta_router', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696177310180708352', '新增字典', '1', '/form/doAdd', 'meta_dict', b'1', b'0', 'auth', 'add:meta_dict', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696177407417257984', '更新字典', '1', '/form/doUpdate', 'meta_dict', b'1', b'0', 'auth', 'update:meta_dict', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696177519040270336', '删除字典', '1', '/table/delete', 'meta_dict', b'1', b'0', 'auth', 'delete:meta_dict', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696177879901409280', '异常数据删除', '1', '/table/delete', 'meta_exception', b'1', b'0', 'auth', 'delete:meta_exception', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696178023577292800', '权限数据新增接口', '1', '/form/doAdd', 'meta_auth', b'1', b'0', 'auth', 'add:meta_auth', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696178190523174912', '权限数据列表', '1', '/table/list', 'meta_auth', b'1', b'0', 'auth', 'query:meta_auth', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696178301361852416', '权限数据更新接口', '1', '/form/doUpdate', 'meta_auth', b'1', b'0', 'auth', 'update:meta_auth', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696178379577233408', '权限数据删除接口', '1', '/table/delete', 'meta_auth', b'1', b'0', 'auth', 'delete:meta_auth', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696178536532283392', '接口资源新增接口', '1', '/form/doAdd', 'meta_api_resource', b'1', b'0', 'auth', 'add:meta_api_resource', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696178653947629568', '接口资源列表', '1', '/table/list', 'meta_api_resource', b'1', b'0', 'auth', 'query:meta_api_resource', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696178779864829952', '接口资源更新接口', '1', '/form/doUpdate', 'meta_api_resource', b'1', b'0', 'auth', 'update:meta_api_resource', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696178862790414336', '接口资源删除接口', '1', '/table/delete', 'meta_api_resource', b'1', b'0', 'auth', 'delete:meta_api_resource', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696339795437293568', '用户列表', '1', '/table/list', 'meta_user', b'1', b'0', 'auth', 'query:meta_user', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696339990992523264', '用户新增接口', '1', '/form/doAdd', 'meta_user', b'1', b'0', 'auth', 'add:meta_user', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696340082122166272', '用户更新接口', '1', '/form/doUpdate', 'meta_user', b'1', b'0', 'auth', 'update:meta_user', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696340217535270912', '用户删除接口', '1', '/table/delete', 'meta_user', b'1', b'0', 'auth', 'delete:meta_user', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696342334748626944', '角色新增接口', '1', '/form/doAdd', 'meta_role', b'1', b'0', 'auth', 'add:meta_role', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696342436988981248', '角色删除接口', '1', '/table/delete', 'meta_role', b'1', b'0', 'auth', 'delete:meta_role', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696342549929005056', '角色更新接口', '1', '/form/doUpdate', 'meta_role', b'1', b'0', 'auth', 'update:meta_role', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('696342647471738880', '角色列表', '1', '/table/list', 'meta_role', b'1', b'0', 'auth', 'query:meta_role', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('704005535661428736', '组件实例配置一键生成接口', '0', '/component/import-auto-computed', NULL, b'1', b'0', 'auth', 'add:meta_component_instance', 'any', NULL, 'any', '一键生成组件的实例配置', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('704009277622325248', '元字段配置sql校验', '0', '/check/sql', NULL, b'1', b'0', 'auth', 'update:meta_field', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('704011526276780032', '组件实例配置删除接口', '0', '/component/delete', NULL, b'1', b'0', 'auth', 'delete:meta_component_instance', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('704013975712567296', '元对象更新', '0', '/meta/editObject', NULL, b'1', b'0', 'auth', 'update:meta_object', 'any', NULL, 'any', 'ROOT用户快捷编辑元对象', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('704014393469440000', '元字段更新', '0', '/meta/editField', NULL, b'1', b'0', 'auth', 'update:meta_field', 'any', NULL, 'any', 'ROOT用户快捷编辑元字段', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('704017169255632896', '异常信息列表', '1', '/table/list', 'meta_exception', b'1', b'0', 'auth', 'query:meta_exception', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('704017720508813312', '字典列表', '1', '/table/tree', 'meta_dict', b'1', b'0', 'auth', 'query:meta_dict', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('704020269446074368', '查询当前用户拥有的角色', '0', '/user/roles', NULL, b'1', b'1', 'auth', NULL, 'any', NULL, 'any', '获取当前登录用户拥有的角色', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('704020454637178880', '获取当前用户拥有的权限接口', '0', '/user/auths', NULL, b'1', b'1', 'auth', NULL, 'any', NULL, 'any', '获取当前登录用户拥有的权限列表', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_api_resource` VALUES ('711632186226380800', '选项数据', '0', '/component/options', NULL, b'1', b'1', 'auth', NULL, 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
COMMIT;


-- ----------------------------
-- Records of meta_auth
-- ----------------------------
BEGIN;
INSERT INTO `meta_auth` VALUES ('694149240007561216', 'menu:meta_feature', '菜单:功能维护', '711604182171389952', 'menu', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('694149341149007872', 'menu:meta_component', '菜单:组件全局配置', '711604231555125248', 'menu', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('694149424439496704', 'menu:meta_component_instance', '菜单:组件实例配置', '711604268704075776', 'menu', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('694149476058796032', 'menu:meta_menu', '菜单:菜单维护', '711604305995632640', 'menu', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('694149530551193600', 'menu:meta_router', '菜单:路由维护', '711604336572108800', 'menu', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('694149774206701568', 'menu:meta_dict', '菜单:字典管理', '711603985097822208', 'menu', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('694149834445295616', 'menu:meta_exception', '菜单:异常信息', '711604059425083392', 'menu', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('694149907166138368', 'menu:meta_auth', '菜单:权限配置', '711604024738189312', 'menu', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('694149960505102336', 'menu:meta_api_resource', '菜单:接口资源', '711604368734031872', 'menu', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('694150160565014528', 'menu:meta_user', '菜单:用户管理', '711603857981050880', 'menu', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('694183855120322560', 'menu:meta_role', '菜单:角色管理', '711603907276705792', 'menu', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('694280287013703680', 'api:app-init', '系统重置', NULL, 'api,button', '拥有此权限则可调用系统重置接口，完成系统重置', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('694330954252161024', 'menu:platform-ops', '菜单:平台维护', '711603760899690496', 'menu', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('694335625574354944', 'route:meta_component_instance:edit', '页面:组件实例配置编辑', '711604268704075776', 'router', '编辑组件UI实例配置', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('694343798075035648', 'route:meta_feature', '页面:功能维护', '711604182171389952', 'router', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('694343885119426560', 'route:meta_component', '页面:组件全局配置', '711604231555125248', 'router', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('694344082931191808', 'route:meta_component:edit', '页面:组件全局配置-编辑', '711604231555125248', 'router', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('694344336845967360', 'route:meta_router', '页面:路由维护', '711604336572108800', 'router', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('694344409273208832', 'route:meta_menu', '页面:菜单维护', '711604305995632640', 'router', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('694344675334688768', 'route:meta_dict', '页面:字典管理', '711603985097822208', 'router', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('694344780561387520', 'route:meta_exception', '页面:异常信息', '711604059425083392', 'router', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('694344875012919296', 'route:meta_auth', '页面:权限配置', '711604024738189312', 'router', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('694344969531559936', 'route:meta_api_resource', '页面:接口资源', '711604368734031872', 'router', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696152559060127744', 'query:meta_auth', '权限列表', '711604024738189312', 'api', '查看权限编码列表数据', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696153360272855040', 'delete:meta_auth', '删除权限', '711604024738189312', 'api,button', '拥有权限数据的删除权限', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696153730193690624', 'add:meta_auth', '权限新增', '711604024738189312', 'api,button', '拥有新增权限的权限', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696153853300707328', 'update:meta_auth', '权限更新', '711604024738189312', 'api,button', '拥有更新权限的权限', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696154203961298944', 'add:meta_api_resource', '新增接口资源', '711604368734031872', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696154346861236224', 'delete:meta_api_resource', '删除接口资源', '711604368734031872', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696154466335985664', 'update:meta_api_resource', '更新接口资源', '711604368734031872', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696154568492453888', 'query:meta_api_resource', '查询接口资源', '711604368734031872', 'api', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696154835401183232', 'query:meta_exception', '异常数据列表', '711604059425083392', 'api', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696154907899727872', 'delete:meta_exception', '异常数据删除', '711604059425083392', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696155030516011008', 'add:meta_dict', '新增字典', '711603985097822208', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696155122442571776', 'delete:meta_dict', '删除字典', '711603985097822208', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696155278286131200', 'update:meta_dict', '更新字典', '711603985097822208', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696155376374124544', 'query:meta_dict', '查询字典', '711603985097822208', 'api', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696155502178078720', 'add:meta_router', '新增路由', '711604336572108800', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696155564840980480', 'delete:meta_router', '删除路由', '711604336572108800', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696155654074798080', 'update:meta_router', '更新路由', '711604336572108800', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696155922124378112', 'add:meta_menu', '新增菜单', '711604305995632640', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696156011769237504', 'delete:meta_menu', '删除菜单', '711604305995632640', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696156079964426240', 'update:meta_menu', '更新菜单', '711604305995632640', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696156165645668352', 'query:meta_menu', '查询菜单', '711604305995632640', 'api', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696159659219881984', 'add:meta_user', '新增用户', '711603857981050880', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696159713649364992', 'delete:meta_user', '删除用户', '711603857981050880', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696159771941801984', 'update:meta_user', '更新用户', '711603857981050880', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696159836404060160', 'query:meta_user', '查询用户', '711603857981050880', 'api', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696159898110660608', 'add:meta_role', '新增角色', '711603907276705792', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696159978653880320', 'delete:meta_role', '删除角色', '711603907276705792', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696160054025523200', 'update:meta_role', '更新角色', '711603907276705792', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696160111751729152', 'query:meta_role', '查询角色', '711603907276705792', 'api', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696176274166648832', 'query_meta_router', '查询路由', '711604336572108800', 'api', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696886596984770560', 'route:meta_user', '页面:用户管理', '711603857981050880', 'router', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('696886708112855040', 'route:meta_role', '页面:角色管理', '711603907276705792', 'router', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('697160516417753088', 'bind:roles:to-user', '为指定用户绑定角色', '711603857981050880', 'api,button', '为用户绑定角色', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('697160698198888448', 'bind:auths-to-role', '为指定角色绑定权限', '711603907276705792', 'api', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('711618459557040128', 'menu:sys', '菜单:系统管理', '711603723775905792', 'menu', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('711621009249275904', 'add:meta_object', '创建元对象', '711604134998052864', 'api,button', '拥有元对象创建权限', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('711621133711052800', 'route:meta_data', '页面:元数据管理', '711604134998052864', 'router', '拥有进入元数据管理页面的权限', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('711621340548960256', 'menu:meta_data', '菜单:元数据管理', '711604134998052864', 'menu', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('711623619599536128', 'update:meta_object', '更新元对象', '711604134998052864', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('711623737232986112', 'delete:meta_object', '删除元对象', '711604134998052864', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('711624070503993344', 'sync:meta_field', '同步增量字段', '711604134998052864', 'button,api', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('711624225265422336', 'delete:meta_field', '删除元字段', '711604134998052864', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('711624303988314112', 'update:meta_field', '更新元字段', '711604134998052864', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('711624635795509248', 'add:meta_feature', '创建功能', '711604182171389952', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('711624738526597120', 'update:meta_feature', '更新功能', '711604182171389952', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('711624886224818176', 'delete:meta_feature', '删除功能', '711604182171389952', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('711625652272500736', 'update:meta_component', '更新默认组件配置', '711604231555125248', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('711625820128546816', 'delete:meta_component', '删除默认组件配置', '711604231555125248', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('711626372585492480', 'route:meta_component_instance', '页面:组件实例配置', '711604268704075776', 'router', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('711626548469436416', 'add:meta_component_instance', '新增组件实例配置', '711604268704075776', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('711626638537920512', 'update:meta_component_instance', '更新组件实例配置', '711604268704075776', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('711626755575779328', 'delete:meta_component_instance', '删除组件实例配置', '711604268704075776', 'api,button', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('711627077702520832', 'query:meta_component_instance', '查询组件实例配置', '711604268704075776', 'api', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('711627199916150784', 'query:meta_component', '查询组件默认配置', '711604231555125248', 'api', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('711627319839690752', 'query:meta_feature', '查询功能', '711604182171389952', 'api', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('711627452220313600', 'query:meta_object', '查询元对象', '711604134998052864', 'api', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth` VALUES ('711627513272602624', 'query:meta_field', '查询元字段', '711604134998052864', 'api', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
COMMIT;


-- ----------------------------
-- Records of meta_auth_module
-- ----------------------------
BEGIN;
INSERT INTO `meta_auth_module` VALUES ('711603723775905792', '系统管理', NULL, 99, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth_module` VALUES ('711603760899690496', '平台维护', NULL, 100, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth_module` VALUES ('711603857981050880', '用户管理', '711603723775905792', 0, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth_module` VALUES ('711603907276705792', '角色管理', '711603723775905792', 1, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth_module` VALUES ('711603985097822208', '字典管理', '711603723775905792', 2, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth_module` VALUES ('711604024738189312', '权限配置', '711603760899690496', 7, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth_module` VALUES ('711604059425083392', '异常信息', '711603723775905792', 4, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth_module` VALUES ('711604134998052864', '元数据管理', '711603760899690496', 0, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth_module` VALUES ('711604182171389952', '功能维护', '711603760899690496', 1, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth_module` VALUES ('711604231555125248', '组件全局配置', '711603760899690496', 2, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth_module` VALUES ('711604268704075776', '组件实例配置', '711603760899690496', 3, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth_module` VALUES ('711604305995632640', '菜单维护', '711603760899690496', 4, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth_module` VALUES ('711604336572108800', '路由维护', '711603760899690496', 5, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth_module` VALUES ('711604368734031872', '接口资源', '711603760899690496', 6, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_auth_module` VALUES ('711604523105390592', '首页', NULL, 0, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
COMMIT;


-- ----------------------------
-- Records of meta_dict
-- ----------------------------
BEGIN;
INSERT INTO `meta_dict` VALUES ('711536798504980480', NULL, '0', '权限类型', 'authType', 'VARCHAR', 0, '权限类型字典项', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711537043011932160', 'authType', '0', '菜单权限', 'menu', 'VARCHAR', 0, '声明权限应用于菜单', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711541084135755776', 'authType', '0', '页面权限', 'router', 'VARCHAR', 1, '表明权限应用于页面，描述访问页面需要的权限', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711541340051214336', 'authType', '0', '接口权限', 'api', 'VARCHAR', 2, '表示权限类别为接口，应用于接口访问控制', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711541699972829184', 'authType', NULL, '按钮权限', 'button', 'VARCHAR', 3, '表示权限类型为按钮类型，应用于页面类似于按钮等元素的显示/隐藏', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711739800452993024', NULL, '0', '角色类别', 'roleType', 'VARCHAR', 1, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711740014207307776', 'roleType', '0', '内建角色', 'buildIn', 'VARCHAR', 0, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711910939632472064', NULL, '0', '性别', 'sex', 'VARCHAR', 2, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711911746872414208', 'sex', '0', '男', '1', 'VARCHAR', 0, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711911999772168192', 'sex', '0', '女', '0', 'VARCHAR', 1, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711912389875994624', NULL, '0', '是否(布尔)', 'yn', 'VARCHAR', 3, '布尔类型的是否字典, 值为true/false', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711912939531145216', 'yn', '0', '是', 'true', 'BIT', 0, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711913403630882816', 'yn', '0', '否', 'false', 'BIT', 1, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711913498510233600', NULL, '0', '是否(字符串)', 'ynStr', 'VARCHAR', 4, '字符串类型的字典值, 值为字符串1/0', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711913885640298496', 'ynStr', '0', '是', '1', 'VARCHAR', 0, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711913912634839040', 'ynStr', '0', '否', '0', 'VARCHAR', 1, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711914148920954880', NULL, '0', '鉴权依据', 'authorizeBy', 'VARCHAR', 5, '依据什么来鉴权', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711915401012973568', 'authorizeBy', '0', '按权限判定', 'auth', 'VARCHAR', 0, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711915556827172864', 'authorizeBy', '0', '按角色判定', 'role', 'VARCHAR', 1, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711915796225462272', NULL, '0', '鉴权匹配模式', 'authorizeMatchMode', 'VARCHAR', 6, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711915985724116992', 'authorizeMatchMode', '0', '全部匹配', 'all', 'VARCHAR', 0, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711916022109704192', 'authorizeMatchMode', '0', '任一匹配', 'any', 'VARCHAR', 1, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711916243736727552', NULL, '0', '字典类型', 'dictType', 'VARCHAR', 7, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711916270408306688', 'dictType', '0', '系统字典', '0', 'VARCHAR', 0, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711916297830666240', 'dictType', '0', '业务字典', '1', 'VARCHAR', 1, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711925469871738880', NULL, '0', '数据库字段类型', 'dbFieldType', 'VARCHAR', 8, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711926885944266752', 'dbFieldType', '0', 'BIT', 'BIT', 'VARCHAR', 0, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711927029049724928', 'dbFieldType', '0', 'TEXT', 'TEXT', 'VARCHAR', 1, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711927063153610752', 'dbFieldType', '0', 'DATE', 'DATE', 'VARCHAR', 2, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711927098125717504', 'dbFieldType', '0', 'DATETIME', 'DATETIME', 'VARCHAR', 3, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711927420827078656', 'dbFieldType', '0', 'TIMESTAMP', 'TIMESTAMP', 'VARCHAR', 4, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711927420827078657', 'dbFieldType', '0', 'TIME', 'TIME', 'VARCHAR', 5, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711927420827078658', 'dbFieldType', '0', 'TINYINT', 'TINYINT', 'VARCHAR', 6, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711927420827078659', 'dbFieldType', '0', 'SMALLINT', 'SMALLINT', 'VARCHAR', 7, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711927420827078660', 'dbFieldType', '0', 'MEDIUMINT', 'MEDIUMINT', 'VARCHAR', 8, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711927420827078661', 'dbFieldType', '0', 'INT', 'INT', 'VARCHAR', 9, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711927420827078662', 'dbFieldType', '0', 'BIGINT', 'BIGINT', 'VARCHAR', 10, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711927420827078663', 'dbFieldType', '0', 'SMALLINT UNSIGNED', 'SMALLINT UNSIGNED', 'VARCHAR', 11, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711927420827078664', 'dbFieldType', '0', 'MEDIUMINT UNSIGNED', 'MEDIUMINT UNSIGNED', 'VARCHAR', 12, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711927420827078665', 'dbFieldType', '0', 'INT UNSIGNED', 'INT UNSIGNED', 'VARCHAR', 13, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711927420827078666', 'dbFieldType', '0', 'BIGINT UNSIGNED', 'BIGINT UNSIGNED', 'VARCHAR', 14, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711927420827078667', 'dbFieldType', '0', 'FLOAT', 'FLOAT', 'VARCHAR', 15, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711927420827078668', 'dbFieldType', '0', 'DOUBLE', 'DOUBLE', 'VARCHAR', 16, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711927420827078669', 'dbFieldType', '0', 'DECIMAL', 'DECIMAL', 'VARCHAR', 17, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711927420827078670', 'dbFieldType', '0', 'CHAR', 'CHAR', 'VARCHAR', 18, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711927420831272960', 'dbFieldType', '0', 'BINARY', 'BINARY', 'VARCHAR', 19, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711927420831272961', 'dbFieldType', '0', 'VARBINARY', 'VARBINARY', 'VARCHAR', 20, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711927420831272962', 'dbFieldType', '0', 'TINYBLOB', 'TINYBLOB', 'VARCHAR', 21, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711927420831272963', 'dbFieldType', '0', 'BLOB', 'BLOB', 'VARCHAR', 22, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711927420831272964', 'dbFieldType', '0', 'MEDIUMBLOB', 'MEDIUMBLOB', 'VARCHAR', 23, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711927420831272965', 'dbFieldType', '0', 'LONGBLOB', 'LONGBLOB', 'VARCHAR', 24, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711927420831272966', 'dbFieldType', '0', 'VARCHAR', 'VARCHAR', 'VARCHAR', 25, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711927420831272967', 'dbFieldType', '0', 'MEDIUMTEXT', 'MEDIUMTEXT', 'VARCHAR', 27, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711927420831272968', 'dbFieldType', '0', 'LONGTEXT', 'LONGTEXT', 'VARCHAR', 28, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_dict` VALUES ('711927420831272969', 'dbFieldType', '0', 'JSON', 'JSON', 'VARCHAR', 29, NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
COMMIT;


-- ----------------------------
-- Records of meta_menu
-- ----------------------------
BEGIN;
INSERT INTO `meta_menu` VALUES ('696347057069363200', '', '首页', b'0', b'0', 'dashboard', '/dashboard', 0, '', b'0', 'auth', '', 'any', '', 'any', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_menu` VALUES ('702190979259699200', '7bcfbe31357f48bf8c88072a18208599', '字典管理', b'0', b'0', 'dict', '/dict', 3, NULL, b'1', 'auth', 'menu:meta_dict', 'any', NULL, 'any', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_menu` VALUES ('702191170834534400', '7bcfbe31357f48bf8c88072a18208599', '异常信息', b'0', b'0', 'el-icon-warning', '/exception', 99, NULL, b'1', 'auth', 'menu:meta_exception', 'any', NULL, 'any', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_menu` VALUES ('7bcfbe31357f48bf8c88072a18208599', NULL, '系统管理', b'0', b'0', 'system-manager', '/user', 3, NULL, b'1', 'auth', 'menu:sys', 'any', NULL, 'any', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_menu` VALUES ('96eab66157be4165b9916d6f8b079dd9', '7bcfbe31357f48bf8c88072a18208599', '角色管理', b'0', b'0', 'role', '/role', 1, NULL, b'1', 'auth', 'menu:meta_role', 'any', NULL, 'any', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_menu` VALUES ('f49ce02324d94fa698a0db718e380074', '7bcfbe31357f48bf8c88072a18208599', '用户管理', b'0', b'0', 'user_manage', '/user', 0, NULL, b'1', 'auth', 'menu:meta_user', 'any', NULL, 'any', b'1', NOW(), 'SYSTEM', NULL, NULL);
COMMIT;

-- ----------------------------
-- Records of meta_role
-- ----------------------------
BEGIN;
INSERT INTO `meta_role` VALUES ('711640534434844672', 'ADMIN', '超级管理员', '拥有除【平台维护】外的所有权限', 'buildIn', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_role` VALUES ('711660585720352768', 'DEVELOPER', '开发者', '开发者角色拥有平台维护权限', 'buildIn', b'1', NOW(), 'SYSTEM', NULL, NULL);
COMMIT;

-- ----------------------------
-- Records of meta_role_auth_rela
-- ----------------------------
BEGIN;
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '694149774206701568', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '694149834445295616', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '694150160565014528', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '694183855120322560', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '694344675334688768', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '694344780561387520', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '696154835401183232', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '696154907899727872', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '696155030516011008', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '696155122442571776', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '696155278286131200', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '696155376374124544', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '696159659219881984', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '696159713649364992', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '696159771941801984', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '696159836404060160', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '696159898110660608', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '696159978653880320', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '696160054025523200', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '696160111751729152', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '696886596984770560', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '696886708112855040', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '697160516417753088', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '697160698198888448', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711640534434844672', '711618459557040128', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '694149240007561216', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '694149341149007872', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '694149424439496704', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '694149476058796032', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '694149530551193600', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '694149907166138368', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '694149960505102336', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '694330954252161024', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '694335625574354944', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '694343798075035648', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '694343885119426560', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '694344082931191808', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '694344336845967360', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '694344409273208832', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '694344875012919296', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '694344969531559936', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '696152559060127744', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '696153360272855040', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '696153730193690624', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '696153853300707328', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '696154203961298944', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '696154346861236224', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '696154466335985664', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '696154568492453888', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '696155502178078720', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '696155564840980480', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '696155654074798080', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '696155922124378112', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '696156011769237504', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '696156079964426240', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '696156165645668352', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '696176274166648832', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '711621009249275904', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '711621133711052800', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '711621340548960256', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '711623619599536128', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '711623737232986112', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '711624070503993344', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '711624225265422336', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '711624303988314112', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '711624635795509248', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '711624738526597120', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '711624886224818176', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '711625652272500736', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '711625820128546816', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '711626372585492480', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '711626548469436416', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '711626638537920512', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '711626755575779328', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '711627077702520832', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '711627199916150784', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '711627319839690752', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '711627452220313600', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
INSERT INTO `meta_role_auth_rela` VALUES ('711660585720352768', '711627513272602624', b'1', NOW(), NULL, NULL, NULL) ON DUPLICATE KEY UPDATE build_in = b'1', created_by = 'SYSTEM',  created_time = NOW();
COMMIT;


-- ----------------------------
-- Records of meta_router
-- ----------------------------
BEGIN;
INSERT INTO `meta_router` VALUES ('2b17f805d48548ef89a574bd80a0dd3a', '698272484012724224', 'UserList', '用户列表', '/user', NULL, 'UserList', '{}', b'0', b'0', 0, '{}', b'1', 'auth', 'route:meta_user', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_router` VALUES ('696345295902085120', '698272484012724224', 'Dashboard', '首页', '/dashboard', '', 'Dashboard', NULL, b'0', b'0', -99999, '{}', b'1', 'auth', NULL, 'any', NULL, 'any', '', b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_router` VALUES ('698272484012724224', NULL, 'MetaLayout', '布局组件', '/', '/dashboard', 'MetaLayout', NULL, b'1', b'0', 0, '{}', b'0', 'auth', NULL, 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_router` VALUES ('698273043436408832', NULL, 'Login', '登录', '/login', NULL, 'Login', NULL, b'0', b'0', 1, '{}', b'0', 'auth', NULL, 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_router` VALUES ('698273460404752384', NULL, 'Page401', '401', '/401', NULL, 'Page401', NULL, b'0', b'0', 100, '{}', b'0', 'auth', NULL, 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_router` VALUES ('698273579418128384', NULL, 'Page404', '404', '*', NULL, 'Page404', NULL, b'0', b'0', 999999, '{}', b'0', 'auth', NULL, 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_router` VALUES ('702189989265543168', '698272484012724224', 'DictList', '字典维护', '/dict', NULL, 'DictList', NULL, b'0', b'0', 0, '{}', b'1', 'auth', 'route:meta_dict', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_router` VALUES ('702190236326825984', '698272484012724224', 'ExceptionList', '系统异常', '/exception', NULL, 'ExceptionList', NULL, b'0', b'0', 0, '{}', b'1', 'auth', 'route:meta_exception', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
INSERT INTO `meta_router` VALUES ('e5a688069fcf43c8ab98af55c105890e', '698272484012724224', 'RoleList', '角色列表', '/role', NULL, 'RoleList', '{}', b'0', b'0', 0, '{}', b'1', 'auth', 'route:meta_role', 'any', NULL, 'any', NULL, b'1', NOW(), 'SYSTEM', NULL, NULL);
COMMIT;

