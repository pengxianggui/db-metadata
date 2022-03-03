<template>
  <!--    <tree-single-grid-tmpl :fc="fc"></tree-single-grid-tmpl>-->
  <div class="el-card">
    <search-view :meta="spMeta" @search="handleSearch"></search-view>
    <table-tree-view :ref="tlRefName" :meta="tlMeta" :filter-params="filterParams">
      <tempalte #operation-bar="{conf}">
        <slot name="operation-bar" v-bind:conf="conf"></slot>
      </tempalte>

      <template #prefix-btn="{conf}">
        <slot name="prefix-btn" v-bind:conf="conf"></slot>
      </template>
      <template #add-btn="{conf}">
        <slot name="add-btn" v-bind:conf="conf"></slot>
      </template>
      <template #suffix-btn="{conf}">
        <slot name="suffix-btn" v-bind:conf="conf"></slot>
      </template>

      <template #buttons="{scope}">
        <slot name="buttons" v-bind:scope="scope"></slot>
      </template>

      <template #inner-before-extend-btn="{scope}">
        <slot name="inner-before-extend-btn" v-bind:scope="scope"></slot>
      </template>
      <template #view-btn="{scope, conf}">
        <slot name="view-btn" v-bind:conf="conf" v-bind:scope="scope"></slot>
      </template>
      <template #edit-btn="{scope, conf}">
        <slot name="edit-btn" v-bind:conf="conf" v-bind:scope="scope"></slot>
      </template>
      <template #delete-btn="{scope, conf}">
        <slot name="delete-btn" v-bind:conf="conf" v-bind:scope="scope"></slot>
      </template>
      <template #inner-after-extend-btn="{scope}">
        <slot name="inner-after-extend-btn" v-bind:scope="scope"></slot>
      </template>
    </table-tree-view>
  </div>
</template>

<script>

import {getSearchViewMeta, getTableTreeViewMeta} from "../../utils/rest";

export default {
  name: "RouterManager",
  data() {
    return {
      objectCode: 'meta_router',
      tlMeta: {},
      spMeta: {},
      filterParams: {}
      // fc: 'meta_router'
    }
  },
  methods: {
    handleSearch(params) {
      const tlRefName = this.tlRefName;
      this.filterParams = params;
      this.$nextTick(() => {
        this.$refs[tlRefName].getData();
      })
    },
  },
  created() {
    const {objectCode} = this
    getTableTreeViewMeta(this.$axios, objectCode).then(resp => {
      this.tlMeta = resp.data;
    }).catch(({msg = '加载TableTreeView meta发生错误'}) => {
      console.error('[ERROR] msg: %s', msg);
    });

    getSearchViewMeta(this.$axios, objectCode).then(resp => {
      this.spMeta = resp.data;
    }).catch(({msg = '加载SearchView meta数据发生错误'}) => {
      console.error('[ERROR] msg: %s', msg);
    });
  },
  computed: {
    tlRefName() {
      return this.tlMeta['name'];
    }
  }
}
</script>

<style lang="scss" scoped>
</style>
