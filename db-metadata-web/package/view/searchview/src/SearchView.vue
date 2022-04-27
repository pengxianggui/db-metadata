<!--
    根据组件元对象的组件类别(component_name)决定比较符号(等于(eq)、不等于(ne)、大于(gt)、小于(lt)、大于等于(ge)、小于等于(le)、范围(range)、模糊(like)、in操作)

    "TextBox": "=",
    "DropDownBox": "in",
    "DateBox": "range",
    "TimeBox": "range",
    "DateTimeBox": "range",
    "NumBox": "=" // 可由用户进行下拉筛选

    其余类别的组件全部是eq逻辑
-->
<template>
  <z-toggle-panel :label-position="meta['label-position']" :default-open="meta['expand']"
                  v-if="meta.columns && meta.columns.length > 0">
    <div class="view-container">
      <el-form :ref="meta['name']" v-bind="formConf" :model="model" inline
               @keyup.enter.native="emitSearch" @submit.native.prevent class="search-form">
        <template v-for="item in meta.columns">
          <el-form-item class="form-item" :key="item.name" :label="item.label || item.name" :prop="item.name"
                        v-if="model.hasOwnProperty(item.name)">

            <component v-model="model[item.name]['value']"
                       :is="item.component_name === 'NumBox' ? 'TextBox' : item.component_name"
                       :meta="item"
                       @change="changeHandler(item.name)">
              <template v-slot:[model[item.name].symbol.optionSlot]
                        v-if="model[item.name]['symbol']['optional'] === true && item['show-symbol-option'] === true">
                <el-select v-model="model[item.name]['symbol']['value']" style="width: 60px;">
                  <el-option v-for="(value, key) in model[item.name]['symbol']['options']"
                             :key="key" :value="key">
                    {{ key }}
                  </el-option>
                </el-select>
              </template>
            </component>
          </el-form-item>
        </template>
        <el-form-item>
          <slot name="action" v-bind:model="model" v-bind:conf="buttonsConf">
            <el-button type="primary" @click="emitSearch"
                       v-bind="buttonsConf.submit.conf" v-if="buttonsConf.submit.show">{{buttonsConf.submit.label}}</el-button>
            <el-button @click="onReset"
                       v-bind="buttonsConf.reset.conf" v-if="buttonsConf.reset.show">{{buttonsConf.reset.label}}</el-button>
          </slot>
        </el-form-item>
      </el-form>
    </div>

    <template #label>
      <slot name="label-bar">
        <el-divider class="divider">
          <i class="el-icon-search"></i>
        </el-divider>
      </slot>
    </template>
  </z-toggle-panel>
</template>

<script>
import util from '@/../package/utils'
import MetaEasyEdit from '@/../package/core/meta/src/MetaEasyEdit'
import symbols from '../ext/config'
import {toParams} from "../ext/config";
import DefaultMeta from '../ui-conf'
import {assembleMeta} from '../ui-conf'
import {buildDefaultMeta} from '@/../package/core/index'
import {ViewMetaBuilder} from "../../ext/mixins";
import {isEmpty} from "@/../package/utils/common";

export default {
  name: "SearchView",
  mixins: [ViewMetaBuilder(DefaultMeta, assembleMeta)],
  components: {MetaEasyEdit},
  data() {
    return {
      model: {}
    }
  },
  methods: {
    changeHandler(name = '') {
      const {directlyTrigger = []} = this
      if (directlyTrigger.indexOf(name) > -1) {
        this.emitSearch()
      }
    },
    emitSearch() {
      this.$emit('search', toParams(this.model));
    },
    onReset() {
      this.assemblyModel(this.meta)
      this.emitSearch()
    },
    assemblyModel(meta) {
      const {columns = []} = meta
      columns.forEach(item => {
        const {component_name: componentName, default_value: defaultValue} = item
        this.$merge(item, buildDefaultMeta(componentName)); // merge column
        let symbol = util.deepClone(symbols.hasOwnProperty(componentName) ? symbols[componentName] : symbols['TextBox']);
        let value = null;
        if (!isEmpty(defaultValue)) {
          value = defaultValue
        }
        this.$set(this.model, item.name, {
          value: value,
          symbol: symbol
        });
      });
    },
    init() {
      this.assemblyModel(this.meta);
      if (Object.values(this.model).some(({value}) => !isEmpty(value))) {
        this.emitSearch()
      }
    }
  },
  filters: {
    decorate(meta, componentName) {
      switch (componentName) {
        case "DropDownBox":
          // meta.conf['multiple'] = true;
          break;
      }
      return meta;
    }
  },
  computed: {
    formConf() {
      const {meta: {conf}, $attrs} = this
      return this.$reverseMerge(conf, $attrs)
    },
    directlyTrigger() {
      const {meta: {directly_trigger: directlyTrigger}} = this
      return directlyTrigger
    },
    buttonsConf() {
      const {buttons: buttonsConf = {}} = this.meta
      return buttonsConf
    }
  }
}
</script>

<style scoped lang="scss">
.divider {
  ::v-deep {
    .el-divider__text {
      background-color: #f7f7f7;
    }
  }
}

.search-form {
  .el-form-item {
    margin: 5px;

    ::v-deep {
      input::-webkit-outer-spin-button,
      input::-webkit-inner-spin-button {
        -webkit-appearance: none;
      }
      input[type="number"] {
        -moz-appearance: textfield;
      }
    }
  }
}

</style>
