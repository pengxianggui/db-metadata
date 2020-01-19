<template>
    <div class="el-card">
        <!--        <el-form v-bind="$reverseMerge(innerMeta.conf, $attrs)" :model="value" size="mini">-->
        <!--            <template v-for="(item, index) in innerMeta.columns">-->
        <!--                    <el-form-item :key="item.name + index" :label="item.label||item.name" :prop="item.name"-->
        <!--                                  :class="{'inline': item.inline, 'width-align': item.inline}">-->
        <!--                        <component :is="item.component_name" v-model="value[item.name]" :meta="item"></component>-->
        <!--                    </el-form-item>-->
        <!--            </template>-->
        <!--        </el-form>-->
        <component :is="formType ? 'mini-form': 'json-box'" :meta="innerMeta" v-model="nativeValue"></component>
        <div style="display: flex;">
            <span style="flex: 1"></span>
            <el-button size="mini" icon="el-icon-guide" circle @click="changeType"></el-button>
        </div>
    </div>
</template>

<script>
    import Core from './core'
    import {DEFAULT} from "@/constant";
    import Meta from '../../mixins/meta'
    import Val from '../../mixins/value'
    import assembleMeta from './assembleMeta'

    export default {
        name: "MiniFormBox",
        mixins: [Meta(DEFAULT.FormView, assembleMeta), Val()],
        props: {
            value: {
                type: [Object, String],
                default: function () {
                    return {}
                }
            }
        },
        components: {
            "mini-form": Core
        },
        data() {
            return {
                formType: true
            }
        },
        methods: {
            changeType() {
                const {formType} = this;
                this.formType = !formType;
            }
        }

    }
</script>

<style scoped>
</style>