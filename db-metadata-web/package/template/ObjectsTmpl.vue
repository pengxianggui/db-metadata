<template>
    <el-card>
        <h4>点击应用SingleGridTmpl渲染相应元对象</h4>
        <el-button v-for="object in objectCodes" @click="jump(object.code)" :key="object.code" class="el-card md_el-card">
            <b>{{object.code}}</b>
        </el-button>
        <iframe id="iframe" :src="path" frameborder="0" width="100%" height="500" style="padding: 20px;"></iframe>
    </el-card>
</template>

<script>
    import {restUrl} from "../constant/url";

    export default {
        name: "ObjectsTmpl",
        data() {
            return {
                objectCodes: [],
                path: null
            }
        },
        methods: {
            jump(objectCode) {
                this.path = this.$compile('/#/table?objectCode={objectCode}', {objectCode: objectCode});
                this.$nextTick(() => {
                    document.getElementById('iframe').contentWindow.location.reload();
                });
            }
        },
        mounted() {
            this.$axios.get(restUrl.OBJECT_CODE_LIST).then(resp => {
                this.objectCodes = resp.data
            })
        }
    }

</script>

<style scoped>

</style>
