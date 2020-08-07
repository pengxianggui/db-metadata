<template>
    <div class="menu-wrapper" v-if="!item.hidden">
        <template v-if="noChild(item)">
            <app-link :to="item.path"
                      :query="resolveParams(item['params'])">
                <el-menu-item :index="item.path">
                    <svg-icon :icon-class="item.icon" v-if="item.icon"></svg-icon>
                    <span slot="title">{{item.title}}</span>
                </el-menu-item>
            </app-link>
        </template>

        <el-submenu v-else ref="subMenu" :index="item.path" popper-append-to-body>
            <template #title>
                <svg-icon :icon-class="item.icon" v-if="item.icon"></svg-icon>
                <span slot="title">{{item.title}}</span>
            </template>
            <menu-item v-for="subMenu in item.children"
                       :key="subMenu.path"
                       :item="subMenu"/>
        </el-submenu>
    </div>
</template>

<script>
    import AppLink from './Link'


    export default {
        name: "MenuItem",
        components: {AppLink},
        props: {
            // route object
            item: {
                type: Object,
                required: true
            }
        },
        data() {
            return {}
        },
        methods: {
            noChild(item) {
                return !item.hasOwnProperty('children') || item['children'].length === 0;
            },
            // hasOneShowingChild(children = [], parent) {
            //     // if (parent.hasOwnProperty('hidden') && parent.hidden === false) return false;
            //
            //     const showingChildren = children.filter(item => !item.hidden);
            //
            //     // if (showingChildren.length === 1) {
            //     //     this.onlyOneChild = showingChildren[0];
            //     //     return true
            //     // }
            //
            //     if (showingChildren.length === 0) {
            //         this.onlyOneChild = {...parent, path: '', noShowingChildren: true}
            //         return true
            //     }
            //
            //     return false
            // },
            // resolvePath(routePath, params) {
            //     if (isExternal(routePath)) {
            //         return routePath
            //     }
            //     if (isExternal(this.basePath)) {
            //         return this.basePath
            //     }
            //     return path.resolve(this.basePath, routePath)
            // },
            resolveParams(pathParams) {
                let params = {};
                if (pathParams && pathParams.indexOf('=') > -1) {
                    let [key, value] = pathParams.split('=');
                    params[key] = value;
                }
                return params;
            }
        }
    }
</script>

<style scoped>

</style>