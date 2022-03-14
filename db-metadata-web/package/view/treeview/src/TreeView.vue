<template>
  <div class="view-container">
    <slot name="operation-bar" v-bind:conf="operationBarConf" v-bind:choseData="choseData"
          v-bind:operations="{handleAdd, handleBatchDelete}">
      <component :is="operationBarConf.group ? 'el-button-group' : 'div'"
                 :style="operationBarConf.style" v-bind="operationBarConf.conf"
                 class="opr-bar"
                 v-if="operationBarConf.show">
        <slot name="prefix-btn" v-bind:conf="operationBarConf" v-bind:choseData="choseData"></slot>
        <slot name="add-btn" v-bind:conf="operationBarConf.add" v-bind:add="handleAdd">
          <el-button @click="handleAdd" v-bind="operationBarConf.add.conf"
                     v-if="operationBarConf.add.show && editable">
            {{ operationBarConf.add.text }}
          </el-button>
        </slot>
        <slot name="batch-delete-btn" v-bind:conf="operationBarConf.delete" v-bind:choseData="choseData"
              v-bind:batchDelete="handleBatchDelete">
          <el-button @click="handleBatchDelete($event)" v-bind="operationBarConf.delete.conf"
                     v-if="operationBarConf.delete.show && editable">
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
              <span>取消全选</span>
            </el-dropdown-item>
            <el-dropdown-item command="handleReverseChose" v-if="multiSelect">
              <i class="el-icon-success"></i>
              <span>反选</span>
            </el-dropdown-item>
            <slot name="suffix-btn" v-bind:conf="operationBarConf" v-bind:choseData="choseData"></slot>
          </el-dropdown-menu>
        </el-dropdown>
      </component>
    </slot>

    <!-- name作为ref不保险 -->
    <el-tree :ref="treeRefName" :data="innerData"
             v-bind="$reverseMerge(meta.conf, $attrs)"
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
      <meta-easy-edit :object-code="meta.objectCode" component-code="TreeView">
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
import {assertEmpty} from "../../../utils/common";

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
      this.$emit('chose-change', this.getCheckedNodes())
    },
    handleChoseAll() {
      this.treeRef.setCheckedNodes(this.innerData);
      this.$emit('chose-change', this.getCheckedNodes())
    },
    handleCleanChose() {
      this.treeRef.setCheckedKeys([]);
      this.$emit('chose-change', this.getCheckedNodes())
    },
    handleExpandAll() {
      this.getAllNodes().forEach(node => node.expanded = true);
    },
    handleShrinkAll() {
      this.getAllNodes().forEach(node => node.expanded = false);
    },
    handleAdd() {
      let currentNode = this.treeRef.getCurrentNode();
      if (currentNode) {
        // pxg_todo 新增节点
        this.$message.warning("开发中")
      } else {
        this.$message.warning('请先选择一个节点');
      }
    },
    handleBatchDelete() {
      this.$message.warning("开发中")
    },
    handleNodeClick(row, node, event) {
      const {primaryKey} = this;

      if (row[primaryKey] === this.activeData[primaryKey]) {  // cancel active row
        this.activeData = {};
        this.treeRef.setCurrentNode()
      } else {
        this.activeData = row;
      }
      this.$emit('active-change', this.activeData);
    },
    handleNodeCheck(row, {checkedNodes, checkedKeys, halfCheckedNodes, halfCheckedKeys}) {
      const {multiSelect} = this;
      if (!multiSelect) return;
      this.choseData = checkedNodes;
      this.halfChoseData = halfCheckedNodes;
      this.$emit('chose-change', checkedNodes);
      this.$emit('half-chose-change', halfCheckedNodes);
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
      const {objectPrimaryKey} = this.meta
      return assertEmpty(objectPrimaryKey, defaultPrimaryKey)
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
