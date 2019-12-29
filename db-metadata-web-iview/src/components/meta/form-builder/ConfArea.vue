<template>
    <el-tabs type="border-card" style="height: 100%; overflow: auto;">
        <el-tab-pane label="域配置" style="height: 100%;">
            <el-form size="mini" v-if="activeFieldMeta" :key="activeFieldMeta.name" label-position="left">
                <template v-for="(value, key, index) in activeFieldMeta">
                    <template v-if="excludes.indexOf(key) < 0">
                        <el-form-item :key="key" :label="key" v-if="key !== 'default_value'">
                            <component :is="getShowComponentName(key)" :meta="metaMapping[key]"
                                       v-model="activeFieldMeta[key]"></component>
                        </el-form-item>
                        <el-form-item :key="key" :label="key" v-else>
                            <div :key="key">
                                <component :is="activeFieldMeta.component_name" :meta="activeFieldMeta"
                                           v-model="activeFieldMeta[key]"></component>
                            </div>
                        </el-form-item>
                    </template>
                </template>
                <el-form-item label="逻辑配置">
                    <el-card>
                        <mini-form-box v-model="fieldConf"></mini-form-box>
                    </el-card>
                </el-form-item>
            </el-form>
        </el-tab-pane>

        <el-tab-pane label="表单配置">
            <el-form size="mini">
                <template v-for="(value, key, index) in formMeta">
                    <template v-if="excludes.indexOf(key) < 0">
                        <el-form-item :key="key" :label="key">
                            <component :is="metaMapping[key].component_name" :meta="metaMapping[key]"
                                       v-model="formMeta[key]"></component>
                        </el-form-item>
                    </template>
                </template>
            </el-form>
        </el-tab-pane>
    </el-tabs>
</template>

<script>
    import {DEFAULT, URL} from "@/constant";
    import utils from '@/utils'
    import OptionsInput from './relate/OptionsInput'

    const CUSTOM_CONF_COMPONENT_MAPPING = {
        name: DEFAULT.TextBox,
        label: DEFAULT.TextBox,
        component_name: utils.merge({data_url: URL.COMPONENT_CODE_LIST}, DEFAULT.DropDownBox),
        conf: DEFAULT.JsonBox,
        inline: DEFAULT.BoolBox,
        data_url: DEFAULT.TextBox,
        delete_url: DEFAULT.TextBox,
        multi_select: DEFAULT.BoolBox,
        editable: DEFAULT.BoolBox,
        options: {
            component_name: 'OptionsInput'
        },
        group: DEFAULT.BoolBox,
        expand: DEFAULT.BoolBox,
        "label-position": utils.merge({
            options: ['top-center', 'top-left', 'top-right', 'bottom-center', 'bottom-left', 'bottom-right']
        }, DEFAULT.DropDownBox),
        action: DEFAULT.TextBox,
        check: DEFAULT.BoolBox,
        check_url: DEFAULT.TextBox,
        theme: utils.merge({options: ['default', 'ambiance']}, DEFAULT.RadioBox),
        lineNumbers: DEFAULT.BoolBox,
        mode: utils.merge({options: ['text/x-mysql']}, DEFAULT.DropDownBox)
    };
    const EXCLUDES = ['columns', 'btns', 'name', 'group', 'objectCode', 'objectPrimaryKey'];

    export default {
        name: "ConfArea",
        props: {
            value: Object,
            selectIndex: Number
        },
        components: {OptionsInput},
        data() {
            return {
                fieldConf: {},
                excludes: EXCLUDES, // temporarily not allow customize
                metaMapping: CUSTOM_CONF_COMPONENT_MAPPING
            }
        },
        methods: {
            getShowComponentName(key) {
                const metaMapping = this.metaMapping;
                if (metaMapping.hasOwnProperty(key)) {
                    return metaMapping[key].component_name;
                }
                return DEFAULT.TextBox.component_name;
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
        height: 100%;
    }
</style>