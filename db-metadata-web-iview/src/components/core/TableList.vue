<template>
    <el-container direction="vertical" class="el-card" ref="container">
        <el-row justify="end">
            <el-col :span="24">
                <!-- operation bar -->
                <slot name="operation-bar"
                      v-bind:conf="innerMeta['operation-bar']"
                      v-bind:operations="{handleAdd, handleBatchDelete}">
                    <el-button-group>
                        <slot name="add-btn" v-bind:conf="innerMeta['operation-bar']" v-bind:add="handleAdd">
                            <el-button @click="handleAdd" icon="el-icon-document-add"
                                       v-bind="innerMeta['operation-bar']">新增
                            </el-button>
                        </slot>
                        <slot name="batch-delete-btn" v-bind:conf="innerMeta['operation-bar']"
                              v-bind:batchDelete="handleBatchDelete">
                            <el-button @click="handleBatchDelete($event)" type="danger" icon="el-icon-delete-solid"
                                       v-bind="innerMeta['operation-bar']">删除
                            </el-button>
                        </slot>
                        <slot name="extend-btn"></slot>
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
                    @row-dblclick="$emit('row-dblclick', $event)"
                    @sort-change="sortChange"
                    @selection-change="handleSelectionChange">

                    <!-- multi select conf -->
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
                            <template slot="header">
                                <pop-menu trigger="rightClick">
                                    <template #label>{{item.label || item.name}}</template>
                                    <template #menu>
                                        <ul id="menu">
                                            <li @click="editMetaObject">编辑元对象({{innerMeta.objectCode}})</li>
                                            <li @click="editMetaField(item.name)">编辑元字段({{item.name}})</li>
                                            <li @click="editInstanceConf()">编辑实例UI配置({{innerMeta.objectCode}})</li>
                                            <li @click="editInstanceFieldConf()">编辑实例字段UI配置({{item.name}})</li>
                                        </ul>
                                    </template>
                                </pop-menu>
                            </template>
                        </el-table-column>
                    </template>
                    <slot name="operation-column">
                        <el-table-column width="180">
                            <template #header>
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
                                <slot name="buttons"
                                      v-bind:conf="innerMeta['buttons']"
                                      v-bind:scope="scope">
                                    <el-button-group>
                                        <slot name="inner-before-extend-btn"
                                              v-bind:conf="innerMeta['buttons']['edit']['conf']"
                                              v-bind:scope="scope"></slot>
                                        <slot name="edit-btn" v-bind:conf="innerMeta['buttons']['edit']['conf']"
                                              v-bind:edit="handleEdit"
                                              v-bind:scope="scope">
                                            <el-button v-bind="innerMeta['buttons']['edit']['conf']"
                                                       @click="handleEdit($event, scope.row, scope.$index)">
                                            </el-button>
                                        </slot>
                                        <slot name="delete-btn" v-bind:conf="innerMeta['buttons']['delete']['label']"
                                              v-bind:delete="handleDelete"
                                              v-bind:scope="scope">
                                            <el-button v-bind="innerMeta['buttons']['delete']['conf']"
                                                       @click="handleDelete($event, scope.row, scope.$index)">
                                            </el-button>
                                        </slot>
                                        <slot name="inner-after-extend-btn"
                                              v-bind:conf="innerMeta['buttons']['edit']['conf']"
                                              v-bind:scope="scope"></slot>
                                    </el-button-group>
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

        <dialog-box :visible.sync="dialogVisible" :meta="dialogMeta" :component-meta="dialogComponentMea"
                    @ok="getData()" @cancel="dialogVisible=false">
            <template #default>
                <slot name="dialog-body" v-bind:meta="dialogComponentMea"></slot>
            </template>
            <template #footer><span></span></template>  <!-- 表单自带button条 -->
        </dialog-box>
    </el-container>
</template>

