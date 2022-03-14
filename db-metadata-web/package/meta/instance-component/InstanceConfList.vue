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
import utils from '../../utils'
import {routeUrl} from "../../constant/url";
import {metaObjectCode} from "../../constant/variable";
import {getSearchViewMeta, getTableViewMeta} from "../../utils/rest";

export default {
  name: "InstanceConfList",
  data() {
    return {
      fc: 'meta_component_instance'
    }
  },
  methods: {
    addConf() {
      const url = utils.compile(routeUrl.R_INSTANCE_CONF_EDIT, {
        componentCode: '',
        objectCode: ''
      });
      this.$router.push(url);
    },
    handlerConf(ev, row, index) {
      if (ev) ev.stopPropagation();
      let instanceCode = utils.convertToString(row['code']);
      let componentCode = utils.convertToString(row['comp_code']);
      let objectCode = utils.convertToString(row['dest_object']);
      const url = utils.compile(routeUrl.R_INSTANCE_CONF_EDIT, {
        instanceCode: instanceCode,
        componentCode: componentCode,
        objectCode: objectCode
      });
      this.$router.push(url);
    },
  }
}
</script>

<style scoped>

</style>
