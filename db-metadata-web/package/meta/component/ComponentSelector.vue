<template>
  <drop-down-box v-model="nativeValue" :meta="meta" v-bind="$attrs" @change="$emit('change')" @clear="$emit('clear')"></drop-down-box>
</template>

<script>
import Val from "../../core/mixins/value";
import {restUrl} from "../../constant/url";
import DefaultDropDownBoxMeta from "../../core/dropdownbox/ui-conf";

export default {
  name: "ComponentSelector",
  mixins: [Val()],
  props: {
    value: {
      type: [String, Array]
    },
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
      meta.data_url = this.$compile(restUrl.COMPONENT_CODE_LIST, {view: true})
    } else if (scope === 'field') {
      meta.data_url = this.$compile(restUrl.COMPONENT_CODE_LIST, {view: false})
    } else {
      meta.data_url = this.$compile(restUrl.COMPONENT_CODE_LIST, {view: null})
    }

    return {
      meta: meta
    }

  },
  computed: {
    attrs() {
      return this.$attrs;
    }
  }
}
</script>

<style scoped>

</style>
