<template>
  <div class="md_page-container">
    <single-grid-tmpl :fc="fc">
      <template #edit-btn="{scope, conf}">
        <el-button v-bind="conf" @click="handlerConf($event, scope.row, scope.$index)"></el-button>
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
