<template>
  <vue-address v-model="nativeValue" v-bind="conf"></vue-address>
</template>

<script>
import Val from "../../mixins/value";
import Meta from '../../mixins/meta'
import DefaultMeta from '../ui-conf'
import VueAddress from "@pxg/vue-address";
import "@pxg/vue-address/lib/vue-address.css"
import conver from "./conver";
import reverse from "./reverse";

export default {
  name: "RegionBox",
  mixins: [Meta(DefaultMeta), Val(conver, reverse)],
  components: {
    'vue-address': VueAddress
  },
  props: {
    value: {
      type: [String, Array],
      default: () => []
    }
  },
  computed: {
    conf() {
      const {innerMeta: {conf}, $attrs: attrConf} = this
      return this.$reverseMerge(conf, attrConf)
    }
  }
}
</script>

<style scoped>

</style>