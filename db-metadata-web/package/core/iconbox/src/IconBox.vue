<template>
    <div>
        <el-input v-model="nativeValue"
                  v-bind="conf"
                  :name="meta.name"
                  @blur="$emit('blur', $event)"
                  @focus="$emit('focus', $event)"
                  @change="$emit('change', $event)"
                  @clear="handlerClear($event)"
                  v-on:click.native="handlerClick($event)"
                  suffix-icon="el-icon-search"
                  placeholder="点击选择图标" clearable>
            <template #prefix>
                <svg-icon v-if="nativeValue" :value="nativeValue"></svg-icon>
            </template>
        </el-input>
        <dialog-box :visible.sync="dialogVisible">
            <icon-panel @selected="selectIcon"></icon-panel>
            <template #footer>
                <span class="tip">tips: 双击图标回选</span>
            </template>
        </dialog-box>
    </div>
</template>
<script>
    import IconPanel from './IconPanel'
    import Meta from '../../mixins/meta'
    import Val from '../../mixins/value'
    import DefaultMeta from '../ui-conf'

    export default {
        name: 'IconBox',
        label: "图标框",
        mixins: [Meta(DefaultMeta), Val()],
        components: {
            IconPanel
        },
        props: {
            value: String
        },
        data() {
            return {
                dialogVisible: false
            }
        },
        methods: {
            handlerClear(ev) {
                if (ev) ev.stopPropagation();
                this.$emit('clear', ev);
            },
            handlerClick(ev) {
                if (ev) ev.stopPropagation();
                this.dialogVisible = true;
            },
            selectIcon(value) {
                this.nativeValue = value;
                this.dialogVisible = false;
            }
        },
        computed: {
            conf() {
                const {meta: {conf}, $attrs, $reverseMerge} = this
                return $reverseMerge(conf, $attrs)
            }
        }
    }
</script>
<style scoped>
    .tip {
        font-size: 12px;
        color: #999999;
    }
</style>
