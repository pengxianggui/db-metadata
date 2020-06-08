<template>
    <el-container direction="vertical">
        <el-row>
            <el-col>
                <search-panel :ref="spMeta['name']" :meta="spMeta" @search="handlerSearch"></search-panel>
            </el-col>
        </el-row>
        <el-row>
            <el-col>
                <table-list :ref="refName" :meta="tlMeta" :filter-params="filterParams" :active-data="activeData"
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
    import DefaultTableListMeta from '@/components/core/tablelist/ui-conf';
    import DefaultSearchPanelMeta from '@/components/core/searchpanel/ui-conf';

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
                activeData: {},
                filterParams: {}
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
                const {refName} = this;
                this.filterParams = params;
                this.$nextTick(() => {
                    this.$refs[refName].getData(); // refresh table data
                });
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
                let meta = this.$merge(this.meta.search, DefaultSearchPanelMeta);
                meta.component_name = 'SearchPanel';
                return meta;
            },
            tlMeta() {
                let meta = this.$merge(this.meta.table, DefaultTableListMeta);
                meta.component_name = 'TableList';
                meta.multi_select = false; // pxg_todo 暂不支持多选
                return meta;
            },
            refName() {
                return this.tlMeta['name'];
            }
        }
    }
</script>

<style scoped>

</style>
