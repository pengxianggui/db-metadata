<template>
    <div>
        <h2>{{meta.component_name}}: </h2>
        <component :is="meta.component_name" :meta="meta" :data="data" :load-childrens="loadChildrens"></component>
        <br>
        <z-toggle-panel>
            <json-box v-model="meta"></json-box>
        </z-toggle-panel>
    </div>
</template>

<script>
    export default {
        name: "table-tree-list-demo",
        data() {
            return {
                meta: {
                    component_name: 'TableTreeList',
                    name: 'TableTreeListDemo',
                    label: 'TableTreeList',
                    columns: [{
                        name: "id"
                    }, {
                        name: "parentId"
                    }, {
                        name: "name"
                    }],
                    conf: {
                        'row-key': 'id',
                        'tree-props': {
                            'children': 'children'
                        },
                        'lazy': false
                    }
                },
                data: [],

                /*
                meta: {
                    component_name: 'TableTreeList',
                    name: 'TableTreeListDemo',
                    label: 'TableTreeList',
                    columns: [{
                        name: 'id',
                    }, {
                        name: 'date'
                    }, {
                        name: 'name'
                    }, {
                        name: 'address'
                    }],
                    conf: {
                        'row-key': 'id',
                        'tree-props': {
                            'children': 'childrens'
                        },
                        'lazy': true
                    },
                },
                data: [{
                    id: 1,
                    date: '2016-05-02',
                    name: '王小虎',
                    address: '上海市普陀区金沙江路 1518 弄'
                }, {
                    id: 2,
                    date: '2016-05-04',
                    name: '王小虎',
                    address: '上海市普陀区金沙江路 1517 弄'
                }, {
                    id: 3,
                    date: '2016-05-01',
                    name: '王小虎',
                    address: '上海市普陀区金沙江路 1519 弄',
                    childrens: [{
                        id: 31,
                        date: '2016-05-01',
                        name: '王小虎',
                        address: '上海市普陀区金沙江路 1519 弄'
                    }, {
                        id: 32,
                        date: '2016-05-01',
                        name: '王小虎',
                        address: '上海市普陀区金沙江路 1519 弄'
                    }]
                }, {
                    id: 4,
                    date: '2016-05-01',
                    name: '王小虎',
                    address: '上海市普陀区金沙江路 1519 弄',
                    hasChildren: true
                }]
                 */
            }
        },
        methods: {
            getData() {
                this.$axios.get("/fileview/view?path=/target").then(resp => {
                    this.data = resp.data;
                }).catch(err => {
                    console.log(err.msg);
                })
            },
            loadChildrens({tree, treeNode, resolve}) {
                console.log(tree);
                console.log(treeNode);
                setTimeout(() => {
                    const mockChildren = [{
                        id: 41,
                        date: '2016-05-01',
                        name: '王小虎',
                        address: '上海市普陀区金沙江路 1519 弄'
                    }, {
                        id: 42,
                        date: '2016-05-01',
                        name: '王小虎',
                        address: '上海市普陀区金沙江路 1519 弄'
                    }];
                    resolve(mockChildren);
                }, 1000);
            }
        },
        created() {
            this.getData();
        }
    }
</script>

<style scoped>

</style>