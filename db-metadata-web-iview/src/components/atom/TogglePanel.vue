<template>
    <div class="toggle-panel">
        <div class="toggle-button" v-text="flag ? '收起元数据' : '查看元数据'" @click="toggle"></div>
        <slot v-if="flag"></slot>
    </div>
</template>

<script>
    export default {
        name: "toggle-panel",
        data () {
            return {
                flag: false
            }
        },
        props: {
            open: {
              type: [Boolean, String],
              default: function () {
                  return false
              }
          }
        },
        methods: {
            toggle () {
                this.flag = !this.flag
            }
        },
        mounted() {
            this.flag = this.open
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
