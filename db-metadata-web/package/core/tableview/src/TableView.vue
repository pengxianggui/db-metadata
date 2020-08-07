<template>
    <el-container direction="vertical" class="el-card" ref="container">
        <el-row justify="end">
            <el-col :span="24">
                <!-- operation bar -->
                <slot name="operation-bar"
                      v-bind:conf="operationBarConf"
                      v-bind:operations="{handleAdd, handleBatchDelete}">
                    <el-button-group>
                        <slot name="prefix-btn" v-bind:conf="operationBarConf"></slot>
                        <slot name="add-btn" v-bind:conf="operationBarConf" v-bind:add="handleAdd">
                            <el-button @click="handleAdd" icon="el-icon-document-add"
                                       v-bind="operationBarConf">新增
                            </el-button>
                        </slot>
                        <slot name="batch-delete-btn" v-bind:conf="operationBarConf"
                              v-bind:batchDelete="handleBatchDelete">
                            <el-button @click="handleBatchDelete($event)" type="danger" icon="el-icon-delete-solid"
                                       v-bind="operationBarConf" v-if="multiSelect">删除
                            </el-button>
                        </slot>
                        <slot name="suffix-btn" v-bind:conf="operationBarConf"></slot>
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
                        v-bind="$reverseMerge(innerMeta.conf, $attrs)"
                        @row-click="handleRowClick"
                        @row-dblclick="handleRowDbClick"
                        @sort-change="sortChange"
                        @selection-change="handleSelectionChange">

                    <!-- multi select conf -->
                    <template v-if="multiSelect">
                        <el-table-column type="selection" width="55"></el-table-column>
                    </template>

                    <template v-for="(item, index) in columns">
                        <el-table-column v-if="item.showable"
                                         v-bind="item.conf"
                                         :key="item.name"
                                         :prop="item.name"
                                         :label="item.label || item.name"
                                         show-overflow-tooltip>
                            <template #header>
                                <meta-easy-edit :object-code="objectCode" :field-code="item.name"
                                                :label="item.label || item.name" :all="true" component-code="TableView">
                                    <template #label>{{item.label || item.name}}</template>
                                </meta-easy-edit>
                            </template>
                            <template #default="scope">
                                <table-cell :edit="multiEdit" :data="scope" :meta="item"></table-cell>
                            </template>
                        </el-table-column>
                    </template>

                    <slot name="operation-column" v-if="operationColMode">
                        <el-table-column width="180" v-bind="operationColumnConf">
                            <template #header>
                                <span>
                                    <span>操作</span>
                                    <el-popover placement="bottom-end" trigger="hover">
                                        <i slot="reference" class="el-icon-caret-bottom" style="cursor: pointer"></i>
                                        <el-checkbox v-for="(item, index) in columns"
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
                                      v-bind:conf="buttonsConf"
                                      v-bind:scope="scope">
                                    <el-button-group>
                                        <slot name="inner-before-extend-btn"
                                              v-bind:conf="buttonsConf['edit']['conf']"
                                              v-bind:scope="scope"></slot>
                                        <slot name="edit-btn" v-bind:conf="buttonsConf['edit']['conf']"
                                              v-bind:edit="handleEdit"
                                              v-bind:scope="scope">
                                            <el-button v-bind="buttonsConf['edit']['conf']"
                                                       @click="handleEdit($event, scope.row, scope.$index)">
                                            </el-button>
                                        </slot>
                                        <slot name="delete-btn"
                                              v-bind:conf="buttonsConf['delete']['conf']"
                                              v-bind:delete="handleDelete"
                                              v-bind:scope="scope">
                                            <el-button v-bind="buttonsConf['delete']['conf']"
                                                       @click="handleDelete($event, scope.row, scope.$index)">
                                            </el-button>
                                        </slot>
                                        <slot name="inner-after-extend-btn"
                                              v-bind:conf="buttonsConf['edit']['conf']"
                                              v-bind:scope="scope"></slot>
                                    </el-button-group>
                                </slot>
                            </template>
                        </el-table-column>
                    </slot>
                </el-table>
            </el-col>
        </el-row>

        <el-row v-if="pageModel" style="margin-top: 5px;">
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

        <slot name="behavior" :on="on" :actions="actions"></slot>
    </el-container>
</template>

