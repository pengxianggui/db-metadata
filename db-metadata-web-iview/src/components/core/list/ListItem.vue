<template>
    <li @click="$emit('click')"
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
        padding: 5px;
        line-height: 32px;
        cursor: pointer;
        border-bottom: #EBEEF5 1px solid;
    }

    li:hover {
        background-color: #eeeeee;
        color: #409EFF;
    }
</style>