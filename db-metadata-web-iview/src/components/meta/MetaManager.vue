<template>
    <el-container direction="vertical">
        <el-button-group style="display: flex;">
            <span style="flex: 1"></span>
            <drop-down-box :ref="objMeta['name']" v-model="metaObj" :meta="objMeta"
                           @change="refreshTableData()"></drop-down-box>
            <el-button type="primary" plain @click="visible=true">创建元对象</el-button>
        </el-button-group>
        <table-list :ref="tableMeta['name']" :meta="tableMeta" v-if="tableMeta && tableMeta['data_url']"></table-list>
        <el-dialog title="创建元数据" :visible.sync="visible">
            <meta-import v-if="formMeta" :meta="formMeta" @cancel="visible = false" @submit="formSubmit"></meta-import>
        </el-dialog>
    </el-container>
</template>

<script>
    import {DEFAULT} from '@/constant'
    import MetaImport from '@/components/meta/MetaImport'

    export default {
        name: "meta-manager",
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
                metaObj: null, // 元对象
            }
        },
        methods: {
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
                this.$refs[this.tableMeta['name']].getData();
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
            MetaImport
        },
        created() {
            this.getFormMeta();
            this.getTableMeta();
        }
    }
</script>

<style scoped>

</style>
