<template>
    <div>
        <h2>{{meta.component_name}}: </h2>
        <component :is="meta.component_name" v-model="value" :meta="meta"></component>
        <br>
        <label>值为：</label><code>{{value}}</code>
        <z-toggle-panel>
            <json-box v-model="meta" mode="code"></json-box>
        </z-toggle-panel>
    </div>
</template>

<script>
    export default {
        name: "time-box-demo",
        data () {
            return {
                value: null,
                meta: {
                    component_name: 'TimeBox',
                    ui_config: {
                        placeholder: '输入时间..',
                        // 'value-format': 'Date' // 传入Date则表明值格式为原Date日期格式
                    }
                },
            }
        }
    }
</script>

<style scoped>

</style>
