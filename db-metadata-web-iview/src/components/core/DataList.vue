<template>
    <el-container>
        <list>
            <list-item v-for="(item, index) in data" :key="index">
                <span>{{item.label}}</span>
            </list-item>
        </list>
    </el-container>
</template>

<script>
    export default {
        name: "DataList"
    }
</script>

<style scoped>

</style>