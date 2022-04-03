<template>
  <drop-down-box v-model="nativeValue" :options="options" :meta="meta">
    <template v-for="option in options" :slot="option.value">
      <div class="option-item">
        <div>
          <svg-icon :value="option.icon" v-if="option.icon"></svg-icon>
          <span v-else>&nbsp;&nbsp;&nbsp;&nbsp;</span> <!-- 保持一致缩进 -->
          <span>{{ option.key || option.value }}</span>
        </div>
        <span style="color: #b8b8b8; font-style: italic; font-size: 12px;"> {{ option.buildIn ? '---- 内置' : '' }} </span>
      </div>
    </template>
  </drop-down-box>
</template>

<script>
import Val from "../../mixins/value";
import {components} from "../../../route/exchange";

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
    const types = {
      page: '页面',
      template: '模板',
      layout: '布局'
    }

    this.options = components.filter(c => {
      const {meta: {type = 'page'} = {}} = c
      return Object.keys(types).indexOf(type) > -1
    }).map(c => {
      const {
        meta: {cn, type = 'page', buildIn, icon} = {},
        name: value = '无名'
      } = c

      return {
        key: cn,
        value: value,
        buildIn: buildIn,
        group: types[type],
        icon: icon
      }
    })
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
