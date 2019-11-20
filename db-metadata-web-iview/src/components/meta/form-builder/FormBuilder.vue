<template>
    <el-row :gutter="10">
        <el-col :span="5">
            <ComponentList></ComponentList>
        </el-col>
        <el-col :span="12">
            <WorkArea @select="handleSelectFormItem" :data="formMeta"></WorkArea>
        </el-col>
        <el-col :span="7">
            <ConfArea v-model="formMeta" :select-index="selectIndex"></ConfArea>
        </el-col>
    </el-row>
</template>

<script>
    export default {
        name: "FormBuilder",
        props: {
            R_oc: String
        },
        data() {
            return {
                objectCode: this.R_oc,
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