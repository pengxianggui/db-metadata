<template>
    <el-row>
        <el-col :span="6">
            <ComponentList></ComponentList>
        </el-col>
        <el-col :span="12">
            <WorkArea @select="handleSelectFormItem"></WorkArea>
        </el-col>
        <el-col :span="6">
            <ConfArea v-model="formMeta" :select-index="selectIndex"></ConfArea>
        </el-col>
    </el-row>
</template>

<script>
    export default {
        name: "FormBuilder",
        data() {
            return {
                formMeta: {},
                selectIndex: null
            }
        },
        methods: {
            handleSelectFormItem (formMeta, selectIndex) {
                this.formMeta = formMeta;
                this.selectIndex = selectIndex;
            }
        }
    }
</script>

<style scoped>
    .el-row, .el-col {
        height: 100%;
    }
</style>