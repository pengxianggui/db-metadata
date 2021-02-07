import utils from '../../utils'
/**
 * 针对RadioBox, CheckBox, DropDownBox这一类选项数据处理进行集中抽取
 * @type {{methods: {initOptions(): (undefined), getOptions(*=): void}, mounted(): void}}
 */
export default {
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
                }).catch(({msg = 'Error'}) => {
                    this.$message.error(msg);
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
