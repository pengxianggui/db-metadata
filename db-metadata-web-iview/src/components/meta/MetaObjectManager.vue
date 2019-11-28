<template>
    <div>
        <el-card>
            <search-panel :meta="mSpMeta" @search="mHandleSearch"></search-panel>
            <table-list :ref="mTlMeta['name']" :meta="mTlMeta" :active-data.sync="activeMData">
                <template #add-btn="{conf}">
                    <el-button v-bind="conf" @click="visible=true">创建元对象</el-button>
                </template>
            </table-list>

            <search-panel :meta="sSpMeta" @search="sHandleSearch"></search-panel>
            <table-list :ref="sTlMeta['name']" :meta="sTlMeta"></table-list>
        </el-card>

        <el-dialog title="创建元对象" :visible.sync="visible">
            <meta-import v-if="formMeta" :meta="formMeta" @cancel="visible = false" @submit="formSubmit"></meta-import>
        </el-dialog>
    </div>
</template>

<script>
    import {getTlMeta, getSpMeta} from "../core/mixins/methods"
    import MetaImport from './MetaImport'

    export default {
        name: "MetaObjectManager",
        mixins: [getTlMeta, getSpMeta],
        props: {
            R_moc: String,
            R_soc: String,
            R_linkage: {
                type: Object,
                validator: (value) => { return value.hasOwnProperty('m') && value.hasOwnProperty('s') }
            },
        },
        components: {
            MetaImport
        },
        data() {
            return {
                mObjectCode: this.R_moc,
                sObjectCode: this.R_soc,
                mSpMeta: {},
                mTlMeta: {},
                activeMData: {},
                sSpMeta: {},
                sTlMeta: {},
                sTableUrl: null, // 初始sTlMeta['data_url']的暂存变量
                visible: false,
                formMeta: {},
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
            mHandleSearch(params) {
                this.mTlRef.getData(params);
            },
            sHandleSearch(params) {
                this.sTlRef.getData(params);
            },
            getFormMeta() {
                this.$axios.get('/meta/toAdd').then(resp => {
                    this.formMeta = resp.data
                }).catch(err => {
                    this.$message.error(err.msg);
                })
            },
            formSubmit(formModel) {
                this.$axios.post(this.formMeta.action, formModel).then(resp => {
                    this.$message.success(resp.msg);
                    this.objectCode = formModel['objectCode'];
                    this.visible = false;
                    this.refreshMasterTableData();
                }).catch(err => {
                    this.$message.error(err.msg);
                })
            },
            refreshMasterTableData() {
                this.mTlRef.getData();
            },
            refreshSlaveTableData() {
                this.sTlRef.getData();
            },
            doDelete(ids, ev, row, index) {
                let objectCodes;
                if (Array.isArray(ids)) {
                    const objectCodeArr = this.mTlRef.innerChoseData.map(row => row.code);
                    objectCodes = objectCodeArr.join(',');
                } else {
                    objectCodes = row.code;
                }
                let title = '<div style="overflow: auto;">确定删除如下元对象? ' + objectCodes + '</div>';
                let url = '/meta/delete?objectCode=' + objectCodes;

                this.$confirm(title, '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning',
                    dangerouslyUseHTMLString: true
                }).then(() => {
                    this.$axios.delete(url).then(resp => {
                        this.$message.success(resp.msg);
                        this.refreshMasterTableData();
                        this.refreshSlaveTableData();
                    }).catch(err => {
                        this.$message.error(err.msg);
                    });
                });
            }
        },
        watch: {
            activeMData: {
                handler: function (newVal, oldVal) {
                    this.sTlMeta['data_url'] = this.$compile(this.sTableUrl, {
                        objectCode: newVal[this.R_linkage['m']]
                    });
                },
                deep: true
            }
        },
        created() {
            this.getFormMeta();

            // 获取主表TableList 组件meta
            this.getTlMeta(this.mObjectCode).then(resp => {
                this.mTlMeta = resp.data;
                this.$nextTick(() => {
                    this.mTlRef.doDelete = this.doDelete; // override doDelete
                });
            }).catch(err => {
                console.error('[ERROR] msg: %s', err.msg);
                this.$message.error(err.msg);
            });
            // 获取主表SearchPanel 组件meta
            this.getSpMeta(this.mObjectCode).then(resp => {
                this.mSpMeta = resp.data;
            }).catch(err => {
                console.error('[ERROR] msg: %s', err.msg);
                this.$message.error(err.msg);
            });
            // 获取从表TableList 组件meta
            this.getTlMeta(this.sObjectCode).then(resp => {
                this.sTlMeta = resp.data;
                this.sTableUrl = this.sTlMeta['data_url'] + '?' + this.R_linkage['s'] + '={objectCode}';
                this.sTlMeta['data_url'] = this.sTableUrl;
            }).catch(err => {
                console.error('[ERROR] msg: %s', err.msg);
                this.$message.error(err.msg);
            });
            // 获取从表SearchPanel 组件meta
            this.getSpMeta(this.sObjectCode).then(resp => {
                this.sSpMeta = resp.data;
            }).catch(err => {
                console.error('[ERROR] msg: %s', err.msg);
                this.$message.error(err.msg);
            });
        },
        computed: {
            mTlRef() {
                return this.$refs[this.mTlMeta['name']];
            },
            sTlRef() {
                return this.$refs[this.sTlMeta['name']];
            }
        }
    }
</script>

<style scoped>
</style>
