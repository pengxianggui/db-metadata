<template>
    <li @click="$emit('click')"
        @mouseenter="$emit('hover')">
        <slot name="title"></slot>
        <slot></slot>
    </li>
</template>

<script>
    export default {
        name: "PopMenuItem"
    }
</script>

<style scoped>
    li {
        padding: 5px;
        cursor: pointer;
    }

    li:hover {
        background-color: #eeeeee;
        color: #409EFF;
    }
</style>