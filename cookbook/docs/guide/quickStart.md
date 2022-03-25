# 快速集成

首先明确，集成db-meta, 需要你先有自己的应用程序, 并且你的应用应当满足以下条件:

- 后端基于`maven(3.x)` + `SpringBoot(2.x)`
- 前端基于`npm(或yarn)` + `Vue(2.x)` + `ElementUI(2.12.0+)`

自行创建一个空的SpringBoot工程 和 Vue工程。
> 标准的SpringBoot工程可以通过idea直接创建, 或者通过[官方入口](https://start.spring.io/)创建;
> 
> 标准的Vue工程可以通过[vue cli创建](https://cli.vuejs.org/zh/guide/creating-a-project.html);

## 后端集成
1. 在`pom.xml`中引入如下坐标:
```xml
<dependency>
    <groupId>com.github.md</groupId>
    <artifactId>db-metadata-server-springboot</artifactId>
    <version>2.1-SNAPSHOT</version>
</dependency>
```

2. 配置文件中添加如下配置(以yml为例):
```yaml
# db-meta支持多数据源, 但默认的spring数据库连接配置就是 dbmeta主库
spring:
  datasource:
    username: root
    password: 123456
    url: jdbc:mysql://127.0.0.1:3306/db-metadata-demo?useSSL=false&characterEncoding=utf-8
    driver-class-name: com.mysql.cj.jdbc.Driver

# db-metadata完整的默认配置。以下均为默认配置。若无需更改，则无需配置。
md:
  dev-mode: true # 开发模式。为true时系统将允许一些操作(比如清空元对象，重新全量生成等，非开发环境禁止设置为true, 默认为false)
  
  app: # 应用配置
    name: DB-Metadata低代码开发工具 # 系统名。会更改页面Header和登录页显示。
    logo: meta # 系统logo, 可支持三类值: 1. svg名(前端注册了); 2.element icon名; 3. url链接。 前端工程可通过基于内置的布局组件来编码指定 系统logo 
    registerable: false # 系统是否支持用户自行注册。开启则提供注册入口
    addable: true # 系统是否支持创建用户。为true，则表示用户管理模块可以添加用户(如果有权限的话)
    default_pass: 888888 # 用户默认(初始)密码， 当添加用户时、用户注册(未提供密码时)，将使用此默认密码。注意: 入库时会进行加密
    pass-encrypt-key: DB-Metadata is delicious # 用户密码加密密钥。无论什么方式，入库的明文密码都需要使用此密钥进行加密。加密方式为AES对称加密
    login-bg: # 登录页背景色(如blue、#0000FF)或背景图(以http/https开头的图片地址，会设置到url中)
    reset-pass: dbmeta # 重置口令, 默认值:dbmeta。系统针对内置模块提供了一套内置配置，当内置模块打乱了，或者升级时，可进行重置。
    # ROOT账号配置。如果md.server.login.login-key不是username, 比如是phone， 则必须为root配置phone属性。以便确认登录的是ROOT用户。注意: ROOT用户的id固定是"0"
    # 若登录时 md.server.login.login-key的值和此配置下同名key的值相等，那么优先进行ROOT认证, 密码匹配则视为ROOT, 返回此ROOT对象; 密码若不匹配再去用户数据库进行匹配。
    root:
      id: 0 # id为0是判断是用户是ROOT的唯一依据
      username: ROOT
      password: 888888

  server: # 服务端配置
    enable-certification: true # 是否启用认证，若关闭, 则不用登录，不用鉴权。完全裸奔。此项配置只有在 md.dev-mode为true时才能设为false
    login: # 认证配置
      token-key: X-TOKEN # 登录后存放在header里的token的键名key
      login-key: username # 登录表单账号的key
      pwd-key: password # 登录表单密码的key
      excludes: # 无需认证的接口url。配置的url，认证拦截器是不会拦截的
        - /app/config
        - /router
        - /user/login
      ctrl:
        enable: true # 是否启用内置登录控制器(接口)，若关闭则以下接口无效。需要自行提供登录接口！
        login-path: /user/login # 内置登录接口
        logout-path: /user/logout # 内置登出接口
        info-path: /user/info # 内置用户信息接口
    auth: # 鉴权配置
      excludes: # 无需鉴权的接口url
        - /app/config
        - /router
        - /user/login
    upload: # 上传配置
      mode: local # 上传模式, local代表本地上传(图片上传到服务端本地)。目前只支持本地上传，如需上传oss，可自行扩展
      base-upload-path: /opt/www/db-meta-serve # 当mode为local时，配置的本地上传根路径。这个路径应当是服务端的某个有权限的路径
    component:
      replace-from-json-file: false # 开启内置组件配置 启动时自动覆盖更新
    meta-object:
      replace-from-json-file: false #  开启内置元对象/元字段 启动时自动覆盖更新

  analysis:
    show-sql: true # 是否打印sql， 此项配置仅仅针对dbmeta内置数据库交互
#    db-source: # 多数据源配置， 若无多数据源，则不配置db-source
#      dbName1:
#        username: 
#        password: 
#        url: 
#        driver-class-name: 
#      dbName2:
#        username: 
#        password: 
#        url: 
#        driver-class-name: 
```

3. 启动类扫描配置
```java
@SpringBootApplication(scanBasePackages = {"your base package", "com.github.md.*"}) // 将此路径添加扫描: com.github.md.*
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

启动后端即可。
:::tip
如果没有错误，那么首次引入dbmeta时, 你会看到dbmeta自动初始化了数据库表结构以及必要的初始数据。
:::


## 前端集成
1. 安装前端包。执行如下node命令:
```shell
npm install db-metadata
或
yarn install db-metadata
```


2. 在main.js中进行vue安装。
```javascript
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css' // 导入element css
import DbMeta from 'db-metadata'
import 'db-metadata/lib/db-metadata.css' // 导入db-metadata css

Vue.use(ElementUI, {}); // ElementUI注册, 写在DbMeta注册之前

Vue.use(DbMeta, {
    // 必要的配置
    router: router, // 路由实例(必须), 创建你自己的VueRouter实例，并传入到这里

    // 以下的都是非必要配置
    // axios: {}, // 为dbmeta内置的axios实例 设置一些配置项，配置内容和axios官方一致
    // menus: [], // 编程菜单，这里传入你手动开发编写的菜单，菜单项通过order进行排序。
    // routerInterceptor: { // 路由守卫
    //     enable: true, // 开启内置的路由守卫。开启后，由dbmeta负责值守路由，并对路由鉴权。如果关闭，你需要自行维持路由鉴权，并维持用户状态。
    // },
    // layout: MyLayout, // 布局组件(可选，空则默认。若为默认，则编程路由需要自行使用dbmeta中导出的MetaLayout)
    // components: [], // 如果你的组件需要能够被动态路由配置时选中，那么需要传入此component中
    // restUrl: {}, // rest请求, 用于覆盖内部rest请求url. 基本无需配置。参考【内置接口地址】
    // routeUrl: {}, // 用于覆盖内置的路由地址。参考【内置路由列表】
    // theme: { // 默认的主题设置。如果需要覆盖什么，就基于以下模板进行修改（优先级低于用户自主配置的）
    //     layout: 'row', // row/column
    //     themeColor: 'light', // light/dark。下面颜色的属性会覆盖此light代表的属性配置
    //     header: {
    //         textColor: '#409EFF',
    //         backgroundColor: '#ffffff'
    //     },
    //     menu: {
    //         textColor: '#303133',
    //         activeTextColor: '#409EFF',
    //         backgroundColor: '#ffffff',
    //         uniqueOpened: false
    //     },
    //     tag: {
    //         show: true,
    //         textColor: '#ffffff',
    //         backgroundColor: '#409EFF'
    //         activeTextColor: '#ffffff',
    //         activeBackgroundColor: '#409EFF'
    //     }
    // }
});
```

启动即可。访问前端地址, 可以看到.

登录页:
![登录页](/page/img_7.png)

:::warning
ROOT用户账密默认为: ROOT/888888

注意账号必须大写。
:::

输入ROOT账密，即可到主页:
![首页](/page/img_8.png)


至此，dbmeta集成结束。

## 接下来
接下来, 借助两张表, 基于在上述Demo工程，图文并茂的形式来创建三个功能模块，分别是:
- 员工单表管理模块
- 部门树型表管理模块
- 部门员工树+表管理模块

两张表的表结构如下:
```sql
-- ----------------------------
-- Table structure for t_department
-- ----------------------------
DROP TABLE IF EXISTS `t_department`;
CREATE TABLE `t_department` (
  `id` varchar(32) COLLATE utf8_bin NOT NULL,
  `name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '部门名',
  `pid` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '父部门',
  `order` int(3) DEFAULT NULL COMMENT '排序',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '职责说明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='部门';

-- ----------------------------
-- Table structure for t_employee
-- ----------------------------
DROP TABLE IF EXISTS `t_employee`;
CREATE TABLE `t_employee` (
  `id` varchar(32) COLLATE utf8_bin NOT NULL,
  `name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '姓名',
  `age` int(3) DEFAULT NULL COMMENT '年龄',
  `sex` varchar(1) COLLATE utf8_bin DEFAULT NULL COMMENT '性别',
  `birth` date DEFAULT NULL COMMENT '出生日期',
  `photo` text COLLATE utf8_bin COMMENT '个人照片',
  `entry_time` datetime NOT NULL COMMENT '入职时间',
  `dep_id` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '所属部门',
  `jobs` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '岗位',
  `address` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '家庭住址',
  `enable` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='员工';
```

:::warning
注意: 数据库表的DDL是DbMeta的起点, 把DDL写的合理一些, 可以让DbMeta更"懂"你。比如, 以下几点约定：
1. 表名将默认作为元对象编码;
1. 字段的注释将默认作为元字段的中文名, 也会默认成为表单域的label、表格header上的中文显示;
1. 字段的顺序也将成为元字段的顺序, 也会成为表格中列的顺序、表单中表单域的顺序;
1. 默认值很重要, 通常会作为表单新增时的默认值;
1. 字段名称含有file时, 会自动将域组件设为FileBox;
1. 字段名称含有image、avatar或picture时, 会自动将域组件设为ImgBox;
1. 字段如果设为必填, 则表单域会自动添加必填的校验规则;
1. 合理的字段类型非常重要, 有利于DbMeta顺利推导实例配置。参考下述表格

|字段类型|控件|说明|
|---|---|---|
|bit(1)|BoolBox/DropDownBox|长度为1的bit会被视为布尔值, 在表单中使用BoolBox，在搜索面板中使用DropDownBox|
|varchar(1)|DropDownBox|一个字符长度, 将被视为下拉|
|varchar(255+)|TextAreaBox|varchar字段类型长度255时, 将被视为多行文本域|
|json|JsonBox|-|
|datetime、timestamp|DateTimeBox|-|
|date|DateBox|-|
|int、tinyint、bigint、decimal|NumBox|-|
|longtext|RichTextBox|大文本字段类型, 使用富文本|
:::
