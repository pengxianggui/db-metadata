<template>
    <div class="el-card">
        <search-panel :meta="spMeta" @search="handleSearch"></search-panel>
        <table-tree-list :ref="tlRefName" :meta="tlMeta" :filter-params="filterParams">
            <template #prefix-btn="{conf}">
                <slot name="prefix-btn" v-bind:conf="conf"></slot>
            </template>
            <template #add-btn="{conf, add}">
                <slot name="add-btn" v-bind:conf="conf" v-bind:add="add"></slot>
            </template>
            <template #batch-delete-btn="{conf, batchDelete}">
                <slot name="batch-delete-btn" v-bind:conf="conf"
                      v-bind:batchDelete="batchDelete">
                </slot>
            </template>
            <template #suffix-btn="{conf}">
                <slot name="suffix-btn" v-bind:conf="conf"></slot>
            </template>

            <template #buttons="{scope, conf}">
                <slot name="buttons" v-bind:conf="conf" v-bind:scope="scope"></slot>
            </template>

            <!-- 主表单条纪录操作扩展插槽 -->
            <template #inner-before-extend-btn="{scope, conf}">
                <slot name="inner-before-extend-btn" v-bind:conf="conf" v-bind:scope="scope"></slot>
            </template>
            <template #edit-btn="{scope, conf, edit}">
                <slot name="edit-btn" v-bind:conf="conf" v-bind:scope="scope"></slot>
            </template>
            <template #delete-btn="{scope, conf}">
                <slot name="delete-btn" v-bind:conf="conf" v-bind:scope="scope"></slot>
            </template>
            <template #inner-after-extend-btn="{scope, conf}">
                <slot name="inner-after-extend-btn" v-bind:conf="conf" v-bind:scope="scope"></slot>
            </template>
        </table-tree-list>
    </div>
</template>

<script>
    import utils from '../utils'
    import {getSpMeta, getTlMeta, loadFeature} from "../core/mixins/methods"

    export default {
        name: "SingleGridTmpl",
        mixins: [loadFeature, getTlMeta, getSpMeta],
        props: {
            fc: String,
            oc: String
        },
        data() {
            const {featureCode: R_fc, objectCode: R_oc} = this.$route.query;
            const featureCode = utils.assertUndefined(this.fc, R_fc);
            const objectCode = utils.assertUndefined(this.oc, R_oc);
            return {
                featureCode: featureCode,
                objectCode: objectCode,
                tlMeta: {},
                spMeta: {},
                filterParams: {}
            }
        },
        methods: {
            refresh() {
                const {$refs, tlRefName} = this;
                $refs[tlRefName].getData();
            },
            handleSearch(params) {
                const tlRefName = this.tlRefName;
                this.filterParams = params;
                this.$nextTick(() => {
                    this.$refs[tlRefName].getData();
                })
            },
            initMeta(objectCode) {
                this.getTlMeta(objectCode).then(resp => {
                    this.tlMeta = resp.data;
                }).catch(err => {
                    console.error('[ERROR] msg: %s', err.msg);
                    this.$message.error(err.msg);
                });

                this.getSpMeta(objectCode).then(resp => {
                    this.spMeta = resp.data;
                }).catch(err => {
                    console.error('[ERROR] msg: %s', err.msg);
                    this.$message.error(err.msg);
                });
            }
        },
        created() {
            const {featureCode, objectCode} = this;

            if (!utils.isEmpty(featureCode)) {
                this.loadFeature(featureCode).then(resp => {
                    const config = resp.data['table'];
                    this.initMeta(config.objectCode);
                })
            } else {
                this.initMeta(objectCode);
            }
        },
        computed: {
            tlRefName() {
                return this.tlMeta['name'];
            }
        }
    }
</script>

<style scoped>

</style>
