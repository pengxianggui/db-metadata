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
    import {getSpMeta, getTableTlMeta, loadFeature} from "../core/mixins/methods"
    import {assert, isEmpty} from "../utils/common";

    export default {
        name: "TreeSingleGridTmpl",
        mixins: [loadFeature, getTableTlMeta, getSpMeta],
        props: {
            fc: String
        },
        data() {
            const {featureCode: R_fc} = this.$route.query;
            const featureCode = utils.assertUndefined(this.fc, R_fc);
            return {
                featureCode: featureCode,
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
                this.getTableTlMeta(objectCode).then(resp => {
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
            const {featureCode, initMeta, loadFeature} = this;
            assert(!isEmpty(featureCode), `featureCode无效: ${featureCode}`)

            loadFeature(featureCode).then(resp => {
                const config = resp.data['table'];
                initMeta(config.objectCode);
            })
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
