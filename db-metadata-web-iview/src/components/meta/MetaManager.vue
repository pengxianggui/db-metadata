<template>
    <div>
        <el-card>
            <meta-import v-if="formMeta" :meta="formMeta" @submit="formSubmit"></meta-import>
        </el-card>
        <table-list :meta="tableMeta" v-if="tableMeta" :data="tableData"></table-list>
    </div>
</template>

<script>
    import TableList from '@/components/feature/TableList'
    import MetaImport from '@/components/meta/MetaImport'
    // import mockData from '@/mockdata.js'

    export default {
        name: "meta-manager",
        data() {
            return {
                // dialogVisible: false,
                // tableMeta1: mockData.TableList,
                // formMeta1: {
                //     form_name: "formName",
                //     action: '/save', // form action (url)
                //     methods: 'POST',
                //     conf: {
                //         "label-width": '80px',
                //         size: 'medium', // medium|small|mini
                //         rules: {
                //             schemaName: [{required: true, message: '数据库必选', trigger: 'blur'}],
                //             tableName: [{required: true, message: '数据表必选', trigger: 'blur'}],
                //             objectName: [{required: true, message: '对象名必填', trigger: 'blur'}],
                //             objectCode: [{required: true, message: '对象编码必填', trigger: 'blur'}],
                //             // ...
                //         },
                //         // ...
                //     },
                //     columns: [{
                //         component_name: 'DropDownBox',
                //         name: 'schemaName',
                //         data_url: '/db/list',
                //         label: '数据库',
                //         conf: {
                //             clearable: true,
                //             placeholder: "请选择数据库..",
                //             // ...
                //         },
                //         methods: {
                //             format: function (data) {
                //                 let options = [];
                //                 for (let item in data) {
                //                     options.push({
                //                         key: item,
                //                         value: item
                //                     })
                //                 }
                //                 return options
                //             }
                //         }
                //     }, {
                //         component_name: 'DropDownBox',
                //         name: 'tableName',
                //         label: '表名',
                //         data_url: '/meta/metaObject',
                //         conf: {
                //             clearable: true,
                //             // placeholder: "请输入姓名..",
                //             // ...
                //         }
                //     }, {
                //         component_name: 'TextBox',
                //         name: 'objectName',
                //         label: '对象名',
                //         conf: {
                //             clearable: true,
                //             // placeholder: "请输入姓名..",
                //             // ...
                //         }
                //     }, {
                //         component_name: 'TextBox',
                //         name: 'objectCode',
                //         label: '对象编码',
                //         conf: {
                //             clearable: true,
                //             placeholder: "请输入姓名..",
                //             // ...
                //         }
                //     },
                //     // ...
                //     ]
                //
                // },
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
                this.$axios({
                    methods: "GET",
                    url: '/meta/fields'
                }).then(resp => {
                    if (resp.state === 'ok') {
                        this.tableMeta = resp.data
                    } else {
                        // error
                        this.$message.error(resp.msg)
                    }
                })
            },
            getFormMeta() {
                let _this = this;
                this.$axios({
                    methods: "GET",
                    url: '/meta/toAdd'
                }).then(resp => {
                    if (resp.state === 'ok') {
                        _this.formMeta = resp.data
                    } else {
                        // error
                        _this.$message.error(resp.msg)
                    }
                })
        },
            getTableData() {
                let _this = this;
                this.$axios({
                    methods: "GET",
                    url: '/table/list/meta_field'
                }).then(resp => {
                    if (resp.state === 'ok') {
                        _this.tableData = resp.data
                    } else {
                        // error
                        _this.$message.error(resp.msg)
                    }
                })
            },
            formSubmit(formModel) {
                // TODO 请求TableList的数据
                this.$axios({
                    methods: 'POST',
                    url: '/tableList',
                    data: formModel
                }).then(resp => {
                })
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
            this.getTableData()
        },
        beforeMount () {

        },
        mounted() {

        }

    }
</script>

<style scoped>

</style>
