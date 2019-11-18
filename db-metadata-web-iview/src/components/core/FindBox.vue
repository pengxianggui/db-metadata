<template>
    <el-input v-model="nativeValue"
              v-bind="innerMeta.conf"
              :name="innerMeta.name"
              @blur="$emit('blur', $event)"
              @focus="$emit('focus', $event)"
              @change="$emit('change', $event)"
              @clear="handlerClear($event)"
              v-on:click.native="handlerClick($event)"
              suffix-icon="el-icon-search"
    ></el-input>
</template>

<script>
    import {DEFAULT} from '@/constant'
    import Meta from './mixins/meta'
    import Val from './form/value-mixins'

    export default {
        mixins: [Meta(DEFAULT.FindBox), Val],
        name: "FindBox",
        label: "查找框",
        props: {
            value: String
        },
        methods: {
            handlerClick(ev) {
                if (ev)
                    ev.stopPropagation();
                let url = this.innerMeta['data_url'];
                if (!url) {
                    console.error('data_url is required property, and not nullable. meta: %o', this.innerMeta);
                    return;
                }
                this.$axios.get(url).then(resp => {
                    let findPanelMeta = resp.data;
                    findPanelMeta.component_name = 'FindPanel';
                    this.$dialog(findPanelMeta, {
                        "title": "请选择数据"
                    }).then(value => {
                        this.nativeValue = value;
                    }).catch(flag => {
                        if (flag === 'clean') this.nativeValue = null;
                    });
                }).catch(err => {
                    console.error(err.msg);
                    this.$message.error(err.msg);
                });
            },
            handlerClear(ev) {
                if (ev)
                    ev.stopPropagation();
                this.$emit('clear', ev);
            }
        }
    }
</script>

<style scoped>

</style>