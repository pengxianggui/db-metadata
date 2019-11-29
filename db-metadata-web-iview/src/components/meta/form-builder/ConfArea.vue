<template>
    <div class="">
        <el-tabs type="border-card">
            <el-tab-pane label="域配置">
<!--                <JsonBox v-model="activeFieldMeta"></JsonBox>-->

                <el-form size="mini">
                    <template v-for="(value, key, index) in activeFieldMeta">
                        <template v-if="excludes.indexOf(key) < 0">
                            <el-form-item :key="key" :label="key" v-if="key !== 'conf'">
                                <component :is="type(activeFieldMeta['name'], key, value)" v-model="activeFieldMeta[key]"></component>
                            </el-form-item>
                            <el-form-item :key="key" :label="key" v-else>
                                <div :key="key">
                                    <json-box v-model="activeFieldMeta[key]" mode="form"></json-box>
                                </div>
                            </el-form-item>
                        </template>
                    </template>
                </el-form>

            </el-tab-pane>

            <el-tab-pane label="表单配置">
<!--                <JsonBox v-model="formMeta"></JsonBox>-->
                <el-form size="mini">
                    <template v-for="(value, key, index) in formMeta">
                        <template v-if="excludes.indexOf(key) < 0">
                            <el-form-item :key="key" :label="key" v-if="key !== 'conf'">
                                <component :is="type(formMeta['name'], key, value)" v-model="formMeta[key]"></component>
                            </el-form-item>
                            <el-form-item :key="key" :label="key" v-else>
                                <div :key="key">
                                    <json-box v-model="formMeta[key]" mode="form"></json-box>
                                </div>
                            </el-form-item>
                        </template>
                    </template>
                </el-form>
            </el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
    import utils from '@/utils'
    import cloneDeep from 'lodash/cloneDeep'
    import OptionsInput from './relate/OptionsInput'
    import JsonBox from "@/components/core/form/JsonBox";

    export default {
        name: "ConfArea",
        props: {
            value: Object,
            selectIndex: Number
        },
        components: {JsonBox, OptionsInput},
        data() {
            return {
                excludes: ['component_name', 'columns', 'btns', 'name'], // temporarily not allow customize
                typeMapping: {}
            }
        },
        methods: {
            cloneDeep: cloneDeep,
            type(field, key, value) {
                let fKey = field + "." + key;
                if (this.typeMapping.hasOwnProperty(fKey)) {
                    value = this.typeMapping[fKey];
                } else {
                    this.typeMapping[fKey] = value;
                }
                let type = utils.typeOf(value);
                let componentName = "TextBox";
                switch (type) {
                    case "[object String]":
                        componentName = "TextBox";
                        break;
                    case "[object Number]":
                        componentName = "NumBox";
                        break;
                    case "[object Boolean]":
                        componentName = "BoolBox";
                        break;
                    case "[object Array]":
                        if (key === 'options') {
                            componentName = 'options-input';
                        } else {
                            componentName = 'JsonBox';
                        }
                        break;
                    case "[object Object]":
                        componentName = "JsonBox";
                        break;
                }
                return componentName;
            }
        },
        watch: {
            formMeta: {
                handler: function (newVal, oldVal) {
                    console.log('formMeta..update..');
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
            activeFieldMeta() {
                return this.formMeta.columns[this.selectIndex];
            }
        }
    }
</script>

<style scoped>
    .container {
        display: flex;
        flex-direction: column;
        height: 100%;
    }

    .container > .el-row {
        flex: 1;
        padding: 20px 0;
        overflow: auto;
    }
</style>