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
            const {objectCode, type} = this;
            let metaFn;
            if (type.toUpperCase() === 'ADD') {
                metaFn = getAddFormMeta
            } else if (type.toUpperCase() === 'UPDATE') {
                metaFn = getUpdateFormMeta
            }
            metaFn(objectCode).then(resp => {
                const {data: meta} = resp
                this.meta = meta;
            })
        }
    }
</script>

<style scoped>

</style>
