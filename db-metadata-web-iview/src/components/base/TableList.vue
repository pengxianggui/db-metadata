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
                        ref="table"
                        :data="data"
                        border
                        stripe
                        highlight-current-row
                        v-bind="metaData.ui_config.table"
                        @row-click="choseRow"
                        @sort-change="sortChange"
                        @selection-change="handleSelectionChange">
                    <el-table-column type="selection" width="55"></el-table-column>
                    <el-table-column v-for="(metaField, index) in fieldMetaData"
                                     v-if="metaField.ui_config.showable"
                                     :key="metaField.id + index"
                                     :prop="metaField.en"
                                     :label="metaField.cn"
                                     show-overflow-tooltip
                                     v-bind="metaField.ui_config"
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
                               v-bind="metaData.ui_config.pagination"
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
            metaData: {
                required: true,
                type: Object
            },
            fieldMetaData: {
                required: true,
                type: Array
            },
            data: {
                required: true,
                type: Array,
                default: () => []
            },
            // 选中的行： 用于批量操作，表现为勾选
            choseData: {
                required: true,
                type: Array
            },
            // 激活的一行： 用于对单行操作
            activeData: {
                required: true,
                type: Object
            },
            // 用于构建上层的数据查询, sort如果只对当前页进行排序，就无需构建查询参数。看取舍
            sortModel: {
                required: false,
                type: Object
            },
            paginationModel: {
                required: false,
                type: Object
            }
        },
        data() {
            return {
                multipleSelection: [],
            }
        },
        methods: {
            handleSelectionChange (val) {
                this.$emit('update:chose-data', val)
            },
            choseRow (row, col, event) {
                let selected = true
                this.$emit('update:active-data', row)
                for (let i = 0; i < this.$refs.table.selection.length; i++) {
                    let choseItem = this.$refs.table.selection[i]
                    if (row.id === choseItem.id) {
                        selected = false
                        break
                    }
                }
                this.$refs.table.toggleRowSelection(row, selected)
            },
            sortChange(param) {
                this.sortModel.prop = param.prop
                this.sortModel.order = param.order
            },
            initConf() {
                // data effective check
                this.metaData.ui_config = this.metaData.ui_config || {}
                this.metaData.ui_config.table = this.metaData.ui_config.table || {}
                this.metaData.ui_config.pagination = this.metaData.ui_config.pagination || {}

                // merge options
                let defaultConf = this.getDefaultTableConf()
                this.merge(this.metaData.ui_config.table, defaultConf.table)
                this.merge(this.metaData.ui_config.pagination, defaultConf.pagination)
            },
            initModel() { // 将配置中的指定的模型数据回朔到model中
                if (this.paginationModel) {
                    this.paginationModel.pageSize = this.metaData.ui_config.pagination['page-size']
                    this.paginationModel.currentPage = this.metaData.ui_config.pagination['current-page']
                }
                if (this.sortModel) {
                    this.$emit('update:sort-model', this.metaData.ui_config.table['default-sort'])
                    // this.sortModel = new Array(this.metaData.ui_config.table['default-sort']);
                }
            },
            getDefaultTableConf () {
                return DEFAULT.tableList
            }
        },
        created() {
            this.initConf()
            this.initModel()
        }
    }
</script>

<style scoped>

</style>
