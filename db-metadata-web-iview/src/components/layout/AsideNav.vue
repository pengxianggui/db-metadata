<template>
    <div :class="{'expand-menu': !isCollapse}" style="display: flex; flex-direction: column;">
        <div style="text-align: center">
            <el-button :icon="isCollapse ? 'el-icon-arrow-right':'el-icon-arrow-left'" size="mini"
                       @click="isCollapse = !isCollapse"></el-button>
        </div>
        <el-menu :default-active="$route.fullPath" :router="true" active-text-color
                 @open="handleOpen" @close="handleClose" :collapse="isCollapse" id="aside-menu">
            <el-submenu v-for="item in menus" :index="item.path" :key="item.path">
                <template #title>
                    <i :class="item.icon"></i>
                    <span v-text="item.label"></span>
                </template>
                <el-menu-item v-for="subItem in item.children" :index="subItem.path" :key="subItem.path">
                    <i :class="subItem.icon"></i>
                    <span slot="title" v-text="subItem.label"></span>
                </el-menu-item>
            </el-submenu>
        </el-menu>
    </div>
</template>

<script>
    import {URL} from '@/constant'
    import axios from 'axios'
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
            axios.get(URL.MENU_DATA).then(resp => this.menus = resp.data);
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