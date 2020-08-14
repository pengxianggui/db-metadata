<template>
    <div class="img-box">
        <template v-if="innerMeta.hasOwnProperty('seats')">
            <upload-item class="upload-item" v-for="(seat, index) in seats" :key="seat" :seat="seat"
                         :meta="innerMeta" v-model="nativeValue[index]" :multiple="false"
                         @input="changeHandler" :show-file-list="true"></upload-item>
        </template>
        <template v-else>
            <!--            <upload-item :meta="innerMeta" v-model="value"></upload-item>-->
        </template>
    </div>
</template>
<script>
    import Meta from '../../mixins/meta'
    import conver from "./conver";
    import reverse from "./reverse";
    import DefaultMeta from '../ui-conf'
    import UploadItem from "./UploadItem"
    import Val from "../../mixins/value";

    export default {
        mixins: [Meta(DefaultMeta), Val(conver, reverse)], // value入参限定为Array，为对象类型，无需维护组件内外的双向绑定, 因此无需添加中间nativeValue
        components: {UploadItem},
        name: 'ImgBox',
        label: '图片上传框',
        props: {
            value: {
                type: [Array, String],
                default: () => []
            },
        },
        methods: {
            changeHandler() {
                this.$emit('input', reverse(this.nativeValue))
            }
        },
        computed: {
            seats() {
                let {innerMeta: {seats = []}} = this
                if (!Array.isArray(seats)) {
                    console.error('[MetaElement] seats参数必须为数组')
                    seats = []
                }
                return seats
            }
        }
    }
</script>
<style lang="scss" scoped>
    .img-box {
        display: flex;
        flex-direction: row;

        .upload-item {
            flex: 1;
            display: inline-block;
        }
    }
</style>
