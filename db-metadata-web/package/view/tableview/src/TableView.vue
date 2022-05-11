<template>
  <div class="view-container" ref="container">
    <!-- 操作条 -->
    <div class="operation-bar" :style="operationBarConf.style">
      <slot name="operation-bar" v-bind:conf="operationBarConf"
            v-bind:choseData="choseData" v-bind:activeData="activeData">

        <component :is="operationBarConf.group ? 'el-button-group' : 'div'"
                   v-bind="operationBarConf.conf"
                   :class="{'not-btn-group': !operationBarConf.group}"
                   v-if="operationBarConf.show">
          <slot name="prefix-btn" v-bind:conf="operationBarConf"
                v-bind:choseData="choseData" v-bind:activeData="activeData"></slot>
          <slot name="add-btn" v-bind:conf="operationBarConf.add"
                v-bind:choseData="choseData" v-bind:activeData="activeData" v-bind:add="handleAdd">
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
          <slot name="suffix-btn" v-bind:conf="operationBarConf"
                v-bind:choseData="choseData" v-bind:activeData="activeData"></slot>
        </component>

        <slot name="float-right-btn" v-bind:conf="operationBarConf"
              v-bind:choseData="choseData" v-bind:activeData="activeData"></slot>

      </slot>
    </div>

    <!-- 表格主体 -->
    <!-- TODO name作为id不保险，改为instanceCode -->
    <el-table :id="tableRefName"
              :ref="tableRefName"
              v-bind="tableConf"
              :data="innerData"
              @row-click="handleRowClick"
              @row-dblclick="handleRowDbClick"
              @sort-change="sortChange"
              @selection-change="handleSelectionChange">

      <!-- multi select conf -->
      <template v-if="multiSelect">
        <el-table-column type="selection" width="55"></el-table-column>
      </template>

      <el-table-column label="序号" width="60" v-if="showIndex">
        <template #default="{row, column, $index}">{{ $index + 1 }}</template>
      </el-table-column>

      <el-table-column v-for="item in columns"
                       :key="item.code"
                       v-bind="item.conf"
                       :prop="item.name"
                       :label="item.label"
                       show-overflow-tooltip>
        <template #header>
          <meta-easy-edit :object-code="objectCode" :field-code="item.name" :label="item.label">
            <template #label>{{ item.label }}</template>
          </meta-easy-edit>
        </template>
        <template #default="scope">
          <table-cell :edit="multiEdit" :data="scope" :meta="item"></table-cell>
        </template>
      </el-table-column>

      <slot name="operation-column">
        <el-table-column v-bind="operationColumnConf.conf" :style="operationColumnConf.style"
                         v-if="operationColumnConf.show">
          <template #header>
            <span>操作</span>
            <el-popover placement="bottom-end" trigger="hover">
              <i slot="reference" class="el-icon-caret-bottom" style="cursor: pointer"></i>
              <!-- TODO 2.5 字段控制的显隐应当缓存到sessionStorage中, 缓存key应当包含instanceCode+fieldCode确保唯一 -->
              <el-checkbox v-for="(v, k) in showColumns"
                           v-model="v.show"
                           :key="k"
                           :label="v.label"
                           @change="$forceUpdate()"
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
                      v-bind:del="handleDelete">
                  <el-button v-bind="buttonsConf.delete.conf" @click="handleDelete($event, scope.row, scope.$index)"
                             v-if="ifShow(buttonsConf.delete.show, scope.row)"
                             v-authorize="buttonsConf.delete.authorize">
                    {{ buttonsConf.delete.text }}
                  </el-button>
                </slot>
                <slot name="inner-after-extend-btn" v-bind:scope="scope" v-bind:conf="buttonsConf"></slot>
              </component>
            </slot>
          </template>
        </el-table-column>
      </slot>
    </el-table>

    <!-- 底部分页 -->
    <slot name="pagination" v-bind:pageModel="pageModel">
      <div class="pagination-bar" :style="paginationConf.style">
        <el-pagination v-bind="paginationConf.conf"
                       :page-size.sync="pageModel.size"
                       :current-page.sync="pageModel.index"
                       :total="pageModel.total"
                       @size-change="sizeChange"
                       @current-change="getData"
                       v-if="paginationConf.show"></el-pagination>
        <slot name="pagination-extend" v-bind:choseData="choseData" v-bind:activeData="activeData"
              v-bind:pageModel="pageModel"></slot>
      </div>
    </slot>

    <slot name="behavior" :on="on" :actions="actions"></slot>
  </div>
</template>

<script>
import utils from '../../../utils'
import {restUrl} from "../../../constant/url";
import {defaultPrimaryKey} from "../../../config";
import MetaEasyEdit from '@/../package/core/meta/src/MetaEasyEdit'
import assembleMeta from './assembleMeta'
import TableCell from '@/../package/view/ext/table/tableCell'
import DefaultMeta from '../ui-conf'
import columnsValid from "@/../package/view/ext/table/columnsValid";
import {assertBoolean, isEmpty} from "@/../package/utils/common";
import {ViewMixin, ViewMetaBuilder} from '../../ext/mixins'

