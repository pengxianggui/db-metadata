<template>
    <div class="toggle-panel">
        <div :class="after" class="label" @click="toggle">
            <slot name="label" v-if="before === 'top'" v-bind:open="open">
                <i :class="{'el-icon-caret-bottom': !open, 'el-icon-caret-top': open}"></i>
            </slot>
        </div>
        <div v-show="open">
            <slot></slot>
        </div>
        <div :class="after" class="label" @click="toggle">
            <slot name="label" v-if="before === 'bottom'">
                <i :class="{'el-icon-caret-bottom': !open, 'el-icon-caret-top': open}"></i>
            </slot>
        </div>
    </div>
</template>

<script>
    export default {
        name: "ZTogglePanel",
        props: {
            labelPosition: {
                type: String,
                default: () => "bottom-center",
                validator: function (value) {
                    return ["top-left", "top-center", "top-right", "bottom-left", "bottom-center", "bottom-right"].indexOf(value) >= 0;
                }
            },
            defaultOpen: {
                type: Boolean,
                default: () => false
            }
        },
        watch: {
            defaultOpen: function (newV) {
                this.open = newV
            }
        },
        data() {
            return {
                open: this.defaultOpen
            }
        },
        methods: {
            toggle() {
                this.open = !this.open
            }
        },
        computed: {
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

    .el-divider.el-divider--horizontal {
        margin-top: 12px;
        margin-bottom: 12px;
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
