<template>
    <div>
        <div class="header">
            <slot name="title">
                <h3>Tree</h3>
            </slot>
        </div>
        <el-tree :ref="refName" :data="innerData"
                 v-bind="$reverseMerge(innerMeta.conf, $attrs)"
                 @node-click="handleNodeClick"
                 @node-contextmenu="$emit('node-contextmenu')"
                 @check-change="$emit('check-change')"
                 @check="handleNodeCheck"
                 @current-change="$emit('current-change')"
                 @node-expand="$emit('node-expand')"
                 @node-collapse="$emit('node-collapse')">
            <template slot-scope="{ node, data }">
                <slot v-bind:node="node" v-bind:data="data">
                    <span>{{data[innerMeta.conf['props']['label']]}}</span>
                </slot>
            </template>
        </el-tree>
    </div>
</template>

<script>
    import utils from '@/utils'
    import {DEFAULT, CONSTANT} from '@/constant'
    import Meta from './mixins/meta'

    export default {
        mixins: [Meta(DEFAULT.Tree)],
        name: "Tree",
        props: {
            data: Array
        },
        data() {
            return {
                innerData: [],
                activeData: {},
                choseData: [],
                halfChoseData: []
            }
        },
        methods: {
            handleNodeClick(row, node, event) {
                const {primaryKey} = this;

                if (row[primaryKey] === this.activeData[primaryKey]) {  // cancel active row
                    this.activeData = {};
                } else {
                    this.activeData = row;
                }
                this.$emit('active-change', this.activeData);
            },

            handleNodeCheck(row, {checkedNodes, checkedKeys, halfCheckedNodes, halfCheckedKeys}) {
                const {multiMode} = this;
                if (!multiMode) return;
                this.choseData = checkedNodes;
                this.halfChoseData = halfCheckedNodes;
                this.$emit('chose-change', checkedNodes);
                this.$emit('half-chose-change', halfCheckedNodes);
            },

            filter(value) {
                const {refName} = this;
                this.$ref[refName].filter(value);
            },
            getChechedNodes() {
                return this.ref.getCheckedNodes();
            },
            getCheckedKeys() {
                return this.ref.getCheckedKeys();
            },
            setCheckedKeys(keys) {
                this.ref.setCheckedKeys(keys);
            },
            getHalfCheckedNodes() {
                return this.ref.getHalfCheckedNodes();
            },
            getHalfCheckedKeys() {
                return this.ref.getHalfCheckedKeys();
            },
            getCurrentKey() {
                return this.ref.getCurrentKey();
            },
            getCurrentNode() {
                return this.ref.getCurrentNode();
            },
            getNode(key) {
                return this.ref.getNode(key);
            },
            remove(key) {
                this.ref.remove(key);
            },
            append(data, parentNode) {
                this.ref.append(data, parentNode);
            },
            insertBefore(data, refNode) {
                this.ref.append(data, refNode);
            },
            insertAfter(data, refNode) {
                this.ref.append(data, refNode);
            },
            getData(params) {
                if (!utils.isObject(params)) params = {};

                if (!this.innerMeta.hasOwnProperty('data_url')) {
                    console.error('lack data_url attribute');
                    return;
                }

                let url = this.innerMeta['data_url'];

                this.$axios.safeGet(url, {
                    params: params
                }).then(resp => {
                    this.innerData = resp.data;
                    this.$emit("update:data", resp.data);
                }).catch(err => {
                    this.$message.error(err.msg);
                });
            },
            initData() { // init business data
                let {data} = this;
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
        mounted() {
            this.initData();
        },
        computed: {
            innerMeta() {
                return this.$merge(this.meta, DEFAULT.Tree);
            },
            refName() {
                return this.innerMeta['name'];
            },
            multiMode() {
                return this.innerMeta['conf']['show-checkbox'];
            },
            primaryKey() {
                const {objectPrimaryKey} = this.meta;
                const defaultPrimaryKey = CONSTANT.DEFAULT_PRIMARY_KEY;

                if (utils.isEmpty(objectPrimaryKey)) {
                    console.error('Missing primary key info! will use default primaryKey:%s', defaultPrimaryKey);
                    return defaultPrimaryKey;
                } else {
                    return objectPrimaryKey;
                }
            },
        }
    }
</script>

<style scoped>

</style>