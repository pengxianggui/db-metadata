<template>
    <el-form :inline="true" :rules="rules" :model="searchModel" :ref="formName" class="demo-form-inline">
        <el-form-item v-for="(item, index) in metaData.ui_config" :key="item.en + index" :label="item.ui_config.show_label?item.cn:''" :prop="item.en">
            <component :is="item.component_name" v-model="searchModel[item.en]" :meta-data="item"></component>
        </el-form-item>
        <el-form-item v-if="metaData.ui_config.length > 0">
            <el-button type="primary" @click="search(formName)">查询</el-button>
        </el-form-item>
    </el-form>
</template>

<script>
    import Vue from 'vue'
    export default {
        name: "search-bar",
        data() {
            return {
                formName: 'form' + Math.random(),
                rules: {},
                items: []
            }
        },
        props: {
            metaData: {
                required: true,
                type: Object
            },
            searchModel: {
                required: true,
                type: Object
            }
        },
        methods: {
            initData () {
                let _this = this
                _this.searchForm = {}
                _this.metaData.ui_config.forEach(item => {
                    Vue.set(_this.searchModel, item.en, null) // 这种赋值方法, 双向绑定才生效
                    if (item.rules){
                        Vue.set(_this.rules, item.en, item.rules)
                    }
                })
                // _this.emit()
            },
            // emit () {
            //     let params= {}
            //     if (typeof this.searchForm === 'object') {
            //         let keys = Object.keys(this.searchForm)
            //         keys.forEach(key => {
            //             params[key] = this.searchForm[key]
            //         })
            //     }
            //     this.$emit('update:search-model', params)
            // },
            search (form) {
                this.$refs[form].validate((valid) => {
                    if (valid) {
                        this.$emit('search') // submit
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
