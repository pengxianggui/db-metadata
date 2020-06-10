<template>
    <div class="el-card">
        <template v-if="formType">
            <mini-form v-bind="$reverseMerge(innerMeta.conf, $attrs)" :meta="innerMeta"
                       v-model="nativeValue"></mini-form>
        </template>
        <template v-else>
            <json-box v-model="nativeValue" mode="code" @input="$emit('json-change')"></json-box>
        </template>
        <div style="display: flex;" v-if="showChangeType">
            <span>
                <el-button size="mini" icon="el-icon-guide" circle @click="changeType"></el-button>
            </span>
            <slot name="button-expand" v-bind:value="nativeValue"></slot>
            <span style="flex: 1"></span>
        </div>
    </div>
</template>

<script>
    import Core from './core'
    import Meta from '../../mixins/meta'
    import Val from '../../mixins/value'
    import assembleMeta from './assembleMeta'
    import DefaultFormViewMeta from '../../form/ui-conf'

    export default {
        name: "MiniFormBox",
        mixins: [Meta(DefaultFormViewMeta, assembleMeta), Val()],
        props: {
            value: {
                type: [Object, String],
                default: () => {
                }
            },
            showChangeType: {
                type: Boolean,
                default: () => false
            }
        },
        components: {
            "mini-form": Core
        },
        data() {
            return {
                formType: true
            }
        },
        methods: {
            changeType() {
                const {formType} = this;
                this.formType = !formType;
                this.$emit('change-type', this.formType); // hook
            }
        }

    }
</script>

<style scoped>
</style>
