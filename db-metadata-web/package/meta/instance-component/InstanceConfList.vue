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
      let instanceCode = utils.convertToString(row['code']);
      const url = utils.compile(routeUrl.R_INSTANCE_CONF_EDIT, {
        instanceCode: instanceCode,
        fieldCode: null
      });
      this.$router.push(url);
    }
  }
}
</script>

<style scoped>

</style>
