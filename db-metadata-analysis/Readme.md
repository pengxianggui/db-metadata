# 元数据分析

> 主要完成 数据源 -> 元对象的过程;

## 项目依赖

- jfinal
- druid
- fastjson
- mysql-connector-java
- lombok

## 工程文件结构

```shell
.
├── AnalysisConfig.java
├── AnalysisManager.java
└── analysis
    ├── MetaAnalysisException.java
    ├── component                           # 前端组件骨架
    │   ├── Component.java
    │   ├── ComponentRender.java
    │   ├── ComponentType.java
    │   ├── ManualRender.java
    │   └── ViewContainer.java
    ├── db                                  #分析元数据库信息构建元对象
    │   ├── Column.java
    │   ├── DateTime.java
    │   ├── DbService.java
    │   ├── MetaDataTypeConvert.java
    │   ├── MetaObjectAssembly.java
    │   ├── MetaObjectMysqlAssembly.java
    │   ├── MysqlService.java
    │   ├── SnowFlake.java
    │   └── Table.java
    └── meta
        ├── BusinessService.java
        ├── ConfigExtension.java
        ├── DbMetaService.java
        ├── DbTypeJudge.java
        ├── IMetaField.java
        ├── IMetaObject.java
        ├── ManualMetaField.java
        ├── ManualMetaObject.java
        ├── MetaConfigFactory.java
        ├── MetaData.java
        ├── MetaFactory.java
        ├── MetaField.java
        ├── MetaFieldConfigParse.java
        ├── MetaJudge.java
        ├── MetaObject.java
        ├── MetaObjectConfigParse.java
        ├── MetaOperateException.java
        ├── MetaSqlKit.java
        ├── aop                               #预埋PointCut
        │   ├── AddPointCut.java
        │   ├── AopInvocation.java
        │   ├── DeletePointCut.java
        │   ├── IPointCut.java
        │   ├── PointCut.java
        │   ├── PointCutChain.java
        │   ├── PointCutFactory.java
        │   ├── QueryInvocation.java
        │   ├── QueryPointCut.java
        │   ├── UpdatePointCut.java
        │   └── ViewPointCut.java
        ├── ext
        │   └── ExtImportExport.java
        ├── fixed                               #硬编码元对象时的扩展
        └── validate
            └── Validator.java

8 directories, 48 files


```