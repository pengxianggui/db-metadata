<template>
    <div v-if="isExternal" :style="styleExternalIcon"
         class="svg-external-icon svg-icon" v-on="$listeners" v-bind="$attrs"></div>
    <span v-else>
        <i v-if="value.startsWith('el-icon')" :class="value"></i>
        <svg v-else aria-hidden="true" class="svg-icon" v-on="$listeners" v-bind="$attrs">
            <use :xlink:href="iconName"/>
        </svg>
    </span>
</template>

<script>
    import {isExternal} from '../../../utils/common'

    export default {
        name: 'SvgIcon',
        props: {
            value: {
                type: String,
                required: true
            }
        },
        computed: {
            isExternal() {
                return isExternal(this.value)
            },
            iconName() {
                return `#icon-${this.value}`
            },
            styleExternalIcon() {
                return {
                    'background-image': `url(${this.value})`,
                    'background-size': '100% 100%'
                }
            }
        }
    }
</script>

<style scoped>
    .svg-icon {
        width: 1em;
        height: 1em;
        vertical-align: -0.15em;
        fill: currentColor;
        overflow: hidden;
    }

    .svg-external-icon {
        background-color: currentColor;
        mask-size: cover !important;
        display: inline-block;
    }
</style>
