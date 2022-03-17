<template>
  <drop-down-box v-model="nativeValue" :meta="meta" v-bind="$attrs"
                 @clear="$emit('clear')" @change="$emit('change')"></drop-down-box>
</template>

<script>
import Val from "../../core/mixins/value";
import {restUrl} from "../../constant/url";
import DefaultDropDownBoxMeta from '../../core/dropdownbox/ui-conf'


export default {
  name: "MetaObjectSelector",
  mixins: [Val()],
  props: {
    value: String
  },
  data() {
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
    this.$merge(meta, DefaultDropDownBoxMeta)
    return {
      meta: meta
    }
  }
}
</script>

<style scoped>

</style>
