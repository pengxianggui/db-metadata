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
    <z-toggle-panel :label-position="innerMeta['label-position']" :default-open="innerMeta['expand']">
        <div class="el-card" style="padding: 5px;">
            <el-form :ref="innerMeta['name']" v-bind="formConf" :model="model" inline
                     @keyup.enter.native="onSubmit" v-if="innerMeta.columns && innerMeta.columns.length > 0">
                <template v-for="(item) in innerMeta.columns">
                    <el-form-item :key="item.name" :label="item.label||item.name" :prop="item.name"
                                  v-if="model.hasOwnProperty(item.name)">
                        <component v-model="model[item.name]['value']" v-bind="linkProps(model, item)"
                                   @change="changeHandler(item.name)">
                            <template #prepend v-if="model[item.name]['symbol']['optional']">
                                <el-select v-model="model[item.name]['symbol']['value']" style="width: 60px;">
                                    <el-option v-for="(value, key) in model[item.name]['symbol']['options']"
                                               :key="key" :value="key">
                                        {{key}}
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
                        <el-button type="primary" @click="onSubmit">
                            <slot name="search-label">搜索</slot>
                        </el-button>
                        <el-button @click="onReset">重置</el-button>
                    </slot>
                </el-form-item>
            </el-form>
            <div style="float: right; margin: -20px 5px 0px 0px">
                <meta-easy-edit :object-code="innerMeta.objectCode" component-code="SearchView">
                    <template #label><i class="el-icon-setting"></i></template>
                </meta-easy-edit>
            </div>
        </div>
        <template #label>
            <slot name="label-bar">
                <el-divider>
                    <i class="el-icon-search"></i>
                </el-divider>
            </slot>
        </template>
    </z-toggle-panel>
</template>

<script>
    import util from '../../../utils'
    import MetaEasyEdit from '../../meta/src/MetaEasyEdit'
    import Meta from '../../mixins/meta'
    import symbols from '../ext/config'
    import DefaultMeta from '../ui-conf'
    import {defaultMeta} from '../../index'
    import {isEmpty} from "../../../utils/common";

    export default {
        name: "SearchView",
        mixins: [Meta(DefaultMeta)],
        components: {MetaEasyEdit},
        data() {
            return {
                model: {}
            }
        },
        methods: {
            linkProps(model, meta) {
                const {$merge} = this
                const {component_name, conf} = meta;
                const props = {
                    is: component_name,
                    meta: meta,
                    clearable: true
                }
                $merge(props, conf);

                switch (component_name) {
                    case 'DateBox':
                        $merge(props, {"is-range": true, "type": 'daterange'})
                        break;
                    case 'TimeBox':
                        $merge(props, {"is-range": true, "type": "timerange"})
                        break;
                    case 'DateTimeBox':
                        $merge(props, {"is-range": true, "type": 'datetimerange'})
                        break;
                }
                return props
            },
            toParams(model = {}) {
                let params = {};
                for (let key in model) {
                  let item = model[key];
                  let name = key + "_";
                  let value = item.value;
                  let symbol = item.symbol;

                  if (value == null || value.length == 0) continue;

                  switch (symbol.value) {
                    case "in":
                      value = Array.isArray(value) ? value.join(',') : value;
                      params[name + 'in'] = value;
                      break;
                    case "range":
                      params[name + "gt"] = value[0];
                      params[name + "lt"] = value[1];
                      break;
                    default:
                      params[name + symbol.options[symbol.value]] = value;
                  }
                }
                return params
            },
            changeHandler(name = '') {
                const {directlyTrigger = []} = this
                if (directlyTrigger.indexOf(name) > -1) {
                    this.onSubmit()
                }
            },
            onSubmit() {
                const {model, toParams} = this
                this.$emit('search', toParams(model));
            },
            onReset() {
                this.model = {};
                this.$emit('search', {});
            },
            assemblyModel(meta) {
                const {toParams, $merge} = this
                const {columns} = meta
                let shoot = false
                this.model = {}

                if (Array.isArray(columns)) {
                    columns.forEach(item => {
                        const {component_name: componentName, default_value:defaultValue} = item
                        $merge(item, defaultMeta[componentName]); // merge column
                        let symbol = util.deepClone(symbols.hasOwnProperty(componentName) ? symbols[componentName] : symbols['TextBox']);
                        let value = null;
                        if (!isEmpty(defaultValue)) {
                            value = defaultValue
                            shoot = true
                        }
                        this.$set(this.model, item.name, {
                            value: value,
                            symbol: symbol
                        });
                    });
                }

                if (shoot === true) {
                    this.$nextTick(() => {
                        this.$emit('search', toParams(this.model))
                    })
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
            innerMeta() {
                let meta = this.$merge(this.meta, DefaultMeta);
                this.assemblyModel(meta);
                return meta;
            },
            formConf() {
                const {innerMeta: {conf}, $attrs} = this
                return this.$reverseMerge(conf, $attrs)
            },
            directlyTrigger() {
                const {innerMeta: {directly_trigger: directlyTrigger}} = this
                return directlyTrigger
            }
        }
    }
</script>

<style scoped>
    .el-card {
        margin-bottom: 10px;
    }
</style>
