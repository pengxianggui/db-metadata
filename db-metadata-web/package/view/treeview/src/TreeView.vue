<template>
  <div class="view-container">
    <slot name="operation-bar" v-bind:conf="operationBarConf" v-bind:choseData="choseData"
          v-bind:operations="{handleAdd, handleBatchDelete}">
      <component :is="operationBarConf.group ? 'el-button-group' : 'div'"
                 :style="operationBarConf.style" v-bind="operationBarConf.conf"
                 class="opr-bar"
                 v-if="operationBarConf.show">
        <slot name="prefix-btn" v-bind:conf="operationBarConf" v-bind:choseData="choseData" v-bind:activeData="activeData"></slot>
        <slot name="add-btn" v-bind:conf="operationBarConf.add" v-bind:add="handleAdd"
              v-bind:activeData="activeData" v-bind:choseData="choseData">
          <el-button @click="handleAdd" v-bind="operationBarConf.add.conf"
                     v-if="operationBarConf.add.show && editable" v-authorize="operationBarConf.add.authorize">
            {{ operationBarConf.add.text }}
          </el-button>
        </slot>
        <slot name="edit-btn" v-bind:choseData="choseData" v-bind:activeData="activeData"
              v-bind:conf="operationBarConf" v-bind:edit="handleEdit">
          <el-button @click="handleEdit" v-bind="operationBarConf.edit.conf"
                     v-if="operationBarConf.edit.show && editable" v-authorize="operationBarConf.edit.authorize">
            {{ operationBarConf.edit.text }}
          </el-button>
        </slot>
        <slot name="batch-delete-btn" v-bind:choseData="choseData" v-bind:activeData="activeData"
              v-bind:conf="operationBarConf.delete" v-bind:batchDelete="handleBatchDelete">
          <el-button @click="handleDelete" v-bind="operationBarConf.delete.conf"
                     v-if="operationBarConf.delete.show && editable" v-authorize="operationBarConf.delete.authorize">
            {{ operationBarConf.delete.text }}
          </el-button>
        </slot>
        <el-dropdown @command="handleCommand">
          <el-button v-bind="operationBarConf.conf">
            <span>操作</span>
            <i class="el-icon-arrow-down el-icon--right"></i>
          </el-button>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="handleExpandAll">
              <i class="el-icon-caret-bottom"></i>
              <span>展开所有</span>
            </el-dropdown-item>
            <el-dropdown-item command="handleShrinkAll">
              <i class="el-icon-caret-right"></i>
              <span>收起所有</span>
            </el-dropdown-item>
            <el-dropdown-item command="handleChoseAll" v-if="multiSelect">
              <i class="el-icon-circle-check"></i>
              <span>全选</span>
            </el-dropdown-item>
            <el-dropdown-item command="handleCleanChose" v-if="multiSelect">
              <i class="el-icon-circle-close"></i>
              <span>取消勾选</span>
            </el-dropdown-item>
            <el-dropdown-item command="handleReverseChose" v-if="multiSelect">
              <i class="el-icon-success"></i>
              <span>反选</span>
            </el-dropdown-item>
            <slot name="suffix-btn" v-bind:conf="operationBarConf" v-bind:choseData="choseData"
                v-bind:activeData="activeData"></slot>
          </el-dropdown-menu>
        </el-dropdown>
      </component>
    </slot>

    <!-- name作为ref不保险 -->
    <el-tree :ref="treeRefName" :data="innerData"
             v-bind="treeConf"
             :props="props"
             @node-click="handleNodeClick"
             @node-contextmenu="$emit('node-contextmenu')"
             @check-change="$emit('check-change')"
             @check="handleNodeCheck"
             @current-change="$emit('current-change')"
             @node-expand="$emit('node-expand')"
             @node-collapse="$emit('node-collapse')">
      <template slot-scope="{ node, data }">
        <slot v-bind:node="node" v-bind:data="data">
          <span class="el-tree-node__label">{{ data[meta.conf['props']['label']] }}</span>
        </slot>
      </template>
    </el-tree>

    <div style="float: right">
      <meta-easy-edit :object-code="meta.objectCode">
        <template #label><i class="el-icon-setting"></i></template>
      </meta-easy-edit>
    </div>
  </div>
</template>

<script>
import utils from '../../../utils'
import {defaultPrimaryKey} from '../../../config'
import DefaultMeta from '../ui-conf'
import MetaEasyEdit from '@/../package/core/meta/src/MetaEasyEdit'
import {ViewMetaBuilder} from "../../ext/mixins";
import {assertEmpty, isEmpty} from "../../../utils/common";
import {restUrl} from "../../../constant/url";

