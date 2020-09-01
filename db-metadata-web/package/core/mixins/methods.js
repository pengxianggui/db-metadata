import utils from '../../utils'

// /**
//  * 获取TableView的meta, 参数objectCode.
//  * @type {{methods: {getTlMeta(*=): void}}}
//  */
// export const getTlMeta = {
//     methods: {
//         getTlMeta(objectCode) {
//             let url = this.$compile(restUrl.COMPONENT_INSTANCE_META, {
//                 objectCode: objectCode,
//                 componentCode: 'TableView'
//             });
//             return this.$axios.safeGet(url);
//         }
//     }
// };

/**
 * 获取TableTreeList的meta, 参数objectCode.
 * @type {{methods: {getTableTlMeta(*=): *}}}
 */
// export const getTableTlMeta = {
//     methods: {
//         getTableTlMeta(objectCode) {
//             let url = this.$compile(restUrl.COMPONENT_INSTANCE_META, {
//                 objectCode: objectCode,
//                 componentCode: 'TableTreeView'
//             });
//             return this.$axios.safeGet(url)
//         }
//     }
// }

// export const getTreeMeta = {
//     methods: {
//         getTreeMeta(objectCode) {
//             let url = this.$compile(restUrl.COMPONENT_INSTANCE_META, {
//                 objectCode: objectCode,
//                 componentCode: 'Tree'
//             });
//             return this.$axios.safeGet(url);
//         }
//     }
// };

// export const getAddFormMeta = {
//     methods: {
//         getAddFormMeta(objectCode) {
//             let url = this.$compile(restUrl.RECORD_TO_ADD, {
//                 objectCode: objectCode,
//                 componentCode: 'FormView'
//             });
//             return this.$axios.safeGet(url);
//         }
//     }
// }
//
// export const getUpdateFormMeta = {
//     methods: {
//         getUpdateFormMeta(objectCode) {
//             let url = this.$compile(restUrl.RECORD_TO_UPDATE, {
//                 objectCode: objectCode,
//                 componentCode: 'FormView'
//             });
//             return this.$axios.safeGet(url);
//         }
//     }
// }

/**
 * 获取DataList的meta, 参数objectCode.
 * **组件实例中TableView的meta的命名必须为 dlMeta**
 * @type {{methods: {getDlMeta(*=): void}}}
 */
// export const getDlMeta = {
//     methods: {
//         getDlMeta(objectCode) {
//             let url = this.$compile(restUrl.COMPONENT_INSTANCE_META, {
//                 objectCode: objectCode,
//                 componentCode: 'DataList'
//             });
//             return this.$axios.safeGet(url);
//         }
//     }
// };

/**
 * 获取SearchPanel的meta, 参数objectCode.
 * **组件实例中SearchPanel的meta的命名必须为 spMeta**
 * @type {{methods: {getSpMeta(): void}}}
 */
// export const getSpMeta = {
//     methods: {
//         getSpMeta(objectCode) {
//             let url = this.$compile(restUrl.COMPONENT_INSTANCE_META, {
//                 componentCode: 'SearchView',
//                 objectCode: objectCode
//             });
//             return this.$axios.safeGet(url);
//         }
//     }
// };

/**
 * 针对RadioBox, CheckBox, DropDownBox这一类选项数据处理进行集中抽取
 * @type {{methods: {initOptions(): (undefined), getOptions(*=): void}, mounted(): void}}
 */
export const options = {
    props: {
        options: Array,
        dataUrl: String,
        format: Function
    },
    methods: {
        assemblyOptions(options) {
            if (!utils.isUndefined(this.format)) {
                return this.format(this.options);
            }
            if (options.every(ele => utils.isObject(ele) && ele.hasOwnProperty('key') && ele.hasOwnProperty('value'))) {
                return options;
            }
            if (!utils.isArray(options)) {
                console.error('options show be Array!');
                return [];
            }
            if (options.every(ele => utils.isString(ele)
                || utils.isNumber(ele)
                || utils.isBoolean(ele)
                || utils.isNull(ele))) {
                return utils.converKv1(options);
            }
            return options;
        },
        initOptions() {
            let options = this.options;
            if (options !== undefined) { // 父组件定义了options
                this.innerOptions = this.assemblyOptions(options);
                return;
            }

            if (this.dataUrl !== undefined) {   // 直接透传dataUrl
                this.getOptions(this.dataUrl);
                return;
            }

            options = this.meta['options'];
            if (utils.isArray(options) && options.length > 0) { // 组件元对象定义了options, 并且有值
                this.innerOptions = this.assemblyOptions(options);
                return;
            }

            if (this.meta.hasOwnProperty('data_url')) {
                this.getOptions();
                return
            }
            console.error("options or data_url in meta provide one at least!")
        },
        getOptions(url) {
            url = utils.assertUndefined(url, this.innerMeta['data_url']);
            if (url) {
                this.$axios.safeGet(url).then(resp => {
                    // if provide format callback fn, execute callback fn
                    let format = utils.assertUndefined(this.format, this.getBehavior('format'));
                    this.innerOptions = format ? format(resp.data) : this.assemblyOptions(resp.data);
                    this.$emit('update:options', this.innerOptions);
                }).catch(err => {
                    this.$message.error(err.msg);
                })
            }
        }
    },
    watch: {
        'options': function () {
            this.initOptions();
        },
        'dataUrl': function () {
            this.initOptions();
        },
        'innerMeta.options': function () {
            this.initOptions();
        },
        'innerMeta.data_url': function () {
            this.initOptions();
        },
    },
    mounted() {
        this.initOptions();
    }
};
