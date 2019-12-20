<template>
    <ul id="menu">
        <slot></slot>
    </ul>
</template>

<script>
    export default {
        name: "List"
    }
</script>

<style scoped>
    ul#menu {
        list-style: none;
        margin: 0;
        padding: 5px;
    }
</style>