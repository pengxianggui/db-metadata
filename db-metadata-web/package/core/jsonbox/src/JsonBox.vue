<template>
  <div>
    <vue-json-editor v-model="nativeValue"
                     :mode="innerMode"
                     v-bind="$reverseMerge(innerMeta.conf, $attrs)">
    </vue-json-editor>
  </div>
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
    }
  },
  computed: {
    innerMode() {
      const {$attrs: {mode: attrMode} = {}, innerMeta: {conf: {mode: metaMode} = {}}} = this
      return utils.assertEmpty(attrMode, metaMode);
    }
  }
};
</script>
