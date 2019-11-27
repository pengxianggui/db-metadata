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
    <z-toggle-panel label-position="bottom-right">
        <el-card>
            <el-form :ref="innerMeta['name']" v-bind="innerMeta.conf" :model="model" inline>
                <template v-for="(item) in innerMeta.columns">
                    <el-form-item :key="item.name" :label="item.label" :prop="item.name"
                                  v-if="model.hasOwnProperty(item.name)">
                        <DropDownBox v-model="model[item.name]['value']" :meta="item|decorate('DropDownBox')"
                                     v-if="['DropDownBox'].indexOf(item.component_name) >= 0">
                        </DropDownBox>

                        <BoolBox v-model="model[item.name]['value']" :meta="item"
                                 v-else-if="['BoolBox'].indexOf(item.component_name) >= 0"></BoolBox>

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
                            <template #prepend v-if="item.component_name == 'NumBox'">
                                <el-select v-model="model[item.name]['symbol']" style="width: 70px;">
                                    <el-option v-for="(value, key) in symbols" :key="key" :value="key">{{key}}
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
        </el-card>
        <template #label>
            <slot name="label-bar"></slot>
        </template>
    </z-toggle-panel>
</template>

<script>
    import {DEFAULT} from '@/constant'
    import Meta from './mixins/meta'

    // 控件默认的搜索符，其他控件均为eq
    let defaultSymbol = {
        "TextBox": "=", // should support like symbol
        "DropDownBox": "in",
        "DateBox": "range",
        "TimeBox": "range",
        "DateTimeBox": "range"
    };

    export default {
        name: "SearchPanel",
        mixins: [Meta(DEFAULT.SearchPanel)],
        data() {
            return {
                model: {},
                symbols: {
                    "=": "eq",
                    "!=": "ne",
                    ">": "gt",
                    "<": "lt",
                    ">=": "ge",
                    "<=": "le",
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

                    switch (symbol) {
                        case "in":
                            value = value.join(',');
                            params[name + symbol] = value;
                            break;
                        case "range":
                            params[name + "gt"] = value[0];
                            params[name + "lt"] = value[1];
                            break;
                        default:
                            params[name + this.symbols[symbol]] = value;
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
                let columns = meta.columns;
                if (Array.isArray(columns)) {
                    columns.forEach(item => {
                        let componentName = item.component_name;
                        this.$merge(item, DEFAULT[componentName]); // merge column
                        this.$set(this.model, item.name, {
                            value: null,
                            symbol: defaultSymbol.hasOwnProperty(componentName) ? defaultSymbol[componentName] : '='
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

</style>