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
        <div class="el-card">
            <el-form :ref="innerMeta['name']" v-bind="innerMeta.conf" :model="model" inline
                     @keyup.enter.native="onSubmit" v-if="innerMeta.columns.length > 0">
                <template v-for="(item) in innerMeta.columns">
                    <el-form-item :key="item.name" :label="item.label||item.name" :prop="item.name"
                                  v-if="model.hasOwnProperty(item.name)">
                        <drop-down-box v-model="model[item.name]['value']" :meta="item|decorate('DropDownBox')"
                                       v-if="['DropDownBox'].indexOf(item.component_name) >= 0">
                        </drop-down-box>

                        <bool-box v-model="model[item.name]['value']" :meta="item"
                                  v-else-if="['BoolBox'].indexOf(item.component_name) >= 0"></bool-box>

                        <el-date-picker v-model="model[item.name]['value']" v-bind="item.conf"
                                        is-range type="daterange"
                                        v-else-if="['DateBox'].indexOf(item.component_name) >= 0">
                        </el-date-picker>

                        <el-time-picker v-model="model[item.name]['value']" v-bind="item.conf"
                                        is-range type="timerange"
                                        v-else-if="['TimeBox'].indexOf(item.component_name) >= 0">
                        </el-time-picker>

                        <el-date-picker v-model="model[item.name]['value']" v-bind="item.conf"
                                        is-range type="datetimerange"
                                        v-else-if="['DateTimeBox'].indexOf(item.component_name) >= 0">
                        </el-date-picker>

                        <el-input v-model="model[item.name]['value']" v-bind="item.conf" clearable v-else>
                            <template #prepend>
                                <el-select v-model="model[item.name]['symbol']['value']" style="width: 80px;">
                                    <el-option v-for="(value, key) in model[item.name]['symbol']['options']"
                                               :key="key" :value="key">
                                        {{key}}
                                    </el-option>
                                </el-select>
                            </template>
                        </el-input>
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
    import {DEFAULT} from '@/constant'
    import Meta from './mixins/meta'
    import BoolBox from "./form/BoolBox";
    import DropDownBox from "./form/DropDownBox";
    import util from '@/utils'

    export default {
        name: "SearchPanel",
        components: {DropDownBox, BoolBox},
        mixins: [Meta(DEFAULT.SearchPanel)],
        data() {
            return {
                model: {},
                symbols: {
                    "TextBox": {
                        "value": "%v%",
                        "options": {
                            "%v": "lk_l",
                            "v%": "lk_r",
                            "%v%": "lk"
                        }
                    },
                    "BoolBox": {
                        "value": "=",
                        "options": {
                            "=": "eq"
                        }
                    },
                    "NumBox": {
                        "value": "=",
                        "options": {
                            "=": "eq",
                            "!=": "ne",
                            ">": "gt",
                            "<": "lt",
                            ">=": "ge",
                            "<=": "le"
                        }
                    },
                    "DropDownBox": {
                        "value": "in",
                        "options": {
                            "in": "in"
                        }
                    },
                    "DateBox": {
                        "value": "range",
                        "options": {
                            "range": "range"
                        }
                    },
                    "TimeBox": {
                        "value": "range",
                        "options": {
                            "range": "range"
                        }
                    },
                    "DateTimeBox": {
                        "value": "range",
                        "options": {
                            "range": "range"
                        }
                    }
                }
            }
        },
        methods: {
            onSubmit() {
                let params = {};
                let model = this.model;
                for (let key in model) {
                    let item = model[key];
                    let name = key + "_";
                    let value = item.value;
                    let symbol = item.symbol;

                    if (value == null || value.length == 0) continue;

                    switch (symbol.value) {
                        case "in":
                            value = value.join(',');
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

                this.$emit('search', params);
            },
            onReset() {
                this.model = {};
                this.$emit('search', {});
            },
            assemblyModel(meta) {
                this.model = {};
                let symbols = this.symbols;
                let columns = meta.columns;

                if (Array.isArray(columns)) {
                    columns.forEach(item => {
                        let componentName = item.component_name;
                        this.$merge(item, DEFAULT[componentName]); // merge column
                        let symbol = util.deepCopy(symbols.hasOwnProperty(componentName) ? symbols[componentName] : symbols['TextBox']);
                        this.$set(this.model, item.name, {
                            value: null,
                            symbol: symbol
                        });
                    });
                }
            }
        },
        filters: {
            decorate(meta, componentName) {
                switch (componentName) {
                    case "DropDownBox":
                        meta.conf['multiple'] = true;
                        break;
                }
                return meta;
            }
        },
        computed: {
            innerMeta() {
                let meta = this.$merge(this.meta, DEFAULT.SearchPanel);
                this.assemblyModel(meta);
                return meta;
            }
        }
    }
</script>

<style scoped>
    .el-card {
        margin-bottom: 10px;
    }
</style>