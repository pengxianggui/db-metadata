<template>
    <el-container direction="vertical" class="el-card">
        <el-row>
            <el-col :span="24">
                <!-- operation bar -->
                <slot name="operation-bar">
                    <el-button-group>
                        <el-button @click="handleAdd">新增</el-button>
                        <el-button @click="handleBatchDelete($event)" type="danger">删除</el-button>
                    </el-button-group>
                </slot>
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

                    <!-- muti select conf -->
                    <template v-if="innerMeta.multi_select">
                        <el-table-column type="selection" width="55"></el-table-column>
                    </template>

                    <template v-for="(item, index) in innerMeta.columns">
                        <el-table-column v-if="item.showable"
                                         v-bind="item.conf"
                                         :key="item.name"
                                         :prop="item.name"
                                         :label="item.label || item.name"
                                         show-overflow-tooltip>
                        </el-table-column>
                    </template>
                    <slot name="operation-column">
                        <el-table-column width="180">
                            <template slot="header">
                                <span>
                                    <span>操作</span>
                                    <el-popover placement="bottom-end" trigger="hover">
                                        <i slot="reference" class="el-icon-caret-bottom" style="cursor: pointer"></i>
                                        <el-checkbox v-for="(item, index) in innerMeta.columns"
                                                     :key="item.name + '' + index"
                                                     :label="item.name"
                                                     v-model="item.showable"
                                                     @change="$forceUpdate(); getData()"
                                                     style="display: block;"></el-checkbox>
                                    </el-popover>
                                </span>
                            </template>
                            <template slot-scope="scope">
                                <slot name="buttons">
                                    <el-button :size="innerMeta.conf['size']"
                                               @click="handleEdit($event, scope.row, scope.$index)">编辑
                                    </el-button>
                                    <el-button :size="innerMeta.conf['size']" type="danger"
                                               @click="handleDelete($event, scope.row, scope.$index)">删除
                                    </el-button>
                                </slot>
                            </template>
                        </el-table-column>
                    </slot>
                </el-table>
            </el-col>
        </el-row>
        <el-row v-if="pageModel">
            <el-col>
                <!-- pagination bar -->
                <slot name="pagination" v-bind:pageModel="pageModel">
                    <el-pagination background
                                   :page-size.sync="pageModel.size"
                                   :current-page.sync="pageModel.index"
                                   :total="pageModel.total"
                                   v-bind="innerMeta.pagination"
                                   @size-change="sizeChange"
                                   @current-change="getData"
                    ></el-pagination>
                </slot>
            </el-col>
        </el-row>
    </el-container>
</template>

