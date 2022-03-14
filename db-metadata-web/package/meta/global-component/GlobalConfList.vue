<template>
  <div class="page-container">
    <single-grid-tmpl :fc="fc">
      <template #add-btn="{conf}">
        <el-button v-bind="conf.conf" @click="addConf" icon="el-icon-document-add">新增</el-button>
      </template>
      <template #inner-before-extend-btn="{scope, conf}">
        <el-tooltip content="配置" placement="left">
          <el-button icon="el-icon-s-tools" v-bind="conf.conf"
                     @click="handlerConf($event, scope.row, scope.$index)"></el-button>
        </el-tooltip>
      </template>
    </single-grid-tmpl>
  </div>
</template>

<script>
import {routeUrl} from '../../constant/url'

export default {
  name: "GlobalConfList",
  data() {
    return {
      fc: 'meta_component'
    }
  },
  methods: {
    addConf() {
      this.$router.push(routeUrl.R_GOBAL_CONF_ADD);
    },
    handlerConf(ev, row, index) {
      if (ev) ev.stopPropagation();
      let componentCode = row['code'];
      this.$router.push({
        path: routeUrl.R_GOBAL_CONF_EDIT,
        query: {
          componentCode: componentCode
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
