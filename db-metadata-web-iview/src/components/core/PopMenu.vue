<template>
    <el-popover :trigger="nativeTrigger" v-model="visible">
            <span slot="reference">
                <span @click.right="rightClickHander">
                    <slot name="label">
                        <i class="el-icon-caret-bottom" style="cursor: pointer"></i>
                    </slot>
                </span>
            </span>
        <slot name="menu">
            <ul id="menu">
                <li v-for="(item, index) in data" :key="index" @click="item.behavior(item.data)">{{item.data}}</li>
            </ul>
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
            data: {
                type: Array,
                validator: value => {
                    return value.filter(item => !(item instanceof Object)
                        || !item.hasOwnProperty('data')
                        || !item.hasOwnProperty('behavior')
                        || !(item.behavior instanceof Function)).length <= 0;
                }
            }
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
    ul#menu {
        list-style: none;
        margin: 0;
        padding: 5px;
    }

    ul#menu li {
        padding: 5px 0;
        cursor: pointer;
    }

    ul#menu li:hover {
        background-color: #eeeeee;
    }
</style>