<template>
  <el-container direction="vertical" class="el-card" ref="container">
    <el-row justify="end">
      <el-col :span="24">
        <!-- operation bar -->
        <slot name="operation-bar" v-bind:conf="operationBarConf" v-if="operationBarConf['show']">
          <el-button-group>
            <slot name="prefix-btn" v-bind:conf="operationBarConf"></slot>
            <slot name="add-btn" v-bind:conf="operationBarConf">
              <el-button @click="handleAdd" icon="el-icon-document-add"
                         v-bind="operationBarConf">新增
              </el-button>
            </slot>
            <slot name="batch-delete-btn" v-bind:conf="operationBarConf"
                  v-bind:batchDelete="handleBatchDelete" v-if="multiMode">
              <el-button @click="handleBatchDelete($event)" type="danger" icon="el-icon-delete-solid"
                         v-bind="operationBarConf">删除
              </el-button>
            </slot>
            <slot name="expand-all-btn" v-bind:conf="operationBarConf">
              <el-button @click="handleExpandAll" icon="el-icon-caret-bottom" type="info"
                         v-bind="operationBarConf">展开全部
              </el-button>
            </slot>
            <slot name="shrink-all-btn">
              <el-button @click="handleShrinkAll" icon="el-icon-caret-right" type="info"
                         v-bind="operationBarConf">收起全部
              </el-button>
            </slot>
            <slot name="suffix-btn" v-bind:conf="operationBarConf"></slot>
          </el-button-group>
        </slot>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24">
        <el-table :id="innerMeta.name"
                  :ref="innerMeta.name"
                  :data="innerData"
                  :load="handleLoad"
                  v-bind="tableConf"
                  @row-click="handleRowClick"
                  @sort-change="sortChange"
                  @selection-change="handleSelectionChange"
                  @row-dblclick="$emit('row-dblclick', $event)"
                  :default-expand-all="expandAll"
                  v-if="show">

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
              <template slot="header">
                <meta-easy-edit :object-code="objectCode" :field-code="item.name"
                                :label="item.label || item.name" :all="true"
                                component-code="TableTreeView">
                  <template #label>{{ item.label || item.name }}</template>
                </meta-easy-edit>
              </template>
              <template #default="scope">
                <table-cell :edit="multiEdit" :data="scope" :meta="item"></table-cell>
              </template>
            </el-table-column>
          </template>
          <slot name="operation-column" v-if="operationColumnConf['show']">
            <el-table-column v-bind:width="operationColumnConf['width']"
                             v-bind:fixed="operationColumnConf['fixed']">
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
                <slot name="buttons" v-bind:scope="scope" v-if="_showable(scope.row, buttonsConf['show'])">
                  <el-button-group>
                    <slot name="inner-before-extend-btn" v-bind:scope="scope"></slot>
                    <slot name="view-btn" v-bind:conf="buttonsConf['view']['conf']"
                          v-bind:scope="scope" v-if="_showable(scope.row, buttonsConf['view']['show'])">
                      <el-button v-bind="buttonsConf['view']['conf']"
                                 @click="handleView($event, scope.row, scope.$index)"></el-button>
                    </slot>
                    <slot name="edit-btn" v-bind:conf="buttonsConf['edit']['conf']"
                          v-bind:scope="scope" v-if="_showable(scope.row, buttonsConf['edit']['show'])">
                      <el-button v-bind="buttonsConf['edit']['conf']"
                                 @click="handleEdit($event, scope.row, scope.$index)">
                      </el-button>
                    </slot>
                    <slot name="delete-btn" v-bind:conf="buttonsConf['delete']['label']"
                          v-bind:scope="scope" v-if="_showable(scope.row, buttonsConf['delete']['show'])">
                      <el-button v-bind="buttonsConf['delete']['conf']"
                                 @click="handleDelete($event, scope.row, scope.$index)">
                      </el-button>
                    </slot>
                    <slot name="inner-after-extend-btn" v-bind:scope="scope"></slot>
                  </el-button-group>
                </slot>
              </template>
            </el-table-column>
          </slot>
        </el-table>
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
import {restUrl} from "../../../constant/url";
import {defaultPrimaryKey} from "../../../config";
import utils from '../../../utils'
import MetaEasyEdit from '../../meta/src/MetaEasyEdit'
import Meta from '../../mixins/meta'
import assembleMeta from './assembleMeta'
import DefaultMeta, {CHOSE_TYPE} from '../ui-conf'
import TableCell from '../../tableview/src/tableCell'
import columnsValid from "../../tableview/src/columnsValid";
import showable from "../../mixins/showable";