<script>
    import DEFAULT from '@/constant/default'
    import {FORM_TO_ADD_URL, FORM_TO_EDIT_URL, TABLE_DATA_DELETE_URL} from '@/constant/constant'
    import utils from '@/utils'

    export default {
        name: "TableList",
        data() {
            return {
                innerData: [],
                innerChoseData: [],
                innerActiveData: {},
                sortModel: {
                    prop: null,
                    order: null
                }, // {prop: , order: }
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
                validator: function (val) {
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
            handleSelectionChange(selection) {
                if (this.innerMeta.multi_select) {
                    this.innerChoseData = selection;
                    this.$emit('update:chose-data', selection);
                }
            },
            handleEdit(ev, row, index) { // edit/add
                if (ev) ev.stopPropagation();
                this.doEdit(row.id, ev, row, index); // params ev,row,index is for convenient to override
            },
            doEdit(id) {
                let url;
                if (id) {
                    url = this.$compile(FORM_TO_EDIT_URL, {
                        objectCode: this.innerMeta['objectCode'],
                        id: id
                    });
                } else {
                    url = this.$compile(FORM_TO_ADD_URL, {objectCode: this.innerMeta['objectCode']});
                }
                this.$axios.get(url).then(resp => {
                    let formMeta = resp.data;
                    this.$dialog(formMeta, {
                        title: id ? '编辑' : '新增',
                    }).then(() => {
                        this.getData(); // refresh
                    });
                }).catch(err => {
                    console.log(err);
                });
            },
            // 删除单行
            handleDelete(ev, row, index) {
                if (ev) ev.stopPropagation();
                const id = row.id;
                this.doDelete(id, ev, row, index); // params ev,row,index is for convenient to override
            },
            // 批量删除
            handleBatchDelete(ev) {
                const idArr = this.innerChoseData.map(row => row.id);
                if (idArr.length > 0) {
                    this.doDelete(idArr, ev);
                    return
                }
                this.$message.warning('请至少选择一项!');
            },
            // default remove the assembly logic is based on id get on
            doDelete(ids) {
                let title = '确定删除此条记录?';
                if (Array.isArray(ids)) {
                    let size = ids.length;
                    ids = ids.join(',');
                    title = '确定删除选中的' + size + '条记录?';
                }

                this.$confirm(title, '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    let beforeCompileUrl = this.innerMeta['delete_url'] || TABLE_DATA_DELETE_URL;
                    let afterCompileUrl = this.$compile(beforeCompileUrl,
                        {objectCode: this.innerMeta['objectCode'], ids: ids});
                    this.$axios.delete(afterCompileUrl).then(resp => {
                        this.$message.success(resp.msg);
                        this.getData();
                    }).catch(err => {
                        this.$message.error(err.msg);
                    });
                });
            },
            // 新增一行
            handleAdd() {
                this.doEdit();
            },
            choseRow(row, col, event) {
                let selected = true;
                this.innerActiveData = row;
                this.$emit('update:active-data', row);

                if (this.innerMeta.multi_select) {
                    let tableRefName = this.innerMeta['name'];
                    for (let i = 0; i < this.$refs[tableRefName]['selection'].length; i++) { // cancel chose judge
                        let choseItem = this.$refs[tableRefName]['selection'][i];
                        if (row.id === choseItem.id) {
                            selected = false;
                            break
                        }
                    }
                    this.$refs[tableRefName].toggleRowSelection(row, selected);
                }
            },
            sortChange(param) {
                let {prop, order} = param;
                this.sortModel = {
                    prop: prop,
                    order: order
                };
            },
            setPage(index) {
                this.pageModel['index'] = index;
            },
            sizeChange() {
                this.setPage(1); // jump to page one
                this.getData();
            },
            setPageModel(page) {
                const {total, index, size} = page;
                this.pageModel['total'] = parseInt(total);
                this.pageModel['index'] = parseInt(index);
                this.pageModel['size'] = parseInt(size);
            },
            getData(params) {
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

                this.$axios.safeGet(url, {
                    params: params
                }).then(resp => {
                    this.innerData = resp.data;
                    this.$emit("update:data", resp.data);
                    if (resp.hasOwnProperty('page')) {
                        this.setPageModel(resp['page']);
                    }
                }).catch(err => {
                    this.$message.error(err.msg);
                });
            },
            initData() { // init business data
                let page = this.page;
                let data = this.data;
                if (page !== undefined) {
                    this.setPageModel(page)
                }
                if (data !== undefined) {
                    this.innerData = data;
                    return;
                }
                if (this.innerMeta.hasOwnProperty('data_url')) {
                    this.getData();
                    return;
                }
                console.error("data or data_url in meta provide one at least!")
            },
        },
        watch: {
            'innerMeta.data_url': function () {
                this.getData();
            }
        },
        mounted() {
            this.initData();
        },
        computed: {
            innerMeta() {
                if (this.meta.hasOwnProperty('columns')) { // init column.showable of columns
                    this.meta['columns'].forEach(item => {
                        if (!item.hasOwnProperty('showable')) { // default true
                            item.showable = true;
                        }
                    });
                }
                return this.$merge(this.meta, DEFAULT.TableList);
            }
        }
    }
</script>

<style scoped>
</style>
