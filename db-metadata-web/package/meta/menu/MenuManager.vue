<template>
  <div class="page-container">
    <search-view :meta="spMeta" @search="handleSearch"></search-view>
    <table-tree-view :ref="tlRefName" :meta="tlMeta" :filter-params="filterParams"></table-tree-view>
  </div>
</template>

<script>
    import {getSearchViewMeta, getTableTreeViewMeta} from "../../utils/rest";

    export default {
        name: "MenuManager",
        data() {
            return {
                objectCode: 'meta_menu',
                tlMeta: {},
                spMeta: {},
                filterParams: {}
                // fc: 'meta_menu'
            }
        },
        methods: {
          handleSearch(params) {
            const tlRefName = this.tlRefName;
            this.filterParams = params;
            this.$nextTick(() => {
              this.$refs[tlRefName].getData();
            })
          }
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

<style scoped>

</style>
