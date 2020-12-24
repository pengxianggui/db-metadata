<template>
  <div v-if="!item.hidden &&  $hasRoles(item.roles)">
    <app-link v-if="noChild(item)" :to="item.path" :query="resolveParams(item['params'])">
        <el-menu-item :index="item.path">
            <svg-icon :value="item.icon" v-if="item.icon"></svg-icon>
            <span slot="title">{{item.title}}</span>
        </el-menu-item>
    </app-link>

    <el-submenu v-else ref="subMenu" :index="item.path" popper-append-to-body>
        <template slot="title">
            <svg-icon :value="item.icon" v-if="item.icon"></svg-icon>
            <span slot="title">{{item.title}}</span>
        </template>
        <template v-for="subMenu in item.children">
          <menu-item :key="subMenu.path"
                     :item="subMenu"></menu-item>
        </template>
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