<script>
    import {DEFAULT, URL} from '@/constant'
    import utils from '@/utils'
    import PopMenu from "./PopMenu";

    export default {
        name: "TableList",
        components: {PopMenu},
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
                dialogComponentMea: {}, // 弹窗内包含的组件元对象
                dialogMeta: {}, // 弹窗组件元对象
                dialogVisible: false,   // 弹窗显隐
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
            choseData: Array,   // 选中的行： 用于批量操作，表现为勾选
            activeData: Object, // 激活的一行： 用于对单行操作
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
                    url = this.$compile(URL.RECORD_TO_UPDATE, {
                        objectCode: this.innerMeta['objectCode'],
                        id: id
                    });
                } else {
                    url = this.$compile(URL.RECORD_TO_ADD, {objectCode: this.innerMeta['objectCode']});
                }
                this.$axios.get(url).then(resp => {
                    this.dialogComponentMea = resp.data;
                    this.dialogMeta = {
                        component_name: "DialogBox",
                        conf: {
                            title: id ? '编辑' : '新增'
                        }
                    };
                    this.dialogVisible = true
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
                    let beforeCompileUrl = this.innerMeta['delete_url'] || URL.RECORD_DELETE;
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

                if (!event.ctrlKey) return; // ctrl + 鼠标左击 实现多选
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
            rightClickHander(ev, objectCode, fieldCode) {
                if (ev) {
                    ev.stopPropagation();
                    ev.preventDefault();
                }
                // this.popVisible = true;
                this.$refs['pop-' + fieldCode].value = true;
            },

            editMetaObject() {
                let objectCode = this.innerMeta['objectCode'];
                let url = this.$compile(URL.META_OBJECT_TO_EDIT, {objectCode: objectCode});
                this.$axios.get(url).then(resp => {
                    this.dialogComponentMea = resp.data;
                    this.dialogMeta = {
                        component_name: "DialogBox",
                        conf: {
                            title: "编辑元对象:" + objectCode
                        }
                    };
                    this.dialogVisible = true
                });
            },
            editMetaField(fieldCode) {
                let objectCode = this.innerMeta['objectCode'];
                let url = this.$compile(URL.META_FIELD_TO_EDIT, {
                    objectCode: objectCode,
                    fieldCode: fieldCode
                });
                this.$axios.get(url).then(resp => {
                    this.dialogComponentMea = resp.data;
                    this.dialogMeta = {
                        component_name: "DialogBox",
                        conf: {
                            title: "编辑元字段:" + fieldCode
                        }
                    };
                    this.dialogVisible = true
                });
            },
            editInstanceConf() {
                let objectCode = this.innerMeta['objectCode'];
                let componentCode = 'TableList';
                let url = URL.RR_INSTANCE_CONF_ADD;
                let routeUrl = this.$router.resolve({
                    path: url,
                    query: {
                        componentCode: componentCode,
                        objectCode: objectCode
                    }
                });
                window.open(routeUrl.href, '_blank');
            },
            editInstanceFieldConf(fieldCode) {
                this.editInstanceConf(); // just edit the ui conf of field named fieldCode. anchor point ?
            },

            getData(params) {
                if (!utils.isObject(params)) params = {};

                if (!this.innerMeta.hasOwnProperty('data_url')) {
                    console.error('lack data_url attribute');
                    return;
                }

                let columnNames = (this.innerMeta['columns'] || [])
                    .filter(column => column.hasOwnProperty('showable') && column['showable'])
                    .map(column => column['name']);

                let url = this.innerMeta['data_url'];

                Object.assign(params, {
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
            'innerMeta.data_url': {
                handler: function () {
                    this.getData();
                },
                immediate: false
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

    .right-menu {
        position: fixed;
        background: #fff;
        border: solid 1px rgba(0, 0, 0, .2);
        border-radius: 3px;
        z-index: 999;
        display: none;
    }

    .right-menu a {
        width: 75px;
        height: 28px;
        line-height: 28px;
        text-align: center;
        display: block;
        color: #1a1a1a;
    }

    .right-menu a:hover {
        background: #eee;
        color: #fff;
    }

    html,
    body {
        height: 100%;
    }

    .right-menu {
        border: 1px solid #eee;
        box-shadow: 0 0.5em 1em 0 rgba(0, 0, 0, .1);
        border-radius: 1px;
    }

    ul#menu {
        list-style: none;
        margin: 0;
        padding: 5px;
    }

    ul#menu li {
        padding: 5px;
        cursor: pointer;
    }

    ul#menu li:hover {
        background-color: #eeeeee;
    }
</style>
