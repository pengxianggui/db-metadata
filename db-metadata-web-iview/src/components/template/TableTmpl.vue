<template>
    <div>
<!--        <el-button @click="importMeta">导入元对象</el-button>-->
<!--        <el-dialog title="选择元对象" :visible.sync="dialogVisible">-->
<!--            <form-tmpl :meta="formMeta"></form-tmpl>-->
<!--        </el-dialog>-->
        <form-tmpl :meta="formMeta"></form-tmpl>
        <table-list :meta="tableMeta"></table-list>
    </div>
</template>

<script>
    import TableList from '@/components/feature/TableList'
    import FormTmpl from '@/components/template/FormTmpl'
    import mockData from '@/mockdata.js'
    export default {
        name: "table-tmpl",
        data () {
            return {
                // dialogVisible: false,
                tableMeta: mockData.TableList,
                formMeta: {
                    form_name: "formName",
                    action: '/save', // form action (url)
                    methods: 'POST',
                    conf: {
                        "label-width": '80px',
                        size: 'medium', // medium|small|mini
                        rules: {
                            schemaName: [{required: true, message: '数据库必选', trigger: 'blur'}],
                            tableName: [{required: true, message: '数据表必选', trigger: 'blur'}],
                            objectName: [{required: true, message: '对象名必填', trigger: 'blur'}],
                            objectCode: [{required: true, message: '对象编码必填', trigger: 'blur'}],
                            // ...
                        },
                        // ...
                    },
                    columns: [{
                        component_name: 'DropDownBox',
                        name: 'schemaName',
                        data_url: '/db/list',
                        label: '数据库',
                        conf: {
                            clearable: true,
                            // placeholder: "请输入姓名..",
                            // ...
                        }
                    },{
                        component_name: 'DropDownBox',
                        name: 'tableName',
                        label: '表名',
                        data_url: '/xxx',
                        conf: {
                            clearable: true,
                            // placeholder: "请输入姓名..",
                            // ...
                        }
                    },{
                        component_name: 'TextBox',
                        name: 'objectName',
                        label: '对象名',
                        conf: {
                            clearable: true,
                            // placeholder: "请输入姓名..",
                            // ...
                        }
                    },{
                        component_name: 'TextBox',
                        name: 'objectCode',
                        label: '对象编码',
                        conf: {
                            clearable: true,
                            placeholder: "请输入姓名..",
                            // ...
                        }
                    },
                    ]

                }
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
            // importMeta () {
            //     this.dialogVisible = true
            // }
        },
        components: {
            TableList,
            FormTmpl
        },
        created() {
        },

    }
</script>

<style scoped>

</style>