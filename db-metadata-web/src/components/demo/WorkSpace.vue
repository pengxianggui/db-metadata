<template>
    <div style="padding: 20px; overflow: auto; height: 100%;">
        <h1>组件开发调试页面</h1>

        <!--        <img-box :meta="meta" v-model="value"></img-box>-->

        <form-view :meta="meta" :model="data">
            <template #form-item-content="{column, model}">
                <el-form-item :name="column.name" :label="column.label">
                    <rich-text-box v-model="model[column.name]"></rich-text-box>
                </el-form-item>
            </template>
        </form-view>
    </div>
</template>

<script>
    import {Rest} from "../../../package/index";

    export default {
        name: "WorkSpace",
        data() {
            return {
                oc: 'news',
                meta1: {
                    "conf": {
                        "action": "/file/upload?objectCode=news&fieldCode=thumbnail",
                        "maxlength": 0,
                        "show-overflow-tooltip": true
                    },
                    "seats": ['1', '2'],
                    "name": "thumbnail",
                    "label": "文章缩略图",
                    "inline": false,
                    "default_value": {},
                    "component_name": "ImgBox"
                },
                meta: {},
                value: [],
                data: {}
            }
        },
        methods: {
            getMeta() {
                Rest.getAddFormMeta(this.oc).then(resp => {
                    this.meta = resp.data
                    // TODO mock
                    this.meta.columns.filter(c => c.name === 'account_id').map(c => c.default_value = this.userInfo.accountId)
                })
            }
        },
        created() {
            this.getMeta()
        }
    }
</script>

<style scoped>
    .ddd {
        font-size: 40px;
    }
</style>
