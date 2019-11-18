<template>
    <el-dialog :visible.sync="nativeVisible" v-bind="innerMeta.conf">
        <slot name="body">
            <component :is="componentMeta.component_name" v-if="componentMeta.component_name"
                       @ok="ok" @cancel="cancel" v-model="model" :meta="componentMeta"></component>
        </slot>
        <!-- 考虑到子组件(如FormTmpl)中可以自定义按钮, 为方便起见，弹框的按钮由子组件替代 -->
<!--        <slot name="footer" class="dialog-footer">-->
<!--            <el-button @click="cancel">取 消</el-button>-->
<!--            <el-button type="primary" @click="ok">确 定</el-button>-->
<!--        </slot>-->
    </el-dialog>
</template>

<script>
    import {DEFAULT} from '@/constant'
    import Meta from './mixins/meta'

    export default {
        mixins: [Meta(DEFAULT.DialogBox)],
        props: {
            visible: Boolean,
            componentMeta: Object,
            initModel: Object
        },
        name: "DialogBox",
        label: "弹出框",
        data() {
            return {
                model: this.initModel,
            };
        },
        methods: {
            ok(params) {
                this.nativeVisible = false;
                this.$emit('ok', params);
            },
            cancel(params) {
                this.nativeVisible = false;
                this.$emit('cancel', params);
            }
        },
        computed: {
            nativeVisible: {
                get: function () {
                    return this.visible;
                },
                set: function (newVal) {
                    this.$emit('update:visible', newVal)
                }
            }
        }
    };
</script>