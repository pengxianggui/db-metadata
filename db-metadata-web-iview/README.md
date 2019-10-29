# db-metadata-web-iview

## Project setup
```
yarn install
```

### Compiles and hot-reloads for development
```
yarn run serve
```

### Compiles and minifies for production
```
yarn run build
```

### Run your tests
```
yarn run test
```

### Lints and fixes files
```
yarn run lint
```


### 表单设计器

- [form-create](https://github.com/xaboy/form-create)
- [vue-form-making](http://tools.xiaoyaoji.cn/)

- [form-design](https://github.com/vincentzyc/form-design)

### 所有组件默认处理的响应数据格式为
```
{
    state: String,
    code: Number,
    msg: String,
    data: [Object, Array]
}
```


- [ ] 全局局部方法 约定;
    - data() 中变量的格式统一约定
       ```
       如组件公共属性:name,label 
       用来绑定form的数据对象使用model命名?
       data() {
            return {
                model: {},
                tableOptions: [],
                schemaOptions: []
            }
        }
       ```
- [ ] 异步加载数据的组件,指定默认值
- [ ] 表格中的批量编辑

##### 其他资料

- vue中动态增加method https://stackoverflow.com/questions/51797029/create-method-dynamically-in-vue-js



##### TODO
 -[x] meta-Manager 默认不加载
 -[x] vue watch 看上去时生效, 时而不生效(data_url延迟)
 -[ ] 组件中的变量初始值统一(null, {}等)
 -[ ] 组件中各个属性先后位置尽量统一
 -[ ] 不同组件中相同功能的属性命名尽量统一
 -[ ] const、let 分时适用
 -[ ] 对象属性调用方式, 应当形成规约: obj.a OR obj['a']
 -[ ] this泛用