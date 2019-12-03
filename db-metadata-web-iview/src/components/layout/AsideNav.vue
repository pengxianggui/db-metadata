<template>
    <div :class="{'expand-menu': !isCollapse}">
        <div style="text-align: center">
            <el-button :icon="isCollapse ? 'el-icon-arrow-right':'el-icon-arrow-left'" size="mini"
                       @click="isCollapse = !isCollapse"></el-button>
        </div>
        <el-menu :default-active="$route.fullPath" :router="true" active-text-color
                 @open="handleOpen" @close="handleClose" :collapse="isCollapse">
            <el-submenu index="business">
                <template #title>
                    <i class="el-icon-s-custom"></i>
                    <span>业务模块</span>
                </template>
                <el-menu-item index="/main/table?objectCode=test_table">
                    <i class="el-icon-s-help"></i>
                    <span slot="title">单表CRUD</span>
                </el-menu-item>
                <el-menu-item index="/main/demo">
                    <i class="el-icon-menu"></i>
                    <span slot="title">组件概览</span>
                </el-menu-item>
            </el-submenu>
            <el-submenu index="system">
                <template #title>
                    <i class="el-icon-s-grid"></i>
                    <span>系统管理</span>
                </template>
                <el-menu-item index="/main/table?objectCode=change_log">
                    <i class="el-icon-finished"></i>
                    <span slot="title">ChangeLog</span>
                </el-menu-item>
                <el-menu-item index="/main/table?objectCode=db_version">
                    <i class="el-icon-paperclip"></i>
                    <span slot="title">DbVersion</span>
                </el-menu-item>
            </el-submenu>
            <el-submenu index="meta">
                <template #title>
                    <i class="el-icon-s-tools"></i>
                    <span>平台维护</span>
                </template>
                <el-menu-item index="/main/meta-data">
                    <i class="el-icon-warning"></i>
                    <span slot="title">元数据管理</span>
                </el-menu-item>
                <el-menu-item index="/main/meta-component">
                    <i class="el-icon-star-off"></i>
                    <span slot="title">组件全局配置</span>
                </el-menu-item>
                <el-menu-item index="/main/meta-component-instance">
                    <i class="el-icon-star-on"></i>
                    <span slot="title">组件实例配置</span>
                </el-menu-item>
                <el-menu-item index="/main/form-builder">
                    <i class="el-icon-s-order"></i>
                    <span slot="title">表单构建</span>
                </el-menu-item>
                <el-menu-item index="/main/table?objectCode=meta_config">
                    <i class="el-icon-s-tools"></i>
                    <span slot="title">MetaConfig</span>
                </el-menu-item>
                <el-menu-item index="/main/table?objectCode=meta_dict">
                    <i class="el-icon-collection"></i>
                    <span slot="title">字典</span>
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
            }
        },
        methods: {
            handleOpen(key, keyPath) {
                console.log(key, keyPath);
            },
            handleClose(key, keyPath) {
                console.log(key, keyPath);
            }
        }
    }
</script>

<style scoped>
    .expand-menu {
        min-width: 200px;
    }
</style>