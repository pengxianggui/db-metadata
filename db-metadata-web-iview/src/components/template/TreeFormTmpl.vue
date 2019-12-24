<template>
    <row-grid :span="[6, 18]">
        <template #left>
            <tree :meta="treeMeta" @active-change="handleActiveChange" @chose-change="handleChoseChange"></tree>
        </template>
        <template #right>
            <form-tmpl :ref="formRefName" :meta="formMeta"></form-tmpl>
        </template>
    </row-grid>
</template>

<script>
    import utils from '@/utils'
    import {DEFAULT, URL} from '@/constant'
    import {getFormMeta, getTreeMeta, loadFeature} from "@/components/core/mixins/methods"

    export default {
        name: "TreeFormTmpl",
        mixins: [getFormMeta, getTreeMeta, loadFeature],
        props: {
            fc: String
        },
        data() {
            const featureCode = utils.assertUndefined(this.fc, this.$route.query.featureCode);
            return {
                treeConf: {},
                formConf: {},
                treeMeta: {},
                formMeta: {},
                featureCode: featureCode
            }
        },
        methods: {
            handleActiveChange(activeData) {
                if (utils.isEmpty(activeData)) {
                    this.formMeta = this.$merge({}, DEFAULT.FormTmpl);
                    return;
                }
                const primaryKey = this.treeMeta['objectPrimaryKey'];
                const primaryValue = utils.extractValue(activeData, primaryKey.split(',')).join('_');
                const objectCode = this.treeMeta['objectCode'];

                let url = this.$compile(URL.RECORD_TO_UPDATE, {
                    objectCode: objectCode,
                    primaryKey: primaryValue
                });
                this.$axios.get(url).then(resp => {
                    this.formMeta = resp.data;
                });
            },
            handleChoseChange(choseData) {
                // pxg_todo
            }
        },
        created() {
            this.loadFeature(this.featureCode).then(resp => {
                const feature = resp.data;
                this.treeConf = feature['tree'];
                this.formConf = feature['form'];

                const treeObjectCode = this.treeConf['objectCode'];

                this.getTreeMeta(treeObjectCode).then(resp => {
                    this.treeMeta = resp.data;
                }).catch(err => {
                    console.error('[ERROR] msg: %s', err.msg);
                    this.$message.error(err.msg);
                });
            });
        },
        computed: {
            formRefName() {
                return this.formMeta['name'];
            }
        }

    }
</script>

<style scoped>

</style>