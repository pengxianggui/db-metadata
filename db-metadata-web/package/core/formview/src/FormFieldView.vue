<template>
  <div class="container">
    <template v-for="meta in innerMetas">
      <component :is="meta.component_name" :meta="meta" v-bind="meta.conf"
                 :value="meta.conf.value" v-if="meta.component_name !== 'div'"></component>
      <div v-html="meta.conf.value" v-else></div>
    </template>
  </div>
</template>

<script>
import {isArray, isEmpty, strToArray} from "../../../utils/common";

// TODO 在Form Field组件中进行判断显示效果会更准确，不过显得很侵入, 需要有更合适的语法糖将Form Field component在两种模式(view,edit)下的display
// 给合理的结合起来
const mapping = {
  ImgBox: function (value, meta) {
    let v = strToArray(value)
    if (!isArray(v) || v.length <= 0) {
      return [{
        component_name: 'div',
        conf: {
          value: v
        }
      }]
    }

    return v.map(item => {
      return {
        component_name: 'el-image',
        conf: {
          src: item.url
        }
      }
    })
  },
  FileBox: function (value, meta) {
    let v = strToArray(value)
    if (!isArray(v) || v.length <= 0) {
      return [{
        component_name: 'div',
        conf: {
          value: v
        }
      }]
    }
    return [this.$merge({
      conf: {
        disabled: true,
        value: v
      }
    }, meta)]
  },
  RegionBox: function (value) {
    value = strToArray(value)
    let v = isArray(value) ? value.map(i => i.name).join('/') : value
    return [{
      component_name: 'div',
      conf: {
        value: v
      }
    }]
  },
  IconBox: function (value) {
    return [{
      component_name: 'SvgIcon',
      conf: {
        value: value
      }
    }]
  },
  JsonBox: function (value) {
    return [{
      component_name: 'JsonBox',
      conf: {
        value: value,
        disabled: true
      }
    }]
  },
  DropDownBox: function (value, meta) { // 前端需要自行转义通过ui conf配置的k:v
    const {options, data_url: dataUrl} = meta
    let v = value
    // 前端转义
    if (isArray(options) && options.length > 0) {
      v = options.filter(o => o.value === value)['key']
    } else if (!isEmpty(dataUrl)) {
      // TODO 通过dataUrl从服务端获取k:v, 再命中key

    }

    return [{
      component_name: 'div',
      conf: {
        value: v
      }
    }]
  }
}

export default {
  name: "FormFieldReadOnly",
  props: ['value', 'meta'],
  computed: {
    innerMetas() {
      const {meta, meta: {component_name: cc}, value} = this
      if (mapping.hasOwnProperty(cc)) {
        return mapping[cc].call(this, value, meta)
      } else {
        return [{
          component_name: 'div',
          conf: {
            value: value
          }
        }]
      }
    }
  }
}
</script>

<style scoped lang="scss">
.container {
  width: 100%;
  overflow: auto;
}
</style>