<template>
    <div>
        <el-input v-model="nativeValue"
                  v-bind="$reverseMerge(innerMeta.conf, $attrs)"
                  :name="innerMeta.name"
                  @blur="$emit('blur', $event)"
                  @focus="$emit('focus', $event)"
                  @change="$emit('change', $event)"
                  @clear="handlerClear($event)"
                  v-on:click.native="handlerClick($event)"
                  suffix-icon="el-icon-search"
        ></el-input>
        <dialog-box :visible.sync="dialogVisible" :meta="dialogMeta">
            <find-panel :meta="findPanelMeta" @ok="handlerOk" @cancel="handlerCancel"></find-panel>
            <template #footer><span></span></template>
        </dialog-box>
    </div>
</template>

<script>
    import {DEFAULT} from '@/constant'
    import Meta from '../../mixins/meta'
    import Val from '../../mixins/value'

    export default {
        mixins: [Meta(DEFAULT.FindBox), Val()],
        name: "FindBox",
        label: "查找框",
        props: {
            value: String
        },
        data() {
            return {
                findPanelMeta: {},
                dialogMeta: {},
                dialogVisible: false
            }
        },
        methods: {
            handlerClick(ev) {
                if (ev) ev.stopPropagation();
                const {innerMeta} = this;
                const {data_url: url} = innerMeta;

                if (!url) {
                    console.error('data_url is required property, and not nullable. meta: %o', innerMeta);
                    return;
                }
                this.$axios.get(url).then(resp => {
                    this.findPanelMeta = resp.data;
                    this.findPanelMeta.component_name = 'FindPanel';
                    this.dialogVisible = true;
                }).catch(err => {
                    console.error(err);
                    this.$message.error(err.msg);
                });
            },
            handlerOk(value) {
                this.nativeValue = value;
                this.dialogVisible = false;
            },
            handlerCancel() {
                this.nativeValue = null;
                this.dialogVisible = false;
            },
            handlerClear(ev) {
                if (ev) ev.stopPropagation();
                this.$emit('clear', ev);
            }
        }
    }
</script>

<style scoped>

</style>