<template>
    <el-row>
        <el-col :span=6 v-for="(item, index) in meta" size="mini">
            <template v-if="item.type=='ElInput'">
                {{item.name}} :
                <component :is="item.type" v-model="item.value"></component>
            </template>
            <template v-if="item.type=='el-radio-group'">
                {{item.name}} :
                <el-radio-group v-model="item.value" size="mini">
                    <el-radio-button v-for="opt in item.options" :label="opt.key" :key="opt.value">{{opt.key}}
                    </el-radio-button>
                </el-radio-group>
            </template>
            <template v-if="item.type=='el-checkbox'">
                {{item.name}} :
                <el-checkbox v-model="item.value" :label="item.name" border></el-checkbox>
            </template>
        </el-col>
    </el-row>
</template>

<script>
    export default {
        name: "MiniFormDemo",
        data() {
            return {
                meta: [{
                    name: "isNullable",
                    type: "el-checkbox",
                    value: ""
                }, {
                    name: "isQuery",
                    type: "ElInput",
                    value: ""
                }, {
                    name: "isQuery",
                    type: "ElInput",
                    value: ""
                }, {
                    name: "addStatus",
                    type: "el-radio-group",
                    options: [
                        {key: "显示", value: 100},
                        {key: "隐藏", value: 50},
                        {key: "只读", value: 30},
                        {key: "禁用", value: 10}
                    ],
                    value: [50, 10]
                }, {
                    name: "updateStatus",
                    type: "el-radio-group",
                    options: [
                        {key: "显示", value: 100},
                        {key: "隐藏", value: 50},
                        {key: "只读", value: 30},
                        {key: "禁用", value: 10}
                    ],
                    value: [50, 10]
                }]
            }
        }
    }
</script>

<style scoped>

</style>