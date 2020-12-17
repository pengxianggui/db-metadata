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
            <div data-v-5bbdcef1="" data-v-1188c409=""><p>&nbsp; &nbsp; &nbsp; &nbsp;</p>
<p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<img style="display: block; margin-left: auto; margin-right: auto;" src="http://www.hthjsj.com/dede58/img/1920x1080/34.jpg" alt="" width="900" height="506"></p>
<p style="text-align: left;">浙江航天恒嘉数据科技有限公司是为落实浙江省与中国航天科技集团战略合作内容，由嘉兴港区管委会下属国资公司与航天恒星科技有限公司联合创办的高新技术企业，是航天技术在智慧化工园区领域应用的一次全新探索。</p>
<p>&nbsp; &nbsp; &nbsp; &nbsp; 浙江航天恒嘉数据科技有限公司秉承“诚信、务实、专注、创新”的发展原则，依托母公司航天恒星科技有限公司在大系统工程项目管理，系统集成、云计算、大数据、物联网、卫星遥感和导航等领域的基础优势，着力聚焦2+1（基于物联网的大数据融合与处理技术基于化工数据处理模型的人工智能（AI）数据分析与挖掘技术；信息安全保护与防范技术）的核心技术，通过自主研发、资本运作、产业联盟等方式，整合完善智慧城市和行业信息化建设的产业链，打造数据生态云平台，全面推进基础数据采集、系统架构设计、核心产品研发和行业应用示范。公司开展以智慧化工园区为代表的智慧城市和行业信息化系统建设，形成智慧城市建设综合解决方案，打造方案可复制、模式可推广、运营可持续的智慧园区产品，成为全球化工领域一流的系统集成商、产品提供商、运维服务商。</p>
<p>&nbsp; &nbsp; &nbsp; &nbsp; 浙江航天恒嘉数据科技有限公司是为落实浙江省与中国航天科技集团战略合作内容，由嘉兴港区管委会下属国资公司与航天恒星科技有限公司联合创办的高新技术企业，是航天技术在智慧化工园区领域应用的一次全新探索。</p>
<p>&nbsp; &nbsp; &nbsp; &nbsp; 浙江航天恒嘉数据科技有限公司秉承“诚信、务实、专注、创新”的发展原则，依托母公司航天恒星科技有限公司在大系统工程项目管理，系统集成、云计算、大数据、物联网、卫星遥感和导航等领域的基础优势，着力聚焦2+1（基于物联网的大数据融合与处理技术基于化工数据处理模型的人工智能（AI）数据分析与挖掘技术；信息安全保护与防范技术）的核心技术，通过自主研发、资本运作、产业联盟等方式，整合完善智慧城市和行业信息化建设的产业链，打造数据生态云平台，全面推进基础数据采集、系统架构设计、核心产品研发和行业应用示范。公司开展以智慧化工园区为代表的智慧城市和行业信息化系统建设，形成智慧城市建设综合解决方案，打造方案可复制、模式可推广、运营可持续的智慧园区产品，成为全球化工领域一流的系统集成商、产品提供商、运维服务商。</p></div>
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
            inline: true,
            conf: {}
          },
          {
            name: 'adult',
            label: '是否成年',
            inline: true,
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
