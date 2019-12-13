<template>
    <el-row :gutter="10">
        <el-col :span="5">
            <ComponentList></ComponentList>
        </el-col>
        <el-col :span="12">
            <WorkArea @select="handleSelectFormItem" v-model="formMeta">
                <template #operation-extend>
                    <drop-down-box @change="loadConf(objectCode)"
                        data-url="/table/list?objectCode=meta_object&fs=code,table_name&code->key&table_name->value"
                        v-model="objectCode"></drop-down-box>
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
        props: {
            R_oc: String
        },
        components: {ComponentList, WorkArea, ConfArea},
        data() {
            return {
                objectCode: this.R_oc,
                formMeta: this.$merge({}, DEFAULT.FormTmpl),
                selectIndex: null
            }
        },
        methods: {
            handleSelectFormItem (formMeta, selectIndex) {
                this.formMeta = formMeta;
                this.selectIndex = selectIndex;
            },
            loadConf(objectCode) {
                let self = this;
                const url = this.$compile(URL.COMPONENT_INSTANCE_META, {
                    componentCode: 'FormTmpl',
                    objectCode: objectCode
                });
                self.$axios.safeGet(url).then(resp => {
                    let formMeta = resp.data;
                    self.$reverseMerge(self.formMeta, formMeta, true);
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