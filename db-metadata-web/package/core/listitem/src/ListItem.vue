<template>
    <li @click="$emit('click')"
        v-bind="$attrs"
        @mouseenter="$emit('hover')">
        <slot name="title"></slot>
        <slot></slot>
    </li>
</template>

<script>
    export default {
        name: "ListItem"
    }
</script>

<style scoped>
    li {
        height: 100%;
        padding: 5px;
        box-sizing: border-box;
        line-height: 32px;
        cursor: pointer;
        border-bottom: #EBEEF5 1px solid;
    }

    li:hover {
        background-color: rgba(255, 255, 255, 0.2);
        color: #409EFF;
    }
</style>
