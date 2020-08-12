<template>
    <div style="padding: 20px; overflow: auto; height: 100%;">
        <h1>组件开发调试页面</h1>

<!--        <svg-icon-->
<!--                value="https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80"></svg-icon>-->
<!--        <svg-icon value="bug" style="color: red" class="ddd"></svg-icon>-->
<!--        <svg-icon value="more"></svg-icon>-->
<!--        <svg-icon value="pass"></svg-icon>-->
<!--        <svg-icon value="el-icon-setting" style="color: blue" class="ddd"></svg-icon>-->

        <svg-icon v-for="i in svgs" :value="i"></svg-icon>

    </div>
</template>

<script>

    export default {
        name: "WorkSpace",
        data() {
            return {
                svgs: []
            }
        },
        methods: {},
        created() {
            const symbols = document.querySelectorAll('svg>symbol')
            const svgs = Array.prototype.slice.call(symbols).map(s => s.id.match(/icon-(\S*)/)[1])
            this.svgs = svgs
        }
    }
</script>

<style scoped>
    .ddd {
        font-size: 40px;
    }
</style>
