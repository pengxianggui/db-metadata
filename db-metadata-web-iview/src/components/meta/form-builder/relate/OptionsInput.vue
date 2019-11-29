<template>
    <ul class="container" style="display: block;">
        <br>
        <li v-for="(item, index) in nativeValue" :key="item.key">
            <el-input v-model="item.key">
                <template #prepend><label>K</label></template>
            </el-input>
            <el-input v-model="item.value">
                <template #prepend><label>V</label></template>
            </el-input>
            &nbsp;
            <el-button circle icon="el-icon-minus" @click="minus(item)"></el-button>
            <el-button circle icon="el-icon-plus" @click="plus"></el-button>
        </li>
    </ul>
</template>

<script>
    export default {
        name: "OptionsInput",
        props: {
            value: Array
        },
        data() {
            return {
                nativeValue: [{
                    key: "",
                    value: ""
                }]
            }
        },
        methods: {
            plus() {
                this.nativeValue.push({
                    key: null,
                    value: null
                })
            },
            minus(item) {
                if (this.nativeValue.length === 1) return;
                let index = this.nativeValue.map(item => item.key).indexOf(item.key);
                this.nativeValue.splice(index, 1);
            }
        },
        mounted() {
            if (this.value.length !== 0) {
                this.nativeValue = this.value;
            }
        },
        updated() {
            let options = this.nativeValue.filter(item => item.key && item.value);
            this.$emit('input', options);
        }
    }
</script>

<style scoped>
    ul.container {
        list-style: none;
    }

    ul.container li {
        margin: 3px 0;
    }

    ul.container .el-input {
        width: 200px;
    }
</style>