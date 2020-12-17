<template>
  <div>
    <h1>组件开发调试页面</h1>
    <form-view :meta="meta" :model="model"></form-view>
  </div>
</template>

<script>
import UiConfEditor from "../../../package/meta/component/UiConfEditor";

export default {
  name: "WorkSpace",
  components: {UiConfEditor},
  data() {
    return {
      model: {
        username: 'pengxg',
        age: 26,
        content: `
            <table style="border-collapse: collapse; width: 100%;" border="1">
            <tbody>
            <tr>
            <td style="width: 25%;">jldfs &nbsp;dj&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;</td>
            <td style="width: 25%;">sdfsf</td>
            <td style="width: 25%;">sdfsf</td>
            <td style="width: 25%;">sdf</td>
            </tr>
            <tr>
            <td style="width: 25%;">2353yh</td>
            <td style="width: 25%;">er</td>
            <td style="width: 25%;">sdf</td>
            <td style="width: 25%;">sdf</td>
            </tr>
            </tbody>
            </table>
            <p>你好，</p>
            <p>&nbsp; &nbsp;我是yyy,</p>
            <p>&nbsp;</p>
        `,
        region: [{"code": "130000", "name": "河北省"}, {"code": "130300", "name": "秦皇岛市"}, {
          "code": "130304",
          "name": "北戴河区"
        }],
        avatar: [{url: 'https://www.baidu.com/img/flexible/logo/pc/result@2.png', name: '百度logo', seat: ''},
          {url: 'https://www.baidu.com/img/flexible/logo/pc/result@2.png', name: '百度logo', seat: ''}],
        attach: [{url: 'https://www.baidu.com/img/flexible/logo/pc/result@2.png', name: '百度logo', seat: ''},
          {url: 'https://www.baidu.com/img/flexible/logo/pc/result@2.png', name: '百度logo', seat: ''}],
        json: {name: 'pengxg', conf: {rules: [1, 2, 3]}},
        icon: 'el-icon-platform-eleme',
        sex: '1'
      },
      meta: {
        component_name: 'form-view',
        name: 'demoForm',
        label: '测试表单',
        action: '/demo/save',
        conf: {},
        form_type: 'VIEW',
        columns: [
          {
            name: 'username',
            label: '姓名',
            component_name: 'TextBox',
            conf: {},
            default_value: 'zhangsan'
          },
          {
            name: 'password',
            label: '密码',
            component_name: 'PassBox',
            default_value: '123456',
            conf: {}
          },
          {
            name: 'age',
            label: '年龄',
            component_name: 'NumBox',
            default_value: 23,
            conf: {}
          },
          {
            name: 'adult',
            label: '是否成年',
            component_name: 'BoolBox',
            default_value: '1',
            conf: {}
          },
          {
            name: 'content',
            label: '富文本',
            component_name: 'RichTextBox',
            conf: {}
          }, {
            name: 'region',
            label: '地区',
            component_name: 'RegionBox'
          }, {
            name: 'avatar',
            label: '头像',
            component_name: 'ImgBox'
          }, {
            name: 'attach',
            label: '附件',
            component_name: 'FileBox',
            seats: ['1', '2']
          }, {
            name: 'json',
            label: 'JSON',
            component_name: 'JsonBox'
          }, {
            name: 'icon',
            label: 'ICON',
            component_name: 'IconBox'
          }, {
            name: 'sex',
            label: '性别',
            component_name: 'DropDownBox'
          }
        ]
      }
    }
  },
  computed: {},
  methods: {},
  created() {
  }
}
</script>

<style scoped>
</style>
