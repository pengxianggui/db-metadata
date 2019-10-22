<!--
# meta
eg:
{
    name: "",
    label: "",
    component_name: 'TableList',
    data_url: "",
    // ...
    conf: {
        "default-sort": {prop: 'id', order: 'descending'}, // descending, ascending
        "size": 'medium',
        "max-height": 360,
    }
    columns: [{
        component_name: 'TextBox',
        name: 'username',
        label: '用户名',
        conf: {
            sortable: true,
            // ...
        }
    }],
    pagination: {
        "page-size": DICT.PAGE_NUM_AREA[0],
        "page-sizes": DICT.PAGE_NUM_AREA,
        "current-page": 1,
        "layout": "total, sizes, prev, pager, next, jumper"
    }
}
-->
<template>
    <el-container direction="vertical">
        <el-row>
            <el-col :span="24">
                <!-- operation bar -->
                <!-- -->
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <el-table
                        :ref="innerMeta.name"
                        :data="data"
                        v-bind="innerMeta.conf"
                        @row-click="choseRow"
                        @sort-change="sortChange"
                        @selection-change="handleSelectionChange">
                    <el-table-column type="selection" width="55"></el-table-column>
                    <el-table-column v-for="(item, index) in innerMeta.columns"
                                     :key="item.name + index"
                                     :prop="item.name"
                                     :label="item.label"
                    ></el-table-column>
                </el-table>
            </el-col>
        </el-row>
        <el-row v-if="paginationModel">
            <el-col>
                <!-- pagination bar -->
                <el-pagination background
                               :page-size.sync="paginationModel.pageSize"
                               :current-page.sync="paginationModel.currentPage"
                               :total="paginationModel.total"
                               v-bind="innerMeta.pagination"
                ></el-pagination>
            </el-col>
        </el-row>
    </el-container>
</template>

<script>
    import {DEFAULT} from '@/constant'

    export default {
        name: "TableList",
        props: {
            meta: {
                type: Object,
                default: function () {
                    return {}
                }
            },
            data: {
                required: false,
                type: Array
            },
            // 选中的行： 用于批量操作，表现为勾选
            choseData: {
                type: Array
            },
            // 激活的一行： 用于对单行操作
            activeData: {
                type: Object
            },
        },
        data() {
            return {
                innerMeta: {},
                multipleSelection: [],
                sortModel: {},
                paginationModel: {
                    pageSize: 10,
                    currentPage: 1,
                    total: null
                }
            }
        },
        methods: {
            handleSelectionChange(val) {
                this.$emit('update:chose-data', val)
            },
            choseRow(row, col, event) {
                let selected = true;
                this.$emit('update:active-data', row);
                let tableRefName = this.innerMeta.name;
                for (let i = 0; i < this.$refs[tableRefName].selection.length; i++) {
                    let choseItem = this.$refs[tableRefName].selection[i];
                    if (row.id === choseItem.id) {
                        selected = false;
                        break
                    }
                }
                this.$refs[tableRefName].toggleRowSelection(row, selected)
            },
            sortChange(param) {
                this.sortModel.prop = param.prop;
                this.sortModel.order = param.order
            },
            initMeta() {
                // merge options
                let defaultMeta = this.getDefaultMeta();
                this.$merge(this.innerMeta, defaultMeta);
                this.$merge(this.innerMeta, this.meta);
            },
            assemblyModel() {
                // this.sortModel = this.meta.conf['sort_model']
                // this.paginationModel = this.meta.pagination['model']
                // if (this.paginationModel) {
                //     this.paginationModel.pageSize = this.meta.ui_config.pagination['page-size']
                //     this.paginationModel.currentPage = this.meta.ui_config.pagination['current-page']
                // }
                // if (this.sortModel) {
                //     this.$emit('update:sort-model', this.meta.ui_config.table['default-sort'])
                //     // this.sortModel = new Array(this.meta.ui_config.table['default-sort']);
                // }
            },
            getDefaultMeta() {
                return DEFAULT.TableList
            },
            getData() {
                let _this = this;
                this.$axios.get(_this.innerMeta['data_url']).then(resp => {
                    _this.data = resp.data;
                    _this.paginationModel.total = _this.data.length
                }).catch(resp => {
                    _this.$message.error(resp.toString())
                })
            },
            initData() { // init business data
                if (this.data) {
                    this.paginationModel.total = this.data.length
                } else {
                    this.getData()
                }
            },
        },
        created() {
            this.initMeta();
            this.assemblyModel();
        },
        mounted() {
            this.initData();
        }
    }
</script>

<style scoped>

</style>
