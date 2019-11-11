<template>
    <div>
        <!--        <el-radio-group v-model="isCollapse" style="margin-bottom: 20px;">-->
        <!--            <el-radio-button :label="false">展开</el-radio-button>-->
        <!--            <el-radio-button :label="true">收起</el-radio-button>-->
        <!--        </el-radio-group>-->
        <el-menu :default-active="$route.path" class="el-menu-vertical-demo" :router="true" active-text-color
                 @open="handleOpen" @close="handleClose" :collapse="isCollapse">
<!--            <el-menu-item index="/main/meta-data">-->
<!--                <i class="el-icon-menu"></i>-->
<!--                <span slot="title">MetaData</span>-->
<!--            </el-menu-item>-->
            <el-menu-item index="/main/meta-object">
                <i class="el-icon-menu"></i>
                <span slot="title">元对象管理</span>
            </el-menu-item>
            <el-menu-item index="/main/meta-component-instance-conf">
                <i class="el-icon-menu"></i>
                <span slot="title">元对象-组件配置</span>
            </el-menu-item>
            <el-menu-item index="/main/meta-manager">
                <i class="el-icon-menu"></i>
                <span slot="title">元字段管理</span>
            </el-menu-item>
            <el-menu-item index="/main/change-log">
                <i class="el-icon-menu"></i>
                <span slot="title">ChangeLog</span>
            </el-menu-item>
            <el-menu-item index="/main/table-data">
                <i class="el-icon-menu"></i>
                <span slot="title">test_table</span>
            </el-menu-item>
            <el-menu-item index="/main/meta-config">
                <i class="el-icon-menu"></i>
                <span slot="title">MetaConfig</span>
            </el-menu-item>
            <el-menu-item index="/main/db-version">
                <i class="el-icon-menu"></i>
                <span slot="title">DbVersion</span>
            </el-menu-item>
            <el-menu-item index="/main/demo">
                <i class="el-icon-menu"></i>
                <span slot="title">Demo</span>
            </el-menu-item>
            <!--            <el-submenu index="1">-->
            <!--                <template slot="title">-->
            <!--                    <i class="el-icon-location"></i>-->
            <!--                    <span slot="title">导航一</span>-->
            <!--                </template>-->
            <!--                <el-menu-item-group>-->
            <!--                    <span slot="title">分组一</span>-->
            <!--                    <el-menu-item index="1-1">选项1</el-menu-item>-->
            <!--                    <el-menu-item index="1-2">选项2</el-menu-item>-->
            <!--                </el-menu-item-group>-->
            <!--                <el-menu-item-group title="分组2">-->
            <!--                    <el-menu-item index="1-3">选项3</el-menu-item>-->
            <!--                </el-menu-item-group>-->
            <!--                <el-submenu index="1-4">-->
            <!--                    <span slot="title">选项4</span>-->
            <!--                    <el-menu-item index="1-4-1">选项1</el-menu-item>-->
            <!--                </el-submenu>-->
            <!--            </el-submenu>-->
            <!--            <el-menu-item index="2">-->
            <!--                <i class="el-icon-menu"></i>-->
            <!--                <span slot="title">导航二</span>-->
            <!--            </el-menu-item>-->
            <!--            <el-menu-item index="3" disabled>-->
            <!--                <i class="el-icon-document"></i>-->
            <!--                <span slot="title">导航三</span>-->
            <!--            </el-menu-item>-->
            <!--            <el-menu-item index="4">-->
            <!--                <i class="el-icon-setting"></i>-->
            <!--                <span slot="title">导航四</span>-->
            <!--            </el-menu-item>-->
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

</style>