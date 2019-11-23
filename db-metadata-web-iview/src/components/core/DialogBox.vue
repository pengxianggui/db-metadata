<template>
    <el-dialog :visible.sync="nativeVisible" v-bind="innerMeta.conf" >
        <template #default>
            <slot>
                <template v-if="componentMeta !== undefined">
                    <component :is="componentMeta.component_name" v-if="componentMeta.component_name"
                               @ok="ok" @cancel="cancel" :meta="componentMeta"></component>
                </template>
                <template v-else>
                    body
                </template>
            </slot>
        </template>
        <template #footer>
            <slot name="footer">
                <el-button class="btn btn-primary" @click="ok">确定</el-button>
                <el-button class="btn" @click="cancel">取消</el-button>
            </slot>
        </template>
    </el-dialog>
</template>

<script>
    import {DEFAULT} from '@/constant'
    import Meta from './mixins/meta'

    export default {
        mixins: [Meta(DEFAULT.DialogBox)],
        props: {
            visible: Boolean,
            componentMeta: Object
        },
        name: "DialogBox",
        label: "弹出框",
        data() {
            return {
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