<template>
    <el-container direction="vertical">
        <el-row>
            <el-col>
                <search-view :ref="spMeta['name']" :meta="spMeta" @search="handlerSearch"></search-view>
            </el-col>
        </el-row>
        <el-row>
            <el-col>
                <table-view :ref="refName" :meta="tlMeta" :filter-params="filterParams" :active-data="activeData"
                            @active-change="handlerActiveChange"
                            @row-dblclick="handleRowDbClick">
                    <template #operation-bar><span></span></template>
                    <template #buttons><span></span></template>
                    <template #operation-column><span></span></template>
                </table-view>
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
    import DefaultTableListMeta from '../../tableview/ui-conf';
    import DefaultSearchPanelMeta from '../../searchview/ui-conf';

    export default {
        name: "FindPanel",
        props: {
            meta: {
                type: Object,
                default: () => {
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
            handleRowDbClick({row, column, event}) {
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
                const {activeData} = this;
                if (!activeData) {
                    this.$message.warning('请选择数据.');
                    return;
                }

                this.$emit('ok', activeData); // feedback activeData
            },
            cancel() {
                this.$emit('cancel', 'clean'); // feedback clean
            }
        },
        computed: {
            spMeta() {
                let meta = this.$merge(this.meta.search, DefaultSearchPanelMeta);
                meta.component_name = 'SearchView';
                return meta;
            },
            tlMeta() {
                let meta = this.$merge(this.meta.table, DefaultTableListMeta);
                meta.component_name = 'TableView';
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
