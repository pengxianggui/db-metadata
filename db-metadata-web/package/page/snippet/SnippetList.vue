<template>
  <div>
    <single-grid-tmpl :fc="snippetFc">
      <template #float-right-btn="{conf}">
        <el-button type="text" :size="conf.conf.size" @click="openVarList">
          <svg-icon value="var"></svg-icon>
          <span>全局变量</span>
        </el-button>
      </template>
    </single-grid-tmpl>
    <el-dialog :visible.sync="varVisible" title="全局变量" width="80%">
      <single-grid-tmpl :fc="varFc"></single-grid-tmpl>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "SnippetList",
  meta: {
    type: 'page',
    cn: '代码片段',
    buildIn: true
  },
  data() {
    return {
      snippetFc: 'meta_snippet',
      varFc: 'meta_var',
      varVisible: false
    }
  },
  methods: {
    openVarList() {
      this.varVisible = true
    }
  }
}
</script>

<style scoped>

</style>
