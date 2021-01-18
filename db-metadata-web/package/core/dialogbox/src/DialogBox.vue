<template>
    <!-- v-if 确保了弹框每次打开, 弹框中的组件都会被重新创建. destroy-on-close 没有如愿销毁掉弹框中的组件内容, 因此采用 v-if -->
    <el-dialog :visible.sync="nativeVisible" v-bind="$reverseMerge(innerMeta.conf, $attrs)" :title="innerTitle"
               v-if="visible" :append-to-body="true">
        <template #default>
            <slot>
                <template v-if="componentMeta !== undefined">
                    <component :is="componentMeta.component_name" v-if="componentMeta.component_name"
                               @ok="ok" @cancel="cancel" :meta="componentMeta" style="width: 100%"></component>
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
    import utils from '../../../utils'
    import DefaultMeta from '../ui-conf'
    import Meta from '../../mixins/meta'

    export default {
        mixins: [Meta(DefaultMeta)],
        props: {
            visible: Boolean,
            title: String,
            componentMeta: Object
        },
        name: "DialogBox",
        label: "弹出框",
        data() {
            return {};
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
            },
            innerTitle() {
                return utils.assertUndefined(this.title, this.innerMeta['conf']['title']);
            }
        }
    };
</script>
