<template>
    <row-grid :span="[14, 10]">
        <template #left>
            <table-list :ref="tlRefName" :meta="tlMeta" @active-change="handleActiveChange">
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
            </table-list>
        </template>
        <template #right>
            <div class="el-card" style="margin-left: 5px">
                <form-view :meta="fmMeta" @ok="formSubmit"></form-view>
            </div>
        </template>
    </row-grid>
</template>

<script>
    import utils from '@/utils'
    import {CONSTANT, DEFAULT, URL} from '@/constant'
    import {getSpMeta, getTlMeta, loadFeature} from "@/components/core/mixins/methods"

    export default {
        name: "TableFormTmpl",
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
                fmMeta: {},
                tlMeta: {}
            }
        },
        methods: {
            formSubmit() {
                const tlRefName = this.tlRefName;
                this.$refs[tlRefName].getData();
            },
            handleChoseChange(rows) {
                // pxg_todo
            },
            handleActiveChange(row) {
                if (utils.isEmpty(row)) {
                    this.fmMeta = this.$merge({}, DEFAULT.FormView);
                    return;
                }
                const primaryKey = this.primaryKey;
                const primaryValue = utils.extractValue(row, primaryKey);

                const objectCode = this.tlMeta['objectCode'];

                let url = this.$compile(URL.RECORD_TO_UPDATE, {
                    objectCode: objectCode,
                    primaryKv: primaryValue
                });
                this.$axios.get(url).then(resp => {
                    this.fmMeta = resp.data;
                });
            },
            initMeta(objectCode) {
                this.getTlMeta(objectCode).then(resp => {
                    this.tlMeta = resp.data;
                }).catch(err => {
                    console.error('[ERROR] msg: %s', err.msg);
                    this.$message.error(err.msg);
                });
            }
        },
        created() {
            const featureCode = this.featureCode;
            let objectCode;

            if (!utils.isEmpty(featureCode)) {
                this.loadFeature(featureCode).then(resp => {
                    objectCode = resp.data['objectCode'];
                    this.initMeta(objectCode);
                })
            } else {
                objectCode = this.objectCode;
                this.initMeta(objectCode);
            }
        },
        computed: {
            tlRefName() {
                return this.tlMeta['name'];
            },
            primaryKey() {
                let primaryKey = this.tlMeta.hasOwnProperty('objectPrimaryKey') ? this.tlMeta['objectPrimaryKey'] : CONSTANT.DEFAULT_PRIMARY_KEY;
                return primaryKey.split(',');
            }
        }
    }
</script>

<style scoped>

</style>