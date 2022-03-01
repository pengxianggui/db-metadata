<template>
    <div class="el-card container-view">
        <search-view :meta="spMeta" @search="handleSearch"></search-view>
        <table-view :ref="tlRefName" :meta="tlMeta" :filter-params="filterParams">
            <tempalte #operation-bar="{conf, choseData}">
                <slot name="operation-bar" v-bind:conf="conf" v-bind:choseData="choseData"></slot>
            </tempalte>

            <template #prefix-btn="{conf, choseData}">
                <slot name="prefix-btn" v-bind:conf="conf" v-bind:choseData="choseData"></slot>
            </template>
            <template #add-btn="{conf}">
                <slot name="add-btn" v-bind:conf="conf"></slot>
            </template>
            <template #batch-delete-btn="{conf, choseData}">
                <slot name="batch-delete-btn" v-bind:conf="conf" v-bind:choseData="choseData"></slot>
            </template>
            <template #suffix-btn="{conf, choseData}">
                <slot name="suffix-btn" v-bind:conf="conf" v-bind:choseData="choseData"></slot>
            </template>

            <template #buttons="{scope}">
                <slot name="buttons" v-bind:scope="scope"></slot>
            </template>

            <template #inner-before-extend-btn="{scope}">
                <slot name="inner-before-extend-btn" v-bind:scope="scope"></slot>
            </template>
            <template #view-btn="{scope, conf}">
                <slot name="view-btn" v-bind:conf="conf" v-bind:scope="scope"></slot>
            </template>
            <template #edit-btn="{scope, conf}">
                <slot name="edit-btn" v-bind:conf="conf" v-bind:scope="scope"></slot>
            </template>
            <template #delete-btn="{scope, conf}">
                <slot name="delete-btn" v-bind:conf="conf" v-bind:scope="scope"></slot>
            </template>
            <template #inner-after-extend-btn="{scope}">
                <slot name="inner-after-extend-btn" v-bind:scope="scope"></slot>
            </template>
        </table-view>
    </div>
</template>

<script>
    import utils from '../utils'
    import {getSearchViewMeta, getTableViewMeta, loadFeature} from "../utils/rest";

    export default {
        name: "SingleGridTmpl",
        meta: {
          isTemplate: true,
          isPage: false,
          cn: '单表模板',
          icon: 'table',
          buildIn: true // 内建：DbMeta提供
        },
        props: {
            fc: String,
            oc: String,
            searchMeta: {
              type: Object,
              default: () => {}
            },
            tableMeta: {
              type: Object,
              default: () => {}
            }
        },
        data() {
            const {fc: R_fc, oc: R_oc} = this.$route.query;
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
                getTableViewMeta(objectCode).then(resp => {
                    const {tableMeta} = this
                    this.tlMeta = utils.reverseMerge(resp.data, tableMeta);
                }).catch(({msg = '获取TableView meta数据错误'}) => {
                    console.error('[ERROR] msg: %s', msg);
                });

                getSearchViewMeta(objectCode).then(resp => {
                    const {searchMeta} = this
                    this.spMeta = utils.reverseMerge(resp.data, searchMeta);
                }).catch(({msg = '获取SearchView meta数据错误'}) => {
                    console.error('[ERROR] msg: %s', msg);
                });
            }
        },
        created() {
            const {featureCode, objectCode} = this;

            if (!utils.isEmpty(featureCode)) {
                loadFeature(featureCode).then(resp => {
                    const config = resp.data['singleGrid'];
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
