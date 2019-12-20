<template>
    <el-popover :trigger="nativeTrigger" :placement="placement" v-model="visible" popper-class="no-padding">
        <span slot="reference">
            <span @click.right="rightClickHander">
                <slot name="label">
                    <i class="el-icon-caret-bottom" style="cursor: pointer"></i>
                </slot>
            </span>
        </span>
        <slot>
            <list>
                <template #body>
                    <slot name="body"></slot>
                </template>
            </list>
        </slot>
    </el-popover>
</template>

<script>
    export default {
        name: "PopMenu",
        props: {
            trigger: {
                type: String,
                default: 'click',
                validator: value => ['click', 'focus', 'hover', 'rightClick'].indexOf(value) > -1
            },
            placement: String
        },
        data() {
            return {
                visible: false
            }
        },
        methods: {
            rightClickHander(ev) {
                let self = this;
                if (self.trigger === 'rightClick') {
                    ev.preventDefault();
                    self.visible = true;
                    let div = document.createElement('div');
                    div.style = 'position: absolute; width: 100%; height: 100%; top: 0; left: 0; z-index: 1;';
                    document.body.appendChild(div);
                    div.addEventListener('click', function () {
                        self.visible = false;
                        document.body.removeChild(div);
                    })
                }
            }
        },
        computed: {
            nativeTrigger() {
                return this.trigger !== 'rightClick' ? this.trigger : 'manual';
            }
        }
    }
</script>

<style scoped>
</style>