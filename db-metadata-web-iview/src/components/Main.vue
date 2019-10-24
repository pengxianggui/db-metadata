<template>
    <el-container>
        <el-header>Header 占坑</el-header>
        <el-container>
            <el-aside width="200px">
                <aside-nav></aside-nav>
            </el-aside>
            <el-main>
                <router-view></router-view>
            </el-main>
        </el-container>
    </el-container>
</template>

<script>
    import AsideNav from '@/components/feature/AsideNav'
    export default {
        name: "Main",
        components: {
            AsideNav
        }
    }
</script>

<style scoped>

</style>