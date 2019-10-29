<template>
    <el-container direction="vertical">
        <el-button-group>
            <el-button type="primary" plain @click="visible=true">创建元对象</el-button>
            <drop-down-box v-model="metaObj" :meta="objMeta"
                           @change="refreshTableDataUrl()"></drop-down-box>
        </el-button-group>
        <table-list :meta="tableMeta" v-if="tableMeta"></table-list>
        <el-dialog title="创建元数据" :visible.sync="visible">
            <meta-import v-if="formMeta" :meta="formMeta" @cancel="visible = false" @submit="formSubmit"></meta-import>
        </el-dialog>
    </el-container>
</template>

<script>
    import {DEFAULT} from '@/constant'
    import TableList from '@/components/feature/TableList'
    import MetaImport from '@/components/meta/MetaImport'

    export default {
        name: "meta-manager",
        data() {
            return {
                visible: false,
                tableMeta: null,
                formMeta: null,
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
                let _this = this;
                this.$axios.get('/meta/fields').then(resp => {
                    _this.$merge(resp.data, DEFAULT.TableList); // 确保 resp.data 中 含有data_url 属性
                    _this.tableMeta = resp.data;
                    _this.tableMeta['data_url'] = _this.tableUrl + "&object_code=''";
                }).catch(resp => {
                    _this.$message({type: 'error', message: resp.msg})
                })
            },
            getFormMeta() {
                let _this = this;
                this.$axios.get('/meta/toAdd').then(resp => {
                    _this.formMeta = resp.data
                }).catch(resp => {
                    _this.$message({type: 'error', message: resp.msg})
                })
            },
            refreshTableDataUrl() {
                this.tableMeta['data_url']= this.tableUrl + '&object_code=' + this.metaObj;
            },
            formSubmit(formModel) {
                let _this = this;
                this.$axios.post(_this.formMeta.action, formModel).then(resp => {
                    _this.$message({type: 'success', message: resp.msg || '操作成功'});
                    _this.metaObj = formModel['objectCode'];
                    _this.refreshTableDataUrl();
                    _this.visible = false;
                }).catch(resp => {
                    _this.$message({type: 'error', message: resp.msg})
                })
            },
        },
        components: {
            TableList,
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
