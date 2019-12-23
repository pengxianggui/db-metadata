<template>
    <div>
        <list style="width: 100%">
            <list-item v-for="(item, index) in innerData" :key="index"
                       :class="{'active': index === activeIndex}"
                       @click="handleClick(item, index, $event)">
                <span>{{item[innerLabelProps.label]}}</span>
            </list-item>
        </list>
        <slot name="pagination" v-bind:pageModel="pageModel">
            <el-pagination background
                           :page-size.sync="pageModel.size"
                           :current-page.sync="pageModel.index"
                           :total="pageModel.total"
                           v-bind="innerMeta.pagination"
                           @size-change="sizeChange"
                           @current-change="getData"
            ></el-pagination>
        </slot>
    </div>
</template>

<script>
    import utils from '@/utils'
    import {DEFAULT} from '@/constant'

    export default {
        name: "DataList",
        props: {
            labelProps: Object,
            meta: {
                type: Object,
                default: function () {
                    return {};
                }
            },
            data: Array,
            page: Object
        },
        data() {
            return {
                innerData: [],
                activeIndex: null,
                activeData: {},
                pageModel: {
                    size: 10,
                    index: 1,
                    total: 0
                },
            }
        },
        methods: {
            setPage(index) {
                this.pageModel['index'] = index;
            },
            sizeChange() {
                this.setPage(1); // jump to page one
                this.getData();
            },
            handleClick(row, index, event) {
                this.activeData = row;
                this.activeIndex = index;
                this.$emit('active-change', row);
            },
            setPageModel(page) {
                const {total, index, size} = page;
                if (!utils.isEmpty(total)) this.pageModel['total'] = parseInt(total);
                if (!utils.isEmpty(index)) this.pageModel['index'] = parseInt(index);
                if (!utils.isEmpty(size)) this.pageModel['size'] = parseInt(size);
            },
            getData(params) {
                if (!utils.isObject(params)) params = {};

                if (!this.innerMeta.hasOwnProperty('data_url')) {
                    console.error('lack data_url attribute');
                    return;
                }

                let url = this.innerMeta['data_url'];

                Object.assign(params, {
                    'p': this.pageModel['index'],
                    's': this.pageModel['size']
                });

                this.$axios.safeGet(url, {
                    params: params
                }).then(resp => {
                    this.innerData = resp.data;
                    this.$emit("update:data", resp.data);
                    if (resp.hasOwnProperty('page')) {
                        this.setPageModel(resp['page']);
                    }
                }).catch(err => {
                    this.$message.error(err.msg);
                });
            },
            initData() { // init business data
                let {page, data} = this;    // pxg_todo set page for DataList?

                if (page !== undefined) {
                    this.setPageModel(page)
                }

                if (data !== undefined) {
                    this.innerData = data;
                    return;
                }
                if (this.innerMeta.hasOwnProperty('data_url')) {
                    this.getData();
                    return;
                }
                console.error("data or data_url in meta provide one at least!")
            },
        },
        mounted() {
            this.initData();
        },
        watch: {
            'data': function (newVal, oldVal) {
                this.initData();    // 为避免data数据过大, 不进行深度监听
            },
            'innerMeta.data_url': {
                handler: function () {
                    this.initData();
                },
                immediate: false
            }
        },
        computed: {
            innerMeta() {
                return this.$merge(this.meta, DEFAULT.DataList);
            },
            innerLabelProps() {
                return utils.assertUndefined(this.labelProps, this.innerMeta['conf']['label-props'])
            }
        }
    }
</script>

<style scoped>
    .active {
        background-color: #dddddd;
        color: #409EFF;
    }
</style>