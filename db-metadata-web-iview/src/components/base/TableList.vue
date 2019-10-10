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
                        max-height="400"
                        highlight-current-row
                        @row-click="choseRow"
                        :size="metaData.ui_config.size"
                        :default-sort = "{prop: 'id', order: 'descending'}"
                        @selection-change="handleSelectionChange">
                    <el-table-column type="selection" width="55"></el-table-column>
                    <el-table-column v-for="(metaField, index) in fieldMetaData"
                                     v-if="metaField.ui_config.showable"
                                     :key="metaField.id + index"
                                     :prop="metaField.en"
                                     :label="metaField.cn"
                                     :sortable="metaField.ui_config.sortable"
                                     show-overflow-tooltip
                                ></el-table-column>
                </el-table>
            </el-col>
        </el-row>
        <el-row v-if="paginationModel">
            <el-col>
                <!-- pagination bar -->
                <el-pagination background layout="total, sizes, prev, pager, next, jumper"
                               :page-size.sync="paginationModel.pageNum"
                               :current-page.sync="paginationModel.currentPage"
                               :page-sizes="pageCountRange"
                               :total="paginationModel.totalNum"
                               :small="('mini').indexOf(metaData.ui_config.size) >= 0"
                ></el-pagination>
            </el-col>
        </el-row>
    </el-container>
</template>

<script>
    import {DICT} from '@/constant'
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
                type: Array
            },
            paginationModel: {
                required: false,
                type: Object
            }
        },
        data() {
            return {
                multipleSelection: [],
                pageCountRange: DICT.PAGE_NUM_AREA
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
            }
        }
    }
</script>

<style scoped>

</style>
