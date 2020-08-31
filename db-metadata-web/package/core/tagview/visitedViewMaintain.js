export default {
    methods: {
        addView(view) {
            this.addVisitedView(view)
            this.addCachedView(view)
        },
        addVisitedView(view) {
            const {visitedViews} = this
            if (!visitedViews.some(v => v.path === view.path)) {
                visitedViews.push(
                    Object.assign({}, view, {
                        title: view.meta.title | 'no-name'
                    })
                )
            }
        },
        addCachedView(view) {
            const {cachedViews} = this
            if (!cachedViews.includes(view.name)) {
                if (view.meta && view.meta.noCache !== true) { // 除非显示声明noCache为true, 不然一律缓存
                    cachedViews.push(view.name)
                }
            }
        },
        deleteView(view) {
            const {deleteVisitedView, deleteCachedView} = this
            return new Promise(resolve => {
                deleteVisitedView(view)
                deleteCachedView(view)
                resolve({
                    visitedViews: [...this.visitedViews],
                    cachedViews: [...this.cachedViews]
                })
            })
        },
        deleteVisitedView(view) {
            return new Promise(resolve => {
                const {visitedViews} = this
                for (const [i, v] of visitedViews.entries()) {
                    if (v.path === view.path) {
                        visitedViews.splice(i, 1)
                        break
                    }
                }
                resolve([...visitedViews])
            })
        },
        deleteCachedView(view) {
            const {cachedViews} = this
            return new Promise(resolve => {
                const index = cachedViews.indexOf(view.name)
                index > -1 && cachedViews.splice(index, 1)
                resolve([...cachedViews])
            })
        },
        deleteOtherView(view) {
            const {deleteOtherVisitedView, deleteOtherCachedView} = this
            return new Promise(resolve => {
                deleteOtherVisitedView(view)
                deleteOtherCachedView(view)
                resolve({
                    visitedViews: [...this.visitedViews],
                    cachedViews: [...this.cachedViews]
                })
            })
        },
        deleteOtherVisitedView(view) {
            return new Promise(resolve => {
                const {visitedViews} = this
                this.visitedViews = visitedViews.filter(v => v.meta.affix || v.path === view.path)
                resolve([...this.visitedViews])
            })

        },
        deleteOtherCachedView(view) {
            return new Promise(resolve => {
                const {cachedViews} = this
                const index = cachedViews.indexOf(view.name)
                if (index > -1) {
                    this.cachedViews = cachedViews.slice(index, index + 1)
                } else {
                    this.cachedViews = []
                }
                resolve([...this.cachedViews])
            })
        },
        deleteAllViews(view) {
            const {deleteAllVisitedView, deleteAllCachedView} = this
            return new Promise(resolve => {
                deleteAllVisitedView(view)
                deleteAllCachedView(view)
                resolve({
                    visitedViews: [...this.visitedViews],
                    cachedViews: [...this.cachedViews]
                })
            })
        },
        deleteAllVisitedView() {
            return new Promise(resolve => {
                const {visitedViews} = this
                this.visitedViews = visitedViews.filter(v => v.meta.affix)
                resolve([...this.visitedViews])
            })
        },
        deleteAllCachedView() {
            return new Promise(resolve => {
                this.cachedViews = []
                resolve([...this.cachedViews])
            })
        },
        updateVisitedView(view) {
            const {visitedViews} = this
            for (let v of visitedViews) {
                if (v.path === view.path) {
                    v = Object.assign(v, view)
                    break
                }
            }
        }
    }
}


//
// export function addVisitedView(visitedViews, view) {
//     if (visitedViews.some(v => v.path === view.path)) return
//
//     visitedViews.push(
//         Object.assign({}, view, {
//             title: view.meta.title | 'no-name'
//         })
//     )
// }

// export function deleteVisitedView(visitedViews, view) {
//     for (const [i, v] of visitedViews.entries()) {
//         if (v.path === view.path) {
//             visitedViews.splice(i, 1)
//             break
//         }
//     }
// }

// export function deleteOtherVisitedView(visitedViews, view) {
//     const newVisitedViews = visitedViews.filter(v => v.meta.affix || v.path === view.path)
//     visitedViews.length = 0
//     visitedViews.push(...newVisitedViews)
// }

// export function deleteAllVisitedView(visitedViews) {
//     const newVisitedViews = visitedViews.filter(v => v.meta.affix)
//     visitedViews.length = 0
//     visitedViews.push(...newVisitedViews)
//
// }

// export function updateVisitedView(visitedViews, view) {
//     for (let v of visitedViews) {
//         if (v.path === view.path) {
//             v = Object.assign(v, view)
//             break
//         }
//     }
// }