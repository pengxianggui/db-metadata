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
        <br>
        <br>
        <br>
        {{showColumns}}
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
                    :data="data"
                    v-bind="innerMeta.conf"
                    @row-click="choseRow"
                    @sort-change="sortChange"
                    @selection-change="handleSelectionChange">
                    <el-table-column type="selection" width="55"></el-table-column>
                    <el-table-column :render-header="renderHeader1"></el-table-column>
                    <el-table-column v-for="(item, index) in innerMeta.columns"
                                     v-bind="item.conf"
                                     :key="item.name + index"
                                     :prop="item.name"
                                     :label="item.label"
                                     v-if="item.conf.showable"
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
                },
                showColumns: []
            }
        },
        methods: {
            handleSelectionChange(val) {
                this.$emit('update:chose-data', val)
            },
            renderHeader(h) {
                debugger
                let _this = this;
                let group = document.getElementsByClassName("el-checkbox-group el-table-filter__checkbox-group");
                let filterButtoms = document.getElementsByClassName("el-table-filter__bottom");
                if (filterButtoms.length != 0) {
                    for (let i = 0; i < filterButtoms.length; i++) {
                        filterButtoms[i].style.display == 'none';
                    }
                }
                // if (group.length != 0) {
                //     for (let i = 0; i < group.length; i++) {
                //         let checkBoxs = group[i].getElementsByClassName("el-checkbox__label")
                //         for (let j = 0; j < checkBoxs.length; j++) {
                //             let item = checkBoxs[j];
                //             item.setAttribute("index", j);
                //             item.onclick = function () {
                //                 let index = this.getAttribute('index');
                //                 let name = _this.innerMeta.columns[index].name;
                //                 let showable = _this.innerMeta.columns[index].conf.showable;
                //                 _this.innerMeta.columns[index].conf.showable = !showable;
                //                 if (showable) {
                //                     delete _this.showColumns[index]
                //                 } else {
                //                     _this.showColumns[index] = name
                //                 }
                //             }
                //         }
                //     }
                // }
                return h("span", {domProps: {innerHTML: `操作`}});
            },
            renderHeader1(h) {
                let _this = this;

                /*

                    <span>操作</span>
                    <el-popover
                      placement="right"
                      width="400"
                      trigger="click">
                      <el-checkbox-group v-model="showColumns">
                        <el-checkbox label="复选框 A"></el-checkbox>
                        <el-checkbox label="复选框 B"></el-checkbox>
                        <el-checkbox label="复选框 C"></el-checkbox>
                        <el-checkbox label="禁用" disabled></el-checkbox>
                        <el-checkbox label="选中且禁用" disabled></el-checkbox>
                      </el-checkbox-group>
                      <i slot="reference" class="el-icon-question"></el-button>
                    </el-popover>

                */

                return h('span', {
                    style: {
                    },
                }, [
                    h("span", {
                        style: {}
                    }, "操作"),
                    h('el-popover', {//el-select实现下拉框
                        props: {
                            placement: 'bottom-end',
                            trigger: 'click'
                        }
                    }, [
                        //下拉框里面填充选项，通过filters遍历map，为每一个选项赋值。
                        h('i', {slot: 'reference', class: 'el-icon-question'}, ''),
                        h('el-checkbox-group', {
                            on: {
                                input: (value) => {
                                    _this.showColumns = value
                                    // TODO
                                }
                            },
                            style: {
                            },
                            props: {
                                value: _this.showColumns
                            }
                        }, [
                            _this.innerMeta.columns.map(item => {
                                return h("el-checkbox", {
                                    props: {
                                        key: item.name,
                                        label: item.name
                                    }
                                }, item.label)
                            })
                        ])
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
                _this.innerMeta.columns
                    .forEach(item => {
                        if (!item.conf.hasOwnProperty('showable')) { // default: showable: true
                            _this.$set(item.conf, 'showable', true)
                        }
                        if (item.conf.showable) _this.showColumns.push(item.name);
                    });
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

<style>
    /*将下面的重置和筛选隐藏掉。*/
    div.el-table-filter div.el-table-filter__bottom {
        display: none !important;
    }
</style>
