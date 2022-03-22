<template>
  <div>
    <template v-for="item in columns">
      <!-- 布局组件-->
      <template v-if="isLayoutComp(item)">
        <component :is="item.component_name" :meta="item" :show-line="false" :key="item.name">
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
          <el-form-item :prop="item.name" :key="item.name" :rules="getItemRules(item)" v-if="!item.hidden">
            <template #label>
              <span>{{item.label || item.name}}</span>
              <el-tooltip placement="right" v-if="item.explain">
                <div slot="content" v-html="item.explain" style="white-space: pre-wrap;"></div>
                <i class="el-icon-question"></i>
              </el-tooltip>
            </template>
            <component :is="item.component_name" :meta="item" v-model="model[item.name]"></component>
          </el-form-item>
        </slot>
      </template>

    </template>
  </div>
</template>

<script>
import {isLayoutComp} from "../../../meta/form-builder/relate/componentData";
import {isArray} from "../../../utils/common";
import FormFieldView from "./FormFieldView";

export default {
  name: "NestFormItem",
  components: {
    FormFieldView
  },
  inject: {
    isView: {
      default: false
    }
  },
  props: {
    columns: {
      type: Array,
      default: () => {
        return []
      }
    },
    model: {
      type: Object,
      default: () => {
        return {}
      }
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
