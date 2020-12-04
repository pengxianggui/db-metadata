<template>
  <div>
    <template v-if="formType">
      <el-form size="mini" :key="nativeValue.name" label-position="top">
        <template v-for="(value, key) in nativeValue">
          <template v-if="editableJudge(nativeValue.component_name, key)">
            <el-form-item :key="key" :label="key">

              <!--              特殊-->
              <component :is="nativeValue.component_name" :meta="nativeValue"
                         v-model="nativeValue[key]" v-if="key === 'default_value'"></component>

              <component-selector v-model="nativeValue['component_name']" scope="field"
                                  @change="handleCompChange(nativeValue['component_name'])"
                                  v-else-if="key === 'component_name'"></component-selector>

              <!--          <mini-form-box v-model="nativeValue[key]" :meta="attrsConfMeta[key]" :show-change-type="true"-->
              <!--                         v-else-if="attrsConfMeta[key]['component_name'] === 'MiniFormBox'">-->
              <!--            <template #button-expand="{value}">-->
              <!--              <el-popover placement="right" trigger="click"-->
              <!--                          popper-class="ui-conf-tip-popper">-->
              <!--                <ui-conf-tip :component-name="componentCode"></ui-conf-tip>-->
              <!--                <el-button slot="reference" size="mini" icon="el-icon-question" circle></el-button>-->
              <!--              </el-popover>-->
              <!--              <meta-field-config-button :object-code="objectCode" :field-code="fieldCode"-->
              <!--                                        v-if="objectCode && fieldCode && !isLayoutComp(nativeValue.component_name)">-->
              <!--                <template #default="{open}">-->
              <!--                  <el-button size="mini" icon="el-icon-s-tools" circle @click="open"></el-button>-->
              <!--                </template>-->
              <!--              </meta-field-config-button>-->
              <!--              TODO 元对象编辑-->
              <!--            </template>-->
              <!--          </mini-form-box>-->

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
      <json-box v-model="nativeValue" mode="code" @input="handleJsonChange"></json-box>
    </template>

    <div style="display: flex; flex-direction: row">
      <span style="flex: 1"></span>
      <el-button size="mini" icon="el-icon-refresh" circle @click="changeType"></el-button>
      <el-popover placement="right" trigger="click"
                  popper-class="ui-conf-tip-popper">
        <ui-conf-tip :component-name="componentCode"></ui-conf-tip>
        <el-button slot="reference" size="mini" icon="el-icon-question" circle></el-button>
      </el-popover>
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
import UiConfTip from "./UiConfTip";
import MetaFieldConfigButton from "./MetaFieldConfigButton";
import {defaultMeta} from "../../core";
import Val from "../../core/mixins/value";
import {isObject} from '@/../package/utils/common'

let conver = function (value) {
  if (!isObject(value)) {
    return {}
  }
  return value
}

export default {
  name: "UiConfEditor",
  mixins: [Val(conver)],
  components: {ComponentSelector, UiConfTip, MetaFieldConfigButton},
  props: {
    value: {
      type: {
        type: Object,
        default: () => {
        }
      }
    },
    objectCode: {
      type: String
    },
    fieldCode: {
      type: String
    }
  },
  data() {
    return {
      formType: true
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
    handleCompChange(value) {
      const {nativeValue} = this
      for (let key of Object.keys(nativeValue)) {
        if (key !== 'name' && key !== 'label') { // name和label是需要保留的
          this.$delete(nativeValue, key)
        }
      }
      this.$merge(nativeValue, defaultMeta[value])
    },
    handleJsonChange() {
      // TODO 当值中的component_name发生变化时, 需要重新替换整个nativeValue
      console.log(this.nativeValue)
    }
  },
  computed: {
    attrsConfMeta() {
      const {nativeValue} = this
      const value = {}
      for (let key of Object.keys(nativeValue)) {
        value[key] = buildMeta(nativeValue[key], key)
      }
      return value
    },
    componentCode() {
      const {nativeValue: {component_name}} = this
      return component_name
    }
  }
}
</script>

<style scoped>

</style>