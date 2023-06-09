# Change log..

版本号规范: `x.y.z` , 其中:

- x: 主版本号，重大更新时。不兼容更新
  - y: 子版本号，一些较大的功能升级。可能兼容，也可能不兼容。
- z: 修正版本号，可能是bug修复，或者小功能升级。一定兼容。

对于后端pom版本, 基线版本不保留修正版本号，对于前端npm包，则保留修正版本号。

> 阐述下这里的基线含义，例如2.3版本，则前后端一定是对应的。即后端2.3，前端2.3.x。则这个2.3就是一个基线版本。
>
> 如果2.3发布后存在bug, 则会在2.3.x上进行bug修复，多次修复并发布则多次自增x, 但是无论修正版本号加到多少。后端的2.3.x和前端的2.3.x一定是兼容的，互相玩得转的。

若选择某个版本的dbmeta时，例如2.3版本，则后端可以使用: `2.3`或`2.3.x`(若有后者说明存在bug修复)，也可以用`2.3-SNAPSHOT`（2.3还未发布，仍处于开发中的情况，注意，SNAPSHOT不保证可靠）；前端的话可以使用：`~2.3.0`。

> **以上规范从2.3开始践行**

## v2.4

* [X]  移除静态配置的ROOT用户，改为从动态user中进行鉴别，user是否为ROOT由业务系统去定义。默认情况下，用户名为ROOT的视为ROOT用户。
* [X]  移除asoco-common的所有依赖
* [X]  将公司内部的oss上传相关代码从dbmeta中移出，以spring-boot-starter的形式自动装配。
* [ ]  提供阿里云OSS上传实现，并内置。
* [X]  内置提供DefaultDbMetaConfigurer配置器，默认会自动装配spring容器中的文件服务类(上传/下载)
* [ ]  修复当树表中的pid设置为不在表格中显示时，列表无法呈现树结构的问题
* [ ]  将自定义接口响应的Ret改为java 接口定义，并支持业务系统扩展。
* [ ]  升级认证鉴权模块，使业务系统满足更多场景。如：1. 用户、角色、权限托管在第三方的场景；
* [x]  修复上传控件(FileBox和ImgBox)在seat模式下删除前面图片导致的前移问题。 
* [ ]  TableView中字段的render改为下拉选择Code Snippet, dbmeta建表支持单独维护。
* [ ]  将application.yml中关于`md.app`下的配置改为动态维护， 存储于表`meta_app_config`中。

## v2.3

略~
