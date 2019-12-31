<template>
    <el-container direction="vertical">
        <el-row>
            <el-col>
                <search-panel :ref="spMeta['name']" :meta="spMeta" @search="handlerSearch"></search-panel>
            </el-col>
        </el-row>
        <el-row>
            <el-col>
                <table-list :ref="tlMeta['name']" :meta="tlMeta" :active-data="activeData"
                            @active-change="handlerActiveChange"
                            @row-dblclick="handleRowDbClick">
                    <template #operation-bar><span></span></template>
                    <template #buttons><span></span></template>
                    <template #operation-column><span></span></template>
                </table-list>
            </el-col>
        </el-row>
        <slot name="action">
            <el-row style="margin-top: 20px;">
                <el-col>
                    <el-button @click="ok" type="primary">
                        <slot name="ok">确定</slot>
                    </el-button>
                    <el-button @click="cancel">
                        <slot name="cancel">清空选择</slot>
                    </el-button>
                </el-col>
            </el-row>
        </slot>
    </el-container>
</template>

<script>
    import utils from '@/utils'
    import {DEFAULT} from '@/constant'

    export default {
        name: "FindPanel",
        props: {
            meta: {
                type: Object,
                default: function () {
                    return {}
                }
            }
        },
        data() {
            return {
                activeData: {}
            }
        },
        methods: {
            handleRowDbClick(row) {
                this.activeData = row;
                this.ok();
            },
            handlerActiveChange(row) {
                this.activeData = row;
            },
            handlerSearch(params) {
                this.$refs[this.tlMeta['name']].getData(params); // refresh table data
            },
            ok() {
                const {activeData, tlMeta} = this;
                if (!activeData) {
                    this.$message.warning('请选择数据.');
                    return;
                }
                const {objectPrimaryKey} = tlMeta;
                const feedBackValue = utils.extractValue(activeData, objectPrimaryKey);

                this.$emit('ok', feedBackValue[0]); // feedback activeData
            },
            cancel() {
                this.$emit('cancel', 'clean'); // feedback clean
            }
        },
        computed: {
            spMeta() {
                let meta = this.$merge(this.meta.search, DEFAULT.SearchPanel);
                meta.component_name = 'SearchPanel';
                return meta;
            },
            tlMeta() {
                let meta = this.$merge(this.meta.table, DEFAULT.TableList);
                meta.component_name = 'TableList';
                meta.multi_select = false; // pxg_todo 暂不支持多选
                return meta;
            }
        }
    }
</script>

<style scoped>

</style>