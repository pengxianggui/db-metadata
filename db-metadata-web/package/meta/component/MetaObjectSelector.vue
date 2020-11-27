<template>
  <drop-down-box v-model="nativeValue" :meta="meta" v-bind="$attrs"></drop-down-box>
</template>

<script>
import Val from "../../core/mixins/value";
import {restUrl} from "../../constant/url";
import DefaultDropDownBoxMeta from '../../core/dropdownbox/ui-conf'

let meta = {
  name: "object",
  label: "元对象",
  data_url: restUrl.OBJECT_CODE_LIST,
  group: false,
  conf: {
    "filterable": true,
    "clearable": true
  },
  behavior: {
    format: function (data) {
      let arr = [];
      for (let i = 0; i < data.length; i++) {
        arr.push({
          key: data[i].code,
          value: data[i].code
        })
      }
      return arr;
    }
  }
};

export default {
  name: "MetaObjectSelector",
  mixins: [Val()],
  props: {
    value: String
  },
  data() {
    this.$merge(meta, DefaultDropDownBoxMeta)
    return {
      meta: meta
    }
  }
}
</script>

<style scoped>

</style>