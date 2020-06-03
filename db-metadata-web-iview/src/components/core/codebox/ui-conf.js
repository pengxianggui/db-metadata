export const ConfDesc = `
    |配置项|说明|类型|可选值|默认值|
    |-----|---|----|----|-----|
    |component_name|渲染组件名|string|组件列表|-|
    |name|name属性|string|-|-|
    |label|该实例标签名,表现为表单域的label|string|-|-|
    |conf|codemirror的原生配置项|object|-|-|
    |width|宽度(必须带上px单位)|string|-|-|
    |height|高度(必须带上px单位)|string|-|-|
    |show-theme-chose|是否显示主题选择按钮|boolean|true/false|false|
    |models|可供选择的编辑器模式|array|["darcula", "material", "ambiance", "elegant", "eclipse", "idea"]|-|
`;

export default {
    "component_name": "CodeBox",
    "name": "CodeBox",
    "label": "代码编辑框",
    "width": "auto",
    "height": "120px",
    "show-theme-chose": true,
    "show-mode-chose": true,
    "themes": ["darcula", "material", "ambiance", "elegant", "eclipse", "idea"],
    "modes": ["text/javascript", "text/html", "text/css", "text/x-vue", "text/markdown"],
    "conf": {   // codemirror config
        "theme": "darcula",
        "mode": "text/javascript",
        "lineNumbers": true,
        "indentWithTabs": true,
        "smartIndent": true,
        "matchBrackets": true,
        "extraKeys": {'Ctrl': 'autocomplete'}, //自定义快捷键
    }
}