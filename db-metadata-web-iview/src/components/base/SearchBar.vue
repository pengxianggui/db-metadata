<template>
    <el-form :inline="true" :model="searchForm" class="demo-form-inline">
        <el-form-item v-for="(item, index) in searchItems" :key="item.name + index" :label="item.label">
            <component :is="formComponents[item.formType]" v-model="searchForm[item.name]" :options="item.options"
                ></component>
        </el-form-item>

        <el-form-item>
            <el-button type="primary" @click="search()">查询</el-button>
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
            'x-input': XInput,
            'x-select': Select
        },
        data() {
            return {
                searchForm: {},
                searchItems: [],
                // todo 全局引用
                formComponents: {
                    'x-input': XInput,
                    'x-select': Select
                }
            }
        },
        props: {
            formItems: {
                required: true,
                type: Array
            }
        },
        methods: {
            initData () {
                this.searchForm = {}
                this.searchItems = []
                this.formItems.forEach(item => {
                    // this.searchForm[item.name] = item.value
                    Vue.set(this.searchForm, item.name, item.value) // 这种赋值方法, 双向绑定才生效
                    if (item.formType && item.formType !== 'hidden') {
                        this.searchItems.push(item)
                    }
                })
            },
            search () {
                this.$emit('search', this.searchForm) // submit
            }
        },
        watch: {
            formItems() {
                this.initData()
            }
        },
        mounted() {
            this.initData()
        }
    }
</script>

<style scoped>

</style>