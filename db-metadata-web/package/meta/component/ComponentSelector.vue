<template>
  <drop-down-box v-model="nativeValue" :meta="meta" :options="components" v-bind="$attrs"
                 @change="handleChange" @clear="$emit('clear', nativeValue)"></drop-down-box>
</template>

<script>
import Val from "../../core/mixins/value";
import {restUrl} from "../../constant/url";
import DefaultDropDownBoxMeta from "../../core/dropdownbox/ui-conf";
import {isUndefined} from "../../utils/common";

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
    },
    components: Array
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

    if (isUndefined(this.components)) {
      const {scope} = this
      this.$merge(meta, DefaultDropDownBoxMeta)
      if (scope === 'view') {
        meta.data_url = this.$compile(restUrl.COMPONENT_CODE_LIST, {view: true})
      } else if (scope === 'field') {
        meta.data_url = this.$compile(restUrl.COMPONENT_CODE_LIST, {view: false})
      } else {
        meta.data_url = this.$compile(restUrl.COMPONENT_CODE_LIST, {view: null})
      }
    }

    return {
      meta: meta
    }
  },
  methods: {
    handleChange({value}) {
      this.$emit('change', value)
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
