<template>
  <el-container direction="vertical" class="el-card" ref="container">
    <!-- operation bar -->
    <slot name="operation-bar" v-bind:conf="operationBarConf" v-bind:choseData="choseData" v-if="operationBarConf['show']">
      <component :is="operationBarConf.group ? 'el-button-group' : 'div'"
                 :style="operationBarConf.style" v-bind="operationBarConf.conf"
                 v-if="operationBarConf.show">
        <slot name="prefix-btn" v-bind:conf="operationBarConf" v-bind:choseData="choseData"></slot>
        <slot name="add-btn" v-bind:conf="operationBarConf.add" v-bind:add="handleAdd">
          <el-button @click="handleAdd" v-bind="operationBarConf.add.conf"
                     v-if="operationBarConf.add.show">
            {{operationBarConf.add.text}}
          </el-button>
        </slot>
        <slot name="batch-delete-btn" v-bind:conf="operationBarConf.delete" v-bind:choseData="choseData"
              v-bind:batchDelete="handleBatchDelete">
          <el-button @click="handleBatchDelete($event)" v-bind="operationBarConf.delete.conf"
                     v-if="multiSelect && operationBarConf.delete.show">
            {{operationBarConf.delete.text}}
          </el-button>
        </slot>
        <slot name="expand-btn" v-bind:conf="operationBarConf.expand" v-bind:choseData="choseData"
              v-bind:expand="handleExpandAll">
          <el-button @click="handleExpandAll" v-bind="operationBarConf.expand.conf"
                     v-if="operationBarConf.expand.show">
            {{operationBarConf.expand.text}}
          </el-button>
        </slot>
        <slot name="shrink-btn" v-bind:conf="operationBarConf.shrink" v-bind:choseData="choseData"
              v-bind:shrink="handleShrinkAll">
          <el-button @click="handleShrinkAll" v-bind="operationBarConf.shrink.conf"
                    v-if="operationBarConf.shrink.show">
            {{operationBarConf.shrink.text}}
          </el-button>
        </slot>
        <slot name="suffix-btn" v-bind:conf="operationBarConf" v-bind:choseData="choseData"></slot>
      </component>
    </slot>

    <el-table :id="innerMeta.name"
              :ref="innerMeta.name"
              :data="innerData"
              :load="handleLoad"
              v-bind="tableConf"
              @row-click="handleRowClick"
              @sort-change="sortChange"
              @selection-change="handleSelectionChange"
              @row-dblclick="$emit('row-dblclick', $event)"

              v-if="show">

      <!-- multi select conf -->
      <template v-if="multiSelect">
        <el-table-column type="selection" width="55"></el-table-column>
      </template>

      <template v-for="item in columns">
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

      <slot name="operation-column">
        <el-table-column  v-bind="operationColumnConf.conf" v-if="operationColumnConf.show">
          <template #header>
            <span>操作</span>
            <el-popover placement="bottom-end" trigger="hover">
              <i slot="reference" class="el-icon-caret-bottom" style="cursor: pointer"></i>
              <!-- TODO 2.5 字段控制的显隐应当缓存到sessionStorage中, 缓存key应当包含instanceCode+fieldCode确保唯一 -->
              <el-checkbox v-for="item in columns"
                           v-model="item.showable"
                           :key="item.name"
                           :label="item.name || item.name"
                           @change="$forceUpdate(); getData()"
                           style="display: block;"></el-checkbox>
            </el-popover>
          </template>
          <template slot-scope="scope">
            <slot name="buttons" v-bind:scope="scope" v-bind:conf="buttonsConf">
              <component :is="buttonsConf.group ? 'el-button-group' : 'div'"
                         v-if="_showable(scope.row, buttonsConf.show)">
                <slot name="inner-before-extend-btn" v-bind:scope="scope" v-bind:conf="buttonsConf"></slot>
                <slot name="view-btn" v-bind:conf="buttonsConf.view.conf" v-bind:scope="scope" v-bind:view="handleView">
                  <el-button v-bind="buttonsConf.view.conf" @click="handleView($event, scope.row, scope.$index)"
                             v-if="_showable(scope.row, buttonsConf.view.show)">{{buttonsConf.view.text}}</el-button>
                </slot>
                <slot name="edit-btn" v-bind:conf="buttonsConf.edit.conf" v-bind:scope="scope" v-bind:edit="handleEdit">
                  <el-button v-bind="buttonsConf.edit.conf" @click="handleEdit($event, scope.row, scope.$index)"
                             v-if="_showable(scope.row, buttonsConf.edit.show)">{{buttonsConf.edit.text}}</el-button>
                </slot>
                <slot name="delete-btn" v-bind:conf="buttonsConf.delete.conf" v-bind:scope="scope" v-bind:delete="handleDelete">
                  <el-button v-bind="buttonsConf.delete.conf" @click="handleDelete($event, scope.row, scope.$index)"
                             v-if="_showable(scope.row, buttonsConf.delete.show)">{{buttonsConf.delete.text}}</el-button>
                </slot>
                <slot name="inner-after-extend-btn" v-bind:scope="scope" v-bind:conf="buttonsConf"></slot>
              </component>
            </slot>
          </template>
        </el-table-column>
      </slot>
    </el-table>

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
import {restUrl} from "../../../constant/url"
import {defaultPrimaryKey} from "../../../config"
import utils from '../../../utils'
import MetaEasyEdit from '../../meta/src/MetaEasyEdit'
import Meta from '../../mixins/meta'
import assembleMeta from './assembleMeta'
import DefaultMeta from '../ui-conf'
import TableCell from '../../tableview/src/tableCell'
import columnsValid from "../../tableview/src/columnsValid"
import showable from "../../mixins/showable"
import {resolvePath} from '../../../utils/url'

export default {
  name: "TableTreeView",
  mixins: [Meta(DefaultMeta, assembleMeta), showable],
  components: {MetaEasyEdit, TableCell},
  data() {
    return {
      multiEdit: false, // 多行编辑模式
      show: true, // use to reRender for table
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
      this.$reverseMerge(this.innerMeta, {
        conf: {
          "default-expand-all": true
        }
      })
      this.tableReRender();
    },
    handleShrinkAll() {
      this.$reverseMerge(this.innerMeta, {
        conf: {
          "default-expand-all": false
        }
      })
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
            let params = {}
            params[pidKey] = activeData[idKey]
            path = resolvePath(path, params)
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
        let url;
        if (deleteUrl.indexOf("?") > 0) {
          url = deleteUrl + '&' + primaryKvExp;
        } else {
          url = deleteUrl + '?' + primaryKvExp
        }
        this.$axios.delete(url).then(resp => {
          const {msg = '删除成功'} = resp
          this.$message.success(msg);
          this.getData();
        })
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
      })
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
    operationBarConf() {
      const {innerMeta: {"operation-bar": operationBarConf = {}}} = this
      return operationBarConf;
    },
    tableConf() {
      const {innerMeta: {conf}} = this
      return conf
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
      const {innerMeta: { "operation-column": {buttons: buttonsConf = {}} = {}} = {}} = this
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
