<!--
# meta required
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
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24">
                <el-table
                    :id="innerMeta.name"
                    :ref="innerMeta.name"
                    :data="innerData"
                    v-bind="innerMeta.conf"
                    @row-click="choseRow"
                    @sort-change="sortChange"
                    @selection-change="handleSelectionChange">

                    <el-table-column type="selection" width="55"></el-table-column>

                    <template v-for="(item, index) in innerMeta.columns">
                        <el-table-column v-if="item.conf['showable']"
                                         v-bind="item.conf"
                                         :key="item.name + '' + index"
                                         :prop="item.name"
                                         :label="item.label">
                        </el-table-column>
                    </template>

                    <el-table-column width="180">
                        <template slot="header">
                            <span>
                                <span>操作</span>
                                <el-popover placement="bottom-end" trigger="hover">
                                    <i slot="reference" class="el-icon-caret-bottom" style="cursor: pointer"></i>
                                    <el-checkbox v-for="(item, index) in innerMeta.columns"
                                                 :key="item.name + '' + index"
                                                 :label="item.name"
                                                 v-model="item.conf['showable']"
                                                 @change="$forceUpdate(); getData()" style="display: block;"></el-checkbox>
                                </el-popover>
                            </span>
                        </template>
                        <template slot-scope="scope">
                            <el-button :size="innerMeta.conf.size"
                                       @click="handleEdit(scope.$index, scope.row)">编辑
                            </el-button>
                            <el-button :size="innerMeta.conf.size" type="danger"
                                       @click="handleDelete(scope.$index, scope.row)">删除
                            </el-button>
                        </template>
                    </el-table-column>

                </el-table>
            </el-col>
        </el-row>
        <el-row v-if="pageModel">
            <el-col>
                <!-- pagination bar -->
                <el-pagination background
                               :page-size.sync="pageModel.size"
                               :current-page.sync="pageModel.index"
                               :total="pageModel.total"
                               v-bind="innerMeta.pagination"
                               @size-change="sizeChange"
                               @current-change="indexChange"
                ></el-pagination>
            </el-col>
        </el-row>
    </el-container>
</template>

<script>
    import {DEFAULT} from '@/constant'
    import utils from '@/utils'

    export default {
        name: "TableList",
        data() {
            return {
                innerData: [],
                sortModel: {}, // {prop: , order: }
                pageModel: {
                    size: 10,
                    index: 1,
                    total: 0
                },
            }
        },
        props: {
            meta: {
                type: Object,
                default: function () {
                    return {};
                }
            },
            data: Array,
            page: {
                type: Object,
                validate: function (val) {
                    if (!val.hasOwnProperty('total')
                        || !val.hasOwnProperty('size')
                        || !val.hasOwnProperty('index')) {
                        return false;
                    }
                    return true;
                }
            },
            // 选中的行： 用于批量操作，表现为勾选
            choseData: Array,
            // 激活的一行： 用于对单行操作
            activeData: Object,
        },
        methods: {
            handleSelectionChange(val) {
                this.$emit('update:chose-data', val)
            },
            handleEdit(index, row) {
                // pxg_todo
            },
            handleDelete(index, row) {
                // pxg_todo
            },
            choseRow(row, col, event) {
                let selected = true;
                this.$emit('update:active-data', row);
                let tableRefName = this.innerMeta.name;

                for (let i = 0; i < this.$refs[tableRefName].selection.length; i++) { // cancel chose judge
                    let choseItem = this.$refs[tableRefName].selection[i];
                    if (row.id === choseItem.id) {
                        selected = false;
                        break
                    }
                }
                this.$refs[tableRefName].toggleRowSelection(row, selected)
            },
            sortChange(param) {
                this.sortModel['prop'] = param['prop'];
                this.sortModel['order'] = param['order'];
            },
            sizeChange() {
                this.pageModel['index'] = 1; // jump to page one
                this.getData();
            },
            indexChange() {
                this.getData();
            },
            setPage(total, index, size) {
                this.pageModel['total'] = total;
                this.pageModel['index'] = index;
                this.pageModel['size'] = size;
            },
            getData() {
                if (!this.innerMeta.hasOwnProperty('data_url')) {
                    console.error('lack data_url attribute');
                    return;
                }

                let columnNames = (this.innerMeta['columns'] || [])
                    .filter(column => column['conf']['showable']).map(column => column['name']);

                let url = utils.splice(this.innerMeta['data_url'], {
                    'fs': columnNames.join(','),
                    'p': this.pageModel['index'],
                    's': this.pageModel['size']
                });

                this.$axios.get(url).then(resp => {
                    this.innerData = resp.data;
                    this.$emit("update:data", resp.data);
                    if (resp.hasOwnProperty('page')) {
                        this.setPage(resp['page']['total'] - 0, resp['page']['index'] - 0, resp['page']['size'] - 0);
                    }
                }).catch(resp => {
                    this.$message({type: 'error', message: resp})
                })
            },
            initData() { // init business data
                let page = this.page;
                if (typeof page !== 'undefined') {
                    this.setPage(page['total'],page['index'],page['size'])
                }
                if (typeof this.data !== 'undefined') {
                    this.innerData = this.data;
                } else {
                    this.getData()
                }
            },
        },
        mounted() {
            this.initData();
        },
        watch: {
            'innerMeta.data_url': { // watch innerMeta.data_url, if changed, refresh business data
                handler: function (n, o) {
                    if (n === o) return;
                    this.getData()
                }
            }
        },
        computed: {
            innerMeta() {
                if (this.meta.hasOwnProperty('columns')) { // init column.showable of columns
                    this.meta.columns.forEach(item => {
                        if (!item.hasOwnProperty('conf')) {
                            item['conf'] = {}
                        }
                        if (!item['conf'].hasOwnProperty('showable')) { // default true
                            item['conf'].showable = true;
                        }
                    });
                }
                return this.$merge(this.meta, DEFAULT.TableList);
            }
        }
    }
</script>

<style>
    .el-popover {
        min-width: 50px;
    }
</style>
