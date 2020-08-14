<!--
    容器型组件-路由指向入口
    ps: @/components/core/FormTmpl应该更名为FormBox
-->
<template>
    <form-view :ref="meta['name']" :meta="meta">
        <template #action="{model, conf}">
            <slot name="action" v-bind:model="model" v-bind:conf="conf"></slot>
        </template>
    </form-view>
</template>

<script>
    import utils from '../utils'
    import FormView from "../core/formview/src/FormView";
    import {getAddFormMeta, getUpdateFormMeta} from '../utils/rest'

    export default {
        name: "FormTmpl",
        props: {
            oc: String,
            type: {
                type: String,
                default: () => 'ADD',
                validator: (value) => ['ADD', 'UPDATE'].indexOf(value.toUpperCase()) > -1
            },
            pkvs: {
                // 当type为UPDATE时, 必须传入pkvs (当metaObject为单主键, 其值为主键值, 当为联合主键, 此值为pk1_pv1,pk2_pv2,..)
                type: String
            }
        },
        components: {
            FormView
        },
        data() {
            const {oc: R_oc} = this.$route.query
            const objectCode = utils.assertUndefined(this.oc, R_oc);
            return {
                objectCode: objectCode,
                meta: {}
            }
        },
        created() {
            const {objectCode, type, pkvs} = this;
            let promise;
            if (type.toUpperCase() === 'ADD') {
                promise = getAddFormMeta(objectCode)
            } else if (type.toUpperCase() === 'UPDATE') {
                promise = getUpdateFormMeta(objectCode, pkvs)
            }

            promise.then(resp => {
                const {data: meta} = resp
                this.meta = meta;
            })
        }
    }
</script>

<style scoped>

</style>
