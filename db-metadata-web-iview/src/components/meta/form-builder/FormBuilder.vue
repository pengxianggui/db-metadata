<template>
    <el-container style="height: 100%; display: flex; overflow: hidden;">
        <div style="flex: 2">
            <ComponentList></ComponentList>
        </div>
        <div style="flex: 5;margin-left: 5px">
            <WorkArea @select="handleSelectFormItem" v-model="formMeta">
                <template #operation-extend>
                    <drop-down-box placeholder="选择元对象"
                                   @clear="handleClear"
                                   data-url="/table/list?objectCode=meta_object&fs=code,table_name&code->key&table_name->value"
                                   v-model="objectCode" filterable></drop-down-box>
                </template>
            </WorkArea>
        </div>
        <div style="flex: 3;margin-left: 5px">
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
        props: {
            oc: String
        },
        data() {
            return {
                formMeta: this.$merge({}, DEFAULT.FormTmpl),
                selectIndex: null
            }
        },
        methods: {
            handleClear() {
                this.objectCode = null;
                this.setInitState();
            },
            setInitState() {
                this.formMeta = this.$merge({}, DEFAULT.FormTmpl);
            },
            handleSelectFormItem(formMeta, selectIndex) {
                this.formMeta = formMeta;
                this.selectIndex = selectIndex;
            },
            loadConf(objectCode) {
                if (utils.isEmpty(objectCode)) return;
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
            this.loadConf(objectCode);
        },
        computed: {
            objectCode: {
                get() {
                    const objectCode = utils.assertUndefined(this.oc, this.$route.query.objectCode);
                    this.loadConf(objectCode);
                    return objectCode;
                },
                set(val) {
                    this.$emit('oc-change', val);
                }
            }
        }
    }
</script>

<style scoped>
    .el-row, .el-col {
        height: 100%;
    }
</style>