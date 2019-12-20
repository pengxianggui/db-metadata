<template>
    <row-grid :span="[6, 18]">
        <template #left>
            <tree-list :meta="treeMeta" :data="data"></tree-list>
        </template>
        <template #right>
            <table-list :ref="tlRefName" :meta="tlMeta" @active-change="handleActiveChange"></table-list>
        </template>
    </row-grid>
</template>

<script>
    export default {
        name: "TreeTableTmpl",
        data() {
            return {
                tlMeta: {},
                treeMeta: {
                    component_name: 'TreeList',
                    name: 'TreeList', // 可省略, value不指定时, 则选取的值即为整个item对象
                    label: 'TreeList',
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
        },
        methods: {
            handleActiveChange() {},

        },
        computed: {
            tlRefName() {
                return "TableList";
            }
        }
    }
</script>

<style scoped>

</style>