<template>
  <component :is="innerMeta.component_name" :meta="innerMeta" v-bind="innerMeta.conf"
             :value="innerMeta.conf.value" v-if="innerMeta.component_name !== 'div'"></component>
  <div v-html="innerMeta.conf.value" v-else></div>
</template>

<script>
import {isArray, strToArray} from "../../../utils/common";

// TODO 在Form Field组件中进行判断显示效果会更准确，不过显得很侵入, 需要有更合适的语法糖将Form Field component在两种模式(view,edit)下的display
// 给合理的结合起来
const mapping = {
  ImgBox: function (value, meta) {
    return this.$merge({
      conf: {
        disabled: true,
        value: value
      }
    }, meta)
  },
  FileBox: function (value, meta) {
    return this.$merge({
      conf: {
        disabled: true,
        value: value
      }
    }, meta)
  },
  RegionBox: function (value) {
    value = strToArray(value)
    let v = isArray(value) ? value.map(i => i.name).join('/') : value
    return {
      component_name: 'div',
      conf: {
        value: v
      }
    }
  },
  IconBox: function (value) {
    return {
      component_name: 'SvgIcon',
      conf: {
        value: value
      }
    }
  },
  JsonBox: function (value) {
    return {
      component_name: 'JsonBox',
      conf: {
        value: value,
        disabled: true
      }
    }
  },
  DropDownBox: function (value, meta) {
    const {options} = meta
    let v
    if (!isArray(options)) { // TODO 还有可能是data_url动态渲染
      v = value
    } else {
      v = options.filter(o => o.value === value)['value']
    }
    return {
      component_name: 'div',
      conf: {
        value: v
      }
    }
  }
}

export default {
  name: "FormFieldReadOnly",
  props: ['value', 'meta'],
  computed: {
    innerMeta() {
      const {meta, meta: {component_name: cc}, value} = this
      if (mapping.hasOwnProperty(cc)) {
        return mapping[cc].call(this, value, meta)
      } else {
        return {
          component_name: 'div',
          conf: {
            value: value
          }
        }
      }
    }
  }
}
</script>

<style scoped>

</style>