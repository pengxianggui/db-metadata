<template>
  <div class="el-card container-view">
    <row-grid :span="[14, 10]">
        <template #0>
            <table-view :ref="tlRefName" :meta="tlMeta" @active-change="handleActiveChange">
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

                <template #buttons="{scope, conf}">
                    <slot name="buttons" v-bind:conf="conf" v-bind:scope="scope"></slot>
                </template>
            </table-view>
        </template>
        <template #1>
            <div class="el-card" style="margin-left: 5px">
                <form-view :meta="fmMeta" @ok="formSubmit">
                    <template #action="{model, conf}">
                        <slot name="action" v-bind:model="model" v-bind:conf="conf"></slot>
                    </template>
                </form-view>
            </div>
        </template>
    </row-grid>
  </div>
</template>

<script>
    import utils from '../utils'
    import {defaultPrimaryKey} from '../config'
    import {getTableViewMeta, getUpdateFormMeta, loadFeature} from "../utils/rest";
    import DefaultFormViewMeta from '../core/formview/ui-conf'

    export default {
        name: "TableFormTmpl",
        props: {
            fc: String,
            oc: String
        },
        data() {
            const {fc: R_fc, oc: R_oc} = this.$route.query;
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
            refresh() {
                const {$refs, tlRefName} = this;
                $refs[tlRefName].getData();
            },
            formSubmit() {
                const tlRefName = this.tlRefName;
                this.$refs[tlRefName].getData();
            },
            handleChoseChange(rows) {
                // pxg_todo
            },
            handleActiveChange(row) {
                if (utils.isEmpty(row)) {
                    this.fmMeta = this.$merge({}, DefaultFormViewMeta);
                    return;
                }
                const primaryKey = this.primaryKey;
                const primaryValue = utils.extractValue(row, primaryKey);

                const objectCode = this.tlMeta['objectCode'];

                getUpdateFormMeta(objectCode, primaryValue).then(resp => {
                  this.fmMeta = resp.data
                })
            },
            initMeta(objectCode) {
                getTableViewMeta(objectCode).then(resp => {
                    this.tlMeta = resp.data;
                }).catch(({msg = '获取TableView meta数据错误'}) => {
                    console.error('[ERROR] msg: %s', msg);
                });
            }
        },
        created() {
            const featureCode = this.featureCode;
            let objectCode;

            if (!utils.isEmpty(featureCode)) {
                loadFeature(featureCode).then(resp => {
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
                let primaryKey = this.tlMeta.hasOwnProperty('objectPrimaryKey') ? this.tlMeta['objectPrimaryKey'] : defaultPrimaryKey;
                return primaryKey.split(',');
            }
        }
    }
</script>

<style scoped>

</style>
