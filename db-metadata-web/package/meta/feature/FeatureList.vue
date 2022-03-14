<template>
  <div class="page-container">
    <single-grid-tmpl :fc="fc" :ref="fc">
      <template #add-btn="{conf}">
        <el-button v-bind="conf.conf" @click="openAdd">新增</el-button>
      </template>
    </single-grid-tmpl>
    <el-dialog :visible.sync="visible" width="80%" :destroy-on-close="true">
      <feature-add @ok="handleOk" @cancel="visible = false"></feature-add>
    </el-dialog>
  </div>
</template>

<script>
import SingleGridTmpl from "../../template/SingleGridTmpl";
import FeatureAdd from "./FeatureAdd";

export default {
  name: "FeatureList",
  components: {SingleGridTmpl, FeatureAdd},
  data() {
    return {
      fc: 'meta_feature',
      visible: false
    }
  },
  methods: {
    openAdd() {
      this.visible = true
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
