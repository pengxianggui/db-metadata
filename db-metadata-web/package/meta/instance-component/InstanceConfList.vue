<template>
  <div class="page-container">
    <single-grid-tmpl :fc="fc">
      <template #add-btn="{conf}">
        <auto-computed-button>
          <template #default="{click}">
            <el-button v-bind="conf.conf" @click="click">添加</el-button>
          </template>
        </auto-computed-button>
      </template>

      <template #edit-btn="{scope, conf}">
        <el-button v-bind="conf" @click="handlerConf($event, scope.row, scope.$index)"></el-button>
      </template>
    </single-grid-tmpl>
  </div>
</template>

<script>
import utils from '../../utils'
import {routeUrl} from "../../constant/url";
import AutoComputedButton from "../feature/ext/AutoComputedButton";

export default {
  name: "InstanceConfList",
  components: {AutoComputedButton},
  data() {
    return {
      fc: 'meta_component_instance'
    }
  },
  methods: {
    handlerConf(ev, row, index) {
      if (ev) ev.stopPropagation();

      let fieldCode
      const {code, dest_object} = row
      let instanceCode = utils.convertToString(code);
      if (dest_object.indexOf('.') > -1) {
        fieldCode = dest_object.split('.')[1]
      }

      const url = utils.compile(routeUrl.R_INSTANCE_CONF_EDIT, {
        instanceCode: instanceCode,
        fieldCode: fieldCode
      });
      this.$router.push(url);
    }
  }
}
</script>

<style scoped>

</style>
