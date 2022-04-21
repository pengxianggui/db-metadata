<template>
  <el-tabs type="border-card" style="height: 100%; overflow: auto;">
    <el-tab-pane label="域配置" style="height: 100%;">
      <ui-conf-editor v-model="activeItem" :object-code="objectCode" :field-code="fieldCode"
                      view-component-code="FormView"
                      v-if="!isEmpty(activeItem)"></ui-conf-editor>
      <div v-else class="blank-tip">
        请先选择一个字段
      </div>
    </el-tab-pane>

    <el-tab-pane label="表单配置">
      <ui-conf-editor v-model="formMeta" :object-code="objectCode" :field-code="fieldCode"
                      view-component-code="FormView"></ui-conf-editor>
    </el-tab-pane>
  </el-tabs>
</template>

<script>
import OptionsInput from './relate/OptionsInput'
import UiConfTip from "../component/UiConfTip";
import confFilter from './relate/confFilter'
import confComponentMapping from './relate/confComponentMapping'
import DefaultTextBoxMeta from '../../core/textbox/ui-conf'
import {isLayoutComp} from "./relate/componentData";
import MetaFieldConfigButton from "../component/MetaFieldConfigButton";
import utils from '@/../package/utils'
import ComponentSelector from "../component/ComponentSelector";
import ComponentPlus from "../component/ComponentPlus";
import UiConfEditor from "../component/UiConfEditor";


export default {
  name: "ConfArea",
  inject: ['objectCode'],
  props: {
    value: Object,
    activeItem: Object,
    fieldCode: String
  },
  components: {OptionsInput, MetaFieldConfigButton, UiConfTip, ComponentSelector, ComponentPlus, UiConfEditor},
  data() {
    return {
      metaMapping: confComponentMapping
    }
  },
  methods: {
    isEmpty(value) {
      return utils.isEmpty(value)
    },
    isLayoutComp(componentName) {
      return isLayoutComp(componentName)
    },
    editableJudge(componentName, key) {
      return confFilter(componentName, key);
    },
    getShowComponentName(key) {
      const metaMapping = this.metaMapping;
      if (metaMapping.hasOwnProperty(key)) {
        return metaMapping[key].component_name;
      }
      return DefaultTextBoxMeta.component_name;
    }
  },
  watch: {
    formMeta: {
      handler: function (newVal) {
        this.$emit('input', newVal);
      },
      deep: true
    }
  },
  computed: {
    formMeta: {
      get: function () {
        return this.value;
      },
      set: function (newVal) {
        this.$emit('input', newVal);
      }
    }
  }
}
</script>

<style scoped>
.container {
  height: 100%;
}

.blank-tip {
  height: 400px;
  line-height: 400px;
  text-align: center;
  border: 1px solid #eee;
  margin: 5px 0;
  color: #999999;
}
</style>
