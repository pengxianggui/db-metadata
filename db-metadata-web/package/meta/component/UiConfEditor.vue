<template>
  <div>
    <el-form size="mini" :model="value" label-position="top" v-show="formType">
      <template v-for="key in keys">
        <template v-if="editableJudge(value.component_name, key)">
          <el-form-item :key="key" :label="key">

            <component-selector v-model="value.component_name" @change="handleCompChange"
                                scope="field" :components="fieldComponents"
                                :popper-append-to-body="false" :disabled="value.component_name === viewComponentCode"
                                v-if="key === 'component_name'"></component-selector>

            <!--              特殊-->
            <component :is="value.component_name" :meta="value"
                       v-model="value[key]" v-else-if="key === 'default_value'"></component>


            <!--              常规-->
            <component :is="attrsConfMeta[key]['component_name']" v-model="value[key]"
                       :meta="attrsConfMeta[key]"
                       v-else></component>

          </el-form-item>
        </template>
      </template>
    </el-form>

    <!--
     这里有一个大坑！不能直接使用v-model，会产生两个问题:
     1. 由于JsonBox内部v-model实现，会导致更新后的值内存地址发生了变化，即value不在是原先那个value，这会导致域配置的更新没有体现到容器配置中。
        因为value就是容器配置中columns中点选的那个域配置，是同一个内存地址。而之所以上面的form不会产生这样的问题，是form形式是直接修改value里的属性值，
        而不是改value，因此value指针指向的内存地址是不变的。对value的改动，就是对容器配置中columns点选域配置的改变。
     2. 倘若直接使用v-model, JsonBox中若更改了component_name, 则无法根据component_name的新值重新刷新配置
     解决办法: 此处使用:value和@input组合解构v-model的语法糖，如此一来就可以在handleInput方法中避免上述两个问题。
    -->
    <json-box :value="value" @input="handleInput" mode="code" :modes="['code']"
              :height="jsonBoxHeight" v-show="!formType"></json-box>

    <div class="bottom-btn-group">
      <span style="flex: 1"></span>
      <el-button size="mini" icon="el-icon-refresh" circle @click="changeType"></el-button>
      <doc-link :path="docPath"></doc-link>

      <meta-field-config-button :object-code="objectCode" :field-code="fieldCode"
                                v-if="objectCode && fieldCode && !isLayoutComp(value.component_name)">
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
import DocLink from "../../doc/DocLink";
import {buildDefaultMeta} from "../../core";
import utils from '../../utils'
import {restUrl} from "../../constant/url";

export default {
  name: "UiConfEditor",
  components: {ComponentSelector, MetaFieldConfigButton, DocLink},
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
    },
    defaultFormType: {
      type: Boolean,
      default: () => false
    },
    jsonBoxHeight: {
      type: String,
      default: () => '300px'
    }
  },
  data() {
    return {
      fieldComponents: [],
      formType: this.defaultFormType
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
    handleInput(newV) {
      // 这里先删除this.value的所有属性，然后在merge上去，是防止value变量指向的内存地址发生变化。
      const {component_name: oldComponentName} = this.value
      const {component_name: newComponentName} = newV

      if (oldComponentName !== newComponentName) {
        this.$message.warning('请在表单模式中下拉切换组件, 否则可能导致配置异常')
      }

      utils.deleteAllAttrs(this.value)
      this.$merge(this.value, newV)
    },
    editableJudge(componentName, key) {
      return confFilter(componentName, key);
    },
    isLayoutComp(componentName) {
      return isLayoutComp(componentName)
    },
    changeType() {
      this.formType = !this.formType;
      this.$emit('change-type', this.formType);
    },
    handleCompChange(newV) {
      const {objectCode, fieldCode} = this
      utils.deleteAllAttrs(this.value, ['name', 'label'])
      const defMeta = buildDefaultMeta(newV, objectCode, fieldCode)
      this.$merge(this.value, utils.assertEmpty(defMeta, {
        component_name: 'TextBox'
      }))
    }
  },
  computed: {
    attrsConfMeta() {
      const {value, viewComponentCode} = this
      const attrsConfMeta = {}
      for (let key of Object.keys(value)) {
        attrsConfMeta[key] = buildMeta(value[key], key, viewComponentCode)
      }
      return attrsConfMeta
    },
    componentCode() {
      const {value: {component_name}} = this
      return component_name
    },
    keys() {
      const {value = {}} = this
      return Object.keys(value).sort()
    },
    docPath() {
      const {viewComponentCode, componentCode: fieldComponentCode} = this
      if ((viewComponentCode === 'FormView' && fieldComponentCode !== 'FormView')
          || (viewComponentCode === 'SearchView' && fieldComponentCode !== 'SearchView') ) {
        return `/component/field/${fieldComponentCode.toLowerCase()}.html#配置项`
      }
      return `/component/view/${viewComponentCode.toLowerCase()}.html#域配置`
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
