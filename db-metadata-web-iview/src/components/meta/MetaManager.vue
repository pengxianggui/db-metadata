<template>
    <el-container direction="vertical">
        <el-button-group>
            <el-button @click="visible=true">导入元数据</el-button>
<!--            其他默认操作 -->
        </el-button-group>
        <table-list :meta="tableMeta" v-if="tableMeta" :data="tableData"></table-list>
        <el-dialog title="导入元数据" :visible.sync="visible">
            <meta-import v-if="formMeta" :meta="formMeta" @cancel="formCancel"></meta-import>
        </el-dialog>
    </el-container>
</template>

<script>
    import TableList from '@/components/feature/TableList'
    import MetaImport from '@/components/meta/MetaImport'
    // import mockData from '@/mockdata.js'

    export default {
        name: "meta-manager",
        data() {
            return {
                visible: false,
                tableMeta: null,
                formMeta: null,
                tableData: []
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
            getTableData(params) {
                let _this = this;
                this.$axios.get('/table/list/' + params['objectCode']).then(resp => {
                    _this.tableData = resp.data
                }).catch(resp => {
                    _this.$message.error(resp.toString())
                })
            },
            formCancel() {
                this.visible = false
            },
            formSubmit(formModel) {
                // TODO 请求TableList的数据
                this.getTableData(formModel)
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
