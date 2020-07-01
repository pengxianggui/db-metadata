<template>
    <div>
        <h2>{{meta.component_name}}: </h2>
        <component :is="meta.component_name" :meta="meta" :data="data"></component>
        <br>
        <z-toggle-panel>
            <json-box v-model="meta"></json-box>
        </z-toggle-panel>
    </div>
</template>

<script>
    export default {
        name: "tree-demo",
        data() {
            return {
                meta: {
                    component_name: 'Tree',
                    name: 'Tree', // 可省略, value不指定时, 则选取的值即为整个item对象
                    label: 'Tree',
                    conf: {
                        // other options
                        props: {
                            label: "name",
                            children: "child"
                        }
                    },
                },
                data: [
                    {
                        "id": "1",
                        "name": "meta_object",
                        "code": "meta_object",
                        "child": [
                            {
                                "id": "1-1",
                                "name": "meta_field",
                                "code": "meta_field"
                            },
                            {
                                "id": "1-2",
                                "name": "change_log",
                                "code": "change_log"
                            }
                        ]
                    },
                    {
                        "id": "2",
                        "name": "一级 2",
                        "child": [
                            {
                                "id": "2-1",
                                "name": "二级 2-1",
                                "child": [
                                    {
                                        "id": "2-1-1",
                                        "name": "三级 2-1-1"
                                    }
                                ]
                            },
                            {
                                "id": "2-2",
                                "name": "二级 2-2",
                                "child": [
                                    {
                                        "id": "2-2-1",
                                        "name": "三级 2-2-1"
                                    }
                                ]
                            }
                        ]
                    },
                    {
                        "id": "3",
                        "name": "一级 3",
                        "child": [
                            {
                                "id": "3-1",
                                "name": "二级 3-1",
                                "child": [
                                    {
                                        "id": "3-1-1",
                                        "name": "三级 3-1-1"
                                    }
                                ]
                            },
                            {
                                "id": "3-2",
                                "name": "二级 3-2",
                                "child": [
                                    {
                                        "id": "3-2-1",
                                        "name": "三级 3-2-1"
                                    }
                                ]
                            }
                        ]
                    }
                ]
            }
        }
    }
</script>

<style scoped>

</style>
