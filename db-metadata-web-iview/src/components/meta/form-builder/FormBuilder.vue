<template>
    <el-row>
        <el-col :span="5">
            <ComponentList></ComponentList>
        </el-col>
        <el-col :span="11">
            <WorkArea @select="handleSelectFormItem"></WorkArea>
        </el-col>
        <el-col :span="7">
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

    .el-col {
        margin-left: 10px;
    }
</style>