<template>
    <div>
        <h2>{{meta.component_name}}: </h2>
        <component :is="meta.component_name" :data="data" :meta="meta"></component>
        <br>
        <z-toggle-panel>
            <json-box v-model="meta"></json-box>
        </z-toggle-panel>
    </div>
</template>

<script>
    export default {
        name: "DataListDemo",
        data() {
            return {
                meta: {
                    component_name: 'DataList',
                    name: 'test', // 可省略, value不指定时, 则选取的值即为整个item对象
                    label: '测试',
                    conf: {
                        // other options
                    }
                },
                data: [
                    {label: "1"},
                    {label: "2"},
                    {label: "3"},
                    {label: "4"},
                    {label: "5"}
                ]
            }
        }
    }
</script>

<style scoped>

</style>