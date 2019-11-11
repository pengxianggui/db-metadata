<template>
    <el-checkbox-group v-model="nativeValue">
        <el-checkbox v-for="item in innerOptions"
                     :key="item.key"
                     :label="item.value">
            {{item.key}}
        </el-checkbox>
    </el-checkbox-group>
</template>

<script>
    import {DEFAULT} from '@/constant'
    import Meta from '../mixins/meta'
    import Val from './value-mixins'

    export default {
        mixins: [Meta(DEFAULT.CheckBox), Val],
        name: "CheckBox",
        data () {
            return {
                innerOptions: {}
            }
        },
        props: {
            value: {
                type: [Array]
            },
            options: Array,
        },
        methods: {
            getOptions: function () {
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
                if (this.options !== undefined) { // 父组件定义了options
                    this.innerOptions = this.options;
                    return;
                }
                if (this.innerMeta.hasOwnProperty('data_url')) {
                    this.getOptions();
                    return
                }
                console.error("options or data_url in meta provide one at least!")
            },
        },
        watch: {
            'innerMeta.data_url': function () {
                this.getOptions();
            }
        },
        mounted() {
            this.initOptions();
        },
    }
</script>

<style scoped>

</style>
