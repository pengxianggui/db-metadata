import {tagData} from './data'
import {isArray, isEmpty, strToArray} from "../../utils/common"
import Cache from "../../constant/cacheKey"

export function setToStorage(tag) {
    let cacheTags = getFormStorage();
    tag.meta.affix = true
    cacheTags.push(tag)
    localStorage.setItem(Cache.keyInLocal.TAG_AFFIX_KEY, JSON.stringify(cacheTags))
}

export function removeFromStorage(tag) {
    tag.meta.affix = false
    let cacheTags = getFormStorage().filter(t => t.name != tag.name && t.path !== tag.path)
    localStorage.setItem(Cache.keyInLocal.TAG_AFFIX_KEY, JSON.stringify(cacheTags))
}

export function getFormStorage() {
    let cacheTags = []
    let cacheTagsStr = localStorage.getItem(Cache.keyInLocal.TAG_AFFIX_KEY)
    if (!isEmpty(cacheTagsStr)) {
        cacheTags = strToArray(cacheTagsStr)
        if (!isArray(cacheTags)) cacheTags = []
    }
    return cacheTags
}

export function loadFromStorage() {
    let cacheTags = getFormStorage()
    cacheTags.forEach(tag => {
        addView(tag)
    })
}

/**
 * 可用于关闭当前视图: close(this.$route)
 * @param view
 * @returns {Promise<unknown>}
 */
export function close(view) {
    return new Promise((resolve, reject) => {
        deleteView(view).then(() => resolve()).catch(() => reject())
    })
}

// 获取最后一个tag
export function pop() {
    return tagData.visitedViews.pop()
}

export function addView(view) {
    addVisitedView(view)
    addCachedView(view)
}

export function addVisitedView(view) {
    const {visitedViews} = tagData
    const {fullPath, meta: {id, title, noCache, affix = false} = {}, name, params, path, query} = view
    if (!visitedViews.some(v => v.fullPath === view.fullPath)) {
        let tag = Object.assign({}, {
            fullPath: fullPath,
            meta: {
                id: id,
                title: title,
                noCache: noCache,
                affix: affix,
                order: visitedViews.length
            },
            name: name,
            params: params,
            path: path,
            query: query
        })
        visitedViews.push(tag)
    }
}

export function addCachedView(view) {
    const {cachedViews} = tagData
    if (!cachedViews.includes(view.name)) {
        if (view.meta && view.meta.noCache !== true) { // 除非显示声明noCache为true, 不然一律缓存
            cachedViews.push(view.name)
        }
    }
}

export function deleteView(view) {
    // const {deleteVisitedView, deleteCachedView} = this
    return new Promise(resolve => {
        deleteVisitedView(view)
        deleteCachedView(view)
        resolve({
            visitedViews: [...tagData.visitedViews],
            cachedViews: [...tagData.cachedViews]
        })
    })
}

export function deleteVisitedView(view) {
    return new Promise(resolve => {
        const {visitedViews} = tagData
        for (const [i, v] of visitedViews.entries()) {
            if (v.fullPath === view.fullPath) {
                visitedViews.splice(i, 1)
                break
            }
        }
        resolve([...visitedViews])
    })
}

export function deleteCachedView(view) {
    const {cachedViews} = tagData
    return new Promise(resolve => {
        const index = cachedViews.indexOf(view.name)
        index > -1 && cachedViews.splice(index, 1)
        resolve([...cachedViews])
    })
}

export function deleteOtherView(view) {
    return new Promise(resolve => {
        deleteOtherVisitedView(view)
        deleteOtherCachedView(view)
        resolve({
            visitedViews: [...tagData.visitedViews],
            cachedViews: [...tagData.cachedViews]
        })
    })
}

export function deleteOtherVisitedView(view) {
    return new Promise(resolve => {
        const {visitedViews} = tagData
        let temp = visitedViews.filter(v => {
            const {meta: {affix} = {}, fullPath} = v
            return affix || fullPath === view.fullPath
        })
        tagData.visitedViews.length = 0
        tagData.visitedViews.push(...temp)
        resolve([...tagData.visitedViews])
    })

}

export function deleteOtherCachedView(view) {
    return new Promise(resolve => {
        const {cachedViews} = tagData
        const temp = cachedViews.filter(v => {
            const {meta: {affix} = {}, fullPath} = v
            return affix || fullPath === view.fullPath
        })
        tagData.cachedViews.length = 0
        tagData.cachedViews.push(...temp)
        resolve([...tagData.cachedViews])
    })
}

export function deleteAllViews(view) {
    return new Promise(resolve => {
        deleteAllVisitedView(view)
        deleteAllCachedView(view)
        resolve({
            visitedViews: [...tagData.visitedViews],
            cachedViews: [...tagData.cachedViews]
        })
    })
}

export function deleteAllVisitedView() {
    return new Promise(resolve => {
        const {visitedViews} = tagData
        const temp = visitedViews.filter(v => {
            const {meta: {affix} = {}} = v
            return affix
        })
        tagData.visitedViews.length = 0
        tagData.visitedViews.push(...temp)
        resolve([...tagData.visitedViews])
    })
}

export function deleteAllCachedView() {
    return new Promise(resolve => {
        tagData.cachedViews.length = 0
        resolve([...tagData.cachedViews])
    })
}

export function updateVisitedView(view) {
    const {visitedViews} = tagData
    for (let v of visitedViews) {
        if (v.fullPath === view.fullPath) {
            v = Object.assign(v, view)
            break
        }
    }
}
