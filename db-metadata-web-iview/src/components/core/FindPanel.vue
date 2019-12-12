<template>
    <el-container direction="vertical">
        <el-row>
            <el-col>
                <search-panel :ref="spMeta['name']" :meta="spMeta" @search="handlerSearch"></search-panel>
            </el-col>
        </el-row>
        <el-row>
            <el-col>
                <table-list :ref="tlMeta['name']" :meta="tlMeta" :active-data.sync="choseData"
                    @row-dblclick="ok">
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
    import {DEFAULT} from '@/constant'
    import SearchPanel from "./SearchPanel";

    export default {
        name: "FindPanel",
        components: {SearchPanel},
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
                choseData: {}
            }
        },
        methods: {
            handlerSearch(params) {
                this.$refs[this.tlMeta['name']].getData(params); // refresh table data
            },
            ok() {
                if (!this.choseData.id) {
                    this.$message.warning('请选择数据.');
                    return;
                }
                this.$emit('ok', this.choseData.id); // feedback choseData
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