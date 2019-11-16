<template>
    <el-row>
        <el-col :span="6">
            <ComponentList></ComponentList>
        </el-col>
        <el-col :span="12">
            <WorkArea></WorkArea>
        </el-col>
        <el-col :span="6">
            <ConfArea></ConfArea>
        </el-col>
    </el-row>
</template>

<script>
    export default {
        name: "FormBuilder",
    }
</script>

<style scoped>
    .el-row, .el-col {
        height: 100%;
    }
</style>