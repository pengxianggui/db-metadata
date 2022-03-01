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
import Vue from "vue";
import Val from "../../mixins/value";

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
    let components = Vue.options.components;
    this.options = Object.values(components).filter(c => {
      const {extendOptions: {meta: {isPage = false, isTemplate = false} = {}}} = c
      return isPage || isTemplate
    }).map(c => {
      const {extendOptions: {meta: {cn, isTemplate, buildIn, icon} = {}, name: value}} = c
      return {
        key: cn,
        value: value,
        isTemplate: isTemplate,
        buildIn: buildIn,
        group: isTemplate ? '模板' : '页面',
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
