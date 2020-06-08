<template>
    <div>
        <textarea ref="sqlEditor" type="textarea" :value="cacheValue"></textarea>
        <span v-if="tip.state !== null">
            <span :class="tip.state === 'fail' ? 'error-tip': 'success-tip'">tip: {{tip.msg}}</span>&nbsp;
        </span>
        <br>
        <template v-if="innerMeta['check']">
            <el-button circle icon="el-icon-search" @click="checkSql(cacheValue)"></el-button>
            &nbsp;&nbsp;
            <el-tooltip content="必须执行校验操作并通过, 否则不予保存" placement="right">
                <i class="el-icon-question"></i>
            </el-tooltip>
        </template>
    </div>
</template>

<script>
    import {URL} from '@/constant'
    import DefaultMeta from '../ui-conf'
    import Meta from '../../mixins/meta'
    import Val from '../../mixins/value'
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
        label: "SQL编辑框",
        mixins: [Meta(DefaultMeta), Val()],
        props: {
            value: String
        },
        data() {
            return {
                tip: {
                    state: null, // fail or ok, otherwise null
                    msg: null
                },
                cacheValue: this.value
            }
        },
        methods: {
            cleanValue() {
                this.nativeValue = null;
            },
            setTip(state, msg) {
                this.tip['state'] = state;
                this.tip['msg'] = msg;
            },
            checkSql(value) {
                this.$axios.get(this.$compile(URL.CHECK_SQL, {sql: value})).then(resp => {
                    if (resp.state === 'ok') {
                        this.setTip(resp.state, resp.msg);
                        this.nativeValue = value;
                    } else {
                        this.setTip(resp.state, resp.msg)
                        // this.nativeValue = null;
                    }
                }).catch(err => {
                    this.setTip('fail', err.msg);
                    // this.nativeValue = null;
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
                if (newVal === null || newVal.trim() === '') {
                    self.cleanValue();
                }

                if (!self.innerMeta['check']) { // needn't be checked
                    self.nativeValue = newVal;
                } else {
                    if (self.tip['state'] === 'ok') self.setTip(null, null);
                }
            });
            editor.setSize('auto', '80px');
        }
    }
</script>

<style scoped>
    .error-tip {
        font-size: 12px;
        color: #f56c6c;
    }

    .success-tip {
        font-size: 12px;
        color: #3CB371;
    }
</style>
