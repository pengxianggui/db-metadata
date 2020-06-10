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
                        <el-menu-item v-if="!subItem.hidden" :index="subItem.path" :key="subItem.path">
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

    export default {
        name: "aside-nav",
        data() {
            return {
                isCollapse: false,
                menus: []
            }
        },
        methods: {
            handleOpen(key, keyPath) {
                // console.log(key, keyPath);
            },
            handleClose(key, keyPath) {
                // console.log(key, keyPath);
            }
        },
        mounted() {
            this.$axios.safeGet(restUrl.MENU_DATA).then(resp => {
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
