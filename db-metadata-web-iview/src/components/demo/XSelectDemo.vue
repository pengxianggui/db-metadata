<template>
    <div>
        <h2>xSelect: </h2>
        <x-select v-model="enterpriseId" :options="options"></x-select>
        值为: {{enterpriseId}}
    </div>
</template>

<script>
    import XSelect from '../atom/XSelect'
    export default {
        name: "x-select-demo",
        data() {
            return {
                enterpriseId: null,
                options: {
                    selectLabelKey: 'enterpriseName',   // 显示的文字取自selectItems的哪个key, 必须在selectItems中存在
                    selectValueKey: 'id',       // value所取的值取自selectItems的哪个key, 必须在selectItems中存在且唯一
                    selectItems: [{
                        enterpriseName: '浙江航天恒嘉',
                        id: "1",
                        disabled: false
                    },{
                        enterpriseName: '阿里巴巴',
                        id: "2",
                        disabled: false
                    },{
                        enterpriseName: '网易有道',
                        id: "3",
                        disabled: true
                    },{
                        enterpriseName: '中国平安',
                        id: "4",
                        disabled: false
                    }],
                    // 其他options属性设置
                    clearable: true,
                    // multiple: true
                }
            }
        },
        components: {
            XSelect
        }
    }
</script>

<style scoped>

</style>