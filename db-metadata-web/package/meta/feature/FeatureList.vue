<template>
  <div class="page-container">
    <single-grid-tmpl :fc="fc" :ref="fc">
      <template #add-btn="{conf}">
        <el-button v-bind="conf.conf" @click="openAdd">新增</el-button>
      </template>
      <template #edit-btn="{conf, scope}">
        <el-button v-bind="conf" @click="openUpdate(scope.row)"></el-button>
      </template>

      <template #float-right-btn="scope">
        <div>
          <el-button size="mini" type="primary" icon="el-icon-download" plain @click="exportData(scope)">导出</el-button>
          <el-button size="mini" type="success" icon="el-icon-upload" plain @click="importData(scope)">导入</el-button>
        </div>
      </template>
    </single-grid-tmpl>
    <el-dialog :visible.sync="visible" width="80%" :destroy-on-close="true">
      <feature-add @ok="handleOk" @cancel="visible = false" v-if="primary === null"></feature-add>
      <feature-edit :primary-kv="primary" @ok="handleOk" @cancel="visible = false" v-else></feature-edit>
    </el-dialog>
  </div>
</template>

<script>
import SingleGridTmpl from "../../template/SingleGridTmpl";
import FeatureAdd from "./FeatureAdd";
import FeatureEdit from "./FeatureEdit";

export default {
  name: "FeatureList",
  components: {SingleGridTmpl, FeatureAdd, FeatureEdit},
  data() {
    return {
      fc: 'meta_feature',
      visible: false,
      primary: null
    }
  },
  methods: {
    openAdd() {
      this.visible = true
      this.primary = null
    },
    openUpdate(row) {
      this.visible = true
      this.primary = row.id
    },
    handleOk() {
      this.visible = false
      this.$refs[this.fc].refresh()
    },
    exportData(scope) {
      // TODO 2.3 导入整个功能(连同 功能配置 + UI实例配置 + 元对象 + 元字段 + 菜单 + 路由)， 导出格式： 1.SQL; 2.JSON格式(dbmeta需提供配套的导入)
      //  如果需要连同菜单、路由一起导出的话，那么功能和菜单、路由之间的关联必须再"硬"一点了，而不能仅仅依靠路径后面的?fc={fc}了
      this.$message.warning('努力开发中...')
    },
    importData() {
      // TODO 2.3 导入功能(json)
      this.$message.warning('努力开发中...')
    }
  }
}
</script>

<style scoped>

</style>
