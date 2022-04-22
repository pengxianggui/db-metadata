<template>
    <div v-if="valueType === 'external'" :style="styleExternalIcon"
         class="external-icon svg-icon" v-on="$listeners" v-bind="$attrs"></div>
    <img v-else-if="valueType === 'internal' || valueType === 'relative'"
         :src="imgSrc" class="internal-icon svg-icon">
    <i v-else-if="valueType === 'icon'" :class="value" class="svg-icon"></i>
    <svg v-else aria-hidden="true" class="svg-icon" v-on="$listeners" v-bind="$attrs">
        <use :xlink:href="iconName"/>
    </svg>
</template>

<script>
    import {utils} from "../../../index";

    export default {
        name: 'SvgIcon',
        props: {
            value: {
                type: String,
                required: false
            }
        },
        computed: {
            valueType() { // 值类型：external: 外部链接、internal:相对路径地址、icon: el-icon图标、svg: svg图标
              const {value} = this
              if (utils.isEmpty(value)) {
                return 'svg'
              }

              if (utils.isExternal(value)) { // 外部链接
                return 'external'
              } else if (value.startsWith('/')) { // 内部链接
                return 'internal'
              } else if (value.startsWith('.')) { // 相对地址： 前端静态资源
                return 'relative'
              } else if (value.startsWith('el-icon-')) { // ElementUI原生图标
                return 'icon'
              } else {
                return 'svg'
              }
            },
            iconName() {
                const value = utils.assertEmpty(this.value, 'defaultSvg')
                return `#me-icon-${value}`
            },
            imgSrc() {
                if (this.valueType === 'internal') {
                  const {defaults: {baseURL} = {}} = this.$axios
                  return utils.resolve(baseURL, this.value)
                } else if (this.valueType === 'relative') {
                  return this.value
                }
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
        width: 1.5em;
        height: 1em;
        fill: currentColor;
        overflow: hidden;
        text-align: center;
    }

    .external-icon {
        background-color: currentColor;
        mask-size: cover !important;
        display: inline-block;
    }

    .internal-icon {
        display: inline-block;
    }
</style>
