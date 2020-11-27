<template>
  <drop-down-box v-model="nativeValue" :meta="meta" v-bind="$attrs" @change="$emit('change', nativeValue)"></drop-down-box>
</template>

<script>
import Val from "../../core/mixins/value";
import {restUrl} from "../../constant/url";
import DefaultDropDownBoxMeta from "../../core/dropdownbox/ui-conf";

let meta = {
  name: "component",
  label: "组件",
  group: false,
  conf: {
    "filterable": true,
  }
};
export default {
  name: "ComponentSelector",
  mixins: [Val()],
  props: {
    value: String,
    justView: {
      type: Boolean,
      default: () => false
    }

  },
  data() {
    const {justView} = this
    this.$merge(meta, DefaultDropDownBoxMeta)
    if (justView) {
      meta.options = ['FormView', 'TableView', 'SearchView', 'TableTreeView']
    } else {
      meta.data_url = restUrl.COMPONENT_CODE_LIST
    }

    return {
      meta: meta
    }
  }
}
</script>

<style scoped>

</style>