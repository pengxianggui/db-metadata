<template>
    <el-form ref="form" :model="formModel" v-bind="conf">
        <el-form-item v-for="(item, index) in meta.columns" :key="item.en + index" :label="item.ui_config.show_label?item.cn:''" :prop="item.en">
            <component :is="item.component_name" v-model="formModel[item.en]" :meta="item"></component>
        </el-form-item>
        <el-form-item v-if="meta.columns.length > 0">
            <!-- TODO 采用ButtonBox -->
        </el-form-item>
    </el-form>
</template>

<script>
    import mockData from '@/mockdata.js'
    import {DEFAULT} from '@/constant'
    import Vue from 'vue'
    export default {
        name: "form-tmpl",
        data () {
            return {
                // form conf data
                conf: {},
                meta: {},
                formModel: {}
            }
        },
        props: {
            objectCode: {
                required: true,
                type: String,
            }
        },
        methods: {
            getDefaultConf () {
                // 初始化表单自身的默认配置
                return DEFAULT.FormTmpl
            },
            getConf () {
                // TODO get custom form config by object_code
            },
            initConf () {
                this.conf = this.getConf(this.objectCode) || {}
                let defaultConf = this.getDefaultConf() || {}
                this.merge(this.conf, defaultConf)
            },
            getMeta () {
                // TODO get meta data by object_code.
                return mockData.formMeta // mock data
            },
            initMeta () {
                this.meta = this.getMeta()
            },
            assemblyModel () { // 封装formModel
                let _this = this
                _this.formModel = {}
                _this.meta.columns.forEach(item => {
                    Vue.set(_this.formModel, item.en, item.ui_config.value || null)
                    if (item.rules){
                        Vue.set(_this.rules, item.en, item.ui_config.rules)
                    }
                })
            }
        },
        created() {
            // 1. init current form result conf
            this.initConf()

            // 2.init current form meta data
            this.initMeta()

            // 3.assembly form model
            this.assemblyModel()

        },
        mounted() {
            // request business data
        }
    }
</script>

<style scoped>

</style>
