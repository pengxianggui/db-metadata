// import ElementUI from 'element-ui'
// import 'element-ui/lib/theme-chalk/index.css';
// import DbMeta from 'db-metadata'
// import 'db-metadata/lib/db-metadata.css'

export default async ({Vue}) => {
    if (typeof process === 'undefined') {
        // TODO DbMeta中应当抽出基础组件库，防止其他路由等其他因素导致这里报错
        // Vue.use(ElementUI)
        // Vue.use(DbMeta)
    }
}
