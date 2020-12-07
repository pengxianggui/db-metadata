<template>
  <div>
    <div v-for="(item, i) in columns" :key="i">
      <!-- 布局组件-->
      <template v-if="isLayoutComp(item)">
        <component :is="item.component_name" :meta="item" :show-line="false">
          <template v-for="(c, j) in extractSpan(item)" v-slot:[j]>
            <nest-form-item :columns="item.columns[j]" :model="model">
              <template v-for="(v, k) in fieldSlots" v-slot:[k]="props">
                <slot :name="k" v-bind:column="props.column" v-bind:model="props.model"></slot>
              </template>
            </nest-form-item>
          </template>
        </component>
      </template>

      <!-- 表单组件-->
      <template v-else>
        <slot :name="'form-item-' + item.name" v-bind:column="item" v-bind:model="model">
          <el-form-item :label="item.label || item.name" :prop="item.name"
                        :rules="getItemRules(item)" v-if="!item.hasOwnProperty('showable') || item.showable">
            <component :is="item.component_name" :meta="item" v-model="model[item.name]"></component>
          </el-form-item>
        </slot>
      </template>

    </div>
  </div>
</template>

<script>
import {isLayoutComp} from "../../../meta/form-builder/relate/componentData";
import {isArray} from "../../../utils/common";

export default {
  name: "NestFormItem",
  props: {
    columns: {
      type: Array,
      required: true
    },
    model: {
      type: Object,
      required: true
    }
  },
  methods: {
    isLayoutComp(column) {
      const {component_name: componentName} = column
      return isLayoutComp(componentName)
    },
    getItemRules(item) {
      const {conf: {rules} = {}} = {} = item
      return rules
    },
    extractSpan(column) {
      const {conf: {span} = {}} = column
      if (!isArray(span)) {
        console.error(`[Meta-Element] 容器组件 ${column.component_name} 的配置项 conf.span不正确, 请检查！`)
      }
      return span
    }
  },
  computed: {
    fieldSlots() {
      return this.$scopedSlots
    }
  }
}
</script>

<style scoped lang="scss">
</style>