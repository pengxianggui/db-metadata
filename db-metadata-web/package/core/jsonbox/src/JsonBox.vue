<template>
  <vue-json-editor v-model="nativeValue"
                   :mode="innerMode"
                   :modes="innerModes"
                   v-bind="$reverseMerge(innerMeta.conf, $attrs)"
                   class="json-editor" :style="style">
  </vue-json-editor>

</template>

<script>
import vueJsonEditor from 'vue-json-editor'
import Meta from '../../mixins/meta'
import Val from '../../mixins/value'
import conver from './conver'
import reverse from "./reverse";
import DefaultMeta from '../ui-conf'
import utils from '@/../package/utils'
import {modes} from '../ui-conf'

export default {
  mixins: [Meta(DefaultMeta), Val(conver, reverse)],
  name: "JsonBox",
  label: "Json框",
  inject: {
    isView: {default: false}
  },
  components: {
    vueJsonEditor
  },
  props: {
    value: [Object, String, Array],
    mode: {
      type: String,
      validator: (val) => {
        return modes.indexOf(val) > -1
      }
    },
    modes: {
      type: Array
    },
    height: {
      type: String,
      default: () => 'auto'
    }
  },
  computed: {
    innerMode() {
      const {mode: attrMode, innerMeta: {conf: {mode: metaMode} = {}}, isView} = this
      if (isView) {
        return "view"
      }
      return utils.assertEmpty(attrMode, metaMode);
    },
    innerModes() {
      const {modes: attrModes, innerMeta: {conf: {modes: metaModes} = {}}, isView} = this
      if (isView) { // view模式下，只能选view
        return ['view']
      }
      return utils.assertEmpty(attrModes, metaModes)
    },
    style() {
      return {
        height: this.height
      }
    }
  }
};
</script>
<style scoped lang="scss">
.json-editor {
  ::v-deep {
    .jsoneditor-vue {
      height: 100%;
    }
  }
}

</style>
