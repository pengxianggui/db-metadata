import utils from '../../utils'
import {isEmpty} from "../../utils/common";
import {compile} from "../../utils/url";
import {restUrl} from "../../constant/url";

const behaviorKey = 'behavior';

/**
 * 具备元数据配置的组件混入。
 *
 * <b>注意: 元数据装配好后会调用 组件的init方法，组件可以自定义init方法进行元数据就绪后的操作</b>
 *
 * @param defaultMeta 组件默认的元数据配置。由各组件中的ui-conf.js维护
 * @param assembleMetaFn 元数据配置好后的回调，一般用来处理装配好的元数据或依据元数据装配一些数据。
 * @returns {boolean|{methods: {getBehavior(*=): (*|boolean), init(): void}, created(): void, props: {meta: {default: (function(): *), type: ObjectConstructor}, ic: StringConstructor}}|*}
 */
export default function (defaultMeta, assembleMetaFn) {
    return {
        /**
         * meta、ic 和 metaUrl 三选一。meta为静态数据传入，后后两者为api动态数据。
         * <pre>
         * meta是开发时需要自定义进来的完整数据, 不支持异步响应。
         * ic是实例配置的编码
         * metaUrl是获取meta数据的完整有效url
         *
         * 三个至少需要使用一个。meta和ic，meta和metaUrl可同时配置, 后者异步获取的数据会向meta上覆盖式合并。
         * 而ic和metaUrl是排他性的，后者优先级更高
         * </pre>
         */
        props: {
            meta: { // 外部传入的实例配置，必须是同步数据，不支持响应
                type: Object,
                default: () => {
                    return {}
                }
            },
            ic: { // 实例配置编码
                type: String,
                required: false
            },
            metaUrl: { // 获取实例配置的url
                type: String,
                required: false
            }
        },
        watch: {
            'meta': {
                handler: function (newV) {
                    if (!isEmpty(newV)) {
                        this._refreshMeta(newV)
                    }
                }
            },
            "ic": {
                handler: function (newV) {
                    if (!isEmpty(newV)) {
                        this.$axios.safeGet(compile(restUrl.VIEW_INSTANCE_CONF, {instanceCode: newV}))
                            .then(({data}) => {
                                this._refreshMeta(data)
                            })
                    }
                },
                immediate: true
            }
        },
        methods: {
            getBehavior(name) {
                if (this.meta.hasOwnProperty(behaviorKey)
                    && this.meta[behaviorKey].hasOwnProperty(name)) {
                    return this.meta[behaviorKey][name];
                }
                return false;
            },
            _refreshMeta(meta) {
                this.$reverseMerge(this.meta, meta)
                if (utils.isFunction(assembleMetaFn)) {
                    assembleMetaFn.call(this, this.meta);
                }
                this.init()
            },
            init() {
                console.log(`${defaultMeta.component_name}, init...`)
            }
        },
        created() {
            this.$merge(this.meta, defaultMeta)
        }
    }
}
