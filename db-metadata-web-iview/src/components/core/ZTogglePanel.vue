<template>
    <div class="toggle-panel">
        <div :class="after" class="label" @click="toggle">
            <slot name="label" v-if="before === 'top'">
                <i :class="{'el-icon-caret-bottom': open, 'el-icon-caret-top': !open}"></i>
            </slot>
        </div>
        <slot v-if="open"></slot>
        <div :class="after" class="label" @click="toggle">
            <slot name="label" v-if="before === 'bottom'">
                <i :class="{'el-icon-caret-bottom': !open, 'el-icon-caret-top': open}"></i>
            </slot>
        </div>
    </div>
</template>

<script>
    import {DEFAULT} from '@/constant'
    import Meta from './mixins/meta'
    import Val from './form/value-mixins'

    export default {
        mixins: [Meta(DEFAULT.ZTogglePanel), Val],
        name: "ZTogglePanel",
        props: {
            "labelPosition": {
                type: String,
                default: "bottom-center",
                validator: function (value) {
                    return ["top-left", "top-center", "top-right", "bottom-left", "bottom-center", "bottom-right"].indexOf(value) >= 0;
                }
            }
        },
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
            before() {
                return this.labelPosition.split('-')[0]
            },
            after() {
                return this.labelPosition.split('-')[1]
            }
        }
    }
</script>

<style scoped>
    .toggle-panel {
    }

    .label {
        color: #aaaaaa;
    }

    .label:hover {
        cursor: pointer;
        color: #666666;
    }

    .left {
        text-align: left;
    }

    .center {
        text-align: center;
    }

    .right {
        text-align: right;
    }
</style>
