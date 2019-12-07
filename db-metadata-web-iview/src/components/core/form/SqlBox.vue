<template>
    <div>
        <textarea ref="sqlEditor" type="textarea" :value="cacheValue"></textarea>
        <span v-if="error">
            <span class="tip">tip: {{tip}}</span>&nbsp;
            <el-tooltip content="此值将不会被保存" placement="right">
                <i class="el-icon-question"></i>
            </el-tooltip>
        </span>
    </div>
</template>

<script>
    import Meta from '../mixins/meta'
    import {DEFAULT, URL} from '@/constant'
    import CodeMirror from 'codemirror/lib/codemirror'
    import "codemirror/theme/ambiance.css"
    import "codemirror/lib/codemirror.css"
    import "codemirror/addon/hint/show-hint.css"
    import "codemirror/addon/edit/matchbrackets"
    import "codemirror/addon/selection/active-line"
    import "codemirror/mode/sql/sql"
    import "codemirror/addon/hint/show-hint"
    import "codemirror/addon/hint/sql-hint"

    export default {
        name: "SqlBox",
        mixins: [Meta(DEFAULT.SqlBox)],
        props: {
            value: String
        },
        data() {
            return {
                error: false,
                tip: '',
                cacheValue: this.value
            }
        },
        methods: {
            checkSql(value) {
                this.error = true;
                this.$axios.get(this.$compile(URL.CHECK_SQL, {sql: value})).then(resp => {
                    if (resp.state === 'ok') {
                        this.error = false;
                        this.nativeValue = value;
                    } else {
                        this.nativeValue = null;
                    }
                }).catch(err => {
                    this.tip = err.msg;
                    this.nativeValue = null;
                })
            }
        },
        mounted() {
            let self = this;
            let mime = self.innerMeta['mode'];
            let theme = self.innerMeta['theme'];
            let editor = CodeMirror.fromTextArea(this.$refs.sqlEditor, {
                mode: mime,
                indentWithTabs: true,
                smartIndent: true,
                lineNumbers: true,
                matchBrackets: true,
                theme: theme,
                extraKeys: {'Ctrl': 'autocomplete'}, //自定义快捷键
            });

            editor.on('change', function (instance) {
                let newVal = instance.getValue();
                self.cacheValue = newVal;

                if (!self.innerMeta['check']) { // needn't be checked
                    self.nativeValue = newVal;
                    return;
                }

                // need to checked
                if (newVal.endsWith(';')) {
                    self.checkSql(newVal);
                } else {
                    self.error = true;
                    self.nativeValue = null;
                    self.tip = 'SQL语句必须以英文分号结尾.'
                }
            });
            editor.setSize('auto', '80px');
        },
        computed: {
            nativeValue: {
                get: function () {
                    return this.value;
                },
                set: function (n) {
                    if (n === '') n = null;
                    return this.$emit("input", n); // 通过 input 事件更新 model
                }
            },
        }
    }
</script>

<style scoped>
    .tip {
        font-size: 12px;
        color: #999999;
    }
</style>