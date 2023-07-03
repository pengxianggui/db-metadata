import utils from "../../utils";
import {convertToArray, deleteAllAttrs, isEmpty, isString} from "../../utils/common";
import Val from "./value";

/**
 * 判断是否为合法的文件上传后的json
 * @param file
 * @returns {boolean}
 */
function isLegalUploadFile(file) {
    return !isEmpty(file) && !isEmpty(file.url)
}

/**
 * 入参转义, 数组中无效元素将被剔除
 * @param value {url: string}[] | string , String将被当作url处理
 * @returns {{seat: *, name: *, url: *}[]}[] 返回值必定是数组
 */
function conver (value) {
    let arr = value;
    if (isString(value)) {
        arr = convertToArray(value)
    }

    if (Array.isArray(arr)) {
        // 过滤无效对象，并执行map处理避免太多无用字段
        return arr.filter(f => isLegalUploadFile(f))
            .map(f => deleteAllAttrs(f, ['name', 'url', 'seat']))
    } else {
        console.warn(`[MetaElement] 参数 输入错误, 只接受数组，或可转换为数组的字符串， 请查看${this.$options.name}的入参规则:` + JSON.stringify(value))
        this.$emit('input', [])
        return [];
    }
}

/**
 * 出参转义，数组中无效元素将被剔除
 * @param value
 * @returns {*|*[]} 返回值必定数组
 */
function reverse (value) {
    if (Array.isArray(value)) {
        // 过滤无效对象，并执行map处理避免太多无用字段
        return value.filter(f => isLegalUploadFile(f))
            .map(f => deleteAllAttrs(f, ['name', 'url', 'seat']))
    }

    console.error(`[MetaElement] ${this.$options.name}输出格式有误， 必须是数组`)
    return []
}

/**
 * 针对FileBox和ImgBox的混入提取
 */
export default {
    mixins: [Val(conver, reverse)],
    props: {
        value: {
            type: [Array, String],
            default: () => []
        },
    },
    methods: {
        changeHandler() {
            this.$emit('input', reverse(this.nativeValue))
        },
        handleBeforeUpload(file) {
            const fn = utils.assertEmpty(this.$attrs['before-upload'], (/*f*/) => {
                return true
            })
            fn.call(this, file)
        },
        // 获取指定seat名字的元素在nativeValue中的索引
        getIndexOfNativeValueBySeat(seat) {
            const index = this.nativeValue.findIndex(item => item.seat === seat)
            if (index === -1) { // 若此seat没有文件入座，则返回this.nativeValue的下一个索引，否则返回-1是无法双向绑定的
                return this.nativeValue.length
            }
            return index
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
        },
        isSeat() { // 是否是seat模式, 评判标准是seats数组是否有元素
            return this.seats.length > 0
        },
        conf() {
            const {innerMeta: {conf = {}}, $attrs, $reverseMerge} = this
            return $reverseMerge(conf, $attrs)
        }
    }
}