export default {
  name: "TableTreeView",
  mixins: [Meta(DefaultMeta, assembleMeta), showable],
  components: {MetaEasyEdit, TableCell},
  data() {
    // 利用解构赋值防止空指针
    const {conf: {'default-expand-all': instanceExpandAll} = {}} = this.meta;
    const {conf: {'default-expand-all': defaultExpandAll} = {}} = DefaultMeta;
    const expandAll = utils.assertUndefined(instanceExpandAll, defaultExpandAll);

    return {
      multiEdit: false, // 多行编辑模式
      show: true, // use to reRender for table
      expandAll: expandAll,
      innerData: [],
      choseData: [],
      activeData: {},
      sortParams: {},
      dialogComponentMea: {}, // 弹窗内包含的组件元对象
      dialogMeta: {}, // 弹窗组件元对象
      dialogVisible: false,   // 弹窗显隐
    }
  },
  props: {
    data: Array,
    filterParams: Object,   // 搜索面板过滤参数
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
      }).then(({data}) => {
        resolve(data);
      }).catch(({msg = 'Error'}) => {
        this.$message.error(msg);
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
    handleView(ev, row, index) {
      if (ev) ev.stopPropagation()

      const {primaryKey, $compile, objectCode} = this
      const primaryValue = utils.extractValue(row, primaryKey)
      let primaryKv = (primaryKey.length <= 1 ? primaryValue[0] : utils.spliceKvs(primaryKey, primaryValue));
      const url = $compile(restUrl.RECORD_TO_VIEW, {
        objectCode: objectCode,
        primaryKv: primaryKv
      })
      this.dialog(url, {title: '详情'});
    },
    handleEdit(ev, row, index) { // edit/add
      if (ev) ev.stopPropagation();
      const {primaryKey} = this;
      const primaryValue = utils.extractValue(row, primaryKey);

      this.doEdit(primaryValue, ev, row, index); // params ev,row,index is for convenient to override
    },
    doEdit(primaryValue) {
      let url, title;
      const {activeData, primaryKey, objectCode, treeConf: {idKey, pidKey}} = this;

      if (!utils.isEmpty(primaryValue)) {
        title = '编辑';
        let primaryKv = (primaryKey.length <= 1 ? primaryValue[0] : utils.spliceKvs(primaryKey, primaryValue));
        url = this.$compile(restUrl.RECORD_TO_UPDATE, {
          objectCode: objectCode,
          primaryKv: primaryKv
        });
      } else {
        title = '新增';
        let fillParams = function (path) {
          if (!utils.isEmpty(activeData)) {
            path += ('?' + pidKey + '=' + activeData[idKey])
          }
          return path
        }

        url = this.$compile(fillParams(restUrl.RECORD_TO_ADD), {objectCode: objectCode});
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

    /**
     * default remove the assembly logic is based on primaryKey get on
     * 单条删除("id=pk1_v1,pk2_v2" 或 "pk=v"), 批量删除("id=pk1_v1,pk2_v2&id=pk1_v3,pk2_v4" 或 "pk=v1,v2,v3")
     */
    doDelete(primaryKvExp) {
      let title = '确定删除此条记录?';

      const {choseData: {length: RECORD_SIZE}, innerMeta: {delete_url: deleteUrl}} = this;
      if (RECORD_SIZE > 1) {
        title = '确定删除选中的' + RECORD_SIZE + '条记录?';
      }

      this.$confirm(`${title} 会连同子节点一起删除。`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const url = deleteUrl + '?' + primaryKvExp;
        this.$axios.delete(url).then(resp => {
          const {msg = '删除成功'} = resp
          this.$message.success(msg);
          this.getData();
        }).catch(({msg = 'Error'}) => {
          this.$message.error(msg);
        });
      });
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
      const {primaryKey, operLogic: {'chose_type': choseType} = {}} = this;

      if (choseType === CHOSE_TYPE.toggle && utils.allEqualOnKeys(row, this.activeData, primaryKey)) {  // cancel active row
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
        const sortParams = {}, sortKey = prop + '_st';
        sortParams[sortKey] = (order === 'ascending' ? 'asc' : 'desc');
        this.sortParams = sortParams;
        this.getData();
      }
    },

    // get business data
    getData() {
      const {innerMeta, filterParams, sortParams} = this;

      if (!utils.hasProp(innerMeta, 'data_url')) {
        console.error('lack data_url attribute');
        return;
      }

      let params = {};
      const {data_url: url} = innerMeta;
      const columnNames = (innerMeta['columns'] || [])
          .filter(column => utils.hasProp(column, 'showable') && column['showable'])
          .map(column => column['name']);

      utils.mergeArray(columnNames, this.primaryKey); // 主键必请求,防止编辑/删除异常
      utils.mergeObject(params, filterParams, sortParams, {
        'fs': columnNames.join(',')
      });

      this.$axios.safeGet(url, {
        params: params
      }).then(resp => {
        this.innerData = resp.data;
        this.$emit("update:data", resp.data);
      }).catch(({msg = 'Error'}) => {
        this.$message.error(msg);
      });
    },
    initData() { // init business data
      let {data} = this;
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
    primaryKey() {
      const {objectPrimaryKey} = this.meta;
      let primaryKey = utils.assertUndefined(objectPrimaryKey, defaultPrimaryKey);
      return primaryKey.split(',');
    },
    objectCode() {
      const {innerMeta: {objectCode}} = this
      utils.assert(!utils.isEmpty(objectCode), '[MetaElement] objectCode不能为空' + objectCode)
      return objectCode
    },
    columns() {
      const {innerMeta: {columns}} = this
      columnsValid(columns)
      return columns
    },
    multiSelect() {
      const {$attrs: {'multi-select': multiSelect}, innerMeta: {multi_select}} = this;
      return utils.assertEmpty(multiSelect, multi_select) || false
    },
    operLogic() {
      const {innerMeta: {'oper-logic': operLogic} = {}} = this
      return operLogic
    },
    operationBarConf() {
      const {innerMeta: {"operation-bar": operationBarConf = {}}} = this
      return operationBarConf;
    },
    tableConf() {
      const {innerMeta: {conf}, $attrs, $reverseMerge} = this
      return $reverseMerge(conf, $attrs)
    },
    operationColumnConf() {
      const {
        innerMeta: {'operation-column': operationColumn},
        $attrs: {'operation-column': operationColumnConf}
      } = this

      return utils.mergeObject({}, operationColumn, operationColumnConf);
    },
    treeConf() {
      const {innerMeta: {treeInTableConfig: treeConf}} = this;
      return treeConf
    },
    tlRefName() {
      return this.innerMeta['name'];
    },
    multiMode() {
      return this.innerMeta['multi_select'];
    },
    buttonsConf() {
      const {innerMeta: {buttons: buttonsConf = {}}} = this
      return buttonsConf
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
