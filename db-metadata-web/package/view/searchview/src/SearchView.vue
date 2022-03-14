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
               @keyup.enter.native="emitSearch" class="search-form">
        <template v-for="item in meta.columns">
          <el-form-item class="form-item" :key="item.name" :label="item.label||item.name" :prop="item.name"
                        v-if="model.hasOwnProperty(item.name)">
            <component v-model="model[item.name]['value']"
                       :is="item.component_name"
                       :meta="item"
                       @change="changeHandler(item.name)">
              <template #prepend v-if="model[item.name]['symbol']['optional']">
                <el-select v-model="model[item.name]['symbol']['value']" style="width: 60px;">
                  <el-option v-for="(value, key) in model[item.name]['symbol']['options']"
                             :key="key" :value="key">
                    {{ key }}
                  </el-option>
                </el-select>
              </template>
            </component>

            <!--                        <drop-down-box v-model="model[item.name]['value']" :meta="item|decorate('DropDownBox')"-->
            <!--                                       v-if="['DropDownBox'].indexOf(item.component_name) >= 0">-->
            <!--                        </drop-down-box>-->

            <!--                        <bool-box v-model="model[item.name]['value']" :meta="item"-->
            <!--                                  v-else-if="['BoolBox'].indexOf(item.component_name) >= 0"></bool-box>-->

            <!--                        <el-date-picker v-model="model[item.name]['value']" v-bind="item.conf"-->
            <!--                                        is-range type="daterange"-->
            <!--                                        v-else-if="['DateBox'].indexOf(item.component_name) >= 0">-->
            <!--                        </el-date-picker>-->

            <!--                        <el-time-picker v-model="model[item.name]['value']" v-bind="item.conf"-->
            <!--                                        is-range type="timerange"-->
            <!--                                        v-else-if="['TimeBox'].indexOf(item.component_name) >= 0">-->
            <!--                        </el-time-picker>-->

            <!--                        <el-date-picker v-model="model[item.name]['value']" v-bind="item.conf"-->
            <!--                                        is-range type="datetimerange"-->
            <!--                                        v-else-if="['DateTimeBox'].indexOf(item.component_name) >= 0">-->
            <!--                        </el-date-picker>-->

            <!--                        <el-input v-model="model[item.name]['value']" v-bind="item.conf" clearable v-else>-->
            <!--                            <template #prepend v-if="model[item.name]['symbol']['optional']">-->
            <!--                                <el-select v-model="model[item.name]['symbol']['value']" style="width: 60px;">-->
            <!--                                    <el-option v-for="(value, key) in model[item.name]['symbol']['options']"-->
            <!--                                               :key="key" :value="key">-->
            <!--                                        {{key}}-->
            <!--                                    </el-option>-->
            <!--                                </el-select>-->
            <!--                            </template>-->
            <!--                        </el-input>-->

          </el-form-item>
        </template>
        <el-form-item>
          <slot name="action" v-bind:model="model">
            <el-button type="primary" @click="emitSearch" icon="el-icon-search">搜索</el-button>
            <el-button @click="onReset" icon="el-icon-refresh-left">重置</el-button>
          </slot>
        </el-form-item>
      </el-form>
      <div style="float: right; margin: -20px 10px 0px 0px">
        <meta-easy-edit :object-code="meta.objectCode" component-code="SearchView">
          <template #label><i class="el-icon-setting"></i></template>
        </meta-easy-edit>
      </div>
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
import {defaultMeta} from '@/../package/core/index'
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
        this.$merge(item, defaultMeta[componentName]); // merge column
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
  }
}

</style>
