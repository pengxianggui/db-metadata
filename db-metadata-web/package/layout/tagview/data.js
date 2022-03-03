// 维护TagView数据
export const tagData = {
    cachedViews: [], // 缓存路由name, 表示需要keep-alive的路由: 路由name需与component name一致,缓存才有效
    visitedViews: [], // 已访问的路由对象数组, 用于TagView显示
    affixTags: [] // 固定的路由对象
}
