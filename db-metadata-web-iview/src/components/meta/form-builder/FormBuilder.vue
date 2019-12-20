<template>
    <el-container style="height: 100%; display: flex; overflow: hidden;">
        <div style="flex: 2">
            <ComponentList></ComponentList>
        </div>
        <div style="flex: 5">
            <WorkArea @select="handleSelectFormItem" v-model="formMeta">
                <template #operation-extend>
                    <drop-down-box @change="loadConf(objectCode)" placeholder="选择元对象"
                                   data-url="/table/list?objectCode=meta_object&fs=code,table_name&code->key&table_name->value"
                                   v-model="objectCode" filterable></drop-down-box>
                </template>
            </WorkArea>
        </div>
        <div style="flex: 3">
            <ConfArea v-model="formMeta" :select-index="selectIndex"></ConfArea>
        </div>
    </el-container>
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
        mounted() {
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