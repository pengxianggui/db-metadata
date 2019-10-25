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
<!--        {{innerMeta}}-->
<!--        <json-box v-model="innerMeta"></json-box>-->
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
                innerData: [],
                multipleSelection: [],
                sortModel: {},
                paginationModel: {
                    pageSize: 10,
                    currentPage: 1,
                    total: null
                },
            }
        },
        methods: {
            handleSelectionChange(val) {
                this.$emit('update:chose-data', val)
            },
            handleEdit(index, row) {
                // TODO
            },
            handleDelete(index, row) {
                // TODO
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
            initMeta() {
                // merge options
                let _this = this;
                let defaultMeta = this.getDefaultMeta();
                _this.$merge(_this.innerMeta, defaultMeta);
                _this.$merge(_this.innerMeta, _this.meta);

                // showColumns init
                _this.innerMeta.columns.forEach(item => {
                    if (!item.conf.hasOwnProperty('showable')) { // default: showable: true
                        _this.$set(item.conf, 'showable', true)
                    }
                });
            },
            assemblyModel() {
            },
            getDefaultMeta() {
                return DEFAULT.TableList
            },
            getData() { // 内部根据data_url获取业务数据
                let _this = this;
                if (!_this.innerMeta.hasOwnProperty('data_url')) {
                    console.error('lack data_url attribute');
                    return;
                }
                let columnNames = _this.innerMeta.columns.filter(column => column.conf.showable).map(column => column.name);
                let url = _this.innerMeta['data_url'];
                this.$axios.get(url + '?fs=' + columnNames.join(',')
                    + '&p=' + _this.paginationModel.currentPage
                    + '&s=' + _this.paginationModel.pageSize)
                .then(resp => {
                    _this.innerData = resp.data;
                    _this.paginationModel.total = resp['page'].total;
                    _this.paginationModel.pageSize = resp['page'].size;
                    _this.paginationModel.currentPage = resp['page'].index;
                }).catch(resp => {
                    _this.$message({type: 'error', message: resp})
                })
            },
            initData() { // init business data
                if (this.data) {
                    // this.paginationModel.total = this.data.length; // TODO 改成业务数据中附带的total

                } else {
                    this.getData()
                }
            },
        },
        watch: {
            paginationModel: {
                handler: function () {
                    this.getData()
                },
                deep: true
            },

        },
        created() {
            this.initMeta();
            this.assemblyModel();
        },
        mounted() {
            this.initData();
            this.$watch('meta.data_url', function (n, o) {
                console.log('changed...')
            })
        }
    }
</script>

<style>
    .el-popover {
        min-width: 50px;
    }
</style>
