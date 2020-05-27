<template>
    <div>
        <h2>动态路由: </h2>
        <el-input v-model="value">
            <template slot="prepend">{{prefix}}</template>
            <el-button slot="append" type="primary" @click="handlerJump()">跳转</el-button>
        </el-input>
        <el-container>
            <ul style="width: 200px; overflow: auto; border: 1px solid #dddddd; padding: 10px;">
                <li v-for="item in routes" :key="item.name" @click="choseRoute(item)">
                    {{item.path}}
                </li>
            </ul>
            <iframe id="iframe" :src="path" frameborder="0" width="100%" height="500" style="padding: 20px;"></iframe>
        </el-container>
    </div>
</template>

<script>
    import {globalRoute} from '@/router/splitRoute'

    export default {
        name: "DynamicRouteDemo",
        data() {
            return {
                value: 'meta-object',
                path: null,
                routes: globalRoute,
                prefix: '/#/',
            }
        },
        methods: {
            handlerJump() {
                this.path = this.prefix + this.value;
                this.$nextTick(() => {
                    document.getElementById('iframe').contentWindow.location.reload();
                })
            },
            choseRoute(route) {
                let path = route.path;
                if (path.indexOf('/') == 0) {
                    path = path.substr(1);
                }
                this.value = path;
            }
        }
    }
</script>

<style scoped>

</style>