<template>
    <div class="el-card container">
        <div class="header">
        </div>
        <div class="work-area">
            <FormTmpl :ref="formMeta.name" :meta="formMeta">
                <template #form-item="{columns}">
                    <draggable
                            :animation="200"
                            :disabled="false"
                            :list="list"
                            @add="handleAdd"
                            @end="handleMoveEnd"
                            @start="handleMoveStart"
                            group="form"
                            style="padding: 5px;border: 1px dotted grey;"
                            tag="el-row">
                        <div v-if="columns.length === 0" class="blank-tip">
                            从左侧拖拽来添加表单项
                        </div>
                        <template v-else v-for="(item, index) in columns">
                            <div :class="{'form-item-active': selectIndex === index}" :key="item.name"
                                 class="form-item"
                                 @click="handleFormItemClick(index, $event)">
                                <el-form-item :key="item.name"
                                              v-if="!item.hasOwnProperty('showable') || item.showable"
                                              :label="item.label" :prop="item.name"
                                              :class="{inline: item.inline}">
                                    <component :is="item.component_name"
                                               :meta="item"></component>
                                </el-form-item>
                                <el-button
                                    @click.stop="handleDelete(index)"
                                    class="form-item-delete-btn"
                                    icon="el-icon-delete"
                                    size="mini"
                                    style="border-radius: 0"
                                    type="primary"
                                    v-if="selectIndex === index"
                                ></el-button>
                            </div>
                        </template>
                    </draggable>
                </template>
            </FormTmpl>
        </div>
    </div>
</template>

<script>
    import draggable from 'vuedraggable'
    import cloneDeep from 'lodash/cloneDeep'
    import {DEFAULT} from '@/constant'

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
            handleDelete (index) { // 删除
                this.list.splice(index, 1);
                if (index >= this.list.length) {
                    this.selectIndex = this.list.length - 1
                }
                this.$emit('select', this.formMeta, this.selectIndex); // 防止从中间删除导致未触发emit
            },
            // 新增
            handleAdd(res) {
                this.selectIndex = res.newIndex
            },
            // 移动开始
            handleMoveStart(res) {
                this.selectIndex = res.oldIndex
            },
            // 移动结束
            handleMoveEnd(res) {
                this.selectIndex = res.newIndex
            },
            // 点击选中
            handleFormItemClick(index, ev) {
                if (ev) ev.stopPropagation();
                this.selectIndex = index
            },
        },
        watch: {
            selectIndex(newVal) {
                this.$emit('select', this.formMeta, newVal)
            }
        },
        computed: {
            formMeta() {
                let formMeta = this.$merge({}, DEFAULT.FormTmpl);
                let columnMeta = cloneDeep(this.list);
                formMeta.columns = columnMeta;
                return formMeta;
            }
        }
    }
</script>

<style scoped>
    .container {
        display: flex;
        height: 100%;
        overflow: auto;
    }

    .header {
        height: 50px;
    }

    .work-area {
        flex: 1
    }
    .blank-tip {
        height: 400px;
        line-height: 400px;
        text-align: center;
        border: 1px solid #eee;
        margin: 5px 0;
        color: #999999;
    }

    .form-item {
        background: white;
        cursor: move;
        position: relative;
        z-index: 1;
        padding: 0 20px;
        border: 1px dashed rgba(0, 0, 0, 0);
        /*border-bottom: dotted 1px;*/
    }

    /*!* 遮挡区(遮挡住) *!*/
    .form-item::after {
        content: " ";
        display: block;
        left: 0;
        top: 0;
        right: 0;
        bottom: 0;
        position: absolute;
        z-index: 2;
    }

    .form-item-active {
        border: 1px dashed #409eff;
    }

    .form-item-delete-btn {
        position: absolute;
        right: 0;
        bottom: 0;
        z-index: 3;
    }
</style>