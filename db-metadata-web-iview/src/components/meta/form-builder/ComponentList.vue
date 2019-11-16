<template>
    <div class="el-card" style="height: 100%; overflow: auto;">
        <h4 align="center">组件列表</h4>
        <el-row>
            <draggable
                :clone="addFormItem"
                :group="{ name: 'form', pull: 'clone', put: false }"
                :list="comps"
                :sort="false">
                <el-col v-for="comp of comps" :key="comp.name" class="el-card" style="display: inline-block;">
                    <div class="type-item-title">{{comp.name}}</div>
                    <div>{{comp.label}}</div>
                </el-col>
            </draggable>
        </el-row>
    </div>
</template>

<script>
    import components from '@/components/core/form'
    import draggable from 'vuedraggable'


    export default {
        name: "ComponentList",
        components: {
            draggable
        },
        data() {
            return {
                comps: components
            }
        },
        methods: {
            addFormItem() {
                // pxg_todo
            }
        }
    }
</script>

<style scoped>
    .el-col {
        display: inline-block;
        float: left;
        width: 150px;
        margin: 18px;
        cursor: pointer;
        text-align: center;
    }
</style>