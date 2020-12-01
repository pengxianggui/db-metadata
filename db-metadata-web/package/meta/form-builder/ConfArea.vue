<template>
  <el-tabs type="border-card" style="height: 100%; overflow: auto;">
    <el-tab-pane label="域配置" style="height: 100%;">
      <el-form size="mini" v-if="!isEmpty(activeItem)" :key="activeItem.name" label-position="top">
        <template v-for="(value, key) in activeItem">
          <template v-if="editableJudge(activeItem.component_name, key)">
            <el-form-item :key="key" :label="key" v-if="key !== 'default_value'">
              <component :is="getShowComponentName(key)" :meta="metaMapping[key]" v-model="activeItem[key]"></component>
            </el-form-item>
            <el-form-item :key="key" :label="key" v-else>
                <component :is="activeItem.component_name" :meta="activeItem"
                           v-model="activeItem[key]"></component>
            </el-form-item>
          </template>
        </template>
        <el-form-item label="逻辑配置" v-if="!isLayoutComp(activeItem.component_name)">
          <meta-field-config-button :object-code="objectCode" :field-code="activeItem.name">
            <template #default="{open}">
              <el-button size="mini" icon="el-icon-s-tools" circle @click="open"></el-button>
            </template>
          </meta-field-config-button>
        </el-form-item>
      </el-form>
      <div v-else class="blank-tip">
        请先选择一个字段
      </div>
    </el-tab-pane>

    <el-tab-pane label="表单配置">
      <el-form size="mini" label-position="top">
        <template v-for="(value, key) in formMeta">
          <template v-if="editableJudge('FormView', key)">
            <el-form-item :key="key" :label="key">
              <component :is="getShowComponentName(key)" :meta="metaMapping[key]"
                         v-model="formMeta[key]"></component>
            </el-form-item>
          </template>
        </template>
      </el-form>
    </el-tab-pane>
  </el-tabs>
</template>

<script>
import OptionsInput from './relate/OptionsInput'
import confFilter from './relate/confFilter'
import confComponentMapping from './relate/confComponentMapping'
import DefaultTextBoxMeta from '../../core/textbox/ui-conf'
import {isLayoutComp} from "./relate/componentData";
import MetaFieldConfigButton from "../component/MetaFieldConfigButton";
import utils from '@/../package/utils'


export default {
  name: "ConfArea",
  props: {
    value: Object,
    activeItem: Object,
    objectCode: String
  },
  components: {OptionsInput, MetaFieldConfigButton},
  data() {
    return {
      fieldConf: {},
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
    },
    // activeFieldMeta() {
    // return this.formMeta.columns[this.active];
    // }
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