<script>
    import utils from '../../../utils'
    import {restUrl} from "../../../constant/url";
    import {defaultPrimaryKey} from "../../../config";
    import MetaEasyEdit from '../../meta/src/MetaEasyEdit'
    import Meta from '../../mixins/meta'
    import assembleMeta from './assembleMeta'
    import TableCell from './tableCell'
    import DefaultMeta from '../ui-conf'

    export default {
        name: "TableView",
        mixins: [Meta(DefaultMeta, assembleMeta)],
        components: {MetaEasyEdit, TableCell},
        data() {
            return {
                multiEdit: false, // 多行编辑模式
                innerData: [],
                choseData: [],
                activeData: {},
                sortParams: {},
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
            data: Array,
            page: Object,
            filterParams: Object    // 搜索面板过滤参数
        },
        methods: {
            handleSelectionChange(selection) {
                if (this.multiSelect) {
                    this.choseData = selection;
                    this.$emit('chose-change', selection);
                }
                this.$emit('selection-change', selection)
            },
            handleEdit(ev, row, index) { // edit/add
                if (ev) ev.stopPropagation();
                const {primaryKey} = this;
                const primaryValue = utils.extractValue(row, primaryKey);

                this.doEdit(primaryValue, ev, row, index); // params ev,row,index is for convenient to override
            },
            doEdit(primaryValue) {
                const {objectCode, primaryKey} = this
                let url, title;

                if (!utils.isEmpty(primaryValue)) {
                    title = '编辑';
                    let primaryKv;

                    if (primaryKey.length <= 1) {
                        primaryKv = primaryValue[0];
                    } else {
                        primaryKv = utils.spliceKvs(primaryKey, primaryValue);
                    }

                    url = this.$compile(restUrl.RECORD_TO_UPDATE, {
                        objectCode: objectCode,
                        primaryKv: primaryKv
                    });
                } else {
                    title = '新增';
                    url = this.$compile(restUrl.RECORD_TO_ADD, {objectCode: objectCode});
                }
                this.dialog(url, {title: title});
            },
            dialog(url, conf) {
                this.$axios.get(url).then(resp => {
                    this.dialogComponentMea = resp.data;
                    this.dialogMeta = {
                        component_name: "DialogBox",
                        conf: utils.assertUndefined(conf, {title: '新增'})
                    };
                    this.dialogVisible = true
                });
            },
            // 删除单行
            handleDelete(ev, row, index) {
                if (ev) ev.stopPropagation();

                const {primaryKey} = this;
                const primaryValue = utils.extractValue(row, primaryKey);
                let primaryKvExp;
                if (primaryKey.length > 1 && primaryValue.length > 1) { // 联合主键, 目标: primaryKvExp="id=pk1_v1,pk2_v2"
                    primaryKvExp = 'id=' + utils.spliceKvs(primaryKey, primaryValue);
                } else {    // 单主键, 目标: primaryKvExp="pk=v"
                    primaryKvExp = utils.spliceKv(primaryKey[0], primaryValue[0], '=');
                }
                this.doDelete(primaryKvExp, ev, row, index);
            },
            // 批量删除
            handleBatchDelete(ev) {
                if (this.choseData.length <= 0) {
                    this.$message.warning('请至少选择一项!');
                    return;
                }

                let {primaryKey} = this;
                let primaryValue;
                let primaryKvExp;

                if (primaryKey.length > 1) {    // 联合主键, 目标: primaryKvExp="id=pk1_v1,pk2_v2&id=pk1_v3,pk2_v4"
                    let primaryKvExpArr = [];
                    this.choseData.forEach(row => {
                        primaryValue = utils.extractValue(row, primaryKey);
                        primaryKvExpArr.push('id=' + utils.spliceKvs(primaryKey, primaryValue));
                    });
                    primaryKvExp = primaryKvExpArr.join('&');
                    this.doDelete(primaryKvExp, ev);
                } else {    // 单主键 目标: primaryKvExp="pk=v1&pk=v2&pk=v3"
                    primaryValue = this.choseData.map(row => row[primaryKey[0]]);
                    let primaryKvExpArr = primaryValue.map(value => utils.spliceKv(primaryKey[0], value, "="));
                    primaryKvExp = primaryKvExpArr.join('&');

                    this.doDelete(primaryKvExp, ev);
                }
            },
            /**
             * default remove the assembly logic is based on primaryKey get on
             * 单条删除("id=pk1_v1,pk2_v2" 或 "pk=v"), 批量删除("id=pk1_v1,pk2_v2&id=pk1_v3,pk2_v4" 或 "pk=v1,v2,v3")
             */
            doDelete(primaryKvExp) {
                let title = '确定删除此条记录?';
                const RECORD_SIZE = this.choseData.length;

                if (RECORD_SIZE > 1) {
                    title = '确定删除选中的' + RECORD_SIZE + '条记录?';
                }

                this.$confirm(title, '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    const {delete_url} = this.innerMeta;
                    const url = delete_url + '?' + primaryKvExp;
                    this.$axios.delete(url).then(resp => {
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
            handleRowClick(row, col, event) {
                this.$emit('row-click', {row, col, event});
                event.ctrlKey ? this.choseRow(row) : this.activeRow(row);
            },
            handleRowDbClick(row, col, event) {
                this.$emit('row-dblclick', {row, col, event});
            },
            choseRow(row) {
                let selected = true;
                const {primaryKey, multiSelect} = this;

                if (multiSelect) {
                    let tableRefName = this.innerMeta['name'];
                    for (let i = 0; i < this.$refs[tableRefName]['selection'].length; i++) { // cancel chose judge
                        let choseItem = this.$refs[tableRefName]['selection'][i];
                        if (utils.allEqualOnKeys(row, choseItem, primaryKey)) {
                            selected = false;
                            break
                        }
                    }
                    this.$refs[tableRefName].toggleRowSelection(row, selected);
                }
            },
            activeRow(row) {
                const {primaryKey} = this;

                if (utils.allEqualOnKeys(row, this.activeData, primaryKey)) {  // cancel active row
                    this.activeData = {};
                    const refName = this.innerMeta['name'];
                    this.$refs[refName].setCurrentRow();
                } else {
                    this.activeData = row;
                }
                this.$emit('active-change', this.activeData);
            },
            sortChange(param) {
                let {column, prop, order} = param;
                if (column.sortable === 'custom') { // 判断是否远程排序
                    const sortParams = {}, sortKey = prop + '_st';
                    sortParams[sortKey] = (order === 'ascending' ? 'asc' : 'desc');
                    this.sortParams = sortParams;
                    this.getData();
                }
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
                if (!utils.isEmpty(total)) this.pageModel['total'] = parseInt(total);
                if (!utils.isEmpty(index)) this.pageModel['index'] = parseInt(index);
                if (!utils.isEmpty(size)) this.pageModel['size'] = parseInt(size);
            },
            // get business data
            getData() {
                const {innerMeta: {data_url, columns = []}, pageModel, filterParams, sortParams, primaryKey} = this;

                if (utils.isEmpty(data_url)) {
                    console.error('lack data_url attribute');
                    return;
                }

                let params = {};
                const {index, size} = pageModel;
                const columnNames = columns.filter(column => utils.hasProp(column, 'showable') && column['showable'])
                    .map(column => column['name']);

                utils.mergeArray(columnNames, primaryKey); // 主键必请求,防止编辑/删除异常
                utils.mergeObject(params, filterParams, sortParams, {
                    'fs': columnNames.join(','),
                    'p': index,
                    's': size
                });

                this.$axios.safeGet(data_url, {
                    params: params
                }).then(resp => {
                    this.innerData = resp.data;
                    this.$emit("data-change", resp.data);
                    if (utils.hasProp(resp, 'page')) {
                        this.setPageModel(resp['page']);
                    }
                }).catch(err => {
                    this.$message.error(err.msg);
                });
            },
            initData() { // init business data
                let {page, data} = this;
                if (!utils.isUndefined(page)) {
                    this.setPageModel(page)
                }
                if (!utils.isUndefined(data)) {
                    this.innerData = data;
                    return;
                }
                if (this.innerMeta.hasOwnProperty('data_url')) {
                    this.getData();
                    return;
                }
                console.error("data or data_url in meta provide one at least!")
            }
        },
        watch: {
            'data': function (newVal, oldVal) {
                this.initData();    // 为避免data数据过大, 不进行深度监听
            },
            'innerMeta.data_url': {
                handler: function () {
                    this.initData();
                },
                immediate: false
            }
        },
        mounted() {
            this.initData();
        },
        computed: {
            primaryKey() {
                const {objectPrimaryKey} = this.meta;
                let primaryKey = utils.assertUndefined(objectPrimaryKey, defaultPrimaryKey);
                return primaryKey.split(',');
            },
            objectCode() {
                let {innerMeta: {objectCode}} = this
                utils.assert(!utils.isEmpty(objectCode), '[MetaElement] objectCode不能为空' + objectCode)
                return objectCode
            },
            columns() {
                const {innerMeta: {columns}} = this
                return columns
            },
            multiSelect() {
                const {$attrs: {'multi-select': multiSelect}, innerMeta: {multi_select}} = this;
                if (multiSelect !== undefined) return multiSelect;
                if (multi_select != undefined) return multi_select;
                return true;
            },
            operationColMode() {
                const {$attrs: {'operation-col-mode': operationColMode}, innerMeta: {operation_col_mode}} = this;
                if (operationColMode !== undefined) return operationColMode;
                if (operation_col_mode !== undefined) return operation_col_mode;
                return true;
            },
            operationBarConf() {
                return this.innerMeta['operation-bar'];
            },
            operationColumnConf() {
                return utils.mergeObject({}, this.innerMeta['operation-column'], this.$attrs['operation-column-conf']);
            },
            buttonsConf() {
                return this.innerMeta['buttons'];
            },
            // 支持无渲染的行为插槽
            actions() {
                const {doDelete} = this;
                return {doDelete};
            },
            on() {
                return this.$on.bind(this);
            }
        }
    }
</script>

<style scoped>
</style>
