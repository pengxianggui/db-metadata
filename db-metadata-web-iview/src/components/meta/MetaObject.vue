<template>
    <div>
        <search-panel :ref="spMeta['name']" :meta="spMeta" @search="handleSearch"></search-panel>
        <table-list :ref="tlMeta['name']" :meta="tlMeta">
            <template #operation-bar="{conf, operations}">
                <el-button-group>
                    <el-button v-bind="conf" @click="operations.handleBatchDelete($event)" type="danger"
                               icon="el-icon-delete-solid">删除</el-button>
                </el-button-group>
            </template>
        </table-list>
    </div>
</template>

<script>
    import {getSpMeta} from "../core/mixins/methods"

    export default {
        name: "MetaObject",
        mixins: [getSpMeta],
        props: {
            R_oc: String
        },
        data() {
            return {
                objectCode: this.R_oc,
                tlMeta: {},
                spMeta: {}
            }
        },
        methods: {
            getTlMeta(objectCode) {
                let url = this.$compile("/table/meta/{objectCode}", {
                    objectCode: objectCode
                });
                this.$axios.get(url).then(resp => {
                    this.tlMeta = resp.data;
                    this.$nextTick(() => {
                        this.ref.doDelete = this.doDelete; // override doDelete
                    });
                }).catch(err => {
                    console.error('[ERROR] url: %s, msg: %s', url, err.msg);
                    this.$message.error(err.msg);
                });
            },
            handleSearch(params) {
                this.$refs[this.tlMeta['name']].getData(params);
            },
            doDelete(ids, ev, row, index) {
                let objectCodes;
                if (Array.isArray(ids)) {
                    const objectCodeArr = this.ref.innerChoseData.map(row => row.code);
                    objectCodes = objectCodeArr.join(',');
                } else {
                    objectCodes = row.code;
                }
                let title = '<div style="overflow: auto;">确定删除如下元对象? ' + objectCodes + '</div>';
                let url = '/meta/delete?objectCode=' + objectCodes;

                this.$confirm(title, '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning',
                    dangerouslyUseHTMLString: true
                }).then(() => {
                    this.$axios.delete(url).then(resp => {
                        this.$message.success(resp.msg);
                        this.getData();
                    }).catch(err => {
                        this.$message.error(err.msg);
                    });
                });
            }
        },
        created() {
            this.getTlMeta(this.objectCode);

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