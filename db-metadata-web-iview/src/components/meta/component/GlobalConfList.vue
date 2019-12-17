<template>
    <div>
        <search-panel :ref="spMeta['name']" :meta="spMeta" @search="handleSearch"></search-panel>
        <table-list :ref="tlMeta['name']" :meta="tlMeta">
            <template #add-btn="{conf}">
                <el-button v-bind="conf" @click="addConf" icon="el-icon-document-add">新增</el-button>
            </template>
            <template #batch-delete-btn><span></span></template>
            <template #inner-before-extend-btn="{conf, scope}">
                <el-tooltip content="配置" placement="left">
                    <el-button icon="el-icon-s-tools" v-bind="conf" type="info"
                               @click="handlerConf($event, scope.row, scope.$index)"></el-button>
                </el-tooltip>
            </template>
        </table-list>
    </div>
</template>

<script>
    import {URL} from '@/constant'
    import {getSpMeta, getTlMeta} from "@/components/core/mixins/methods"

    export default {
        name: "GlobalConfList",
        mixins: [getTlMeta, getSpMeta],
        data() {
            return {
                objectCode: this.$route.query.objectCode,
                tlMeta: {},
                spMeta: {}
            }
        },
        methods: {
            handleSearch(params) {
                this.$refs[this.tlMeta['name']].getData(params);
            },
            addConf() {
                this.$router.push(URL.R_GOBAL_CONF_ADD);
            },
            handlerConf(ev, row, index) {
                if (ev) ev.stopPropagation();
                let componentCode = row['comp_code'];
                this.$router.push({
                    path: 'global-conf',
                    query: {
                        componentCode: componentCode
                    }
                })
            }
        },
        created() {
            this.getTlMeta(this.objectCode).then(resp => {
                this.tlMeta = resp.data;
            }).catch(err => {
                console.error('[ERROR] msg: %s', err.msg);
                this.$message.error(err.msg);
            });

            this.getSpMeta(this.objectCode).then(resp => {
                this.spMeta = resp.data;
            }).catch(err => {
                console.error('[ERROR] msg: %s', err.msg);
                this.$message.error(err.msg);
            });
        },
        computed: {
            ref() {
                return this.$refs[this.tlMeta['name']];
            }
        }
    }
</script>

<style scoped>

</style>
