<template>
    <div>
        <h2>PopMenu: </h2>
        <pop-menu trigger="hover">
            <template #body>
                <pop-menu-item>1</pop-menu-item>
                <pop-menu-item>2</pop-menu-item>
                <pop-menu-item>3</pop-menu-item>
            </template>
        </pop-menu>
    </div>
</template>
<script>
    export default {
        name: "PopMenuDemo",
        data() {
            return {};
        }
    };
</script>