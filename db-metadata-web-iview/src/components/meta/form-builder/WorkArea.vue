<template>
    <div class="el-card container">
        <div class="header">
            <el-row>
                <el-col :span="20">
                    <el-button @click="preview" icon="el-icon-view" size="small" type="primary">视图预览</el-button>
                    <el-button @click="jsonView" icon="el-icon-view" size="small" type="primary">json预览</el-button>
                    <el-button @click="submitForm" icon="el-icon-download" size="small" type="success">保存</el-button>
                    <el-button @click="resetForm" icon="el-icon-delete" size="small" type="danger">重置</el-button>
                </el-col>
                <el-col :span="4">
                    <drop-down-box
                            data-url="/table/list?objectCode=meta_object&fs=code,table_name&code->key&table_name->value"
                            v-model="objectCode"></drop-down-box>
                </el-col>
            </el-row>
        </div>
        <div class="work-area">
            <form-tmpl :ref="formMeta.name" :meta="formMeta">
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
            </form-tmpl>
        </div>
    </div>
</template>

<script>
    import draggable from 'vuedraggable'
    import cloneDeep from 'lodash/cloneDeep'
    import FormTmpl from "../../core/FormTmpl";
    import {DEFAULT} from '@/constant'
    import DropDownBox from "@/components/core/form/DropDownBox";

    export default {
        name: "WorkArea",
        components: {
            DropDownBox,
            FormTmpl,
            draggable
        },
        props: {
            value: Object
        },
        data() {
            return {
                objectCode: {},
                selectIndex: null,
                list: []
            }
        },
        methods: {
            handleDelete(index) { // 删除
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
            jsonView() {
                this.$dialog(DEFAULT.JsonBox, this.formMeta, {
                    title: "Json预览"
                })
            },
            preview() {
                this.$dialog(this.formMeta, null, {
                    title: "视图预览"
                })
            },
            submitForm() {
                this.$message.error("submitForm action not finished!");
                // this.$refs[this.formMeta.name].onSubmit();
            },
            resetForm() {
                this.$message.error("resetForm action not finished!");
            }
        },
        watch: {
            selectIndex(newVal) {
                this.$emit('select', this.formMeta, newVal)
            }
        },
        computed: {
            formMeta() {
                let formMeta = this.value;
                let columns = formMeta.columns;
                let columnNames = columns.map(item => item.name);

                let newColumns = cloneDeep(this.list);

                for (let i = 0; i < newColumns.length; i++) {
                    let name = newColumns[i].name;
                    if (columnNames.includes(name)) {
                        this.$reverseMerge(newColumns[i], columns[columnNames.indexOf(name)]);
                    }
                }
                formMeta.columns = newColumns;
                return formMeta;
            }
        }
    }
</script>

<style scoped>
    .container {
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
        padding: 0 0px;
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