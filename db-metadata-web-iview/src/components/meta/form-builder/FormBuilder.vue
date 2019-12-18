<template>
    <el-row :gutter="10">
        <el-col :span="5">
            <ComponentList></ComponentList>
        </el-col>
        <el-col :span="12">
            <WorkArea @select="handleSelectFormItem" v-model="formMeta">
                <template #operation-extend>
                    <drop-down-box @change="loadConf(objectCode)" placeholder="选择元对象"
                                   data-url="/table/list?objectCode=meta_object&fs=code,table_name&code->key&table_name->value"
                                   v-model="objectCode" filterable></drop-down-box>
                </template>
            </WorkArea>
        </el-col>
        <el-col :span="7">
            <ConfArea v-model="formMeta" :select-index="selectIndex"></ConfArea>
        </el-col>
    </el-row>
</template>

<script>
    import utils from '@/utils'
    import {DEFAULT, URL} from '@/constant'
    import ComponentList from './ComponentList'
    import WorkArea from './WorkArea'
    import ConfArea from './ConfArea'

    export default {
        name: "FormBuilder",
        components: {ComponentList, WorkArea, ConfArea},
        data() {
            return {
                objectCode: this.$route.query.objectCode,
                formMeta: this.$merge({}, DEFAULT.FormTmpl),
                selectIndex: null
            }
        },
        methods: {
            setInitState() {
                this.formMeta = this.$merge({}, DEFAULT.FormTmpl);
            },
            handleSelectFormItem(formMeta, selectIndex) {
                this.formMeta = formMeta;
                this.selectIndex = selectIndex;
            },
            loadConf(objectCode) {
                const url = this.$compile(URL.COMPONENT_INSTANCE_META, {
                    componentCode: 'FormTmpl',
                    objectCode: objectCode
                });
                this.$axios.safeGet(url).then(resp => {
                    let formMeta = resp.data;
                    this.$reverseMerge(this.formMeta, formMeta, true);
                }).catch(err => {
                    this.$message.error(err.msg);
                    this.setInitState();
                })
            }
        },
        created() {
            const objectCode = this.objectCode;
            if (!utils.isEmpty(objectCode)) {
                this.loadConf(objectCode);
            }
        }
    }
</script>

<style scoped>
    .el-row, .el-col {
        height: 100%;
    }
</style>