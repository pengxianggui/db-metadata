<template>
  <div class="view-container" ref="container">
    <!-- operation bar -->
    <div class="operation-bar" :style="operationBarConf.style">
      <slot name="operation-bar" v-bind:conf="operationBarConf"
          v-bind:choseData="choseData" v-bind:activeData="activeData" v-if="operationBarConf['show']">
        <component :is="operationBarConf.group ? 'el-button-group' : 'div'"
                   :style="operationBarConf.style" v-bind="operationBarConf.conf"
                   v-if="operationBarConf.show">
          <slot name="prefix-btn" v-bind:conf="operationBarConf"
                v-bind:choseData="choseData" v-bind:activeData="activeData"></slot>
          <slot name="add-btn" v-bind:conf="operationBarConf.add" v-bind:add="handleAdd"
                v-bind:choseData="choseData" v-bind:activeData="activeData">
            <el-button @click="handleAdd" v-bind="operationBarConf.add.conf"
                       v-if="operationBarConf.add.show" v-authorize="operationBarConf.add.authorize">
              {{ operationBarConf.add.text }}
            </el-button>
          </slot>
          <slot name="batch-delete-btn" v-bind:conf="operationBarConf.delete"
                v-bind:choseData="choseData" v-bind:activeData="activeData"
                v-bind:batchDelete="handleBatchDelete">
            <el-button @click="handleBatchDelete($event)" v-bind="operationBarConf.delete.conf"
                       v-if="multiSelect && operationBarConf.delete.show"
                       v-authorize="operationBarConf.delete.authorize">
              {{ operationBarConf.delete.text }}
            </el-button>
          </slot>
          <slot name="expand-btn" v-bind:conf="operationBarConf.expand"
                v-bind:choseData="choseData" v-bind:activeData="activeData"
                v-bind:expand="handleExpandAll">
            <el-button @click="handleExpandAll" v-bind="operationBarConf.expand.conf"
                       v-if="operationBarConf.expand.show">
              {{ operationBarConf.expand.text }}
            </el-button>
          </slot>
          <slot name="shrink-btn" v-bind:conf="operationBarConf.shrink" v-bind:choseData="choseData"
                v-bind:activeData="activeData" v-bind:shrink="handleShrinkAll">
            <el-button @click="handleShrinkAll" v-bind="operationBarConf.shrink.conf"
                       v-if="operationBarConf.shrink.show">
              {{ operationBarConf.shrink.text }}
            </el-button>
          </slot>
          <slot name="suffix-btn" v-bind:conf="operationBarConf" v-bind:choseData="choseData"
                v-bind:activeData="activeData"></slot>
        </component>

        <slot name="float-right-btn" v-bind:conf="operationBarConf"
              v-bind:choseData="choseData" v-bind:activeData="activeData"></slot>
      </slot>
    </div>

    <el-table :id="meta.name"
              :ref="meta.name"
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
            <meta-easy-edit :object-code="objectCode" :field-code="item.name" :label="item.label || item.name">
              <template #label>{{ item.label || item.name }}</template>
            </meta-easy-edit>
          </template>
          <template #default="scope">
            <table-cell :edit="multiEdit" :data="scope" :meta="item"></table-cell>
          </template>
        </el-table-column>
      </template>

      <slot name="operation-column">
        <el-table-column v-bind="operationColumnConf.conf" :style="operationColumnConf.style"
                         v-if="operationColumnConf.show">
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
              <component :is="buttonsConf.group ? 'el-button-group' : 'div'" :style="buttonsConf.style"
                         v-if="ifShow(buttonsConf.show, scope.row)">
                <slot name="inner-before-extend-btn" v-bind:scope="scope" v-bind:conf="buttonsConf"></slot>
                <slot name="view-btn" v-bind:conf="buttonsConf.view.conf" v-bind:scope="scope" v-bind:view="handleView">
                  <el-button v-bind="buttonsConf.view.conf" @click="handleView($event, scope.row, scope.$index)"
                             v-if="ifShow(buttonsConf.view.show, scope.row)" v-authorize="buttonsConf.view.authorize">
                    {{ buttonsConf.view.text }}
                  </el-button>
                </slot>
                <slot name="edit-btn" v-bind:conf="buttonsConf.edit.conf" v-bind:scope="scope" v-bind:edit="handleEdit">
                  <el-button v-bind="buttonsConf.edit.conf" @click="handleEdit($event, scope.row, scope.$index)"
                             v-if="ifShow(buttonsConf.edit.show, scope.row)" v-authorize="buttonsConf.edit.authorize">
                    {{ buttonsConf.edit.text }}
                  </el-button>
                </slot>
                <slot name="delete-btn" v-bind:conf="buttonsConf.delete.conf" v-bind:scope="scope"
                      v-bind:delete="handleDelete">
                  <el-button v-bind="buttonsConf.delete.conf" @click="handleDelete($event, scope.row, scope.$index)"
                             v-if="ifShow(buttonsConf.delete.show, scope.row)"
                             v-authorize="buttonsConf.delete.authorize">{{ buttonsConf.delete.text }}
                  </el-button>
                </slot>
                <slot name="inner-after-extend-btn" v-bind:scope="scope" v-bind:conf="buttonsConf"></slot>
              </component>
            </slot>
          </template>
        </el-table-column>
      </slot>
    </el-table>

    <slot name="behavior" :on="on" :actions="actions"></slot>
  </div>
