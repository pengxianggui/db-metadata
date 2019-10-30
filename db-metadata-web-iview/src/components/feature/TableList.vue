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
                    <el-table-column v-for="(item, index) in innerMeta.columns"
                                     v-bind="item.conf"
                                     :key="item.name + index"
                                     :prop="item.name"
                                     :label="item.label"
                                     v-if="item.conf.showable"
                    ></el-table-column>
                    <el-table-column :render-header="renderHeader" width="180">
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
                // innerMeta: {},
                innerData: [],
                sortModel: {}, // {prop: , order}
                pageModel: {
                    size: 10,
                    index: 1,
                    total: null
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
            data: {
                required: false,
                type: Array
            },
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
            renderHeader(h) {
                let _this = this;
                return h('span', {
                    style: {},
                }, [
                    h("span", {
                        style: {}
                    }, "操作"),
                    h('el-popover', {//el-select实现下拉框
                        props: {
                            placement: 'bottom-end',
                            trigger: 'hover',
                            width: '100',
                        }
                    }, [
                        h('i', {slot: 'reference', class: 'el-icon-caret-bottom', style: {cursor: 'pointer'}}, ''),
                        _this.innerMeta.columns.map(item => {
                            return h("el-checkbox", {
                                on: {
                                    input: (value) => {
                                        item.conf.showable = value;
                                        _this.$forceUpdate();
                                        if (value) _this.getData()
                                    }
                                },
                                props: {
                                    key: item.name,
                                    label: item.name,
                                    value: item.conf.showable
                                }
                            }, item.label)
                        })
                    ])])
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
            setPage(total, index, size) {
                this.pageModel.total = total;
                this.pageModel.index = index;
                this.pageModel.size = size;
            },
            getData() {
                let _this = this;
                if (!_this.innerMeta.hasOwnProperty('data_url')) {
                    console.error('lack data_url attribute');
                    return;
                }
                let columnNames = _this.innerMeta.columns.filter(column => column.conf['showable']).map(column => column.name);
                let url = utils.splice(_this.innerMeta['data_url'], {
                    'fs': columnNames.join(','),
                    'p': _this.pageModel.index,
                    's': _this.pageModel.size
                });

                this.$axios.get(url).then(resp => {
                    _this.innerData = resp.data;
                    _this.$emit("update:data", resp.data);
                    _this.setPage(resp['page']['total'] - 0, resp['page']['index'] - 0, resp['page']['size'] - 0);
                }).catch(resp => {
                    _this.$message({type: 'error', message: resp})
                })
            },
            initData() { // init business data
                if (this.data) {
                    this.innerData = this.data;
                    let page = this.page;
                    if (page) {
                        this.setPage(page['total'],page['index'],page['size'])
                    }
                } else {
                    this.getData()
                }
            },
        },
        created() {
        },
        mounted() {
            this.initData();
        },
        watch: {
            pageModel: {
                handler: function () {
                    this.getData()
                },
                deep: true
            },
            'innerMeta.data_url': { // watch innerMeta.data_url, if changed, refresh business data
                handler: function (n, o) {
                    if (n === o) return;
                    this.getData()
                },
                deep: true
            }
        },
        computed: {
            innerMeta: {
                get: function () {
                    if (this.meta.hasOwnProperty('columns')) { // init column.showable of columns
                        this.meta.columns.forEach(item => {
                            if (!item['conf'].hasOwnProperty('showable')) { // default true
                                item['conf'].showable = true;
                            }
                        });
                    }
                    return this.$merge(this.meta, DEFAULT.TableList);
                },
                set: function (n) {
                    return this.$emit("update:meta", n)
                }
            }
        }
    }
</script>

<style>
    .el-popover {
        min-width: 50px;
    }
</style>
