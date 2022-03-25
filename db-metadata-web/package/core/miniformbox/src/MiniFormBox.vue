<template>
  <div class="el-card">
    <template v-if="formType">
      <mini-form v-bind="conf" :meta="innerMeta" v-model="nativeValue"></mini-form>
    </template>
    <template v-else>
      <json-box v-model="nativeValue" mode="code" @input="$emit('json-change')" v-bind="conf"></json-box>
    </template>
    <div style="display: flex;" v-if="showControls">
      <span>
          <el-button size="mini" icon="el-icon-refresh" circle @click="changeType"></el-button>
      </span>
      <slot name="button-expand" v-bind:value="nativeValue"></slot>
      <span style="flex: 1"></span>
    </div>
  </div>
</template>

<script>
import Core from './core'
import Meta from '../../mixins/meta'
import Val from '../../mixins/value'
import assembleMeta from './assembleMeta'
import DefaultFormViewMeta from '@/../package/view/formview/ui-conf'
import {assertEmpty} from "../../../utils/common";

export default {
  name: "MiniFormBox",
  mixins: [Meta(DefaultFormViewMeta, assembleMeta), Val()],
  props: {
    value: {
      type: [Object, String],
      default: () => {
      }
    },
    controls: {
      type: Boolean,
      default: () => false
    }
  },
  components: {
    "mini-form": Core
  },
  data() {
    return {
      formType: true
    }
  },
  methods: {
    changeType() {
      const {formType} = this;
      this.formType = !formType;
      this.$emit('change-type', this.formType); // hook
    }
  },
  computed: {
    conf() {
      const {meta: {conf}, $attrs} = this
      const finalConf = {}
      this.$reverseMerge(finalConf, conf)
      this.$reverseMerge(finalConf, $attrs)
      return finalConf
    },
    showControls() {
      const {meta: {controls: controlsInMeta = true}, controls: controlsInProp} = this
      return assertEmpty(controlsInProp, controlsInMeta);
    }
  }

}
</script>

<style scoped>
</style>
