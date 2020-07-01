// CodeBox支持的语法
// 语言支持
// javascript   mode: text/javascript
import "codemirror/mode/javascript/javascript"
import "codemirror/addon/hint/javascript-hint.js"
// markdown mode: text/markdown
import "codemirror/mode/markdown/markdown"
// css  mode: text/css
import "codemirror/mode/css/css"
import "codemirror/addon/hint/css-hint"
// html mode: text/html
import "codemirror/mode/htmlembedded/htmlembedded"
import "codemirror/addon/hint/html-hint"
// java mode: text/x-java; c mode: text/x-csrc; c++ mode: text/x-c++src
import "codemirror/mode/clike/clike"
// python mode: text/x-cpython
import "codemirror/mode/python/python"
// shell mode: text/x-sh
import "codemirror/mode/shell/shell"
// sql mode: text/x-sql
import "codemirror/mode/sql/sql"
import "codemirror/addon/hint/sql-hint"
// go mode: text/x-go
import "codemirror/mode/go/go"
// vue mode: text/x-vue
import "codemirror/mode/vue/vue"
// properties mode: text/x-properties
import "codemirror/mode/properties/properties"


export default {
    "javascript": "text/javascript",
    "html": "text/html",
    "css": "text/css",
    "vue": "text/x-vue",
    "shell": "text/x-sh",
    "sql": "text/x-sql",
    "markdown": "text/markdown",
    "python": "text/x-cpython",
    "java": "text/x-java",
    "c": "text/x-csrc",
    "c++": "text/x-c++src",
    "go": "text/x-go",
    "properties": "text/x-properties"
};