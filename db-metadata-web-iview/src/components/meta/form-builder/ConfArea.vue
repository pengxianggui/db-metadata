<template>
    <div class="container el-card">
        <el-row>
            <el-col>
                <h5>表单配置</h5>
                <el-form>
                    <JsonBox v-model="formMeta"></JsonBox>
<!--                    <template v-for="(value, key, index) in formMeta">-->
<!--                        <template v-if="excludes.indexOf(key) < 0">-->
<!--                            <el-form-item :key="key" :label="key" v-if="key !== 'conf'">-->
<!--                                <component v-bind:is="value|type" v-model="formMeta[key]"></component>-->
<!--                            </el-form-item>-->
<!--                            <template v-else>-->
<!--                                <div :key="key">-->
<!--                                    <h6>Element Conf</h6>-->
<!--                                    <JsonBox v-model="formMeta[key]" mode="form"></JsonBox>-->
<!--                                    &lt;!&ndash;                                    <template v-for="(confV, confK) in value">&ndash;&gt;-->
<!--                                    &lt;!&ndash;                                        <el-form-item :key="confK" :label="confK">&ndash;&gt;-->
<!--                                    &lt;!&ndash;                                            <component :is="confV|type" v-model="value[confK]"></component>&ndash;&gt;-->
<!--                                    &lt;!&ndash;                                        </el-form-item>&ndash;&gt;-->
<!--                                    &lt;!&ndash;                                    </template>&ndash;&gt;-->
<!--                                </div>-->
<!--                            </template>-->
<!--                        </template>-->
<!--                    </template>-->
                </el-form>
            </el-col>
        </el-row>
        <el-row>
            <el-col>
                <h5>域配置</h5>
<!--                <el-form v-if="formMeta.columns && formMeta.columns[selectIndex]">-->
<!--                    <el-form-item v-for="(value, key, index) in formMeta.columns[selectIndex]" :key="key">-->
<!--                        &lt;!&ndash;                        <component></component>&ndash;&gt;-->
<!--                        {{key}}-->
<!--                    </el-form-item>-->
<!--                </el-form>-->
                <JsonBox v-model="formMeta.columns[selectIndex]"></JsonBox>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    import {DEFAULT} from '@/constant'
    import cloneDeep from 'lodash/cloneDeep'

    export default {
        name: "ConfArea",
        props: {
            value: Object,
            selectIndex: Number
        },
        data() {
            return {
                excludes: ['component_name', 'columns', 'btns'], // not allow customize

            }
        },
        methods: {
            cloneDeep: cloneDeep
        },
        filters: {
            type(data) {
                let type = typeof data;
                let componentName = "TextBox";
                switch (type) {
                    case "string":
                        componentName = "TextBox";
                        break;
                    case "number":
                        componentName = "NumBox";
                        break;
                    case "boolean":
                        componentName = "BoolBox";
                        break;
                    case "object":
                        componentName = "JsonBox";
                        break;
                }
                return componentName;
            }
        },
        computed: {
            formMeta: {
                get: function () {
                    this.$merge(this.value, DEFAULT.FormTmpl);
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