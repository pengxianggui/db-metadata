<template>
    <el-card>
        <h4>点击应用SingleGridTmpl渲染相应元对象</h4>
        <el-button v-for="object in objectCodes" @click="jump(object.code)" :key="object.code" class="el-card">
            <b>{{object.code}}</b>
        </el-button>
    </el-card>
</template>

<script>
    import {URL} from '@/constant';

    export default {
        name: "ObjectsTmpl",
        data() {
            return {
                objectCodes: []
            }
        },
        methods: {
            jump(objectCode) {
                let url = this.$compile('/#/main/table?objectCode={objectCode}', {objectCode: objectCode});
                window.open(url, '_blank');
            }
        },
        created() {
            this.$axios.get(URL.OBJECT_CODE_LIST).then(resp => {
                this.objectCodes = resp.data
            })
        }
    }

</script>

<style scoped>

</style>