</template>

<script>
import {restUrl} from "../../../constant/url"
import {defaultPrimaryKey} from "../../../config"
import utils from '../../../utils'
import MetaEasyEdit from '@/../package/core/meta/src/MetaEasyEdit'
import assembleMeta from './assembleMeta'
import DefaultMeta from '../ui-conf'
import TableCell from '@/../package/view/ext/table/tableCell'
import columnsValid from "@/../package/view/ext/table/columnsValid"
import {resolvePath} from '../../../utils/url'
import {ViewMixin, ViewMetaBuilder} from '../../ext/mixins'

export default {
  name: "TableTreeView",
  mixins: [ViewMetaBuilder(DefaultMeta, assembleMeta), ViewMixin],
  components: {MetaEasyEdit, TableCell},
  data() {
    return {
      multiEdit: false, // 多行编辑模式
      show: true, // use to reRender for table
      innerData: [],
      choseData: [],
      activeData: {},
      sortParams: {}
    }
  },
  props: {
    filterParams: Object,   // 搜索面板过滤参数
  },
  methods: {
    tableReRender() {
      this.show = false;
      this.$nextTick(() => {
        this.show = true;
      });
    },
    handleExpandAll() {
      this.$reverseMerge(this.meta, {
        conf: {
          "default-expand-all": true
        }
      })
      this.tableReRender();
    },
    handleShrinkAll() {
      this.$reverseMerge(this.meta, {
        conf: {
          "default-expand-all": false
        }
      })
      this.tableReRender();
    },
    handleLoad(tree, treeNode, resolve) {
      const {meta: {conf: {lazy: isLazy}, children_data_url}} = this
      if (!isLazy) return;

      const fn = this.loadChildrens;
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
      if (this.multiSelect) {
        this.choseData = selection;
        this.$emit('chose-change', selection);
      }
      this.$emit('selection-change', selection)
    },
    extractPrimaryValue(row) {
      const {primaryKey} = this;
      return utils.extractValue(row, primaryKey);
    },
    handleView(ev, row, index) {
      if (ev) ev.stopPropagation()

      const {primaryKey} = this
      const primaryValue = utils.extractValue(row, primaryKey)
      let primaryKv = (primaryKey.length <= 1 ? primaryValue[0] : utils.spliceKvs(primaryKey, primaryValue));
      const url = restUrl.RECORD_TO_VIEW
      const params = {
        primaryKv: primaryKv
      }
      this.openFormView(url, params)
    },
    // 新增一行
    handleAdd() {
      const {activeData, treeConfig: {idKey, pidKey}} = this

      let params = {}
      if (!utils.isEmpty(activeData) && !utils.isEmpty(idKey) && !utils.isEmpty(pidKey)) {
        params[pidKey] = activeData[idKey]
      }
      this.openFormView(utils.resolvePath(restUrl.RECORD_TO_ADD, params))
    },
    handleEdit(ev, row, index) { // edit/add
      if (ev) ev.stopPropagation();
      const {primaryKey} = this;
      const primaryValue = utils.extractValue(row, primaryKey);

      let primaryKv = (primaryKey.length <= 1 ? primaryValue[0] : utils.spliceKvs(primaryKey, primaryValue));
      this.openFormView(restUrl.RECORD_TO_UPDATE, {primaryKv: primaryKv})
    },
    openFormView(url, params = {}) {
      this.$emit('open-form-view', {url: url, params: params})
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
        return
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
      const {choseData: {length: RECORD_SIZE}, meta: {delete_url}} = this;

      if (RECORD_SIZE > 1) {
        title = '确定删除选中的' + RECORD_SIZE + '条记录?';
      }

      this.$confirm(`${title} 会连同子节点一起删除。`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let url;
        if (delete_url.indexOf("?") > 0) {
          url = delete_url + '&' + primaryKvExp;
        } else {
          url = delete_url + '?' + primaryKvExp
        }
        this.$axios.delete(url).then(({msg = '删除成功'}) => {
          this.$message.success(msg);
          this.getData();
        })
      });
    },
    handleRowClick(row, col, event) {
      this.$emit('row-click', {row, col, event})
      event.ctrlKey ? this.choseRow(row) : this.activeRow(row);
    },
    choseRow(row) {
      let selected = true;
      const {primaryKey, multiSelect, tableRefName} = this;

      if (multiSelect) {
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
      const {primaryKey, tableRefName} = this;

      if (utils.allEqualOnKeys(row, this.activeData, primaryKey)) {  // cancel active row
        this.activeData = {};
        this.$refs[tableRefName].setCurrentRow();
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
      const {meta: {data_url, columns = []}, filterParams, sortParams, primaryKey} = this;

      if (utils.isEmpty(data_url)) {
        utils.printErr('缺少 data_url 属性配置')
        return;
      }

      let params = {};
      const columnNames = columns.filter(column => utils.hasProp(column, 'showable') && column['showable'])
          .map(column => column['name']);

      utils.mergeArray(columnNames, primaryKey); // 主键必请求,防止编辑/删除异常
      utils.mergeObject(params, filterParams, sortParams, {
        'fs': columnNames.join(',')
      });

      this.$axios.safeGet(data_url, {
        params: params
      }).then((resp) => {
        const {data} = resp
        this.innerData = data
        this.$emit('data-change', data)
      })
    },
    emptyData() {
      this.innerData = []
    },
    init() { // init business data
      this.getData()
    },
  },
  computed: {
    tableRefName() {
      const {meta: {name: tableRefName}} = this
      return tableRefName
    },
    primaryKey() {
      const {objectPrimaryKey} = this.meta;
      let primaryKey = utils.assertUndefined(objectPrimaryKey, defaultPrimaryKey);
      return primaryKey.split(',');
    },
    objectCode() {
      const {meta: {objectCode}} = this
      return objectCode
    },
    columns() {
      const {meta: {columns}} = this
      columnsValid(columns)
      return columns
    },
    multiSelect() {
      const {meta: {multi_select = false}} = this;
      return multi_select
    },
    operationBarConf() {
      const {meta: {"operation-bar": operationBarConf = {}}} = this
      return operationBarConf;
    },
    tableConf() {
      const {meta: {conf}} = this
      return conf
    },
    operationColumnConf() {
      const {
        meta: {'operation-column': operationColumn},
        $attrs: {'operation-column': operationColumnConf}
      } = this

      return utils.mergeObject({}, operationColumn, operationColumnConf);
    },
    treeConfig() {
      const {meta: {treeConfig}} = this;
      return treeConfig
    },
    buttonsConf() {
      const {meta: {"operation-column": {buttons: buttonsConf = {}} = {}} = {}} = this
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

<style scoped lang="scss">
.view-container {
  .operation-bar {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 5px;
  }
}
</style>
