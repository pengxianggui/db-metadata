import {isBoolean, isEmpty} from "../../../utils/common";
import utils from "../../../utils";
import {compile} from "../../../utils/url";
import {restUrl} from "../../../constant/url";

/**
 * 视图通用的混入
 * @type {{methods: {getInstanceConf(*=): *}}}
 */
export const ViewMixin = {
    methods: {
        // 是否显示。show可以为函数, 将data传入此函数, 返回值作为此方法返回值
        ifShow(show, data) {
            if (isBoolean(show)) {
                return show
            }
            const showCallbackFn = utils.strToFn(show);
            return showCallbackFn.call(this, data)
        }
    }
}


/**
 * 具备元数据配置的组件混入。此混入只适用于容器组件。容器组件的元数据是依据instanceCode，即ic来确定的。 而域组件是通过传入meta(参见{@link ../../../core/mixins/meta.js})
 *
 * <b>注意: 元数据装配好后会调用 组件的init方法，组件可以自定义init方法进行元数据就绪后的操作</b>
 *
 * @param defaultMeta 组件默认的元数据配置。由各组件中的ui-conf.js维护
 * @param callback 元数据配置好后的回调，一般用来处理装配好的元数据或依据元数据装配一些数据。
 * @returns {boolean|{methods: {getBehavior(*=): (*|boolean), init(): void}, created(): void, props: {meta: {default: (function(): *), type: ObjectConstructor}, ic: StringConstructor}}|*}
 */
export const ViewMetaBuilder = function (defaultMeta, callback) {
    return {
        props: {
            meta: {
              type: Object,
              default: () => {
                  return {}
              }
            },
            ic: { // 实例配置编码
                type: String,
                required: false
            }
        },
        watch: {
            "ic": {
                handler: function (newV) {
                    if (!isEmpty(newV)) {
                        const metaUrl = this.getMetaUrl();
                        this.$axios.safeGet(compile(metaUrl, {instanceCode: newV}))
                            .then(({data}) => {
                                this._refreshMeta(data)
                            }).catch(() => {
                            this._refreshMeta(defaultMeta) // 异常，则重新使用默认覆盖
                        })
                    }
                },
                immediate: true
            }
        },
        methods: {
            getMetaUrl() {
                return restUrl.VIEW_INSTANCE_CONF
            },
            getBehavior(name) {
                const behaviorKey = 'behavior';
                if (this.meta.hasOwnProperty(behaviorKey)
                    && this.meta[behaviorKey].hasOwnProperty(name)) {
                    return this.meta[behaviorKey][name];
                }
                return false;
            },
            _refreshMeta(meta) {
                this.$reverseMerge(this.meta, meta)
                if (utils.isFunction(callback)) {
                    callback.call(this, this.meta);
                }
                this.init()
            },
            init() {
            }
        },
        created() {
            this.$merge(this.meta, defaultMeta)
            this._refreshMeta(this.meta)
        }
    }
}

