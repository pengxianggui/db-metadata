<template>
    <FormTmpl :ref="innerMeta['name']" :meta="innerMeta">
        <template #action="{model}">
            <el-button :id="innerMeta.name + 'search'" @click="handleSearch(model)">
                <slot>搜索</slot>
            </el-button>
        </template>
    </FormTmpl>
</template>

<script>
    import {DEFAULT} from '@/constant'
    import Meta from './mixins/meta'

    export default {
        name: "SearchPanel",
        mixins: [Meta(DEFAULT.SearchPanel)],
        data() {
            return {
            }
        },
        methods: {
            handleSearch(model) {
                this.$emit('search', model);
            }
        }
    }
</script>

<style scoped>

</style>