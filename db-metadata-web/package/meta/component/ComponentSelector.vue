<template>
  <drop-down-box v-model="nativeValue" :meta="meta" v-bind="$attrs" @change="$emit('change')"></drop-down-box>
</template>

<script>
import Val from "../../core/mixins/value";
import {restUrl} from "../../constant/url";
import DefaultDropDownBoxMeta from "../../core/dropdownbox/ui-conf";

export default {
  name: "ComponentSelector",
  mixins: [Val()],
  props: {
    value: String,
    scope: {
      type: String,
      default: () => 'default',
      validator: (value) => ['default', 'field', 'view'].indexOf(value) > -1
    }
  },
  data() {
    let meta = {
      name: "component",
      label: "组件",
      group: false,
      conf: {
        "filterable": true,
      }
    }

    const {scope} = this
    this.$merge(meta, DefaultDropDownBoxMeta)
    if (scope === 'view') {
      meta.options = ['FormView', 'TableView', 'SearchView', 'TableTreeView'] // TODO 动态获取
    } else if (scope === 'field') {
      meta.data_url = this.$compile(restUrl.COMPONENT_CODE_LIST, {scope: scope})
    } else {
      meta.data_url = this.$compile(restUrl.COMPONENT_CODE_LIST)
    }

    return {
      meta: meta
    }

  }
}
</script>

<style scoped>

</style>