<template>
  <div>
    <template v-if="formType">
      <el-form size="mini" :model="nativeValue" label-position="top">
        <template v-for="key in keys">
          <template v-if="editableJudge(nativeValue.component_name, key)">
            <el-form-item :key="key" :label="key">

              <component-selector v-model="nativeValue.component_name" @change="handleCompChange"
                                  scope="field" :components="fieldComponents"
                                  :popper-append-to-body="false" :disabled="nativeValue.component_name === viewComponentCode"
                                  v-if="key === 'component_name'"></component-selector>

              <!--              特殊-->
              <component :is="nativeValue.component_name" :meta="nativeValue"
                         v-model="nativeValue[key]" v-else-if="key === 'default_value'"></component>


              <!--              常规-->
              <component :is="attrsConfMeta[key]['component_name']" v-model="nativeValue[key]"
                         :meta="attrsConfMeta[key]"
                         v-else></component>

            </el-form-item>
          </template>
        </template>
      </el-form>
    </template>

    <template v-else>
      <!-- 倘若直接使用v-model, JsonBox中若更改了component_name,将直接更新到上一层, 无法根据component_name的新值重新刷新配置,
      此处使用:value和@input组合解构v-model的语法糖-->
      <json-box v-model="nativeValue" mode="tree"></json-box>
    </template>

    <div class="bottom-btn-group">
      <span style="flex: 1"></span>
      <el-button size="mini" icon="el-icon-refresh" circle @click="changeType"></el-button>
      <el-button size="mini" icon="el-icon-question" circle @click="openHelpDoc"></el-button>

      <meta-field-config-button :object-code="objectCode" :field-code="fieldCode"
                                v-if="objectCode && fieldCode && !isLayoutComp(nativeValue.component_name)">
        <template #default="{open}">
          <el-button size="mini" icon="el-icon-s-tools" circle @click="open"></el-button>
        </template>
      </meta-field-config-button>
    </div>
  </div>
</template>

<script>
import buildMeta from "../buildMeta";
import confFilter from "../form-builder/relate/confFilter";
import {isLayoutComp} from "../form-builder/relate/componentData";
import ComponentSelector from "./ComponentSelector";
import MetaFieldConfigButton from "./MetaFieldConfigButton";
import {buildDefaultMeta} from "../../core";
import Val from "../../core/mixins/value";
import utils from '../../utils'
import {restUrl} from "../../constant/url";
import {appConfig} from "../../config";

const reorder = function (value) {
  let keys = Object.keys(value).sort()
  let newValue = {};
  keys.forEach(k => {
    newValue[k] = value[k]
  })
  return newValue
}

export default {
  name: "UiConfEditor",
  mixins: [Val(/*reorder*/)], // TODO 必须实现排序, 保证component_name在第一，但是这里有问题, 会导致双向绑定失效，因为内部新建了对象
  components: {ComponentSelector, MetaFieldConfigButton},
  props: {
    value: {
      type: Object,
      default: () => {
        return {}
      }
    },
    objectCode: String,
    fieldCode: String,
    viewComponentCode: { // 容器组件编码
      type: String,
      required: true
    }
  },
  data() {
    return {
      formType: false,
      fieldComponents: []
    }
  },
  watch: {
    'fieldCode': {
      handler: function () {
        if (!utils.isEmpty(this.fieldComponents)) {
          return
        }
        this.$axios.safeGet(this.$compile(restUrl.COMPONENT_CODE_LIST, {
          view: false
        })).then(resp => {
          this.fieldComponents = resp.data
        })
      },
      immediate: true
    }
  },
  methods: {
    editableJudge(componentName, key) {
      return confFilter(componentName, key);
    },
    isLayoutComp(componentName) {
      return isLayoutComp(componentName)
    },
    changeType() {
      const {formType} = this;
      this.formType = !formType;
      this.$emit('change-type', this.formType); // hook
    },
    handleCompChange() {
      const {nativeValue: {component_name}, objectCode, fieldCode} = this
      utils.deleteAllAttrs(this.nativeValue, ['name', 'label']) // TODO 不能一刀切的删除掉所有属性，比如 ImgBox转FileBox时，如果一刀切，那么action就要重新配置
      const defMeta = buildDefaultMeta(component_name, objectCode, fieldCode)
      this.$merge(this.nativeValue, utils.assertEmpty(defMeta, {
        component_name: 'TextBox'
      }))
    },
    openHelpDoc() { // 可配
      const {viewComponentCode, componentCode: fieldComponentCode} = this
      if ((viewComponentCode === 'FormView' && fieldComponentCode !== 'FormView')
          || (viewComponentCode === 'SearchView' && fieldComponentCode !== 'SearchView') ) {
        window.open(`${appConfig.docUrl}/component/field/${fieldComponentCode.toLowerCase()}.html#配置项`, "帮助文档", "width=1100,height=700")
        return
      }
      window.open(`${appConfig.docUrl}/component/view/${viewComponentCode.toLowerCase()}.html#域配置`, "帮助文档", "width=1100,height=700")
    }
  },
  computed: {
    attrsConfMeta() {
      const {nativeValue, viewComponentCode} = this
      const value = {}
      for (let key of Object.keys(nativeValue)) {
        value[key] = buildMeta(nativeValue[key], key, viewComponentCode)
      }
      return value
    },
    componentCode() {
      const {nativeValue: {component_name}} = this
      return component_name
    },
    keys() {
      const {nativeValue = {}} = this
      return Object.keys(nativeValue).sort()
    }
  }
}
</script>

<style scoped lang="scss">
  .bottom-btn-group {
    display: flex;
    flex-direction: row;

    & > * {
      margin: 0 !important;
    }
  }
</style>
