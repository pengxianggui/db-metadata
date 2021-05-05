export const ConfDesc = `
    |配置项|说明|类型|可选值|默认值|
    |-----|---|----|----|-----|
    |name|name属性|string|-|-|
    |label|该实例标签名,表现为表单域的label|string|-|-|
    |inline|声明为内敛元素,当存在于FormView中时有效|boolean|true/false|false|
    |conf|配置对象|object|{type: default/cascader, level: 0/1/2}|type为展示形态, level为可选到哪一级别, 0为省, 1为市, 2为区县|
`;

export default {
    'component_name': 'RegionBox',
    'name': 'RegionBox',
    'label': '地区/省市框',
    'sort': 0,
    'inline': true,
    'conf': {
        'type': 'default',
        'level': 2
    }
}