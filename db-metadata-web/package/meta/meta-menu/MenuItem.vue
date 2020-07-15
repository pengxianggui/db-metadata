<template>
    <div class="menu-wrapper" v-if="!item.hidden">
        <template v-if="hasOneShowingChild(item.children, item)">
            <el-menu-item :index="resolvePath(onlyOneChild.path, onlyOneChild['meta']['params'])">
                <i :class="item.meta.icon"></i>
                <span slot="title">{{onlyOneChild.meta.title}}</span>
                <!--                <span slot="title" v-text="resolvePath(onlyOneChild.path, onlyOneChild['meta']['params'])"></span>-->
            </el-menu-item>
        </template>

        <el-submenu v-else ref="subMenu" :index="resolvePath(item.path)" popper-append-to-body>
            <template #title v-if="item.meta">
                <i :class="item.meta.icon"></i>
                <span slot="title">{{item.meta.title}}</span>
                <!--                <span slot="title">{{basePath}}{{item.path}}</span>-->
            </template>
            <menu-item
                    v-for="child in item.children"
                    :key="child.path"
                    :is-nest="true"
                    :item="child"
                    :base-path="resolvePath(child.path)"
                    class="nest-menu"
            />
        </el-submenu>
    </div>
</template>

<script>
    import path from "path";

    export default {
        name: "MenuItem",
        props: {
            // route object
            item: {
                type: Object,
                required: true
            },
            basePath: {
                type: String,
                default: ''
            }
        },
        data() {
            return {
                onlyOneChild: null
            }
        },
        methods: {
            hasOneShowingChild(children = [], parent) {
                // eslint-disable-next-line no-prototype-builtins
                if (parent.hasOwnProperty('hidden') && parent.hidden === false) return false;

                const showingChildren = children.filter(item => !item.hidden);

                if (showingChildren.length === 1) {
                    this.onlyOneChild = showingChildren[0];
                    return true
                }

                if (showingChildren.length === 0) {
                    this.onlyOneChild = {...parent, noShowingChildren: true}
                    return true
                }

                return false
            },
            resolvePath(routePath, params) {
                return path.resolve(this.basePath, routePath)
            },
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

<style lang="scss" scoped>
    .menu-wrapper {
        li {
            width: 100%;
            margin: 0;
            padding: 0;
        }
    }

</style>