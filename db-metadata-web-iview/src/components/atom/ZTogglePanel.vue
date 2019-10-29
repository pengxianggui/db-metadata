<template>
    <div class="toggle-panel">
        <div class="toggle-button" @click="toggle">
            {{open ? meta.conf.olabel: meta.conf.clabel}}
        </div>
        <slot v-if="open"></slot>
    </div>
</template>

<script>
    import {DEFAULT} from '@/constant'
    export default {
        name: "z-toggle-panel",
        data() {
            return {
                open: false
            }
        },
        props: {
            meta: {
                type: Object,
                default: function () {
                    return DEFAULT.ZTogglePanel;
                }
            },
        },
        methods: {
            toggle() {
                this.open = !this.open
            },
            getDefaultConf() {
                return DEFAULT.ZTogglePanel
            },
            initConf() {
                this.meta.conf = this.meta.conf || {}
                let defaultConf = this.getDefaultConf() || {}
                this.$merge(this.meta.conf, defaultConf)
                this.open = this.meta.conf['default_open']
            }
        },
        created() {
            this.initConf()
        }
    }
</script>

<style scoped>
    .toggle-panel {
    }

    div.toggle-button {
        text-align: center;
        padding: 2px;
        height: 20px;
        line-height: 20px;
        margin: 0;
        cursor: pointer;
        color: #999999;
    }
</style>
