/**
 * 当前路由和菜单推荐采用 一对一 关系.
 * 而之前采用的方式是一对多的关系，例如：将单表的组件渲染菜单都映射到 单表对应的路由: /main/table，通过fc参数差异化组件数据内容
 * 但是这样会带来一个问题：当有tagViews标签页时, 无法做缓存cache, 因为两个菜单指向的路由其实是同一个, 缓存keep-alive根据路由的name来的
 *
 * 因此建议路由和菜单一对一 对应（菜单是少于路由的，因为有些路由无需在菜单中出现）
 * @type {({redirect: string, path: string, component: string, children: ({path: string, component: string, meta: {cache: boolean, roles: []}, name: string}|{path: string, component: string, meta: {cache: boolean, roles: []}, name: string}|{path: string, component: string, meta: {cache: boolean, roles: []}, name: string}|{path: string, component: string, meta: {cache: boolean, roles: []}, name: string}|{path: string, component: string, meta: {cache: boolean, roles: []}, name: string})[], name: string}|{path: string, component: string, children: ({path: string, component: string, name: string}|{path: string, component: string, name: string}|{path: string, component: string, name: string}|{path: string, component: string, name: string}|{path: string, component: string, name: string})[], name: string})[]}
 */
export const routes = [
    {
        "redirect": "/ops/dashboard",
        "components": null,
        "pid": null,
        "remark": "运维管理下的跟路由",
        "cn": "运维控制台",
        "created_by": "db-meta-web-devUser",
        "path": "/ops",
        "component": "Layout",
        "children": [
            {
                "redirect": null,
                "components": null,
                "pid": "3d82391ef09f4f23a6f629d00b4d849e",
                "remark": "系统字典维护",
                "cn": "系统字典维护",
                "created_by": "db-meta-web-devUser",
                "parentId": "3d82391ef09f4f23a6f629d00b4d849e",
                "path": "/ops/sys-dict",
                "component": "TreeSingleGridTmpl",
                "children": [],
                "meta": "null",
                "name": "SysDict",
                "updated_by": "null",
                "id": "17b9bbfa93be437d993f3a1aab5da990"
            },
            {
                "redirect": null,
                "components": null,
                "pid": "3d82391ef09f4f23a6f629d00b4d849e",
                "remark": "业务字典树",
                "cn": "业务字典维护",
                "created_by": "db-meta-web-devUser",
                "parentId": "3d82391ef09f4f23a6f629d00b4d849e",
                "path": "/ops/biz-dict",
                "component": "TreeSingleGridTmpl",
                "children": [],
                "meta": null,
                "name": "BizDict",
                "updated_by": "null",
                "id": "94a2f2533fba4c47a40e2ee3f7e90319"
            },
            {
                "redirect": null,
                "components": null,
                "pid": "3d82391ef09f4f23a6f629d00b4d849e",
                "remark": "服务方案审核",
                "cn": "服务方案审核",
                "created_by": "db-meta-web-devUser",
                "parentId": "3d82391ef09f4f23a6f629d00b4d849e",
                "path": "/ops/service-plan",
                "component": "TextBox",
                "children": [],
                "meta": null,
                "name": "ServicePlanCertify",
                "updated_by": null,
                "id": "d32853025279481db546fef689cc8e62"
            }
        ],
        "meta": null,
        "name": "OpsManage",
        "updated_by": null,
        "id": "3d82391ef09f4f23a6f629d00b4d849e"
    }
]

export default [
    {
        url: '/router',
        type: 'get',
        disable: true,
        response: config => {
            console.log('mock: /route/tree');
            return {
                code: 200,
                state: 'ok',
                data: routes
            }
        }
    }
]