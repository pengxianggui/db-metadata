<template>
    <ul class="container" style="display: block;">
        <br>
        <li v-for="(item, index) in nativeValue" :key="item.id">
            <el-input v-model="item.key">
                <template #prepend><label>K</label></template>
            </el-input>
            <el-input v-model="item.value">
                <template #prepend><label>V</label></template>
            </el-input>
            <el-button circle icon="el-icon-minus" @click="minus(item)"></el-button>
        </li>
        <li style="text-align: left;">
            <el-button circle icon="el-icon-plus" @click="plus"></el-button>
            <el-button circle icon="el-icon-upload2" type="primary" @click="emit"></el-button>
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
                maxLength: 0,
                nativeValue: [{
                    id: 0,
                    key: null,
                    value: null
                }]
            }
        },
        methods: {
            plus() {
                this.nativeValue.push({
                    id: this.maxLength++,
                    key: null,
                    value: null
                });
            },
            minus(item) {
                if (this.nativeValue.length === 1) return;
                let index = this.nativeValue.map(item => item.key).indexOf(item.key);
                this.nativeValue.splice(index, 1);
            },
            emit() {
                let options = [];
                for (let i = 0; i < this.nativeValue.length; i++) {
                    let item = this.nativeValue[i];
                    if (item.key && item.value) {
                        options.push(item);
                    }
                }
                this.$emit('input', options);
            }
        },
        watch: {
            'value': {
                handler: function (newVal, oldVal) {
                    this.nativeValue.splice(0, this.nativeValue.length);
                    this.maxLength = 0;
                    if (newVal.length !== 0) {
                        for (let i = 0; i < newVal.length; i++) {
                            newVal[i]['id'] = (this.maxLength++);
                            this.nativeValue.push(newVal[i]);
                        }
                    } else {
                        this.nativeValue.push({
                            id: this.maxLength++,
                            key: null,
                            value: null
                        });
                    }
                },
                deep: true,
                immediate: true
            }
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