<template>
    <el-container direction="vertical">
        <el-button-group>
            <el-button type="primary" plain @click="visible=true">导入元对象</el-button>
            <drop-down-box v-model="metaObj" :meta="objMeta"
                           @change="tableMeta.data_url = '/table/list/' + metaObj"></drop-down-box>
<!--            其他默认操作 -->
        </el-button-group>
        <table-list :meta="tableMeta" v-if="tableMeta"></table-list>
        <el-dialog title="导入元数据" :visible.sync="visible">
            <meta-import v-if="formMeta" :meta="formMeta" @cancel="formCancel" @submit="formSubmit"></meta-import>
        </el-dialog>
        <div style="display: flex">
            <span style="flex: 1"></span>
            <el-button type="primary" @click="saveMeta">导入元数据</el-button>
        </div>
    </el-container>
</template>

<script>
    import TableList from '@/components/feature/TableList'
    import MetaImport from '@/components/meta/MetaImport'

    export default {
        name: "meta-manager",
        data() {
            return {
                visible: false,
                tableMeta: null,
                formMeta: null,
                objMeta: {
                    "name": "meta",
                    "data_url": "/table/list/meta_object",
                    "group": false,
                    "conf": {
                        clearable: true,
                        filterable: true
                    },
                    behavior: {
                        format: function (params) {
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
        props: {
            meta: {
                type: Object,
                default: function () {
                    return {}
                }
            }
        },
        methods: {
            getTableMeta() {
                let _this = this;
                this.$axios.get('/meta/fields').then(resp => {
                    _this.tableMeta = resp.data
                }).catch(resp => {
                    _this.$message.error(resp.toString())
                })
            },
            getFormMeta() {
                let _this = this;
                this.$axios.get('/meta/toAdd').then(resp => {
                    _this.formMeta = resp.data
                }).catch(resp => {
                    _this.$message.error(resp.toString())
                })
            },
            formCancel() {
                this.visible = false
            },
            formSubmit(formModel) {
                let _this = this;
                this.$axios.post(_this.formMeta.action, formModel).then(resp => {
                    _this.$message({type: 'success', message: resp.msg || '操作成功'})
                    // _this.getTableData(formModel)
                    _this.tableMeta['data_url'] = '/table/list/' + formModel['objectCode'];
                    _this.visible = false;
                }).catch(resp => {
                    _this.$message({type: 'error', message: resp.msg})
                })
            },
            saveMeta () {
                // TODO save meta data
            }
        },
        components: {
            TableList,
            MetaImport
        },
        beforeCreate() {
        },
        created() {
            this.getFormMeta();
            this.getTableMeta();
        },
        beforeMount() {

        },
        mounted() {

        }

    }
</script>

<style scoped>

</style>
