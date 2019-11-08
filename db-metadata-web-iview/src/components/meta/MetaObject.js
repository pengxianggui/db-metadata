import TableList from '@/components/core/TableList';

export default {
    name: "meta-object",
    mixins: [TableList],
    data() {
        return {
        }
    },
    methods: {
        getTableMeta() {
            const url = '/meta/objs';
            this.$axios.get(url).then(resp => {
                this.meta = resp.data;
                this.getData();
            }).catch(err => {
                console.error('[ERROR] url: %s, msg: %s', url, err.msg);
                this.$message.error(err.msg)
            });
        },
        // 删除单行
        handleDelete(index, row, ev) {
            const objectCode = row.code;
            if (ev) ev.stopPropagation();
            this.$confirm('确定删除?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                const url = '/meta/delete/' + objectCode
                this.$axios.delete(url).then(resp => {
                    this.$message.success(resp.msg);
                    this.getData();
                }).catch(err => {
                    this.$message.error(err.msg);
                });
            });
        },
    },
    mounted() {
        this.getTableMeta()
    },
}