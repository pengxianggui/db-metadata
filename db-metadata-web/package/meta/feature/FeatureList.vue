<template>
  <div class="page-container">
    <single-grid-tmpl :fc="fc" :ref="fc">
      <template #add-btn="{conf}">
        <el-button v-bind="conf.conf" @click="openAdd">新增</el-button>
      </template>
      <template #edit-btn="{conf, scope}">
        <el-button v-bind="conf" @click="openUpdate(scope.row)"></el-button>
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
    }
  }
}
</script>

<style scoped>

</style>
