/**
 * 获取TableList的meta, 参数objectCode.
 * **组件实例中TableList的meta的命名必须为 tlMeta**
 * @type {{methods: {getTlMeta(*=): void}}}
 */
export const getTlMeta = {

    methods: {
        getTlMeta(objectCode) {
            let url = this.$compile("/table/meta/{objectCode}", {
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
            let url = this.$compile("/component/meta?componentCode=SearchPanel&objectCode={objectCode}", {
                objectCode: objectCode
            });
            return this.$axios.get(url);
        }
    }
};