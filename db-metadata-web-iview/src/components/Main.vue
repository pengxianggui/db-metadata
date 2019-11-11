<template>
    <el-container style="display: flex; flex-direction: column; height: 100%;">
        <el-header>
            <header-bar></header-bar>
        </el-header>
        <el-container style="flex: 1; overflow: auto;">
            <el-aside width="200px">
                <aside-nav></aside-nav>
            </el-aside>
            <el-main style="height: auto; overflow: auto;">
                <router-view></router-view>
            </el-main>
        </el-container>
    </el-container>
</template>

<script>
    import AsideNav from '@/components/layout/AsideNav'
    import HeaderBar from '@/components/layout/HeaderBar'

    export default {
        name: "Main",
        components: {
            AsideNav,
            HeaderBar
        }
    }
</script>

<style scoped>

</style>