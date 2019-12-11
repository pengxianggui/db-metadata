import utils from '@/utils'
import {URL} from '@/constant'

/**
 * 加载功能配置
 * @type {{methods: {loadFeature(*=): *}}}
 */
export const loadFeature = {
    methods: {
        loadFeature(featureCode) {
            let url = this.$compile(URL.FEATURE_LOAD, {
                featureCode: featureCode
            });
            return this.$axios.get(url);
        }
    }
}

/**
 * 获取TableList的meta, 参数objectCode.
 * **组件实例中TableList的meta的命名必须为 tlMeta**
 * @type {{methods: {getTlMeta(*=): void}}}
 */
export const getTlMeta = {

    methods: {
        getTlMeta(objectCode) {
            let url = this.$compile(URL.TABLE_INSTANCE_META, {
                objectCode: objectCode
            });
            return this.$axios.get(url);
        }
    }
};

/**
 * 获取SearchPanel的meta, 参数objectCode.
 * **组件实例中SearchPanel的meta的命名必须为 spMeta**
 * @type {{methods: {getSpMeta(): void}}}
 */
export const getSpMeta = {
    methods: {
        getSpMeta(objectCode) {
            let url = this.$compile(URL.COMPONENT_INSTANCE_META, {
                componentCode: 'SearchPanel',
                objectCode: objectCode
            });
            return this.$axios.get(url);
        }
    }
};

export const initOptions = {
    methods: {
        initOptions() {
            let options = this.options;
            if (options !== undefined) { // 父组件定义了options
                this.innerOptions = options;
                return;
            }

            options = this.meta['options'];
            if (utils.isArray(options) && options.length > 0) { // 组件元对象定义了options, 并且有值
                this.innerOptions = this.innerMeta['options'];
                return;
            }
            if (this.meta.hasOwnProperty('data_url')) {
                this.getOptions();
                return
            }
            console.error("options or data_url in meta provide one at least!")
        }
    }
};

export const getOptions = {
    methods: {
        getOptions(url) {
            url = utils.assertUndefined(url, this.innerMeta['data_url']);
            if (url) {
                this.$axios.safeGet(url).then(resp => {
                    // if provide format callback fn, execute callback fn
                    let format = this.getBehavior('format');
                    this.innerOptions = format ? format(resp.data) : resp.data;
                    this.$emit('update:options', this.innerOptions);
                }).catch(err => {
                    this.$message.error(err.msg);
                })
            }
        }
    }
};