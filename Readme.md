# db-metadata

一个自研的低/零代码快速开发工具。

## 传送门

- [文档地址](https://doc-dbmeta.asoco.com.cn/): 参考文档
- [db-metadata集成DEMO代码](https://gitlab.asoco.com.cn/pengxianggui/db-metadata-demo): 一个快速集成的demo代码示例
- [db-metadata集成DEMO访问](http://dev-dbmeta.asoco.com.cn/#/login): 一个快速集成的demo的访问示例

## 介绍

> 对于快速开发框架和快速开发平台的定义,一个具备基本开发工具的整合后框架,同时具备一些基本的模块(RBAC权限,菜单,用户,字典等)的脚手架.
> 这些框架的初衷都是想让开发不必在重头搭建一套完备的系统后,才开始开发和自己业务相关的模块,尽可能把"公共"模块抽离,达到复用的目的;
>
> 但现状是一个大型项目,或者系统中通常有很多子系统或子模块组成,并不是每个子模块都要具有完整的用户权限、登录、菜单管理、字典等等功能。
> 如果是在人员有限且项目规模不大,而且又是从0->1这个阶段开始的话，选择任何一个开源的快速开发框架都是合适的，如果系统非0->1这个阶段已经上线且具备一些功能了，再引入快速开发框架的效果和成本就值得考虑了；

#### 社区的一些参考的快速开发平台

> 社区相对活跃,功能完备,技术栈新的

- [jeesite - https://jeesite.gitee.io/](https://jeesite.gitee.io/) - [演示地址](http://demo.jeesite.com/)
- [jeecg - http://www.jeecg.com/](http://www.jeecg.com/) - [演示地址](http://boot.jeecg.com/)
- [eova - http://www.eova.cn/](http://www.eova.cn/) - [演示地址](http://pro.eova.cn/)
  - 元对象的一些设计思路来源

### 技术栈

- SpringBoot
- JFinal ActiveRecord
- Mysql 5.7 / Druid
- Jdk1.8
- Guava
- FastJson
- Vue
- VueRouter
- ElementUI

### 能干什么

- 迅速构建一个CRUD模块
- 各类模板配置
  - 主子表
  - 单表
  - 树表
  - 树+表
- 图形样式设计
- 天然支持分布式应用
- 可当纯后端提供数据接口
- 可前后端一起使用
- springboot支持

## 快速开始

### 工程结构

```bash
├── db                                数据库文件
├── db-metadata-analysis-springboot   数据库分析组件
├── db-metadata-parent                父pom
├── db-metadata-server-springboot     引入dbMeta后端唯一需要依赖的模块
└── db-metadata-web                   dbMeta前端. 可直接运行, 也可以打包发布
```

### 分支说明

- master: 主分支，给予springboot 实现，标准的spring-boot-starter
- jf: 基于jfinal实现的，没有spring内容，版本较老，不再维护。

### 架构设计

#### 总体架构图

> 元对象,元子段,component等概念的层次结构,对应关系
> ![](db/images/架构图.png)

#### 通信图

> 前端请求渲染-> 后端数据装配->配置载入->merge

#### 数据库E-R图

![](db/images/e-r.png)

#### 元对象-类图

![元对象接口](db/images/MetaObject.png)

#### Component-类图

![元对象接口](db/images/component.png)

#### Query模块

![](db/images/IQueryCondition.png)

#### 扩展

> 为了能更好的融入其他系统，dbmeta对常见的模块做了抽象，用少量的接口保证足够的灵活性

##### 用户体系

> 用户体系单独拎出可以作为一个庞大的子系统来开发，在dbmeta种对用户做了一定抽象

> 核心接口 User(用户实体接口)，LoginService（登录服务），UserService（用户查询服务）,UserFactory(工厂)
> ![元对象接口](db/images/user.png)

##### 权限体系

> dbmeta并未实现RBAC之列的权限控制模块，而是留了扩展接口

> dbmeta中权限核心接口是MResource(资源)，MRPermit(资源判定器),MRLoader(资源加载器)、AuthenticationManager(权限中央管理器), 权限注册器(AuthenticationRegistry)
> ![元对象接口](db/images/auth.png)

### 核心数据结构

#### 各类配置

- 元对象 config
- 元子段 config
- 全局组件 config
- 组件实例 config
- 功能 config

> 配置的使用了全局版本号,参考svn,即任一config变更后,版本号递增?

#### 前端

```

```

## 技术债务

- 系统为了获得动态能力,同时为了快速上线第一个版,底层采用了json存储配置,导致了上层数据搬运时不得不大量使用Kv对象(Map),调用链过长时很难确认当前Kv对象内部的数据 为代码阅读和调试带来了成本和风险
  > 在数据结构稳定后,关键的配置使用具体的模型来存放
  >
- DB上配置信息存放在json字段,虽保证了灵活度,但是不利于检索,Mysql5.7对于Json字段的支持有限
- 元对象config,字段config,component.config,实例config,为了做到各层独立,分别设置了不同作用,不同意义的配置信息.在开始接触的时候对于这些
  配置信息需要消化的成本太高,而且config未做结构校验,很可能在功能大面积爆发时出现无用无效的脏字段

## RoadMap

- server 源代码方式集成,剥离db-metadata-server业务逻辑和容器有关的逻辑,目的为了上层使用其他mvc框架做支持;
- 数据权限的设计

  > 以元对象为基础,配合自定义的模板脚本片段,生成带有权限过滤内容的sql,以此来做数据权限
  >

  ```
      模板片段可以内置类似User,Group,Company等对象
      权限判断可能是
      if(user.role.is('组长'))
          return Query,Add,Update
      if(user.role.is('小弟'))
          return Query
      查看小组下的数据可能是
      if(user.role.is('组长')&&user.role.has(query))
          return create_by in GroupIds

  ```
- 丰富MResource的实现，增加不同纬度的验证（元对象纬度，模板纬度）,对Query模块的查询权限进行控制
- 丰富对数据库视图支持ViewMetaObject,增加元对象属性设定,参考linux文件系统drwxrwxrwx
- formbuilder 覆盖常用模板
- Jsondiff的支持
- springboot 深度集成(用spring完全接管datasource),充分支持spring方式创建router,controller,intercepter等jfinal组件
- 耗时操作的缓存支持(ehcache+redis)
- 对"功能"做版本控制,因功能渲染完全依靠数据配置,必须要保证发布后版本可控,目前元对象配置更新过以后,上游所有组件config会重新计算;
- 目前表单仅支持单个元对象,复杂业务场景可能有同时编辑多张表的需求
- 完善对spring容器的支持,目前还只是初步集成;

## 原则

- 算法+数据结构=程序
- 做一切有利于"快"的需求
- 解决一类问题,而不是一个问题
- 拒绝头疼医头,脚疼医脚
- 计算机科学领域的任何问题都可以通过增加一个间接的中间层来解决
  > Any problem  in computer science can be solved by anther layer of indirection.
  > https://cloud.tencent.com/developer/article/1491973
  >

## 约定

## 设计参考

- Eova ,jeesite,jeecgboot,普元
- Extjs,vue component



## ChangeLog

见[这里](./ChangeLog.md)
