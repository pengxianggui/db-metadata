<template>
    <div class="toggle-panel">
        <div class="toggle-button" @click="toggle">
            {{open ? meta.ui_config.olabel: meta.ui_config.clabel}}
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
                    return {
                        ui_config: {
                        }
                    }
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
                this.meta.ui_config = this.meta.ui_config || {}
                let defaultConf = this.getDefaultConf() || {}
                this.$merge(this.meta.ui_config, defaultConf)
                this.open = this.meta.ui_config['default-open']
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
