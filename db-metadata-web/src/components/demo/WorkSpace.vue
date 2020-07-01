<template>
    <div style="padding: 20px;">
        <h1>组件开发调试页面</h1>

        <mini-form-box v-model="value" :meta="buildMiniFormMeta(value)" v-once></mini-form-box>
    </div>
</template>

<script>
    import buildMeta from '@/../package/meta/buildMeta'

    export default {
        name: "WorkSpace",
        data() {
            return {
                meta: {
                    "name": "__vue_devtool_undefined__",
                    "label": "__vue_devtool_undefined__",
                    "value": {
                        "conf": {"maxlength": 128},
                        "name": "col_findbox",
                        "label": "选择框",
                        "inline": false,
                        "data_url": "/component/options/test_table?f=col_findbox",
                        "default_value": "",
                        "component_name": "DropDownBox"
                    },
                    "component_name": "MiniFormBox",
                    "columns": [{
                        "name": "conf",
                        "label": "conf",
                        "value": {"maxlength": 128},
                        "component_name": "MiniFormBox",
                        "columns": [{
                            "component_name": "NumBox",
                            "name": "maxlength",
                            "label": "maxlength",
                            "value": 128
                        }],
                        "inline": false,
                        "conf": {"label-width": "80px", "size": "mini", "disabled": false, "inline": false}
                    }, {
                        "component_name": "TextBox",
                        "name": "name",
                        "label": "name",
                        "value": "col_findbox"
                    }, {
                        "component_name": "TextBox",
                        "name": "label",
                        "label": "label",
                        "value": "选择框"
                    }, {
                        "component_name": "BoolBox",
                        "name": "inline",
                        "label": "inline",
                        "value": false
                    }, {
                        "component_name": "TextBox",
                        "name": "data_url",
                        "label": "data_url",
                        "value": "/component/options/test_table?f=col_findbox"
                    }, {
                        "component_name": "TextBox",
                        "name": "default_value",
                        "label": "default_value",
                        "value": ""
                    }, {
                        "component_name": "DropDownBox",
                        "name": "component_name",
                        "label": "component_name",
                        "data_url": "/component/list",
                        "conf": {"filterable": true}
                    }],
                    "inline": false,
                    "conf": {"label-width": "80px", "size": "mini", "disabled": false, "inline": false, "rules": {}},
                    "buttons": {
                        "submit": {"label": "提交", "conf": {"type": "primary"}},
                        "cancel": {"label": "取消", "conf": {}}
                    }
                },
                value: {
                    "conf": {"maxlength": 128},
                    "name": "col_findbox",
                    "label": "选择框",
                    "inline": false,
                    "data_url": "/component/options/test_table?f=col_findbox",
                    "default_value": "",
                    "component_name": "DropDownBox"
                }
            }
        },
        methods: {
            buildMiniFormMeta(value) {
                return buildMeta(value)
            }
        }
    }
</script>

<style scoped>

</style>
