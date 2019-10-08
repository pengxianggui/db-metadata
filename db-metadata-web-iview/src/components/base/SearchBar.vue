<template>
    <el-form :inline="true" :rules="rules" :model="searchForm" :ref="formName" class="demo-form-inline" v-bind="options">
        <el-form-item v-for="(item, index) in searchItems" :key="item.en + index" :label="item.cn" :prop="item.en">
            <component :is="formComponents[item.config.form_type]" v-model="searchForm[item.en]" @change="emit"
                       :options="item.config.search_options"></component>
        </el-form-item>
        <el-form-item v-if="searchItems.length > 0">
            <el-button type="primary" @click="search(formName)">查询</el-button>
        </el-form-item>
    </el-form>
</template>

<script>
    import Vue from 'vue'
    import XInput from '../atom/XInput'
    import Select from '../atom/XSelect'
    export default {
        name: "search-bar",
        components: {
            'INPUT': XInput,
            'SELECT': Select
        },
        data() {
            return {
                searchForm: {},
                searchItems: [],
                // todo 全局引用
                formComponents: {
                    'INPUT': XInput,
                    'SELECT': Select
                },
                formName: 'form' + Math.random(),
                rules: {}
            }
        },
        props: {
            formItems: {
                required: true,
                type: Array
            },
            searchModel: {
                required: true,
                type: Object
            },
            options: {
                required: false,
                type: Object
            }
        },
        methods: {
            initData () {
                let _this = this
                _this.searchForm = {}
                _this.searchItems = []
                _this.formItems.forEach(item => {
                    // this.searchForm[item.name] = item.value
                    Vue.set(_this.searchForm, item.en, null) // 这种赋值方法, 双向绑定才生效
                    if (item.config.showable) {
                        _this.searchItems.push(item)
                        if (item.config.search_options && item.config.search_options.rules){
                            Vue.set(_this.rules, item.en, item.config.search_options.rules)
                        }
                    }
                })
                _this.emit()
            },
            emit () {
                let params= {}
                if (typeof this.searchForm === 'object') {
                    let keys = Object.keys(this.searchForm)
                    keys.forEach(key => {
                        params[key] = this.searchForm[key]
                    })
                }
                this.$emit('update:search-model', params)
            },
            search (form) {
                this.$refs[form].validate((valid) => {
                    if (valid) {
                        this.$emit('search', this.searchForm) // submit
                    } else {
                        return false;
                    }
                });
            }
        },
        mounted() {
            this.initData()
        }
    }
</script>

<style scoped>

</style>
