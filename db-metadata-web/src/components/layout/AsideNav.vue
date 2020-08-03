import path from "path";
<template>
    <div :class="{'expand-menu': !isCollapse}" style="display: flex; flex-direction: column;">
        <div style="text-align: center">
            <el-button :icon="isCollapse ? 'el-icon-arrow-right':'el-icon-arrow-left'" size="mini"
                       @click="isCollapse = !isCollapse"></el-button>
        </div>
        <el-menu :default-active="$route.fullPath" :router="true" active-text-color unique-opened
                 @open="handleOpen" @close="handleClose" :collapse="isCollapse" id="aside-menu">
            <template v-for="item in menus">
                <el-submenu :index="item.path" :key="item.path" v-if="!item.hidden">
                    <template #title>
                        <i :class="item.icon"></i>
                        <span v-text="item.label"></span>
                    </template>
                    <template v-for="subItem in item.children">
                        <el-menu-item v-if="!subItem.hidden" :index="resolvePath(item.path, subItem.path)"
                                      :key="resolvePath(item.path, subItem.path)">
                            <i :class="subItem.icon"></i>
                            <span slot="title" v-text="subItem.label"></span>
                        </el-menu-item>
                    </template>
                </el-submenu>
            </template>
        </el-menu>
    </div>
</template>

<script>
    import {restUrl} from '@/../package/constant/url'
    import {routes} from '@/router'
    import path from 'path'

    export default {
        name: "aside-nav",
        data() {
            return {
                isCollapse: false,
                menus: []
            }
        },
        methods: {
            resolvePath(basePath, routePath) {
                return path.resolve(basePath, routePath)
            },
            handleOpen(key, keyPath) {
                // console.log(key, keyPath);
            },
            handleClose(key, keyPath) {
                // console.log(key, keyPath);
            }
        },
        mounted() {
            // 动态路由、菜单、功能应用 这块内容将进行整改, 为了不占用MENU_DATA这个贴切的属性名, 这里硬编码
            this.$axios.safeGet('/feature/menu').then(resp => {
                const dynamicRoutes = resp.data;
                this.menus.push(...dynamicRoutes);
                this.menus.push(...routes);
            });
        }
    }
</script>

<style scoped>
    .expand-menu {
        min-width: 200px;
    }

    #aside-menu {
        flex: 1;
        overflow-y: auto;
        overflow-x: hidden
    }
</style>
