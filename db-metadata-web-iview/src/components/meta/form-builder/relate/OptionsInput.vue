<template>
    <ul class="container" style="display: block;">
        <br>
        <draggable v-model="nativeValue" @end="emit">
            <li v-for="(item, index) in nativeValue" :key="item.id">
                <div :class="{'red-border': item.error}">
                    <el-input v-model="item.key" @change="emit">
                        <template #prepend><label>key</label></template>
                    </el-input>
                    <el-input v-model="item.value" @change="emit">
                        <template #prepend><label>value</label></template>
                    </el-input>
                    <el-button circle icon="el-icon-minus" @click="minus(item)"></el-button>
                </div>
            </li>
        </draggable>
        <li style="text-align: left;">
            <el-button circle icon="el-icon-plus" @click="plus"></el-button>
            <span v-if="errorTip">
                <span class="tip">&nbsp;规则: key值不能重复, 且key, value均不能为空.</span>
                <el-tooltip content="不符合规则的红色项将不被保存" placement="right">
                    <i class="el-icon-question"></i>
                </el-tooltip>
            </span>
        </li>
    </ul>
</template>

<script>
    import utils from '@/utils'
    import draggable from 'vuedraggable'

    export default {
        name: "OptionsInput",
        components: {
            draggable
        },
        props: {
            value: Array
        },
        data() {
            let value = utils.deepClone(this.value);
            value.forEach(ele => {
                ele['error'] = false;
                ele['id'] = Math.floor(Math.random() * 10000);
            });
            return {
                nativeValue: value
            }
        },
        methods: {
            plus() {
                this.nativeValue.push({
                    id: Math.floor(Math.random() * 10000),
                    key: null,
                    value: null,
                    error: true
                });
                this.emit();
            },
            minus(item) {
                if (this.nativeValue.length === 0) return;
                let index = this.nativeValue.map(item => item.key).indexOf(item.key);
                this.nativeValue.splice(index, 1);
                this.emit();
            },
            emit() {
                let options = [];

                utils.markNotRepeatEle(this.nativeValue, 'key', function (ele) {
                    ele['error'] = false || (!ele.key || !ele.value);
                }, function (ele) {
                    ele['error'] = true;
                });

                options = this.nativeValue.filter(ele => ele.error === false).map(ele => {
                    return {
                        key: ele.key,
                        value: ele.value
                    }
                });

                this.$emit('input', options);
                this.$emit('change', options);
            }
        },
        computed: {
            errorTip() {
                return !this.nativeValue.every(ele => ele.error === false);
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

    .red-border {
        display: inline-block;
        border: 1px dotted #f56c6c;
        border-radius: 20px;
        padding: 2px;
    }

    .tip {
        font-size: 12px;
        color: #f56c6c;
    }
</style>