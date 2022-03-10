<template>
  <div class="page-container">
    <single-grid-tmpl :oc="oc">
      <template #add-btn="{conf}">
        <el-button v-bind="conf.conf" @click="openAdd">新增</el-button>
      </template>
    </single-grid-tmpl>
    <el-dialog :visible.sync="visible" width="80%">
      <feature-add @ok="() => visible=false" @cancel="() => visible=false"></feature-add>
    </el-dialog>
  </div>
</template>

<script>
import SingleGridTmpl from "../../template/SingleGridTmpl";
import FeatureAdd from "./FeatureAdd";
import {metaObjectCode} from "../../constant/variable";

export default {
  name: "FeatureList",
  components: {SingleGridTmpl, FeatureAdd},
  data() {
    return {
      oc: metaObjectCode.featureList,
      visible: false
    }
  },
  methods: {
    openAdd() {
      this.visible = true
    }
  }
}
</script>

<style scoped>

</style>
