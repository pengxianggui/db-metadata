<template>
    <el-container style="display: flex; flex-direction: column; height: 100%;">
        <el-header>
            <header-bar></header-bar>
        </el-header>
        <el-container style="flex: 1; overflow: auto;">
            <aside-nav style="overflow: auto;"></aside-nav>
            <el-main style="height: auto; overflow: auto;">
<!--                <tag-nav></tag-nav>-->
                <router-view :key="new Date().getTime()"></router-view>
            </el-main>
        </el-container>
    </el-container>
</template>

<script>
    import AsideNav from './layout/AsideNav'
    import HeaderBar from './layout/HeaderBar'
    import TagNav from './layout/TagNav'

    export default {
        name: "Main",
        components: {
            AsideNav,
            HeaderBar,
            TagNav
        }
    }
</script>

<style scoped>
    .el-main {
        margin: 0px 5px 0px 5px;
        padding: 0px;
    }
</style>