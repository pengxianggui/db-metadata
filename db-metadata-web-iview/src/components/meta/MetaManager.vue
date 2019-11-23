<template>
    <el-container direction="vertical">
        <el-button-group style="display: flex; margin: 5px 0;">
            <span style="flex: 1"></span>
            <drop-down-box :ref="objMeta['name']" v-model="metaObj" :meta="objMeta"
                           @change="refreshTableData()"></drop-down-box>
            <el-button type="primary" plain @click="visible=true">创建元对象</el-button>
        </el-button-group>
        <TableList :ref="tableMeta['name']" :meta="tableMeta" v-if="tableMeta && tableMeta['data_url']">

            <template #dialog-body="{meta}">
                <FormTmpl ref="form" :meta="meta" @ok="ok" @cancel="cancel">
                    <template #form-item-config="{columnMeta, value}">
                        <el-form-item prop="config">
                            <MiniFormConfigDemo ref="configMiniForm" :model="value" @submit="submit"></MiniFormConfigDemo>
                        </el-form-item>
                    </template>
                </FormTmpl>
            </template>

        </TableList>
        <el-dialog title="创建元数据" :visible.sync="visible">
            <meta-import v-if="formMeta" :meta="formMeta" @cancel="visible = false" @submit="formSubmit"></meta-import>
        </el-dialog>
    </el-container>
</template>

<script>
    import {DEFAULT} from '@/constant'
    import MetaImport from '@/components/meta/MetaImport'
    import MiniFormConfigDemo from '@/components/demo/MiniFormConfigDemo'

    export default {
        name: "meta-manager",
        props: {
            R_oc: String
        },
        data() {
            return {
                visible: false,
                tableMeta: {},
                formMeta: {},
                tableUrl: '/table/list?objectCode=meta_field',
                objMeta: { // 下拉选元对象
                    "name": "meta",
                    "data_url": "/table/list/meta_object",
                    "group": false,
                    "conf": {
                        'clearable': true,
                        'filterable': true
                    },
                    'behavior': {
                        'format': function (params) {
                            let kvs = [];
                            for (let i = 0; i < params.length; i++) {
                                kvs.push({
                                    key: params[i].code,
                                    value: params[i].code
                                })
                            }
                            return kvs;
                        }
                    }
                },
                metaObj: this.R_oc, // 元对象
            }
        },
        methods: {
            submit(params) {
                this.$refs['form']['model']['config'] = params; // 通过ref 硬干预 替换插槽表单中的config字段
            },
            ok(params) {
                let tableRef = this.$refs[this.tableMeta['name']];
                tableRef.dialogVisible = false;
                tableRef.getData();
            },
            cancel(params) {
                let tableRef = this.$refs[this.tableMeta['name']];
                tableRef.dialogVisible = false;
            },
            getTableMeta() {
                this.$axios.get('/meta/fields').then(resp => {
                    this.$merge(resp.data, DEFAULT.TableList); // 确保 resp.data 中 含有data_url 属性
                    this.tableMeta = resp.data;
                    this.tableMeta['data_url'] = this.tableUrl + "&object_code=''";
                }).catch(err => {
                    this.$message.error(err.msg);
                })
            },
            getFormMeta() {
                this.$axios.get('/meta/toAdd').then(resp => {
                    this.formMeta = resp.data
                }).catch(err => {
                    this.$message.error(err.msg);
                })
            },
            refreshTableData() {
                this.tableMeta['data_url']= this.tableUrl + '&object_code=' + this.metaObj;
                this.$nextTick(() => {
                    this.$refs[this.tableMeta['name']].getData();
                })
            },
            updateSon() {
                this.refreshTableData();
                this.$refs[this.objMeta.name].getOptions();
            },
            formSubmit(formModel) {
                this.$axios.post(this.formMeta.action, formModel).then(resp => {
                    this.$message.success(resp.msg);
                    this.metaObj = formModel['objectCode'];
                    this.visible = false;
                    this.updateSon();
                }).catch(err => {
                    this.$message.error(err.msg);
                })
            },
        },
        components: {
            MetaImport,
            MiniFormConfigDemo
        },
        created() {
            this.getFormMeta();
            this.getTableMeta();
        },
        mounted() {
            if (this.R_oc !== undefined) {
                this.refreshTableData();
            }
        }
    }
</script>

<style scoped>

</style>
