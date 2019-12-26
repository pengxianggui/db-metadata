<template>
    <el-container direction="vertical" class="el-card" ref="container">
        <el-row justify="end">
            <el-col :span="24">
                <!-- operation bar -->
                <slot name="operation-bar"
                      v-bind:conf="innerMeta['operation-bar']"
                      v-bind:operations="{handleAdd, handleBatchDelete}">
                    <el-button-group>
                        <slot name="prefix-btn" v-bind:conf="innerMeta['operation-bar']"></slot>
                        <slot name="add-btn" v-bind:conf="innerMeta['operation-bar']" v-bind:add="handleAdd">
                            <el-button @click="handleAdd" icon="el-icon-document-add"
                                       v-bind="innerMeta['operation-bar']">新增
                            </el-button>
                        </slot>
                        <slot name="batch-delete-btn" v-bind:conf="innerMeta['operation-bar']"
                              v-bind:batchDelete="handleBatchDelete" v-if="multiMode">
                            <el-button @click="handleBatchDelete($event)" type="danger" icon="el-icon-delete-solid"
                                       v-bind="innerMeta['operation-bar']">删除
                            </el-button>
                        </slot>
                        <slot name="expand-all-btn">
                            <el-button @click="handleExpandAll" icon="el-icon-caret-bottom" type="info"
                                       v-bind="innerMeta['operation-bar']">展开全部
                            </el-button>
                        </slot>
                        <slot name="shrink-all-btn">
                            <el-button @click="handleShrinkAll" icon="el-icon-caret-right" type="info"
                                       v-bind="innerMeta['operation-bar']">收起全部
                            </el-button>
                        </slot>
                        <slot name="suffix-btn" v-bind:conf="innerMeta['operation-bar']"></slot>
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
                    :load="handleLoad"
                    v-bind="$reverseMerge(innerMeta.conf, $attrs)"
                    @row-click="handleRowClick"
                    @sort-change="sortChange"
                    @selection-change="handleSelectionChange"
                    @row-dblclick="$emit('row-dblclick', $event)"
                    :default-expand-all="expandAll"
                    v-if="show">

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
                                <pop-menu trigger="right-click" v-if="$hasAuth('ADMIN')">
                                    <template #label>{{item.label || item.name}}</template>
                                    <list>
                                        <list-item @click="editMetaObject">编辑元对象({{innerMeta.objectCode}})</list-item>
                                        <list-item @click="editMetaField(item.name)">编辑元字段({{item.name}})</list-item>
                                        <list-item @hover="getComponentCode(innerMeta.objectCode)">
                                            <pop-menu trigger="hover" placement="right">
                                                <template #label>编辑实例UI配置({{innerMeta.objectCode}})</template>
                                                <template #default>
                                                    <list>
                                                        <list-item v-for="compCode in componentCodes" :key="compCode"
                                                                   @click="editInstanceConf(innerMeta.objectCode, compCode)">
                                                            {{compCode}}
                                                        </list-item>
                                                    </list>
                                                </template>
                                            </pop-menu>
                                        </list-item>
                                        <list-item @hover="getComponentCode(innerMeta.objectCode)">
                                            <pop-menu trigger="hover" placement="right">
                                                <template #label>编辑实例字段UI配置({{item.name}})</template>
                                                <template #default>
                                                    <list>
                                                        <list-item v-for="compCode in componentCodes" :key="compCode"
                                                                   @click="editInstanceFieldConf(innerMeta.objectCode, compCode, item.name)">
                                                            {{compCode}}
                                                        </list-item>
                                                    </list>
                                                </template>
                                            </pop-menu>
                                        </list-item>
                                    </list>
                                </pop-menu>
                                <span v-if="!$hasAuth('ADMIN')">{{item.label || item.name}}</span>
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
    import {CONSTANT, DEFAULT, URL} from '@/constant'
    import utils from '@/utils'

    export default {
        name: "TableTreeList",
        data() {
            const instanceConf = this.meta['conf'];
            const defaultConf = DEFAULT.TableTreeList['conf'];
            const defaultExpandAll = instanceConf['default-expand-all'] === true ?
                instanceConf['default-expand-all'] : defaultConf['default-expand-all'];

            return {
                show: true, // use to reRender for table
                expandAll: defaultExpandAll,
                innerData: [],
                choseData: [],
                activeData: {},
                pageModel: {
                    size: 10,
                    index: 1,
                    total: 0
                },
                dialogComponentMea: {}, // 弹窗内包含的组件元对象
                dialogMeta: {}, // 弹窗组件元对象
                dialogVisible: false,   // 弹窗显隐
                componentCodes: [], // 当前objectCode 配置过的所有componentCode(容器层, 及元对象层)
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
            page: Object,
            treeProps: {
                type: Object,
                default: function () {
                    return {hasChildren: 'hasChildren', children: 'children'}
                },
                validator: function (value) {
                    return value.hasOwnProperty('hasChildren') && value.hasOwnProperty('children');
                }
            },
            loadChildrens: Function
        },
        methods: {
            tableReRender() {
                this.show = false;
                this.$nextTick(() => {
                    this.show = true;
                });
            },
            handleExpandAll() {
                this.expandAll = true;
                this.tableReRender();
            },
            handleShrinkAll() {
                this.expandAll = false;
                this.tableReRender();
            },
            handleLoad(tree, treeNode, resolve) {
                const isLazy = this.innerMeta['conf']['lazy'];
                if (!isLazy) return;

                const fn = this.loadChildrens;
                const children_data_url = this.innerMeta['children_data_url'];
                const parentPrimary = tree[this.primaryKey];

                if (!utils.isEmpty(fn)) {
                    fn({tree, treeNode, resolve});
                    return;
                }
                this.$axios.safeGet(children_data_url, {
                    parentPrimary: parentPrimary
                }).then(resp => {
                    resolve(resp.data);
                }).catch(err => {
                    this.$message.error(err.msg);
                    console.error(err.msg());
                })
            },
            handleSelectionChange(selection) {
                if (this.multiMode) {
                    this.choseData = selection;
                    this.$emit('chose-change', selection);
                }
            },
            extractPrimaryValue(row) {
                const {primaryKey} = this;
                return utils.extractValue(row, primaryKey);
            },
            handleEdit(ev, row, index) { // edit/add
                if (ev) ev.stopPropagation();
                const primaryValue = this.extractPrimaryValue(row);
                this.doEdit(primaryValue, ev, row, index); // params ev,row,index is for convenient to override
            },
            doEdit(primaryValue) {
                let url, title;
                if (!utils.isEmpty(primaryValue)) {
                    title = '编辑';
                    let primaryKey = this.primaryKey;
                    let primaryKv = utils.spliceKvs(primaryKey, primaryValue);
                    url = this.$compile(URL.RECORD_TO_UPDATE, {
                        objectCode: this.innerMeta['objectCode'],
                        primaryKv: primaryKv
                    });
                } else {
                    title = '新增';
                    url = this.$compile(URL.RECORD_TO_ADD, {objectCode: this.innerMeta['objectCode']});
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
                const primaryValue = this.extractPrimaryValue(row);
                const primaryKv = utils.spliceKvs(primaryKey, primaryValue);

                this.doDelete(primaryKv, ev, row, index);
            },
            // 批量删除
            handleBatchDelete(ev) {
                let primaryKv = [];
                let {primaryKey} = this;
                this.choseData.forEach(row => {
                    let kv = utils.spliceKvs(primaryKey, this.extractPrimaryValue(row));
                    primaryKv.push(kv);
                });

                if (this.choseData.length > 0) {
                    this.doDelete(primaryKv, ev);
                    return
                }
                this.$message.warning('请至少选择一项!');
            },
            // default remove the assembly logic is based on primaryKey get on
            doDelete(primaryKv) {
                // PXG_TODO
            },
            // 新增一行
            handleAdd() {
                this.doEdit();
            },
            handleRowClick(row, col, event) {
                event.ctrlKey ? this.choseRow(row) : this.activeRow(row);
            },
            choseRow(row) {
                let selected = true;
                const {primaryKey, multiMode} = this;

                if (multiMode) {
                    let {tlRefName} = this;
                    for (let i = 0; i < this.$refs[tlRefName]['selection'].length; i++) { // cancel chose judge
                        let choseItem = this.$refs[tlRefName]['selection'][i];
                        if (utils.allEqualOnKeys(row, choseItem, primaryKey)) {
                            selected = false;
                            break
                        }
                    }
                    this.$refs[tlRefName].toggleRowSelection(row, selected);
                }
            },
            activeRow(row) {
                const {primaryKey} = this;

                if (utils.allEqualOnKeys(row, this.activeData, primaryKey)) {  // cancel active row
                    this.activeData = {};
                    const {tlRefName} = this;
                    this.$refs[tlRefName].setCurrentRow();
                } else {
                    this.activeData = row;
                }
                this.$emit('active-change', this.activeData);
            },
            sortChange(param) {
                let {column, prop, order} = param;
                if (column.sortable === 'custom') { // 判断是否远程排序
                    let sortKey = prop + '_st';
                    let params = {};
                    params[sortKey] = (order === 'ascending' ? 'asc' : 'desc');
                    this.getData(params);
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

            // fast edit meta-data or edit ui conf ----------------------------------------------------------
            editMetaObject() {
                let {objectCode} = this.innerMeta;
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
                let {objectCode} = this.innerMeta;
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
            editInstanceConf(objectCode, componentCode) {
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
            editInstanceFieldConf(objectCode, componentCode, fieldCode) {
                this.editInstanceConf(objectCode, componentCode); // just edit the ui conf of field named fieldCode. anchor point ?
            },
            getComponentCode(objectCode) {
                if (!utils.isEmpty(this.componentCodes)) return;
                this.$axios.get(this.$compile(URL.LOAD_COMP_BY_OBJECT, {objectCode: objectCode, kv: false}))
                    .then(resp => {
                        this.componentCodes = resp.data;
                    })
            },

            // get business data
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
                let {page, data} = this;
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
            innerMeta() {
                if (this.meta.hasOwnProperty('columns')) { // init column.showable of columns
                    this.meta['columns'].forEach(item => {
                        if (!item.hasOwnProperty('showable')) { // default true
                            item.showable = true;
                        }
                    });
                }
                return this.$merge(this.meta, DEFAULT.TableTreeList);
            },
            primaryKey() {
                const {objectPrimaryKey} = this.meta;
                const defaultPrimaryKey = CONSTANT.DEFAULT_PRIMARY_KEY;
                let primaryKey;

                if (utils.isEmpty(objectPrimaryKey)) {
                    console.warn('Missing primary key info! will use default primaryKey:%s', defaultPrimaryKey);
                    primaryKey = defaultPrimaryKey.split(',');
                } else {
                    primaryKey = objectPrimaryKey.split(',');
                }
                return primaryKey;
            },
            tlRefName() {
                return this.innerMeta['name'];
            },
            multiMode() {
                return this.innerMeta['multi_select'];
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
