<template>
    <el-form :ref="formName" :model="formModel" :rules="rules"  v-bind="conf">
        <el-form-item v-for="(item, index) in meta.columns" :key="item.en + index" :label="item.ui_config.show_label?item.cn:''" :prop="item.en">
            <component :is="item.component_name" v-model="formModel[item.en]" :meta="item"></component>
        </el-form-item>
        <el-form-item v-if="meta.columns.length > 0">
            <el-form-item>
                <el-button type="primary" @click="onSubmit">提交</el-button>
                <el-button>取消</el-button>
            </el-form-item>
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
                formName: 'form' + Math.random(),
                conf: {},
                meta: {},
                formModel: {},
                rules: {}
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
                    if (item.ui_config.rules){
                        Vue.set(_this.rules, item.en, item.ui_config.rules)
                    }
                })
            },
            submit () {
                let _this = this
                this.$axios({
                    methods: _this.conf.methods,
                    // todo url should be recorrect
                    url: _this.conf.action + "?object_code=" + this.meta.object_code,
                    data: _this.formModel
                })
            },

            onSubmit () {
                let _this = this
                this.$refs[this.formName].validate((valid) => {
                    if (valid) {
                         _this.submit() // submit
                    } else {
                        return false;
                    }
                });
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
