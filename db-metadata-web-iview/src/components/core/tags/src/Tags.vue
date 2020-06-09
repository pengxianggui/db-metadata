<template>
    <div class="input tags-wrap">
        <div class="tags" v-for="(item, $index) in nativeValue" :key="item[primaryKey]">
            <span class="content">{{item[innerMeta.label]}}</span>
            <span class="del" @click="del($index, $event)">&times;</span>
        </div>
        <input v-if="!hiddenInput" class="tags-input" type="label" placeholder="标签，按 enter 创建" v-model="text"
               @keyup.enter="add(text)"
               @keydown.delete="del(nativeValue.length - 1)">
    </div>
</template>

<script>
    import utils from '@/utils'
    import Val from '../../mixins/value'
    import {defaultPrimaryKey} from '@/config'
    import Meta from '../../mixins/meta'
    import DefaultMeta from '../ui-conf'

    export default {
        name: "Tags",
        mixins: [Meta(DefaultMeta), Val()],
        props: {
            value: [String, Array],
            hiddenInput: {
                type: Boolean
            }
        },
        data() {
            return {
                text: '',
            }
        },
        methods: {
            add(text) {
                if (text != '') {
                    this.nativeValue.push({
                        label: text
                    });
                    this.label = ''
                }
            },
            del(index, ev) {
                if (ev) {
                    ev.stopPropagation();
                }
                this.nativeValue.splice(index, 1);
            }
        },
        computed: {
            primaryKey() {
                const {props: {id: objectPrimaryKey}} = this.innerMeta;
                const defaultPrimaryKey = defaultPrimaryKey;
                let primaryKey = utils.assertUndefined(objectPrimaryKey, defaultPrimaryKey);
                return primaryKey.split(',');
            }
        }
    }
</script>

<style lang="scss" scoped>
    //输入框tags
    .tags-wrap {
        width: 100%;
        height: 100%;
        margin: 5px;
        outline: none;

        &::after {
            content: "";
            display: block;
            height: 0;
            clear: both;
        }
    }

    .tags, .tags-input {
        position: relative;
        float: left;
        line-height: 28px;
        margin: 0 4px 4px 0;
        padding: 0 22px 0 10px;
        border-radius: 6px;
        background-color: #dddddd;

        .content {
            line-height: 28px;
        }

        .del {
            width: 22px;
            height: 28px;
            text-align: center;
            cursor: pointer;
            position: absolute;
            top: -1px;
            right: 0;
        }
    }

    .tags-input {
        font-size: 14px;
        padding: 0;
        background-color: inherit;
        border: none;
        color: inherit;
        width: 10em;
    }
</style>
