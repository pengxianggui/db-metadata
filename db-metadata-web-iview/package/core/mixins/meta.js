import utils from '../../utils'

const behaviorKey = 'behavior';

/**
 * meta mixins
 * @returns {{computed: {innerMeta(): *}, methods: {executeBehavior(*, *=): (*|undefined)}, props: {meta: {default: (function(): {}), type: ObjectConstructor}}}|*}
 */
export default function (defaultMeta, assembleMetaFn) {
    return {
        props: {
            meta: {
                type: Object,
                default: function () {
                    return {}
                }
            },
        },
        methods: {
            getBehavior(name) {
                if (this.innerMeta.hasOwnProperty(behaviorKey)
                    && this.innerMeta[behaviorKey].hasOwnProperty(name)) {
                    return this.innerMeta[behaviorKey][name];
                }
                return false;
            }
        },
        computed: {
            innerMeta() {
                let mergedMeta = this.$merge(this.meta, utils.assertUndefined(defaultMeta, {}));
                if (utils.isFunction(assembleMetaFn)) {
                    return assembleMetaFn.call(this, mergedMeta);
                }
                return mergedMeta;
            }
        }
    }
}
