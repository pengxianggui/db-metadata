<template>
    <div>
        <h2>{{meta.component_name}}: </h2>
        <div class="quote">
            <span>{{description}}</span>
            <span>；目前暂时固定其中属性, 和对应的原子控件</span>
        </div>
        <mini-form-box v-model="model"></mini-form-box>
        <label>值为:</label>
        <json-box v-model="model"></json-box>
    </div>
</template>

<script>
    import MiniFormBox from '../core/miniformbox/src/MiniFormBox'

    export default {
        name: "MiniFormConfigDemo",
        data() {
            return {
                model: {},
                description: MiniFormBox.description,
                meta: {
                    component_name: 'MiniFormBox'
                },
            }
        }
    }
</script>

<style scoped>

</style>