export default {
  name: "TableView",
  mixins: [ViewMetaBuilder(DefaultMeta, assembleMeta), ViewMixin],
  components: {MetaEasyEdit, TableCell},
  data() {
    return {
      multiEdit: false, // 多行编辑模式
      innerData: [],
      choseData: [],
      activeData: {},
      sortParams: {},
      showColumns: {}, // 控制列的显隐 {col1: true}
      pageModel: {
        size: 10,
        index: 1,
        total: 0
      }
    }
  },
  props: {
    filterParams: Object, // 搜索面板过滤参数
  },
  methods: {
    handleSelectionChange(selection) {
      if (this.multiSelect) {
        this.choseData = selection;
        this.$emit('chose-change', selection);
      }
      this.$emit('selection-change', selection)
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
      this.openFormView(url, params);
    },
    handleEdit(ev, row, index) { // edit/add
      if (ev) ev.stopPropagation();
      const {primaryKey} = this;
      const primaryValue = utils.extractValue(row, primaryKey);

      this.doEdit(primaryValue); // params ev,row,index is for convenient to override
    },
    doEdit(primaryValue) {
      const {primaryKey} = this
      let url, params;

      if (!utils.isEmpty(primaryValue)) { // 更新
        let primaryKv = (primaryKey.length <= 1 ? primaryValue[0] : utils.spliceKvs(primaryKey, primaryValue));
        url = restUrl.RECORD_TO_UPDATE
        params = {primaryKv: primaryKv}
      } else { // 新增
        url = restUrl.RECORD_TO_ADD
      }
      this.openFormView(url, params);
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

      this.$confirm(title, '提示', {
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
    setPage(index) {
      this.pageModel['index'] = index;
      this.getData();
    },
    sizeChange() {
      this.setPage(1); // jump to page one
    },
    setPageModel(page) {
      const {total, index, size} = page;
      if (!utils.isEmpty(total)) this.pageModel['total'] = parseInt(total);
      if (!utils.isEmpty(index)) this.pageModel['index'] = parseInt(index);
      if (!utils.isEmpty(size)) this.pageModel['size'] = parseInt(size);
    },
    // get business data
    getData() {
      const {meta: {data_url}, columns, pageModel, filterParams, sortParams, primaryKey} = this;

      if (utils.isEmpty(data_url)) {
        utils.printErr('缺少 data_url 属性配置')
        return;
      }

      let params = {};
      const {index, size} = pageModel;

      const columnNames = columns.map(column => column['name']);

      utils.mergeArray(columnNames, primaryKey); // 主键必请求,防止编辑/删除异常
      utils.mergeObject(params, filterParams, sortParams, {
        'fs': columnNames.join(','),
        'p': index,
        's': size
      });

      this.$axios.safeGet(data_url, {
        params: params
      }).then(resp => {
        const {data, page} = resp
        this.innerData = data
        this.$emit("data-change", data)
        if (utils.hasProp(resp, 'page')) {
          this.setPageModel(page);
        }
        if (index > 1 && (isEmpty(data) || data.length <= 0)) {
          this.setPage(1) // 若查询的是非首页, 且没有数据, 则默认回到首页
        }
      })
    },
    // set business data to empty
    emptyData() {
      this.innerData = []
    },
    init() { // init business data
      this.getData()
    }
  },
  computed: {
    tableRefName() {
      const {meta: {name: tableRefName}} = this
      return tableRefName
    },
    primaryKey() {
      const {meta: {objectPrimaryKey} = {}} = this;
      let primaryKey = utils.assertUndefined(objectPrimaryKey, defaultPrimaryKey);
      return primaryKey.split(',');
    },
    objectCode() {
      let {meta: {objectCode}} = this
      return objectCode
    },
    columns() {
      const {meta: {columns = []}, showColumns} = this
      columnsValid(columns)
      return columns.filter(c => showColumns[c.name].show)
    },
    showIndex() {
      const {meta: {show_index = false}} = this
      return show_index
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
      const {meta: {conf}, $attrs} = this
      const tableConf = {}
      this.$reverseMerge(tableConf, conf)
      this.$reverseMerge(tableConf, $attrs)
      return tableConf
    },
    operationColumnConf() {
      const {meta: {'operation-column': operationColumn}} = this
      const {'operation-column': defaultOperationColumn} = DefaultMeta
      return utils.assertEmpty(operationColumn, defaultOperationColumn);
    },
    paginationConf() {
      const {meta: {pagination: paginationConf = {}}} = this
      return paginationConf
    },
    buttonsConf() {
      const {buttons: buttonsConf = {}} = this.operationColumnConf
      return buttonsConf
    },
    // 支持无渲染的行为插槽 TODO 2.3 用法不明确, 移除
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

    .not-btn-group > * {
      margin: 0 5px;
    }
  }

  .pagination-bar {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
}
</style>
