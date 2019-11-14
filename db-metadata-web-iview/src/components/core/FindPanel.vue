<template>
    <el-container direction="vertical">
        <el-row>
            <el-col>
                <SearchPanel :ref="spMeta['name']" :meta="spMeta" @search="handlerSearch"></SearchPanel>
            </el-col>
        </el-row>
        <el-row>
            <el-col>
                <TableList :ref="tlMeta['name']" :meta="tlMeta" :active-data.sync="choseData"
                    @row-dblclick="ok">
                    <template #operation-bar><span></span></template>
                    <template #buttons><span></span></template>
                </TableList>
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
                let meta = this.$merge(this.meta, DEFAULT.FindPanel);
                let columns = this.meta['columns'];

                meta.component_name = 'SearchPanel';
                meta.columns = columns.filter(item => item['searchable']);
                return meta;
            },
            tlMeta() {
                let meta = this.$merge(this.meta, DEFAULT.FindPanel);
                meta.component_name = 'TableList';
                meta.multi_select = false; // pxg_todo 暂不支持多选
                return meta;
            }
        }
    }
</script>

<style scoped>

</style>