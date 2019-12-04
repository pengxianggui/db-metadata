<template>
    <div>
        <el-tree :ref="refName" :data="data"
                 v-bind="innerMeta.conf"
                 @node-click="$emit('node-click')"
                 @node-contextmenu="$emit('node-contextmenu')"
                 @check-change="$emit('check-change')"
                 @check="$emit('check')"
                 @current-change="$emit('current-change')"
                 @node-expand="$emit('node-expand')"
                 @node-collapse="$emit('node-collapse')"

        >
            <span slot-scope="{ node, data }">
<!--            <template slot-scope="{ node, data }">-->
                <slot v-bind:node="node" v-bind:data="data"></slot>
                <!--            </template>-->
                </span>
        </el-tree>
    </div>
</template>

<script>
    import {DEFAULT} from '@/constant'
    import Meta from './mixins/meta'

    export default {
        mixins: [Meta(DEFAULT.TreeList)],
        name: "TreeList",
        props: {
            data: {
                type: Array,
                required: true
            }
        },
        data() {
            return {
                refName: this.meta['name'] + '_TREE'
            }
        },
        methods: {
            filter(value) {
                this.ref.filter(value);
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
        },
        computed: {
            ref() {
                return this.$refs[this.refName];
            }
        }
    }
</script>

<style scoped>

</style>