<template>
    <div style="height: 100%; display: flex;">
        <div class="header"></div>
        <div class="work-area">
            <FormTmpl>
                <template #form-item="columns">
                    <draggable
                        :animation="200"
                        :disabled="false"
                        :list="list"
                        @add="handleAdd"
                        @end="handleMoveEnd"
                        @start="handleMoveStart"
                        group="form"
                        style="padding-bottom: 80px;"
                        tag="el-row"></draggable>
                </template>
            </FormTmpl>
        </div>
    </div>
</template>

<script>
    import draggable from 'vuedraggable'
    import  cloneDeep from 'lodash/cloneDeep'

    export default {
        name: "WorkArea",
        components: {
            draggable
        },
        data() {
            return {
                selectIndex: null,
                list: []
            }
        },
        methods: {
            // 新增
            handleAdd (res) {
                this.selectIndex = res.newIndex
            },
            // 移动开始
            handleMoveStart (res) {
                this.selectIndex = res.oldIndex
            },
            // 移动结束
            handleMoveEnd (res) {
                this.selectIndex = res.newIndex
            },
            // 点击选中
            handleFormItemClick (index) {
                this.selectIndex = index
            },
        },
        watch: {
            // 检查变化, 抛出选中项到属性编辑界面
            selectIndex (index) {
                this.$emit('select', this.list[index])
            },
            // 检测列表变化, 抛出变化, 用于生成代码
            list: {
                handler (list) {
                    list = cloneDeep(list);
                    return list;
                },
                deep: true
            }
        },
    }
</script>

<style scoped>
    .header {
        height: 50px;
    }
    .work-area {
        flex: 1
    }
</style>