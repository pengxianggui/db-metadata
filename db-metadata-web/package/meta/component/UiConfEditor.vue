<template>
  <div>
    <template v-if="formType">
      <el-form size="mini" :key="jsonValue.name" label-position="top">
        <template v-for="(value, key) in jsonValue">
          <template v-if="editableJudge(jsonValue.component_name, key)">
            <el-form-item :key="key" :label="key">

              <!--              特殊-->
              <component :is="jsonValue.component_name" :meta="jsonValue"
                         v-model="jsonValue[key]" v-if="key === 'default_value'"></component>

              <component-selector v-model="jsonValue['component_name']" scope="field"
                                  @change="handleCompChange(jsonValue['component_name'])"
                                  v-else-if="key === 'component_name'"></component-selector>

              <!--          <mini-form-box v-model="jsonValue[key]" :meta="attrsConfMeta[key]" :show-change-type="true"-->
              <!--                         v-else-if="attrsConfMeta[key]['component_name'] === 'MiniFormBox'">-->
              <!--            <template #button-expand="{value}">-->
              <!--              <el-popover placement="right" trigger="click"-->
              <!--                          popper-class="ui-conf-tip-popper">-->
              <!--                <ui-conf-tip :component-name="componentCode"></ui-conf-tip>-->
              <!--                <el-button slot="reference" size="mini" icon="el-icon-question" circle></el-button>-->
              <!--              </el-popover>-->
              <!--              <meta-field-config-button :object-code="objectCode" :field-code="fieldCode"-->
              <!--                                        v-if="objectCode && fieldCode && !isLayoutComp(jsonValue.component_name)">-->
              <!--                <template #default="{open}">-->
              <!--                  <el-button size="mini" icon="el-icon-s-tools" circle @click="open"></el-button>-->
              <!--                </template>-->
              <!--              </meta-field-config-button>-->
              <!--              TODO 元对象编辑-->
              <!--            </template>-->
              <!--          </mini-form-box>-->

              <!--              常规-->
              <component :is="attrsConfMeta[key]['component_name']" v-model="jsonValue[key]" :meta="attrsConfMeta[key]"
                         v-else></component>

            </el-form-item>
          </template>
        </template>
      </el-form>
    </template>
    <template v-else>
      <json-box v-model="jsonValue" mode="code" @input="$emit('json-change')"></json-box>
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
                                v-if="objectCode && fieldCode && !isLayoutComp(jsonValue.component_name)">
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

export default {
  name: "UiConfEditor",
  components: {ComponentSelector, UiConfTip, MetaFieldConfigButton},
  props: {
    jsonValue: {
      type: Object
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
      const {jsonValue} = this
      for (let key of Object.keys(jsonValue)) {
        if (key !== 'name' && key !== 'label') { // name和label是需要保留的
          this.$delete(jsonValue, key)
        }
      }
      this.$merge(jsonValue, defaultMeta[value])
    }
  },
  computed: {
    attrsConfMeta() {
      const {jsonValue} = this
      const value = {}
      for (let key of Object.keys(jsonValue)) {
        value[key] = buildMeta(jsonValue[key], key)
      }
      return value
    },
    componentCode() {
      const {jsonValue: {component_name}} = this
      return component_name
    }
  }
}
</script>

<style scoped>

</style>