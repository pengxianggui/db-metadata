<template>
    <div class="toggle-panel">
        <div class="toggle-button" @click="toggle">
            {{open ? oLabel: cLabel}}
        </div>
        <slot v-if="open"></slot>
    </div>
</template>

<script>
    import {DEFAULT} from '@/constant'
    import Meta from './mixins/meta'
    import Val from './form/value-mixins'
    export default {
        mixins: [Meta(DEFAULT.ZTogglePanel), Val],
        name: "z-toggle-panel",
        data() {
            return {
                open: false
            }
        },
        methods: {
            toggle() {
                this.open = !this.open
            }
        },
        created() {
            this.open = this.defaultOpen;
        },
        computed: {
            innerMeta() {
                return this.$merge(this.meta, DEFAULT.ZTogglePanel);
            },
            defaultOpen() {
                return this.innerMeta['default_open'];
            },
            oLabel() {
                return this.innerMeta['olabel'];
            },
            cLabel() {
                return this.innerMeta['clabel'];
            }
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
