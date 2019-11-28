<template>
    <el-container direction="vertical">
        <el-button-group style="display: flex; margin: 5px 0;">
<!--            <span style="flex: 1"></span>-->
            <drop-down-box :ref="objMeta['name']" v-model="objectCode" :meta="objMeta"
                           @change="refreshTableData()"></drop-down-box>
            <el-button size="small" type="primary" plain @click="visible=true">创建元对象</el-button>
        </el-button-group>
        <search-panel :ref="spMeta['name']" :meta="spMeta" @search="handleSearch"></search-panel>
        <table-list :ref="tlMeta['name']" :meta="tlMeta" v-if="tlMeta && tlMeta['data_url']">

            <template #dialog-body="{meta}">
                <form-tmpl ref="form" :meta="meta" @ok="ok" @cancel="cancel">
                    <template #form-item-config="{columnMeta, value}">
                        <el-form-item prop="config">
                            <MiniFormConfigDemo ref="configMiniForm" :model="value" @submit="submit"></MiniFormConfigDemo>
                        </el-form-item>
                    </template>
                </form-tmpl>
            </template>

        </table-list>
        <el-dialog title="创建元数据" :visible.sync="visible">
            <meta-import v-if="formMeta" :meta="formMeta" @cancel="visible = false" @submit="formSubmit"></meta-import>
        </el-dialog>
    </el-container>
</template>

<script>
    // import {DEFAULT} from '@/constant'
    import MetaImport from '@/components/meta/MetaImport'
    import MiniFormConfigDemo from '@/components/demo/MiniFormConfigDemo'
    import {getTlMeta, getSpMeta} from "../core/mixins/methods"
    import FormTmpl from "../core/FormTmpl";

    export default {
        name: "meta-manager",
        mixins: [getTlMeta, getSpMeta],
        props: {
            R_oc: String,
            R_objC: String
        },
        components: {
            FormTmpl,
            MetaImport,
            MiniFormConfigDemo
        },
        data() {
            return {
                objectCode: this.R_objC,
                tlMeta: {},
                spMeta: {},

                visible: false,
                formMeta: {},
                tableUrl: null, // 初始tlMeta['data_url']的暂存变量
                objMeta: { // 下拉选元对象
                    "data_url": "/table/list/meta_object",
                    "conf": {
                        'clearable': true,
                        'filterable': true,
                        'size': 'small'
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
                }
            }
        },
        methods: {
            submit(params) {
                this.$refs['form']['model']['config'] = params; // 通过ref 硬干预 替换插槽表单中的config字段
            },
            ok() {
                this.ref.dialogVisible = false;
                this.ref.getData();
            },
            cancel() {
                let tableRef = this.$refs[this.tlMeta['name']];
                tableRef.dialogVisible = false;
            },
            getFormMeta() {
                this.$axios.get('/meta/toAdd').then(resp => {
                    this.formMeta = resp.data
                }).catch(err => {
                    this.$message.error(err.msg);
                })
            },
            refreshTableData() {
                this.tlMeta['data_url']= this.$compile(this.tableUrl, {
                    objectCode: this.objectCode
                })
            },
            updateSon() {
                this.refreshTableData();
                this.$refs[this.objMeta.name].getOptions();
            },
            formSubmit(formModel) {
                this.$axios.post(this.formMeta.action, formModel).then(resp => {
                    this.$message.success(resp.msg);
                    this.objectCode = formModel['objectCode'];
                    this.visible = false;
                    this.updateSon();
                }).catch(err => {
                    this.$message.error(err.msg);
                })
            },
            handleSearch(params) {
                this.ref.getData(params);
            }
        },
        created() {
            this.getFormMeta();

            this.getTlMeta(this.R_oc).then(resp => {
                this.tlMeta = resp.data;
                this.tableUrl = this.tlMeta['data_url'] + "?object_code={objectCode}";
                this.tlMeta['data_url'] = this.tableUrl;
            }).catch(err => {
                console.error('[ERROR] msg: %s', err.msg);
                this.$message.error(err.msg);
            });

            this.getSpMeta(this.R_oc).then(resp => {
                this.spMeta = resp.data;
            }).catch(err => {
                console.error('[ERROR] msg: %s', err.msg);
                this.$message.error(err.msg);
            });
        },
        mounted() {
            if (this.R_objC !== undefined) {
                this.refreshTableData();
            }
        },
        computed: {
            ref() {
                return this.$refs[this.tlMeta['name']];
            }
        }
    }
</script>

<style scoped>

</style>
