<template>
    <div :class="{'expand-menu': !isCollapse}" style="display: flex; flex-direction: column;">
        <div style="text-align: center">
            <el-button :icon="isCollapse ? 'el-icon-arrow-right':'el-icon-arrow-left'" size="mini"
                       @click="isCollapse = !isCollapse"></el-button>
        </div>
        <el-menu :default-active="$route.fullPath" :router="true" active-text-color
                 @open="handleOpen" @close="handleClose" :collapse="isCollapse" style="flex: 1; overflow: auto;">
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
    export default {
        name: "aside-nav",
        data() {
            return {
                isCollapse: false,
                menus: [
                    {
                        path: 'business',
                        icon: 'el-icon-s-custom',
                        label: '业务模块',
                        children: [{
                            path: '/main/table?objectCode=test_table',
                            icon: 'el-icon-s-help',
                            label: '单表CRUD'
                        }, {
                            path: '/main/ms-table?featureCode=tcode_test',
                            icon: 'el-icon-s-help',
                            label: '主子表'
                        }, {
                            path: '/main/demo',
                            icon: 'el-icon-menu',
                            label: '组件概览'
                        }, {
                            path: '/main/objects',
                            icon: 'el-icon-menu',
                            label: '动态元对象'
                        }]
                    }, {
                        path: 'system',
                        icon: 'el-icon-s-grid',
                        label: '系统管理',
                        children: [{
                            path: '/main/table?objectCode=change_log',
                            icon: 'el-icon-finished',
                            label: 'ChangeLog'
                        }, {
                            path: '/main/table?featureCode=DbVersionSingleGrid',
                            icon: 'el-icon-paperclip',
                            label: 'DbVersion'
                        }]
                    }, {
                        path: 'meta',
                        icon: 'el-icon-s-tools',
                        label: '平台维护',
                        children: [{
                            path: '/main/table?objectCode=meta_feature',
                            icon: 'el-icon-warning-outline',
                            label: '功能维护'
                        }, {
                            path: '/main/meta-data',
                            icon: 'el-icon-warning',
                            label: '元数据管理'
                        }, {
                            path: '/main/meta-component',
                            icon: 'el-icon-star-off',
                            label: '组件全局配置'
                        }, {
                            path: '/main/meta-component-instance',
                            icon: 'el-icon-star-on',
                            label: '组件实例配置'
                        }, {
                            path: '/main/form-builder',
                            icon: 'el-icon-s-order',
                            label: '表单构建'
                        }, {
                            path: '/main/table?objectCode=meta_config',
                            icon: 'el-icon-s-tools',
                            label: 'MetaConfig'
                        }, {
                            path: '/main/table?objectCode=meta_dict',
                            icon: 'el-icon-collection',
                            label: '字典'
                        }]
                    },

                ]
            }
        },
        methods: {
            handleOpen(key, keyPath) {
                // console.log(key, keyPath);
            },
            handleClose(key, keyPath) {
                // console.log(key, keyPath);
            }
        }
    }
</script>

<style scoped>
    .expand-menu {
        min-width: 200px;
    }
</style>