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
            this.$axios.get(url).then(resp => {
                this.tlMeta = resp.data;
            }).catch(err => {
                console.error('[ERROR] url: %s, msg: %s', url, err.msg);
                this.$message.error(err.msg);
            });
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
        getSpMeta() {
            let url = this.$compile("/component/meta?componentCode=SearchPanel&objectCode={objectCode}", {
                objectCode: this.objectCode
            });
            this.$axios.get(url).then(resp => {
                this.spMeta = resp.data;
            }).catch(err => {
                console.error('[ERROR] url: %s, msg: %s', url, err.msg);
                this.$message.error(err.msg);
            });
        }
    }
};