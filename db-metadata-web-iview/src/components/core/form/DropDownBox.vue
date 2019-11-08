<!--
# meta
description: meta data
eg:
    {
        "name": "",
        "data_url": "", // for options data
        "group": false, // 是否选项组模式
        "conf": {   // ui conf
            "clearable": true,
            "placeholder": "请下拉选择",
            "..."
        }
    }

# options
description: 选项数据(业务数据), 支持直接传入, 或者通过传入组件元数据, 在其中配置data_url参数, 从远端获取选项数据.
eg:
    [{
        key: 'xxx',
        value: 'yyy',
        disabled: false
    },
    ...],
若为选项组模式,
eg:
    [{
     label: "group1",
     options: [{
         key: "xxx",
         value: "yyy"
     }]
    }, {...}]

# custom behavior
## format: Function,
description: format option data, and return formatted data, like: [{key: "xxx", value: "yyy"}, ...]

 -->
<template>
    <el-select v-model="nativeValue"
               v-bind="innerMeta.conf"
               @change="$emit('change', $event)"
               @remove-tag="$emit('remove-tag', $event)"
               @clear="$emit('clear', $event)"
               @blur="$emit('blur', $event)"
               @focus="$emit('focus', $event)">
        <template v-if="!innerMeta.group">
            <el-option v-for="item in innerOptions" :key="item.value" :label="item.key"
                       :value="item.value ? item.value : item">
                {{item.key}}
            </el-option>
        </template>
        <template v-else>
            <el-option-group
                v-for="group in innerOptions"
                :key="group.label"
                :label="group.label">
                <el-option v-for="item in group.options" :key="item.value" :label="item.key"
                           :value="item.value ? item.value : item">
                    {{item.key}}
                </el-option>
            </el-option-group>
        </template>
    </el-select>
</template>

<script>
    import {DEFAULT} from '@/constant'
    import Meta from '../mixins/meta'
    import Val from './value-mixins'

    export default {
        mixins: [Meta(DEFAULT.DropDownBox), Val],
        name: "DropDownBox",
        data() {
            return {
                innerOptions: []
            }
        },
        props: {
            value: {
                type: [Object, String]
            },
            options: Array,
        },
        methods: {
            getOptions: function () {
                // http request options data by innerMeta.data_url
                let url = this.innerMeta['data_url'];
                if (url) {
                    this.$axios.safeGet(url).then(resp => {
                        // if provide format callback fn, execute callback fn
                        let format = this.getBehavior('format');
                        this.innerOptions = format ? format(resp.data) : resp.data;
                        this.$emit('update:options', this.innerOptions);
                    }).catch(err => {
                        this.$message.error(err.msg);
                    });
                }
            },
            initOptions: function () {
                let options = this.options;
                if (options !== undefined) { // 父组件定义了options
                    this.innerOptions = options;
                    return;
                }
                if (this.innerMeta.hasOwnProperty('data_url')) {
                    this.getOptions();
                    return
                }
                console.error("options or data_url in meta provide one at least!")
            },
        },
        created() {
        },
        mounted() {
            // init option data
            this.initOptions()
        },
        computed: {}
    }
</script>

<style scoped>

</style>
