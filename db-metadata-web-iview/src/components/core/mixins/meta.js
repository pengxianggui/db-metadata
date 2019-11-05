const behaviorKey = 'behavior';

/**
 * meta mixins
 * @returns {{computed: {innerMeta(): *}, methods: {executeBehavior(*, *=): (*|undefined)}, props: {meta: {default: (function(): {}), type: ObjectConstructor}}}|*}
 */
export default function (defaultMeta) {
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
                if(this.innerMeta.hasOwnProperty(behaviorKey)
                    && this.innerMeta[behaviorKey].hasOwnProperty(name)) {
                    return this.innerMeta[behaviorKey][name];
                }
                return false;
            }
        },
        computed: {
            innerMeta() {
                return this.$merge(this.meta, defaultMeta);
            }
        }
    }
}