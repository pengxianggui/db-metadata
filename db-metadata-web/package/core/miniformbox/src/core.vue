<template>
  <el-form v-bind="bindConf" :model="value" size="mini" class="mini-form">
      <template v-for="(item, index) in meta.columns">
          <el-form-item :key="item.name + index" :label="item.label||item.name" :prop="item.name"
                        :class="{'indent-block': item.component_name == 'MiniForm'}">
              <component :is="item.component_name"
                         v-model="value[item.name]" :meta="item" v-bind="bindConf">
              </component>
          </el-form-item>
      </template>
  </el-form>
</template>

<script>
    import Meta from '../../mixins/meta'
    import Val from '../../mixins/value'
    import DefaultFormViewMeta from '@/../package/view/formview/ui-conf'

    export default {
        name: "MiniForm",
        label: "迷你表单",
        description: "输入控件的一种,JsonBox的表单表现形式",
        mixins: [Meta(DefaultFormViewMeta), Val()],
        props: {
            value: {
                type: [Object, String],
                default: () => {}
            }
        },
        computed: {
          bindConf() {
            const {meta: {component_name}, meta, $attrs} = this
            if (component_name === 'MiniFormBox' || component_name === 'FormView' || component_name === 'MiniForm') {
              return this.$reverseMerge(meta.conf, $attrs)
            }
            return {}
          }
        }
    }
</script>

<style scoped lang="scss">
.mini-form {

  /deep/ .indent-block {
    & > .el-form-item__label {
      display: block;
      text-align: left;
      vertical-align: middle;
      float: none;
    }
    & > .el-form-item__content {
      margin-left: 10px;
      padding: 10px;
      border: 1px solid #f1f1f1;
    }
  }

}

</style>
