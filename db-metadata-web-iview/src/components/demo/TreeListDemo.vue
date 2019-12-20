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
        name: "tree-list-demo",
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
                        "name": "一级 1",
                        "child": [
                            {
                                "name": "二级 1-1",
                                "child": [
                                    {
                                        "name": "三级 1-1-1"
                                    }
                                ]
                            }
                        ]
                    },
                    {
                        "name": "一级 2",
                        "child": [
                            {
                                "name": "二级 2-1",
                                "child": [
                                    {
                                        "name": "三级 2-1-1"
                                    }
                                ]
                            },
                            {
                                "name": "二级 2-2",
                                "child": [
                                    {
                                        "name": "三级 2-2-1"
                                    }
                                ]
                            }
                        ]
                    },
                    {
                        "name": "一级 3",
                        "child": [
                            {
                                "name": "二级 3-1",
                                "child": [
                                    {
                                        "name": "三级 3-1-1"
                                    }
                                ]
                            },
                            {
                                "name": "二级 3-2",
                                "child": [
                                    {
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
