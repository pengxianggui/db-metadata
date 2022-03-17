<template>
  <drop-down-box v-model="nativeValue" :options="options" :meta="meta">
    <template v-for="option in options" :slot="option.value">
      <div class="option-item">
        <span :style="{'font-style': option.buildIn ? 'italic' : 'normal'}">{{ option.key || option.value }}</span>
        <svg-icon :value="option.icon" v-if="option.icon"></svg-icon>
      </div>
    </template>
  </drop-down-box>
</template>

<script>
import Val from "../../mixins/value";
import {assertArray} from "../../../utils/common";

export default {
  name: "PageSelector",
  mixins: [Val()],
  props: {
    value: String
  },
  data() {
    return {
      options: [],
      meta: {
        group: true,
        conf: {
          filterable: true
        }
      }
    }
  },
  created() {
    let components = assertArray(Object.values(this.$getGlobalComponents()), []) // TODO 2.2 并不总是可以拿到! 改为注册meta-element时将组件传入
    this.options = components.filter(c => {
      const {extendOptions: {meta: {isPage = false, isTemplate = false, isLayout} = {}}} = c
      return isPage || isTemplate || isLayout
    }).map(c => {
      const {
        extendOptions: {
          meta: {cn, isTemplate = false, isLayout = false, isPage = false, buildIn, icon} = {},
          name: value
        }
      } = c
      return {
        key: cn,
        value: value,
        isTemplate: isTemplate,
        buildIn: buildIn,
        group: isTemplate ? '模板' : (isLayout ? '布局' : '页面'),
        icon: icon
      }
    });
  }
}
</script>

<style scoped lang="scss">
.option-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
