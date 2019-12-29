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
                    <slot name="operation-extend"></slot>
                </el-col>
            </el-row>
        </div>
        <div class="work-area">
            <form-tmpl :ref="formMeta.name" :meta="formMeta">
                <template #form-item>
                    <draggable
                        :animation="200"
                        :disabled="false"
                        :list="formMeta.columns"
                        @add="handleAdd"
                        @end="handleMoveEnd"
                        @start="handleMoveStart"
                        group="form"
                        style="padding: 5px;border: 1px dotted grey;"
                        tag="el-row">
                        <div v-if="formMeta.columns.length === 0" class="blank-tip">
                            从左侧拖拽来添加表单项
                        </div>
                        <template v-else v-for="(item, index) in formMeta.columns">
                            <div :key="item.name"
                                 class="form-item"
                                 :class="{'form-item-active': selectIndex === index, 'inline': item.inline, 'width-align': item.inline}"
                                 @click="handleFormItemClick(index, $event)">
                                <el-form-item :key="item.name"
                                              v-if="!item.hasOwnProperty('showable') || item.showable"
                                              :label="item.label" :prop="item.name">
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
    import utils from '@/utils'
    import draggable from 'vuedraggable'
    import FormTmpl from "../../core/form/src/FormTmpl";
    import {DEFAULT, URL} from '@/constant'
    import DropDownBox from "@/components/core/dropdownbox/src/DropDownBox";

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
                selectIndex: null,
            }
        },
        methods: {
            handleDelete(index) { // 删除
                if (this.formMeta.objectCode) {
                    this.$message.warning('编辑元对象时不允许移除控件');
                    return;
                }
                let columns = this.formMeta.columns;
                columns.splice(index, 1);
                if (index >= this.columns.length) {
                    this.selectIndex = this.columns.length - 1
                }
                this.$emit('select', this.formMeta, this.selectIndex); // 防止从中间删除导致未触发emit
            },
            // 新增
            handleAdd(res) {
                if (this.formMeta.objectCode) { // TODO vuedraggable 再找找到类似beforeAdd事件
                    this.$message.warning('编辑元对象时不允许新增控件');
                    this.formMeta.columns.splice(res.newIndex, 1);
                    return false;
                }
                this.selectIndex = res.newIndex;
            },
            // 移动开始
            handleMoveStart(res) {
                this.selectIndex = res.oldIndex
            },
            // 移动结束
            handleMoveEnd(res) {
                this.selectIndex = res.newIndex;
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
                // this.$message.error("submitForm action not finished!");

                const componentCode = 'FormTmpl';
                const objectCode = this.formMeta.objectCode;
                let params = {
                    componentCode: componentCode,
                    objectCode: objectCode
                };
                let objectMeta = utils.deepClone(this.formMeta);
                let columnsMeta;
                if (objectMeta.hasOwnProperty('columns')) {
                    columnsMeta = utils.deepClone(objectMeta.columns);
                    delete objectMeta['columns'];
                }

                params[objectCode] = objectMeta;
                columnsMeta.forEach(fieldMeta => params[fieldMeta.name] = fieldMeta);

                this.$axios({
                    method: 'POST',
                    url: URL.COMP_CONF_UPDATE,
                    data: params
                }).then(resp => {
                    this.$message.success(resp.msg);
                }).catch(err => {
                    this.$message.error(err.msg);
                })
            },
            resetForm() {
                this.$message.error("resetForm action not finished!");
            },
        },
        watch: {
            selectIndex(newVal) {
                this.$emit('select', this.formMeta, newVal)
            }
        },
        computed: {
            formMeta() {
                let formMeta = this.value;
                if (!utils.isArray(formMeta.columns)) {
                    this.$set(formMeta, 'columns', []);
                }
                return formMeta;
            },
        }
    }
</script>

<style scoped>
    .container {
        height: 100%;
    }

    .header {
        height: 50px;
    }

    .work-area {
        flex: 1;
        height: 100%;
        overflow: auto;
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