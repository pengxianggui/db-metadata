<template>
    <div style="padding: 20px;">
        <h1>组件开发调试页面</h1>

        <form-view :meta="formMeta">
            <template #form-item-real_name="{column, model}">
                <el-form-item :name="column.name" :label="column.label">
                    <text-box v-model="model[column.name]"></text-box>
                </el-form-item>
            </template>
        </form-view>
    </div>
</template>

<script>
    import * as Rest from '@/../package/utils/rest'

    export default {
        name: "WorkSpace",
        data() {
            return {
                formMeta: {}
            }
        },
        methods: {},
        mounted() {
            Rest.getAddFormMeta("mb_user").then(resp => {
                this.formMeta = resp.data;
            })
        }
    }
</script>

<style scoped>

</style>