export default {
  mixins: [ViewMetaBuilder(DefaultMeta)],
  name: "TreeView",
  components: {MetaEasyEdit},
  data() {
    return {
      innerData: [],
      activeData: {},
      choseData: [],
      halfChoseData: []
    }
  },
  methods: {
    getAllNodes() {
      return this.treeRef.store._getAllNodes();
    },
    getCheckedNodes() {
      return this.treeRef.getCheckedNodes();
    },
    handleCommand(fn) {
      if (this[fn]) this[fn]();
    },
    handleReverseChose() {
      this.getAllNodes().forEach(node => node.checked = !node.checked);
      this.choseData = this.getCheckedNodes()
      this.$emit('chose-change', this.getCheckedNodes())
    },
    handleChoseAll() {
      this.treeRef.setCheckedNodes(this.innerData);
      this.choseData = this.getCheckedNodes()
      this.$emit('chose-change', this.getCheckedNodes())
    },
    handleCleanChose() {
      this.treeRef.setCheckedKeys([]);
      this.choseData = this.getCheckedNodes()
      this.$emit('chose-change', this.getCheckedNodes())
    },
    handleExpandAll() {
      this.getAllNodes().forEach(node => node.expanded = true);
    },
    handleShrinkAll() {
      this.getAllNodes().forEach(node => node.expanded = false);
    },
    handleAdd() {
      const params = {}
      const {activeData = {}, treeConfig: {idKey, pidKey}} = this

      if (!utils.isEmpty(activeData) && !utils.isEmpty(idKey) && !utils.isEmpty(pidKey)) {
        params[pidKey] = activeData[idKey]
      }
      this.openFormView(utils.resolvePath(restUrl.RECORD_TO_ADD, params))
    },
    handleEdit() {
      const {activeData, primaryKey} = this
      if (isEmpty(activeData)) {
        this.$message.warning('请先点选一个节点！')
        return
      }

      const primaryValue = utils.extractValue(activeData, primaryKey)
      let primaryKv = (primaryKey.length <= 1 ? primaryValue[0] : utils.spliceKvs(primaryKey, primaryValue));
      this.openFormView(restUrl.RECORD_TO_UPDATE, {primaryKv: primaryKv})
    },
    // doEdit(primaryValue) {
    //   const {primaryKey} = this
    //   let url, params
    //
    //   if (!utils.isEmpty(primaryValue)) { // 更新
    //     let primaryKv = (primaryKey.length <= 1 ? primaryValue[0] : utils.spliceKvs(primaryKey, primaryValue));
    //
    //     url = restUrl.RECORD_TO_UPDATE
    //     params = {primaryKv: primaryKv}
    //   } else { // 新增
    //     url = restUrl.RECORD_TO_ADD
    //   }
    //   this.openFormView(url, params);
    // },
    // 删除单行
    handleDelete() {
      const {activeData, choseData} = this
      if (!isEmpty(choseData)) {
        this.handleBatchDelete(choseData)
        return
      }
      if (!isEmpty(activeData)) {
        this.handleBatchDelete([activeData])
        return
      }
      this.$message.warning('请点击或勾选要删除的节点')
      // const primaryValue = utils.extractValue(activeData, primaryKey);
      // let primaryKvExp;
      // if (primaryKey.length > 1 && primaryValue.length > 1) { // 联合主键, 目标: primaryKvExp="id=pk1_v1,pk2_v2"
      //   primaryKvExp = 'id=' + utils.spliceKvs(primaryKey, primaryValue);
      // } else {    // 单主键, 目标: primaryKvExp="pk=v"
      //   primaryKvExp = utils.spliceKv(primaryKey[0], primaryValue[0], '=');
      // }
      // this.doDelete([activeData], primaryKvExp);
    },
    // 批量删除
    handleBatchDelete(dataArr) {
      if (dataArr.length <= 0) {
        this.$message.warning('请至少选择一项!');
        return
      }

      let {primaryKey} = this;
      let primaryValue;
      let primaryKvExp;

      if (primaryKey.length > 1) {    // 联合主键, 目标: primaryKvExp="id=pk1_v1,pk2_v2&id=pk1_v3,pk2_v4"
        let primaryKvExpArr = [];
        dataArr.forEach(row => {
          primaryValue = utils.extractValue(row, primaryKey);
          primaryKvExpArr.push('id=' + utils.spliceKvs(primaryKey, primaryValue));
        });
        primaryKvExp = primaryKvExpArr.join('&');
        this.doDelete(dataArr, primaryKvExp);
      } else {    // 单主键 目标: primaryKvExp="pk=v1&pk=v2&pk=v3"
        primaryValue = dataArr.map(row => row[primaryKey[0]]);
        let primaryKvExpArr = primaryValue.map(value => utils.spliceKv(primaryKey[0], value, "="));
        primaryKvExp = primaryKvExpArr.join('&');

        this.doDelete(dataArr, primaryKvExp);
      }
    },
    /**
     * default remove the assembly logic is based on primaryKey get on
     * 单条删除("id=pk1_v1,pk2_v2" 或 "pk=v"), 批量删除("id=pk1_v1,pk2_v2&id=pk1_v3,pk2_v4" 或 "pk=v1,v2,v3")
     */
    doDelete(data, primaryKvExp) {
      const {meta: {delete_url, conf: { props: {label} = {}}}} = this;

      const labels = data.map(d => d[label])
      const deleteDataLabels = labels.join(',')
      let title = `确定删除此条记录: ${deleteDataLabels}。若存在子节点,会一并删除`;

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
    openFormView(url, params = {}) {
      this.$emit('open-form-view', {url: url, params: params})
    },
    handleNodeClick(row, node, event) {
      const {primaryKey} = this;

      if (row[primaryKey] === this.activeData[primaryKey]) {  // cancel active row
        this.activeData = {};
        this.treeRef.setCurrentKey(null)
      } else {
        this.activeData = row;
      }

      this.$emit('node-click', row, node, event)
      this.$emit('active-change', this.activeData);
    },
    handleNodeCheck(row, {checkedNodes, checkedKeys, halfCheckedNodes, halfCheckedKeys}) {
      const {multiSelect} = this;
      if (!multiSelect) return;
      this.choseData = checkedNodes;
      this.halfChoseData = halfCheckedNodes;
      this.$emit('check', row, {checkedNodes, checkedKeys, halfCheckedNodes, halfCheckedKeys})
      this.$emit('chose-change', checkedNodes);
    },

    filter(value) {
      this.treeRef.filter(value);
    },
    getChechedNodes() {
      return this.treeRef.getCheckedNodes();
    },
    getCheckedKeys() {
      return this.treeRef.getCheckedKeys();
    },
    setCheckedKeys(keys) {
      this.treeRef.setCheckedKeys(keys);
    },
    getHalfCheckedNodes() {
      return this.treeRef.getHalfCheckedNodes();
    },
    getHalfCheckedKeys() {
      return this.treeRef.getHalfCheckedKeys();
    },
    setCurrentKey(key) {
      return this.treeRef.setCurrentKey(key);
    },
    getCurrentKey() {
      return this.treeRef.getCurrentKey();
    },
    getCurrentNode() {
      return this.treeRef.getCurrentNode();
    },
    getNode(key) {
      return this.treeRef.getNode(key);
    },
    remove(key) {
      this.treeRef.remove(key);
    },
    append(data, parentNode) {
      this.treeRef.append(data, parentNode);
    },
    insertBefore(data, refNode) {
      this.treeRef.append(data, refNode);
    },
    insertAfter(data, refNode) {
      this.treeRef.append(data, refNode);
    },
    getData(params) {
      if (!utils.isObject(params)) params = {};

      if (!this.meta.hasOwnProperty('data_url')) {
        console.error('lack data_url attribute');
        return;
      }

      let url = this.meta['data_url'];

      this.$axios.safeGet(url, {
        params: params
      }).then(({data}) => {
        this.innerData = data;
        this.$emit("update:data", this.innerData);
      }).catch(err => {
        console.error(err)
      });
    },
    init() { // init business data
      let {data} = this;
      if (data !== undefined) {
        this.innerData = data;
        return;
      }
      if (this.meta.hasOwnProperty('data_url')) {
        this.getData();
        return;
      }
      console.error("data or data_url in meta provide one at least!")
    },
  },
  computed: {
    treeRefName() {
      const {meta: {name: treeRefName}} = this
      return treeRefName
    },
    treeRef() {
      return this.$refs[this.treeRefName]
    },
    treeConf() { // el-tree 的配置项
      const {meta: {conf}, $attrs} = this
      const treeConf = {}
      this.$reverseMerge(treeConf, conf)
      this.$reverseMerge(treeConf, $attrs)
      return treeConf
    },
    treeConfig() { // 树结构配置信息
      const {meta: {treeConfig = {}}} = this;
      return treeConfig
    },
    multiSelect() {
      const {meta: {conf: {'show-checkbox': multiSelect} = {}} = {}} = this
      return multiSelect;
    },
    editable() {
      const {meta: {editable} = {}} = this
      return editable
    },
    props() {
      const {meta: {conf: {props}}} = this
      return props
    },
    operationBarConf() {
      return this.meta['operation-bar'];
    },
    primaryKey() {
      const {meta: {objectPrimaryKey} = {}} = this;
      let primaryKey = utils.assertUndefined(objectPrimaryKey, defaultPrimaryKey);
      return primaryKey.split(',');
    }
  }
}
</script>

<style scoped lang="scss">
.opr-bar {
  width: 100%;
  box-sizing: border-box;
  -webkit-box-sizing: border-box;
  background-color: #e2e2e2;
  padding: 10px;
}
</style>
